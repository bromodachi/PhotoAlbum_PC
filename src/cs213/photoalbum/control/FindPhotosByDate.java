package cs213.photoalbum.control;

import java.util.Date;
import java.util.List;

import cs213.photoalbum.model.IAlbum;
import cs213.photoalbum.model.IPhoto;
import cs213.photoalbum.model.IPhotoModel;
import cs213.photoalbum.model.IUser;
import cs213.photoalbum.model.PhotoModel;
import cs213.photoalbum.model.TagType;

public class FindPhotosByDate extends FindPhotos {
	private Date start;
	private Date end;

	public FindPhotosByDate(Date start, Date end, IPhotoModel model, TagType tagType) {
		super(model, "", tagType);
		this.start = start;
		this.end = end;
	}

	@Override
	protected void performSearch() {
		List<IUser> users = this.model.getCopyOfUsers();
		for(IUser u : users) {
			List<IAlbum> uAlbums = u.getAlbums();
			for(IAlbum a : uAlbums) {
				List<IPhoto> aPhotos = a.getPhotoList();
				for(IPhoto p : aPhotos) {
					if(p.getDate().before(end) && p.getDate().after(start)) {
						insert(p);
					}
				}
			}
		}
	}

}
