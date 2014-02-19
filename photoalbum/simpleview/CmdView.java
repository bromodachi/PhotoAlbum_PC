package simpleview;

import control.IPhotoControl;
import model.IPhotoModel;

public class CmdView {
	public static void main(String[] args) {
		String errMsg = "Please enter in a valid command.";
		if(args.length > 0) {
			String command = args[0];
			IPhotoModel model = null; //TODO Instantiate the model.
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
