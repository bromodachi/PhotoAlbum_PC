package model;

import java.util.List;

/**
 * @author Mark Labrador
 * This is to be used by the administrator that is first initiated by the CmdView class when the application is started
 * and the user has not logged in yet.
 */
public interface IPhotoAdminModel {
	public void readUser(IUser user);
	public void writeUser(IUser user);
	public void deleteUser(String id);
	public List<IUser> getUsers();
	public void loadPreviousSession();
	public void saveCurrentSession();
}
