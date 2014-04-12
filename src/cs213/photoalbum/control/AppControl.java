package cs213.photoalbum.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import cs213.photoalbum.model.IPhotoModel;
import cs213.photoalbum.model.IUser;
import cs213.photoalbum.model.PhotoModel;
import cs213.photoalbum.view.AdminUI;
import cs213.photoalbum.view.AdminView;
import cs213.photoalbum.view.LoginView;
import cs213.photoalbum.view.LoginUI;
import cs213.photoalbum.view.AlbumCollectionUI;

/**
 * @author Mark Labrador
 *         <p>
 *         Entry-Point for the application.
 *         </p>
 */
public class AppControl implements IControl {
	public static void main(String[] args) {
		LoginView view = new LoginUI();
		IPhotoModel model = new PhotoModel();
		AppControl app = new AppControl(view, model);
		app.run();
	}

	private IPhotoModel model;
	private LoginView view;
	private AdminControl admin;
	private AdminView adminView;
	private String adminUser = "admin";

	public AppControl(LoginView view, IPhotoModel model) {
		this.model = model;
		this.view = view;

		setup();
	}

	@Override
	public void run() {
		this.view.setVisible(true);
	}

	private void setup() {
		this.adminView = new AdminUI();
		this.admin = new AdminControl(model, adminView);
		this.view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		LoginStateListener state = new LoginStateListener();
		AcceptanceListener acceptState = new AcceptanceListener();
		this.view.registerUsernameDocument(state);
		this.view.registerPasswordDocument(state);
		this.view.registerLoginAction(acceptState);
		this.view.addWindowListener(new ReturnToLogin());

		/*
		 * The user must have the option to login to the application again
		 * whether this be the administrator again or as a general user.
		 */
		this.adminView.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				view.setUsername("");
				view.setVisible(true);
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
		if (username.equals(adminUser)) {
			this.view.setVisible(false);
			admin.run();
		} else {
			IUser currUser = this.model.getUser(username);
			if (currUser != null && currUser.getPassword().equals(password)) {
				final AlbumCollectionUI userView = new AlbumCollectionUI();
				userView.registerFrameWindowListener(new ReturnToLogin());
				userView.registerLogoutAction(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						model.saveCurrentSession();
						userView.destroy();
						view.setUsername("");
						view.setPassword("");
						view.setVisible(true);
					}
				});
				InteractiveControl user = new InteractiveControl(username,
						this.model, userView);
				this.view.setVisible(false);
				user.run();
			} else {
				this.view
						.showError("<html><body><font size=\"2\" color=\"red\">Username/password is invalid.</font></body></html>");
				this.view.disableLoginUI();
			}
		}
	}

	public boolean verifyUser(String id) {
		return model.getUsernames().contains(id);
	}

	private class LoginStateListener implements DocumentListener {
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
			login(view.getUsername(), new String(view.getPassword()));
		}
	}
	
	private class ReturnToLogin extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			model.saveCurrentSession();
			view.setDefaultState();
			view.setVisible(true);
		}

		@Override
		public void windowClosed(WindowEvent e) {
			model.saveCurrentSession();
			view.setDefaultState();
			view.setVisible(true);
		}
	}
}
