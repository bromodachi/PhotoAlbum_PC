package control;

import model.IPhotoModel;

/**
 * @author Mark Labrador
 *
 */
public interface IPhotoControl {
	/**
	 * runs the photo model of the userid. Will do the communication with the model and the view
	 * passed in the parameters
	 * @param UserId user's album we wish to run
	 * @param model sets the model passed in the class that implements this interface 
	 * @param view sets the view passed in the class that implements this interface.
	 * please correct me if I'm wrong
	 */
	public void run(String UserId, IPhotoModel model, IInteractiveControl view);
}