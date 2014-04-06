package cs213.photoalbum.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import cs213.photoalbum.model.IPhotoAdminModel;
import cs213.photoalbum.model.PhotoAdminModel;
import cs213.photoalbum.view.AdminUI;
import cs213.photoalbum.view.AdminView;
import cs213.photoalbum.view.LoginView;
import cs213.photoalbum.view.LogMeIn;
import cs213.photoalbum.view.UserAlbum;

/**
 * @author Mark Labrador
 * 
 *         Entry-Point for the application.
 */
public class AppController implements IController {
	public static void main(String[] args) {
		LoginView view = new LogMeIn();
		IPhotoAdminModel model = new PhotoAdminModel();
		AppController app = new AppController(view, model);
		app.run();
	}

	private IPhotoAdminModel model;
	private LoginView view;
	private String adminUser = "admin";

	public AppController(LoginView view, IPhotoAdminModel model) {
		this.model = model;
		this.view = view;
		setup();
	}

	@Override
	public void run() {
		this.view.setVisible(true);
	}

	private void setup() {
		StateListener state = new StateListener();
		AcceptanceListener acceptState = new AcceptanceListener();
		this.view.registerUsernameDocument(state);
		this.view.registerPasswordDocument(state);
		this.view.registerLoginAction(acceptState);
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
		//TODO Must distinguish between a general and administrative user.
		if (username.equals(adminUser)) {
			AdminControl admin = new AdminControl(this.model, new AdminUI());
			this.view.setVisible(false);
			admin.run();
			this.view.setVisible(true);
		} else {
			InteractiveControl user = new InteractiveControl(username,
					this.model, new UserAlbum());
			this.view.setVisible(false);
			user.run(username);
			this.view.setVisible(true);
		}
	}

	public boolean verifyUser(String id) {
		return model.getUserIDs().contains(id);
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
				if(view.getUsername().equals(adminUser)) {
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
			if (model.userExists(view.getUsername()) || view.getUsername().equals(adminUser)) {
				login(view.getUsername(), new String(view.getPassword()));
			} else {
				view.showError("Username/password is invalid");
				view.disableLoginUI();
			}
		}
	}
}
