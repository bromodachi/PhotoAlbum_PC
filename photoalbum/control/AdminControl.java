package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import simpleview.CmdView;
import model.IPhotoAdminModel;
import model.PhotoAdminModel;

/**
 * @author Conrado Uraga
 *
 */
public class AdminControl implements IAdministerControl {
	private IPhotoAdminModel model;
	private CmdView view;

	public AdminControl() {
		this.model = null; //TODO Set default admin model.
	}
	
	public AdminControl(IPhotoAdminModel model, CmdView view) {
		this.model = model;
		this.view=view;
	}
	
	@Override
	public void setErrorMessage(String msg) {
		// TODO Auto-generated method stub
		/*should be passing it through the view, I believe*/
		view.setMessage(msg);
	}

	@Override
	public void showError() {
		view.showMessage();

	}

	@Override
	public String[] readCommand() {
		String [] arg=null;
		try{
		BufferedReader input=new BufferedReader(new InputStreamReader(System.in));
		String output=input.readLine();
		arg=output.split(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		return arg;
		}catch (IOException e){
			return arg;
		}
	}
	
	@Override
	/* Original public void run(String[] args) {
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
			control.run(userId);
			break;
		default:
			String errMsg = "Error: <Please enter a valid command.>";
			setErrorMessage(errMsg);
			showError();
			break;
	}
	}*/
	public void run(String[] args) {
		
		/*BELOW iS JUST FOR SAKE OF EASE FOR ME WIL NOT BE INCLUDED IN FINAL*/
		String cmd ="";
		while(!cmd.equals("logout")) {
			String[] tokens= readCommand();
		cmd =tokens[0];
		switch(cmd) {
		case "listusers":
			this.listUsers();
			break;
		case "adduser":
			if(tokens.length == 3){	
				tokens[1]=tokens[1].replace("\"","");
				tokens[2]=tokens[2].replace("\"","");
				this.addUser(tokens[1], tokens[2]);}
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
			String userId = tokens[1];
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
	}

	@Override
	public void setAdminModel(IPhotoAdminModel model) {
		this.model = model;
	}

	@Override
	public void listUsers() {
		// TODO Auto-generated method stub
		String success="";
		for(int i=0; i<model.getUserIDs().size();i++){
			success=success+"<"+model.getUserIDs()+">\n";
		}
		setErrorMessage(success);
		showError();
		
		
	}

	@Override
	public void addUser(String id, String name) {
		if(verifyUser(id)){
			/**/
			String error="user <"+id+"> already exists with name <"+name+">";
			setErrorMessage(error);
			showError();
		}
		model.addUser(id, name);
		String msg="created user <"+id+"> with name <" +name+">";
		setErrorMessage(msg);
		showError();
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
		IPhotoAdminModel modelz =new PhotoAdminModel();
		InteractiveControl user=new InteractiveControl(id, modelz, this.view);
		user.run(id);
	}
	public boolean verifyUser(String id){
		return model.getUserIDs().contains(id);
	}
}
