package control;

import model.IPhotoAdminModel;
import model.IPhotoModel;

/**
 * @author Conrado Uraga
 *
 */
public class AdminControl implements IAdministerControl {
	private IPhotoAdminModel model;

	public AdminControl() {
		this.model = null; //TODO Set default admin model.
	}
	
	public AdminControl(IPhotoAdminModel model) {
		this.model = model;
	}
	
	@Override
	public void setErrorMessage(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showError() {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] readCommand() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void run(String[] args) {
		//TODO Add additional validation checking on the args: length of list per command.
		String cmd = args[0];
		switch(cmd) {
		case "listusers":
			this.listUsers();
			break;
		case "adduser":
			if(args.length >= 3)	this.addUser(args[1], args[2]);
			else					throw new IllegalArgumentException(); //TODO Place proper adduser error message.
			break;
		case "deleteuser":
			if(args.length >= 2)	this.deleteUser(args[1]);
			else					throw new IllegalArgumentException(); //TODO Place proper deleteuser error message.
			break;
		case "login":
			String userId = args[1];
			IPhotoModel model = null; //TODO Instantiate the model.
			IInteractiveControl control = null; //TODO Instantiate the control.
			control.setInteractiveModel(model);
			control.run(userId);
			break;
		default:
			String errMsg = "Please enter a valid command.";
			setErrorMessage(errMsg);
			showError();
			break;
	}
	}

	@Override
	public void setAdminModel(IPhotoAdminModel model) {
		this.model = model;
	}

	@Override
	public void listUsers() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addUser(String id, String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void login(String id) {
		// TODO Auto-generated method stub

	}
}
