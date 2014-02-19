package model;

import java.io.Serializable;
import java.util.List;

public interface IUser extends Serializable{
	public String getUserId();
	public void setUserId(String id);
	public List<IAlbum> getAlbums();
	
	/*
	 * Operations
	 */
	public void addAlbum(IAlbum album);
	public void deleteAlbum(String id);
	public void renameAlbum(String name);
}
