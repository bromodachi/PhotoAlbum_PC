package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User implements IUser{
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

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
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
		int index = Collections.binarySearch(albumList, albumId);
		IAlbum album = albumList.get(index);
		if(album.getAlbumId().equals(albumId))	albumList.remove(index);
	}

	@Override
	public void renameAlbum(String albumId, String newAlbumName) {
		int index = Collections.binarySearch(albumList, albumId);
		IAlbum album = albumList.get(index);
		if(album.getAlbumId().equals(albumId)) {
			album.setAlbumName(newAlbumName);
		}
	}

	@Override
	public int compareTo(String o) {
		return this.userId.compareTo(o);
	}

	@Override
	public String getFullUsername() {
		return fullName;
	}

	@Override
	public void setFullUsername(String fullName) {
		this.fullName = fullName;
	}
}
