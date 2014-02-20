package model;

import java.util.List;

/**
 * @author Mark Labrador
 * <p>
 * Used by administrator when initiated by the CmdView class at application startup
 * and user is not logged in.
 * </p>
 */
public interface IPhotoAdminModel {
	public void readUser(IUser user);
	public void writeUser(IUser user);
	public void deleteUser(String id);
	public List<IUser> getUsers();
	public void loadPreviousSession();
	public void saveCurrentSession();
}
