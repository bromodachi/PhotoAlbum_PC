package cs213.photoalbum.control;

import java.util.List;

import cs213.photoalbum.model.IPhoto;
import cs213.photoalbum.model.PhotoModel;

public class FindPhotosByTag implements IPhotoFinder {
	private String tag;
	private String tagType;
	private PhotoModel model;
	
	public FindPhotosByTag(PhotoModel model, String tag, String tagType) {
		this.model = model;
		this.tag = tag;
		this.tagType = tagType;
	}
	
	@Override
	public List<IPhoto> find() {
		// TODO Auto-generated method stub
		return null;
	}
}
