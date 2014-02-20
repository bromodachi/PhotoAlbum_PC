package model;

import java.util.List;

/**
 * @author Mark Labrador
 *<p>
 * This is meant to be used by an interactive controller.  It limits the controller to accessing
 * one user at a time, and can only change users when the user requests the change via knowledge
 * of the ID the user wants to access.
 * </p>
 */
public interface IPhotoModel {
	/**
	 * Retrieves the sub-model to operate on a single user.
	 * 
	 * @param userId Identification of the user.
	 * @return Sub-model for the user.
	 */
	public IUser getUser(String userId);
}
