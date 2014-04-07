package cs213.photoalbum.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class User implements IUser {
	private static final long serialVersionUID = 1L;
	private String username;
	private String fullname;
	private String password;
	private String imgPath = IPhotoModel.defaultUserImgPath;
	private ArrayList<IAlbum> albumList;
	
	public User(String userId, String fullname, String password) {
		this.username = userId;
		this.fullname = fullname;
		this.password = password;
		this.albumList = new ArrayList<IAlbum>();
	}
	
	public User(String userId, String fullname, String password, String imgPath) {
		this(userId, fullname, password);
		if(!imgPath.isEmpty()) this.imgPath = imgPath;
	}

	@Override
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String getUserImgPath() {
		return this.imgPath;
	}
	
	@Override
	public void setUserImgPath(String imgPath) {
		if(!imgPath.isEmpty()) this.imgPath = imgPath;
	}

	/**
	 * @return A defensive copy of the original list to prevent the calling
	 *         class from making indirect changes to the master list.
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
		if (index >= 0) {
			IAlbum album = albumList.get(index);
			if (album.getAlbumName().equals(albumId))
				albumList.remove(index);
		}
	}

	@Override
	public int compareTo(String o) {
		return this.username.compareTo(o);
	}

	@Override
	public String getFullname() {
		return fullname;
	}
	
	@Override
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	private class AlbumComparator implements Comparator<IAlbum> {
		@Override
		public int compare(IAlbum o1, IAlbum o2) {
			return o1.getAlbumName().compareTo(o2.getAlbumName());
		}
	}
}
