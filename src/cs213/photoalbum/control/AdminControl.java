package cs213.photoalbum.control;

import cs213.photoalbum.model.IPhotoAdminModel;
import cs213.photoalbum.view.AdminView;
import cs213.photoalbum.view.UserAlbum;

/**
 * @author Conrado Uraga
 * 
 */
public class AdminControl implements IAdminControl {
	private IPhotoAdminModel model;
	private AdminView view;

	public AdminControl(IPhotoAdminModel model, AdminView view) {
		this.model = model;
		this.view = view;
	}
	
	/**
	 * @author Mark Labrador
	 * <p>
	 * Startups the administrative program.
	 * </p>
	 */
	public void run() {
		model.loadPreviousSession();
		//TODO Show view.
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
				this.addUser(tokens[1], tokens[2]);
			} else {
				String error = "Error: Incorrect Format";
				setErrorMessage(error);
				showError();
			}
			break;
		case "deleteuser":
			if (tokens.length == 2)
				this.deleteUser(tokens[1]);
			else {
				String error = "Error: Incorrect Format";
				setErrorMessage(error);
				showError();
			}
			break;
		case "login":
			String userId = tokens[1];
			login(userId);
			break;
		case "logout":
			break;
		default:
			String errMsg = "Error: Please enter a valid command.";
			setErrorMessage(errMsg);
			showError();
			break;
		}
		this.model.saveCurrentSession();
	}

	@Override
	public void setAdminModel(IPhotoAdminModel model) {
		this.model = model;
	}

	@Override
	public void listUsers() {
		String success = "";
		for (int i = 0; i < model.getUserIDs().size(); i++) {
			success = success + model.getUserIDs().get(i) + "\n";
		}
		setErrorMessage(success);
		showError();

	}

	@Override
	public void addUser(String id, String name) {
		if (verifyUser(id)) {
			String error = "user " + id + " already exists with name " + name;
			setErrorMessage(error);
			showError();
			return;
		}
		this.model.addUser(id, name);
		String msg = "created user " + id + " with name " + name;
		setErrorMessage(msg);
		showError();
		this.listUsers();
	}

	@Override
	public void deleteUser(String id) {
		if (!verifyUser(id)) {
			String error = "user " + id + " does not exist";
			setErrorMessage(error);
			showError();
		}
		model.deleteUser(id);
		String msg = "deleted user " + id;
		setErrorMessage(msg);
		showError();
	}

	public boolean verifyUser(String id) {
		return model.getUserIDs().contains(id);
	}
}