package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Album implements IAlbum {
	private String albumId;
	private String albumName;
	private ArrayList<IPhoto> photoList;
	
	public Album(String albumId, String albumName) {
		this.albumId = albumId;
		this.albumName = albumName;
		this.photoList = new ArrayList<IPhoto>();
	}

	@Override
	public String getAlbumId() {
		return this.albumId;
	}

	@Override
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	@Override
	public String getAlbumName() {
		return this.albumName;
	}

	@Override
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	@Override
	public int getAlbumSize() {
		return this.photoList.size();
	}

	@Override
	public void addPhoto(IPhoto photo) {
		int index = Collections.binarySearch(this.photoList, photo.getPhotoID());
		if(!this.photoList.get(index).getPhotoID().equals(photo.getPhotoID()))	this.photoList.add(photo);
	}

	@Override
	public void deletePhoto(String id) {
		int index = Collections.binarySearch(this.photoList, id);
		if(this.photoList.get(index).getPhotoID().equals(id))	this.photoList.remove(index);
	}

	@Override
	public void recaptionPhoto(String id, String caption) {
		int index = Collections.binarySearch(this.photoList, id);
		if(this.photoList.get(index).getPhotoID().equals(id))	this.photoList.get(index).setCaption(caption);
	}

	@Override
	public int compareTo(String o) {
		return this.albumId.compareTo(o);
	}

	@Override
	public List<IPhoto> getPhotoList() {
		ArrayList<IPhoto> defensiveCopy = new ArrayList<IPhoto>();
		Collections.copy(defensiveCopy, this.photoList);
		return defensiveCopy;
	}
}
