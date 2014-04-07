package cs213.photoalbum.control;

import cs213.photoalbum.model.IPhotoModel;

/**
 * @author Conrado Uraga
 * 
 *         <p>
 *         The class that implements this is created first when the program
 *         runs. Holds the control for "administering" the user that is run in
 *         the command line. Implemented methods are read command line args and
 *         to create, delete, or list users on the admin mode. Creates an
 *         interactive controller when the user logs in.
 *         </p>
 */
public interface IAdminControl extends IControl {

	/**
	 * This method provides a hook to enter the program. This should be
	 * implemented such that triggering of the program can occur without having
	 * to recreate the control object.
	 * 
	 * @param args
	 *            Normally command-line arguments
	 */
	public void run(String[] args);

	/**
	 * @param model
	 *            Admin specific set of methods for interacting with the model.
	 */
	public void setAdminModel(IPhotoModel model);

	/**
	 * List the existing users in the current database
	 */
	public void listUsers();

	/**
	 * 
	 * Adds new user into the list
	 * 
	 * @param username
	 *            identifies the newly created user
	 * @param fullname
	 *            The new user full name. If user the name already exists, pass
	 *            an error to IErrorControl.
	 * @param password
	 *            The password to be associated with the user.
	 */
	public void addUser(String username, String fullname, String password);

	/**
	 * delete the user off the list by passing an id
	 * 
	 * @param username
	 *            is found, we remove the user from the list. If not, we pass
	 *            the error to IErrorControl.java
	 */
	public void deleteUser(String username);
}