package cs213.photoalbum.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cs213.photoalbum.model.IAlbum;
import cs213.photoalbum.model.IPhoto;
import cs213.photoalbum.model.IUser;
import cs213.photoalbum.model.PhotoModel;
import cs213.photoalbum.model.TagType;

public class FindPhotosByTag extends FindPhotos {
	public FindPhotosByTag(PhotoModel model, String tag, TagType tagType) {
		super(model, tag, tagType);
	}
	
	@Override
	protected void performSearch() {
		List<IUser> users = this.model.getCopyOfUsers();
		for(IUser u : users) {
			List<IAlbum> uAlbums = u.getAlbums();
			for(IAlbum a : uAlbums) {
				List<IPhoto> aPhotos = a.getPhotoList();
				for(IPhoto p : aPhotos) {
					switch(tagType) {
						case LOCATION:
							if(p.getLocationTag().equals(tag)) {
								insert(p);
							}
							break;
						case PEOPLE:
							List<String> people = p.getPeopleTags();
							if(people.contains(tag)) {
								insert(p);
							}
							break;
					}
				}
			}
		}
	}
	

}
 