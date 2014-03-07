package model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mark Labrador
 * <p>
 * A sub-model for the user by which a single user can be modified.
 * </p>
 */
public interface IUser extends Serializable, Comparable<String>{
	/**
	 * Gets the user's identifier.
	 * @return Identifier associated with user.
	 */
	public String getUserId();
	
	/**
	 * @return Returns the full name of the user.
	 */
	public String getFullName();
	
	/**
	 * Gets a list of albums.
	 * @return Collection of albums.
	 */
	public List<IAlbum> getAlbums();
	
	/*
	 * Operations
	 */
	/**
	 * Adds an album to the user's collection.
	 * @param album Album to be associated with this user's collection of albums.
	 */
	public void addAlbum(IAlbum album);
	
	/**
	 * Deletes an album from the user's collection.
	 * @param albumId Identifier of the album to be deleted from this user's collection of albums.
	 */
	public void deleteAlbum(String albumId);
}
