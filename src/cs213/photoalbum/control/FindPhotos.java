package cs213.photoalbum.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs213.photoalbum.model.IPhoto;
import cs213.photoalbum.model.IPhotoModel;
import cs213.photoalbum.model.PhotoModel;
import cs213.photoalbum.model.TagType;

public abstract class FindPhotos {
	protected String tag;
	protected TagType tagType;
	protected IPhotoModel model;
	private List<IPhoto> photos;
	
	public FindPhotos(IPhotoModel model, String tag, TagType tagType) {
		this.model = model;
		this.tag = tag;
		this.tagType = tagType;
		this.photos = new ArrayList<IPhoto>();
	}
	
	protected void insert(IPhoto photo) {
		if(photos.size() == 0) {
			photos.add(photo);
		} else {
			int index = Collections.binarySearch(photos, photo.getFileName());
			IPhoto curr = photos.get(index);
			if(curr == null || !curr.getFileName().equals(photo.getFileName())) {
				photos.add(index, photo);
			}
		}
	}
	
	public List<IPhoto> find() {
		this.photos = new ArrayList<IPhoto>();
		performSearch();
		return photos;
	}
	
	protected abstract void performSearch();
}
