package model;

import java.io.Serializable;

/**
 * @author Mark Labrador
 *
 */
public interface IAlbum extends Serializable{
	public String getAlbumId();
	public void setAlbumId(String id);
	
	public String getAlbumName();
	public void setAlbumName(String name);
	public int getAlbumSize();
	public void setAlbumSize(int size);
	
	public void addPhoto(IPhoto photo);
	public void deletePhoto(String id);
	public void recaptionPhoto(String id, String caption);
}
