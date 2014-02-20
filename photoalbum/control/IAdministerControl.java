package control;


/**
 * @author Mark Labrador
 *
 */
public interface IAdministerControl extends IErrorControl{
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
	 */
	public void login(String id);
}
