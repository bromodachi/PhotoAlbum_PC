package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Album implements IAlbum{
	private String albumName;
	private ArrayList<IPhoto> photoList;
	
	public Album(String albumName) {
		this.albumName = albumName;
		this.photoList = new ArrayList<IPhoto>();
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
		Collections.sort(this.photoList, new PhotoComparator());
		int index = Collections.binarySearch(this.photoList, photo.getPhotoID());
		if(index >= 0 && this.photoList.get(index).getPhotoID().equals(photo.getPhotoID()))	this.photoList.add(photo);
	}

	@Override
	public void deletePhoto(String id) {
		Collections.sort(this.photoList, new PhotoComparator());
		int index = Collections.binarySearch(this.photoList, id);
		if(this.photoList.get(index).getPhotoID().equals(id))	this.photoList.remove(index);
	}

	@Override
	public void recaptionPhoto(String id, String caption) {
		Collections.sort(this.photoList, new PhotoComparator());
		int index = Collections.binarySearch(this.photoList, id);
		if(index >= 0 && this.photoList.get(index).getPhotoID().equals(id))	this.photoList.get(index).setCaption(caption);
	}

	@Override
	public int compareTo(String o) {
		return this.albumName.compareTo(o);
	}

	@Override
	public List<IPhoto> getPhotoList() {
		ArrayList<IPhoto> defensiveCopy = new ArrayList<IPhoto>();
		defensiveCopy.addAll(this.photoList);
		return defensiveCopy;
	}
	
	private class PhotoComparator implements Comparator<IPhoto> {
		@Override
		public int compare(IPhoto o1, IPhoto o2) {
			return o1.getPhotoID().compareTo(o2.getPhotoID());
		}
	}
}
