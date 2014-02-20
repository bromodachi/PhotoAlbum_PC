package model;

import java.util.List;

/**
 * @author Mark Labrador
 *
 * This is to be passed to the controller.  It was made specifically to separate the responsibilities of the administrator from that of the user.
 * In this model, the controller will only be able to retrieve the user, if it already knows the user by id.  This will be entered in by the user.
 */
public interface IPhotoModel {
	public IUser getUser(String id);
}
