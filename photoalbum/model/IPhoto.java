package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Mark Labrador
 * <p>
 * A sub-model by which users can make changes to a single photo.
 * </p>
 */
public interface IPhoto extends Serializable, Comparable<String>{
	/*
	 * Required
	 */

	/**
	 * @return Identifier associated with the photo.  This is normally a separate and unique 
	 * identifier from filename associated with the photo.
	 */
	public String getPhotoID();
	
	/**
	 * @param id Identifier to be associated the photo.  This is normally a separate and 
	 * unique identifier from filename associated with the photo.
	 */
	public void setPhotoID(String id);
	
	/*
	 * Getters and Setters
	 */
	/**
	 * @return Gets photos file name.
	 */
	public String getFileName();
	
	/**
	 * @param fileName File name of photo; given by the user.
	 */
	public void setFileName(String fileName);
	
	/**
	 * @return Caption associated with the photo.
	 */
	public String getCaption();
	
	/**
	 * @param caption Caption to be associated with the photo.  This is given by the user.
	 */
	public void setCaption(String caption);
	
	/**
	 * @return Date associated with the photo.  This is generated by the application.
	 */
	public Date getDate();
	
	/**
	 * @param date Date to be associated with the photo.  This is generated by the application.  
	 * The milliseconds have been set to zero e.g. cal.set(Calendar.MILLISECOND,0);
	 */
	public void setDate(Date date);
	
	/*
	 * Tags
	 */
	/**
	 * @return The location tag.
	 */
	public String getLocationTag();
	
	/**
	 * @param Location The location to change it to.
	 */
	public void setLocationTag(String Location);
	
	/**
	 * @return List of people tags.
	 */
	public List<String> getPeopleTags();
	
	/**
	 * @param personName The name of a person you'd like to tag in the photo.
	 */
	public void personTag(String personName);
	
	/**
	 * @param personName The name of a person to remove the tag in the photo.
	 */
	public void removePersonTag(String personName);
}
