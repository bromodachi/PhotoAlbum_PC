package control;

import java.util.Iterator;

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
		/*should be passing it through the view, I believe*/
		view.setError(msg);

	}

	@Override
	public void showError() {
		view.showError();

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
			else{
				String error="Error: <Incorrect Format>";
				setErrorMessage(error);
				showError();
			}				//	throw new IllegalArgumentException(); //TODO Place proper adduser error message.
			break;
		case "deleteuser":
			if(args.length >= 2)	this.deleteUser(args[1]);
			else{					
				String error="Error: <Incorrect Format>";
				setErrorMessage(error);
				showError();
				//throw new IllegalArgumentException();
				} //TODO Place proper deleteuser error message.
			break;
		case "login":
			String userId = args[1];
			login(userId);
			/*IPhotoModel model = null; //TODO Create the model by passing it through the constructor
			IInteractiveControl control = null; //TODO Instantiate the control.
			control.setInteractiveModel(model);
			control.run(userId);*/
			break;
		default:
			String errMsg = "Error: <Please enter a valid command.>";
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
		Iterator<String> iter=model.getUsers().iterator();
		String userNames="";
		userNames=userNames+"\n"+iter.next();
		
	}

	@Override
	public void addUser(String id, String name) {
		if(verifyUser(id)){
			/**/
			String error="user <"+id+"> already exists with name <"+name+">";
			setErrorMessage(error);
			showError();
		}
		model.getUsers().add(id);
		String msg="created user <"+id+"> with name <" +name+">";
		//where would I set this msg?
	}

	@Override
	public void deleteUser(String id) {
		if(!verifyUser(id)){
			/**/
			String error="user <"+id+"> does not exist";
			setErrorMessage(error);
			showError();
		}
		model.deleteUser(id);
		String msg="deleted user <"+id+">";
	}

	@Override
	public void login(String id) {
		if(!verifyUser(id)){
			/**/
			String error="user <"+id+"> does not exist";
			setErrorMessage(error);
			showError();
		}
		/*IPhoto is missing constructor*/
		IPhotoModel model =null;
		InteractiveControl user=new InteractiveControl(id, model);
		user.setInteractiveModel(model);
		user.run(id);
	}
	public boolean verifyUser(String id){
		return model.getUsers().contains(id);
	}
}
