package simpleview;

import control.AdminControl;
import control.IAdministerControl;
import model.IPhotoAdminModel;
import model.PhotoAdminModel;

/**
 * @author Mark Labrador
 * Main entry-point for the program.  
 */
public class CmdView {
	private String msg;
	public static void main(String[] args) {
		IPhotoAdminModel adminModel = new PhotoAdminModel(); //TODO Instantiate the administrator's model.
		CmdView test=new CmdView();
		IAdministerControl adminControl = new AdminControl(adminModel, test); //TODO Instantiate the administrator's control.
		adminControl.setAdminModel(adminModel);
		adminControl.run(args);
	}
	public void setMessage(String message){
		msg=message;
	}
	public void showMessage(){
		System.out.println(msg);
	}
}