package control;

import simpleview.IInteractiveUI;
import model.IPhotoModel;

/**
 * @author Mark Labrador
 *
 */
public interface IPhotoControl {
	public void run(String UserId, IPhotoModel model, IInteractiveUI view);
}
