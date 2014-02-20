package control;

import java.util.Date;

import model.IPhotoModel;

/**
 * @author Conrado Uraga
 *
 */
public class InteractiveControl implements IInteractiveControl {
	private IPhotoModel model;
	private String userId;
	
	public InteractiveControl(String userId) {
		this.userId = userId;
		this.model = null; //TODO Set default model.
	}
	
	public InteractiveControl(String userId, IPhotoModel model) {
		this.userId = userId;
		this.model = model;
	}

	@Override
	public void setErrorMessage(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showError() {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] readCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see control.IInteractiveControl#run(java.lang.String)
	 * 
	 * This implementation makes this method the primary entry point
	 * for the interactive control.
	 */
	@Override
	public void run(String userId) {
		this.userId = userId;
		String cmd = "";
		while(!cmd.equals("logout")) {
			String[] tokens = readCommand();
			cmd = tokens[0];
			
			//TODO Implement a condition-based statement to trigger the appropriate command.
		}
	}

	@Override
	public void setInteractiveModel(IPhotoModel model) {
		this.model = model;
	}

	@Override
	public void createAlbum(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAlbum(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void listAlbums() {
		// TODO Auto-generated method stub

	}

	@Override
	public void listPhotos(String albumId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addPhoto(String albumId, String photoFileName,
			String photoCaption) {
		// TODO Auto-generated method stub

	}

	@Override
	public void movePhoto(String albumIdSrc, String albumIdDest, String photoId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removePhoto(String albumId, String photoId) {
		// TODO Auto-generated method stub

	}

	@Override
	public <V> void addTag(String photoId, String tagType, V tagValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTag(String photoId, String tagType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getPhotoInfo(String photoId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getPhotosByDate(Date start, Date end) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getPhotosByTag(String tagType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub

	}
}
