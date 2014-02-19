package control;

import simpleview.IInteractiveUI;
import model.IPhotoAdminModel;

public interface IPhotoControl {
	public void run(String UserId, IPhotoAdminModel model, IInteractiveUI view);
}
