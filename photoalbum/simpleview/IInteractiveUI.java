package simpleview;

import java.util.Date;

/**
 * @author Mark Labrador
 *
 */
public interface IInteractiveUI extends IErrorUI{
	public void createAlbum(String name);
	public void deleteAlbum(String id);
	public void listAlbums();
	public void listPhotos(String albumId);
	public void addPhoto(String albumId, String photoFileName, String photoCaption);
	public void movePhoto(String albumIdSrc, String albumIdDest, String photoId);
	public void removePhoto(String albumId, String photoId);
	public <V> void addTag(String photoId, String tagType, V tagValue);
	public void deleteTag(String photoId, String tagType);
	public void getPhotoInfo(String photoId);
	public void getPhotosByDate(Date start, Date end);
	public void getPhotosByTag(String tagType);
	public void logout();
}
