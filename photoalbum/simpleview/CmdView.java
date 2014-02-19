package simpleview;

import control.IPhotoControl;
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
					IInteractiveUI view = null; //TODO Instantiate the view.
					IPhotoModel model = null; //TODO Instantiate the model.
					IPhotoControl control = null; //TODO Instantiate the control.
					control.run(userId, model, view);
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
