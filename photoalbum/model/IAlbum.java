package model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mark Labrador
 * Primarily a collection of IPhoto objects.  This is a collection of 
 */
public interface IAlbum extends Serializable, List<IPhoto>{
	/*
	 * Required
	 */
	
	/**
	 * @return Identifier associated with the album.  This is normally a separate and unique
	 * identifier from the associated album name.
	 */
	public String getAlbumId();
	
	/**
	 * @param id Identifier to be associated with the album.  This is normally a separate and
	 * unique identifier from the filename associated with the photo.
	 */
	public void setAlbumId(String id);
	
	/*
	 * Getters and Setters
	 */
	/**
	 * @return Gets album's name.
	 */
	public String getAlbumName();
	/**
	 * @param name Name of album.
	 */
	public void setAlbumName(String name);
	/**
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
	 * @param photo New photo to be added.
	 */
	public void addPhoto(IPhoto photo);
	
	/**
	 * Deletes a photo in the album, if it exists.  Otherwise, lets user know it already exists 
	 * and does nothing.
	 * 
	 * @param id Identifier of photo to be deleted.
	 */
	public void deletePhoto(String id);
	
	/**
	 * Recaptions a photo in the album, if it exists.  Otherwise, lets user know it already exists
	 * and does nothing.
	 * 
	 * @param id Identifier of the photo to be recaptioned.
	 * @param caption New caption given by user.
	 */
	public void recaptionPhoto(String id, String caption);
}
