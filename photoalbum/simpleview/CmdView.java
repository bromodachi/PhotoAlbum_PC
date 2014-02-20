package simpleview;

import control.IInteractiveControl;
import control.IPhotoControl;
import control.InteractiveControl;
import model.IPhotoAdminModel;
import model.IPhotoModel;

/**
 * @author Mark Labrador
 *
 */
public class CmdView {
	public static void main(String[] args) {
		String errMsg = "Please enter in a valid command.";
		if(args.length > 0) {
			String command = args[0];
			IPhotoAdminModel adminModel = null; //TODO Instantiate the administrator's model.
			switch(command) {
				case "listusers":
					break;
				case "adduser":
					break;
				case "deleteuser":
					break;
				case "login":
					String userId = args[1];
					IInteractiveControl view = new InteractiveControl(); //TODO Instantiate the view.
					IPhotoModel model = null; //TODO Instantiate the model.
					IPhotoControl control = null; //TODO Instantiate the control.
					control.run(model);
					break;
				default:
					System.out.println(errMsg);
					break;
			}
		} else {
			System.out.println(errMsg);
		}
	}
}
