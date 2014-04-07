package cs213.photoalbum.control;

import cs213.photoalbum.model.IPhotoModel;
import cs213.photoalbum.view.AdminView;

/**
 * @author Conrado Uraga
 * 
 */
public class AdminControl implements IAdminControl {
	private IPhotoModel model;
	private AdminView view;

	public AdminControl(IPhotoModel model, AdminView view) {
		this.model = model;
		this.view = view;
		setup();
	}
	
	private void setup() {
		this.view.setCurrUserImg(this.model.getDefaultUserImgPath());
		this.view.setUserList(this.model.getCopyOfUsers());
	}
	
	/**
	 * @author Mark Labrador
	 * <p>
	 * Startups the administrative program.
	 * </p>
	 */
	public void run() {
		model.loadPreviousSession();
		this.view.setVisible(true);
		model.saveCurrentSession();
	}

	public void run(String[] args) {
		model.loadPreviousSession();
		String cmd = "";
		String[] tokens = args;
		cmd = tokens[0];
		switch (cmd) {
		case "listusers":
			this.listUsers();
			break;
		case "adduser":
			if (tokens.length == 3) {
				tokens[1] = tokens[1].replace("\"", "");
				tokens[2] = tokens[2].replace("\"", "");
				this.addUser(tokens[1], tokens[2], null);
			} else {
				String error = "Error: Incorrect Format";
//				setErrorMessage(error);
//				showError();
			}
			break;
		case "deleteuser":
			if (tokens.length == 2)
				this.deleteUser(tokens[1]);
			else {
				String error = "Error: Incorrect Format";
//				setErrorMessage(error);
//				showError();
			}
			break;
		case "login":
			String userId = tokens[1];
//			login(userId);
			break;
		case "logout":
			break;
		default:
			String errMsg = "Error: Please enter a valid command.";
//			setErrorMessage(errMsg);
//			showError();
			break;
		}
		this.model.saveCurrentSession();
	}

	@Override
	public void setAdminModel(IPhotoModel model) {
		this.model = model;
	}

	@Override
	public void listUsers() {
		String success = "";
		for (int i = 0; i < model.getUsernames().size(); i++) {
			success = success + model.getUsernames().get(i) + "\n";
		}
//		setErrorMessage(success);
//		showError();

	}

	@Override
	public void addUser(String username, String fullname, String password) {
		if (verifyUser(username)) {
			String error = "user " + username + " already exists with name " + fullname;
//			setErrorMessage(error);
//			showError();
			return;
		}
		this.model.addUser(username, fullname, null);
		String msg = "created user " + username + " with name " + fullname;
//		setErrorMessage(msg);
//		showError();
		this.listUsers();
	}

	@Override
	public void deleteUser(String username) {
		if (!verifyUser(username)) {
			String error = "user " + username + " does not exist";
//			setErrorMessage(error);
//			showError();
		}
		model.deleteUser(username);
		String msg = "deleted user " + username;
//		setErrorMessage(msg);
//		showError();
	}

	public boolean verifyUser(String id) {
		return model.getUsernames().contains(id);
	}
}