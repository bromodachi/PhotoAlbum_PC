package control;

import model.IPhotoAdminModel;


/**
 * @author Conrado Uraga
 *
 *<p>
 * The class that implements this is created first when the program runs. 
 * Holds the control for "administering" the user that is run in the command line. 
 * Implemented methods are read command line args and to create, delete, or list users on the admin mode.
 * Creates an interactive controller when the user logs in.
 *</p>
 */
public interface IAdministerControl extends IErrorControl, IPhotoControl{
	
	/**
	 * This method provides a hook to enter the program.  This should be implemented such that
	 * triggering of the program can occur without having to recreate the control object.
	 * 
	 * @param args Normally command-line arguments
	 */
	public void run(String[] args);
	
	/**
	 * @param model Admin specific set of methods for interacting with the model.
	 */
	public void setAdminModel(IPhotoAdminModel model);
	/**
	 * List the existing users in the current database
	 */
	public void listUsers();
	/**
	 * 
	 * Adds new user into the list
	 * @param id identifies the newly created user 
	 * @param name is the new user full name
	 * if user the name already exists, pass an error to
	 * IErrorControl.java
	 */
	public void addUser(String id, String name);
	/**
	 * delete the user off the list by passing an id
	 * @param id is found, we remove the user from the list.
	 * If not, we pass the error to IErrorControl.java
	 */
	public void deleteUser(String id);
	/**
	 * Login allows an existing user to login into the photo album
	 * @param id passed the unique user id to gain access.
	 * If user id doesn't exist, we shall pass an error to 
	 * IErrorControl.java
	 * Once logged in, it goes into interactive mode.
	 */
	public void login(String id);
}
