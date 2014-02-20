package simpleview;

import control.IAdministerControl;
import model.IPhotoAdminModel;

/**
 * @author Mark Labrador
 * Main entry-point for the program.  
 */
public class CmdView {
	public static void main(String[] args) {
		IPhotoAdminModel adminModel = null; //TODO Instantiate the administrator's model.
		IAdministerControl adminControl = null; //TODO Instantiate the administrator's control.
		adminControl.setAdminModel(adminModel);
		adminControl.run(args);
	}
}
