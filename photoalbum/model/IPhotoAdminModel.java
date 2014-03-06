package model;

import java.util.List;

/**
 * @author Mark Labrador
 * <p>
 * Used by administrator when initiated by the CmdView class at application startup
 * and user is not logged in.  
 * </p>
 * 
 * <p>
 * IPhotoModel is the interactive counterpart to this interface.
 * It is separated to make a distinction between a model operating on
 * a single user.  This is because during interactive control operations,
 * the operations perform in the context of a single user; the administrator
 * does not.  Since the administrator model interface can access a list of users
 * (with getUsers), using this for the interactive model would mean
 * the interactive controller would have access to other users with requiring user
 * input.  This violates the user-by-user specification.
 * </p>
 */
public interface IPhotoAdminModel {
	public IUser addUser(String userId, String username);
	
	/**
	 * This reads users from storage into memory.  If the user does not
	 * exist, this should do nothing.
	 * @param userId Identifier of the desired user.
	 */
	public IUser readUser(String userId);
	
	/**
	 * This writes a user from memory into storage.  If the user does not
	 * exist, this should do nothing.
	 * @param userId Identifier of the desired user.
	 */
	public void writeUser(String userId);
	/**
	 * This deletes a user in storage.  If current user is in memory,
	 * this should delete the user from memory and from storage.  If the user
	 * does not exist, this should do nothing.
	 * @param userId Identifier of the desired user.
	 */
	public void deleteUser(String userId);
	/**
	 * @return Collection of user names.
	 */
	public List<String> getUserIDs();
	/**
	 * Loads the users from previous session.
	 */
	public void loadPreviousSession();
	/**
	 * Saves the users in the current session.
	 */
	public void saveCurrentSession();
}
