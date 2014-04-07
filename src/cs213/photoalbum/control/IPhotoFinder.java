package cs213.photoalbum.control;

import java.util.List;

import cs213.photoalbum.model.IPhoto;

public interface IPhotoFinder {
	public List<IPhoto> find();
}
