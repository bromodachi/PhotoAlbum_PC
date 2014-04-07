package cs213.photoalbum.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import cs213.photoalbum.model.IPhotoModel;
import cs213.photoalbum.model.PhotoModel;
import cs213.photoalbum.view.AdminUI;
import cs213.photoalbum.view.AdminView;
import cs213.photoalbum.view.LoginView;
import cs213.photoalbum.view.LoginUI;
import cs213.photoalbum.view.UserAlbumUI;

/**
 * @author Mark Labrador
 * 
 *         Entry-Point for the application.
 */
public class AppControl implements IControl {
	public static void main(String[] args) {
		LoginView view = new LoginUI();
		IPhotoModel model = new PhotoModel();
		model.loadPreviousSession();
//		model.addUser("test2", "tester3");
//		model.addUser("test3", "tester3");
//		model.saveCurrentSession();
		AppControl app = new AppControl(view, model);
		app.run();
	}

	private IPhotoModel model;
	private LoginView view;
	private AdminView adminView;
	private String adminUser = "admin";

	public AppControl(LoginView view, IPhotoModel model) {
		this.model = model;
		this.view = view;
		this.adminView = new AdminUI(this.model.getDefaultUserImgPath());
		setup();
	}

	@Override
	public void run() {
		this.view.setVisible(true);
	}

	private void setup() {
		this.view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		StateListener state = new StateListener();
		AcceptanceListener acceptState = new AcceptanceListener();
		this.view.registerUsernameDocument(state);
		this.view.registerPasswordDocument(state);
		this.view.registerLoginAction(acceptState);

		/*
		 * The user must have the option to login to the application again
		 * whether this be the administrator again or as a general user.
		 */
		this.adminView.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
				//Do nothing.
			}

			@Override
			public void windowClosing(WindowEvent e) {
				view.setVisible(true);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				//Do nothing.
			}

			@Override
			public void windowIconified(WindowEvent e) {
				//Do nothing.
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				//Do nothing.
			}

			@Override
			public void windowActivated(WindowEvent e) {
				//Do nothing.
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				//Do nothing.
			}

		});
	}

	/*
	 * Login allows an existing user to login into the photo album
	 * 
	 * @param username Name of the user. If username doesn't exist, show an
	 * error. Once logged in, it goes into the User's album.
	 * 
	 * @param password Password associated with the user.
	 */
	private void login(String username, String password) {
		this.view.setVisible(false);
		if (username.equals(adminUser)) {
			AdminControl admin = new AdminControl(this.model, adminView);
			this.view.setVisible(false);
			admin.run();
		} else {
			InteractiveControl user = new InteractiveControl(username,
					this.model, new UserAlbumUI());
			this.view.setVisible(false);
			user.run();
		}
	}

	public boolean verifyUser(String id) {
		return model.getUsernames().contains(id);
	}

	private class StateListener implements DocumentListener {
		@Override
		public void insertUpdate(DocumentEvent e) {
			stateChanger();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			stateChanger();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			stateChanger();
		}

		private void stateChanger() {
			/*
			 * @State 1 - Username is the only control enabled.
			 * 
			 * @State 2 - Username and password are the controls enabled.
			 * 
			 * @State 3 - Username, password, and login are the controls
			 * enabled.
			 */
			if (view.getUsername().isEmpty()) {
				view.disablePasswordUI();
				view.disableLoginUI();
			} else if (!view.getUsername().isEmpty()) {
				if (view.getUsername().equals(adminUser)) {
					view.enableLoginUI();
					view.disablePasswordUI();
				} else {
					view.enablePasswordUI();
					if (view.getPassword().length == 0) {
						view.disableLoginUI();
					} else if (view.getPassword().length > 0) {
						view.enableLoginUI();
					}
				}
			}
			view.hideError();
		}
	}

	private class AcceptanceListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			/*
			 * @State 4 - Acceptance. Trigger either the AdminView or UserAlbum
			 * view.
			 * 
			 * @State 5 - Username, password, and error are the controls
			 * enabled. Return control to user.
			 */
			if (model.userExists(view.getUsername())
					|| view.getUsername().equals(adminUser)) {
				login(view.getUsername(), new String(view.getPassword()));
			} else {
				view.showError("Username/password is invalid");
				view.disableLoginUI();
			}
		}
	}
}
