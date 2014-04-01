package cs213.photoalbum.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Mark Labrador
 * <p>
 * A sub-model for the collection of albums.
 * </p>
 */
public interface IAlbum extends Serializable, Comparable<String>{
	/*
	 * Required
	 */
	
	/*
	 * Getters and Setters
	 */
	/**
	 * Gets the album's name.
	 * @return Album name.
	 */
	public String getAlbumName();
	
	/**
	 * Sets the album's name.
	 * @param name Album name.
	 */
	public void setAlbumName(String name);
	
	/**
	 * Gets the number of photos contained in the album.
	 * @return Number of photos in album.
	 */
	public int getAlbumSize();
	
	/*
	 * Operations
	 */
	/**
	 * Adds a photo to the album, if it does not already exists.  Otherwise, lets user know it already
	 * exists and does nothing.
	 * 
	 * This should also keep track of the size of the album by incrementing a counter every time a
	 * photo is added.
	 * 
	 * @param photo Photo to be added.
	 */
	public void addPhoto(IPhoto photo);
	
	/**
	 * Deletes a photo in the album, if it exists.  Otherwise, lets user know it already exists 
	 * and does nothing.
	 * 
	 * This should also keep track of the size of the album by decrementing a counter every time a photo
	 * is removed.
	 * 
	 * @param id Photo identifier.
	 */
	public void deletePhoto(String id);
	
	/**
	 * This is a list of photos contained in the album.
	 * 
	 * @return A full list of photos contained in this album.
	 */
	public List<IPhoto> getPhotoList();
	
	/**
	 * Recaptions a photo in the album, if it exists.  Otherwise, lets user know it already exists
	 * and does nothing.
	 * 
	 * @param photoId Photo identifier.
	 * @param caption New caption.
	 */
	public void recaptionPhoto(String photoId, String caption);

	public void setIcon(Photo setMe);

	public void setPic(Photo setMe);
}