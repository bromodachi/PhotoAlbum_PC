package control;

import java.util.Date;

/**
 * @author Mark Labrador
 *
 */
public interface IInteractiveControl extends IErrorControl{
	/**
	 *note: all errors like illegal parameters, invalid dates should be in this format
	 *Error: <description of error>
	 */
	
	/**
	 * reads the command the user inputs in the commandLine
	 * command must be precise to the specification given to us
	 */
	public String[] readCommand();
	/**
	 * creates the album that is in the parameter. if the album
	 * already exists, we pass an error to IErrorControl.java.
	 * If not, we add it to the user's list
	 * @param name will be the name of the album to be added in the
	 * user's list as long as it doesn't conflict with an exiting name
	 */
	public void createAlbum(String name);
	/**
	 * deletes the album in the user's list
	 * @param id is the user unique identification that is linked to
	 * his list. If, for some reason, he/she passes an id that doesn't exist,
	 * we should print an error stating the user doesn't exist
	 * @param albumName is the name of the album we wish to delete from the user list
	 * if it doesn't exist, we should print an error
	 */
	public void deleteAlbum(String id, String albumName);
	/**
	 * lists the albums in the user's list
	 */
	public void listAlbums();
	/**
	 * list the photos in the user's album given its ID
	 * @param albumId is the name of the album and will print the pictures(if any) information
	 */
	public void listPhotos(String albumId);
	/**
	 * adds the photo given the album id, the photo file name, and the caption the user wishes to add
	 * @param albumId is what the user wishes to add the photo to. If the album doesn't exist,
	 *  print an error
	 * @param photoFileName the name of the photo. If it already exists, print an error. 
	 * @param photoCaption the caption the user wishes to add to the photo. 
	 */
	public void addPhoto(String albumId, String photoFileName, String photoCaption);
	/**
	 * moves an existing photo to another one.
	 * @param albumIdSrc the source where the photo currently lives. If the user inputs an invalid
	 * name of the source, we print an error
	 * @param albumIdDest the destination we wish to move the photo to. If the user passes an invalid
	 * name of the destination, we print an error
	 * @param photoId the add of the photo we will be moving. If the user passes an id that doesn't match
	 * the id in the source album.
	 */
	public void movePhoto(String albumIdSrc, String albumIdDest, String photoId);
	/**
	 * removes the photo in user's album
	 * @param albumId is the album the user wishes to remove the photo from. If the user
	 * inputs an invalid album id, we print an error
	 * @param photoId is the photo id we wish to remove. If the user inputs an invalid photoID
	 * we print an error.
	 * If both succeed, print an message that to tell the user successfully removed that photo
	 * in that album
	 */
	public void removePhoto(String albumId, String photoId);
	/**
	 * adds a tag to a current photo
	 * @param photoId the id the user wishes to tag. If the id doesn't exist,
	 * we print an error
	 * @param tagType the tag type the user wishes to add. If the tag type doesn't exist, another error
	 * to be printed
	 * @param tagValue the value that will be attached to the photo
	 * prints a successful message once we successfully tagged the photo
	 */
	public <V> void addTag(String photoId, String tagType, V tagValue);
	/**
	 * deletes the tag in the photo
	 * @param photoId is the photo id we wish to delete the tag off of. If it doesn't exist, error
	 * @param tagType is the tagtype we wish to delete. If it doesn't exist for that said photo, error.
	 */
	public void deleteTag(String photoId, String tagType);
	/**
	 * prints the photo information which includes, the file name, album, date, caption, and tags
	 * @param photoId the photo id we wish to print. If it doesn't exist, ask IEC to print error
	 */
	public void getPhotoInfo(String photoId);
	/**
	 * retrieves all photo from the specify start and end date
	 * @param start user much specify a valid date, if user passes anything else like a string, error
	 * @param end user much specify a valid date for the end, if user passes anything else like a string, error
	 * end paramater should not be before the start date.
	 */
	public void getPhotosByDate(Date start, Date end);
	/**
	 * retrieves all photos by the tag in chronological order. Tags can  be specified with or without
	 * their types
	 * @param tagType the tag the user which to specify. If not, tagtype can be empty
	 */
	public void getPhotosByTag(String tagType);
	/**
	 * No output for this method. Logs out the user.
	 */
	public void logout();
}
