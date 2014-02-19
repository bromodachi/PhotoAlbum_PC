package model;

import java.util.List;

public interface IPhotoModel {
	public void readUser(IUser user);
	public void writeUser(IUser user);
	public void deleteUser(String id);
	public List<IUser> getUsers();
	public void loadPreviousSession();
	public void saveCurrentSession();
}
