package cs213.photoalbum.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class User implements IUser {
	private static final long serialVersionUID = 1L;
	private String userId;
	private String fullName;
	private ArrayList<IAlbum> albumList;
	
	public User(String userId, String fullName) {
		this.userId = userId;
		this.fullName = fullName;
		this.albumList = new ArrayList<IAlbum>();
	}

	@Override
	public String getUserId() {
		return this.userId;
	}


	/**
	 * @return A defensive copy of the original list to prevent the calling class from making indirect changes to the master list.
	 */
	@Override
	public List<IAlbum> getAlbums() {
		ArrayList<IAlbum> defensiveCopy = new ArrayList<IAlbum>();
		defensiveCopy.addAll(this.albumList);
		return defensiveCopy;
	}

	@Override
	public void addAlbum(IAlbum album) {
		this.albumList.add(album);
	}

	@Override
	public void deleteAlbum(String albumId) {
		Collections.sort(albumList, new AlbumComparator());
		int index = Collections.binarySearch(albumList, albumId);
		if(index >= 0) {
			IAlbum album = albumList.get(index);
			if(album.getAlbumName().equals(albumId))	albumList.remove(index);
		}
	}

	@Override
	public int compareTo(String o) {
		return this.userId.compareTo(o);
	}

	@Override
	public String getFullName() {
		return fullName;
	}
	
	private class AlbumComparator implements Comparator<IAlbum> {
		@Override
		public int compare(IAlbum o1, IAlbum o2) {
			return o1.getAlbumName().compareTo(o2.getAlbumName());
		}
	}
}

