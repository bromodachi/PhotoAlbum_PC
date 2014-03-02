package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import model.IAlbum;
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
		String [] arg=null;
		try{
		BufferedReader input=new BufferedReader(new InputStreamReader(System.in));
		String output=input.readLine();
		arg=output.split(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		return arg;
		}catch (IOException e){
			return arg;
		}
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
		int i=1;
		while(!cmd.equals("logout")) {
			String[] tokens = readCommand();
			cmd = tokens[0];
			switch(cmd){
			case "createAlbum":
				if(tokens.length>1){
					String error="Error: <Incorrect Format>";
					setErrorMessage(error);
					showError();
					break;
				}
				/*replace with proper command. th*/
				createAlbum(tokens[1]);
				break;
			case "deleteAlbum":
				if(tokens.length>1){
					String error="Error: <Incorrect Format>";
					setErrorMessage(error);
					showError();
					break;
				}
				deleteAlbum(tokens[1]);
				break;
			case "listAlbums":
				if(tokens.length>0){
					String error="Error: <Incorrect Format>";
					setErrorMessage(error);
					showError();
					break;
				}
				listAlbums();
				break;
			case "listPhotos":
				if(tokens.length>1){
					String error="Error: <Incorrect Format>";
					setErrorMessage(error);
					showError();
					break;
				}
				tokens[1].replace("\"","" );
				listPhotos(tokens[1]);
				break;
			case "addPhoto":
				if(tokens.length>3){
					String error="Error: <Incorrect Format>";
					setErrorMessage(error);
					showError();
					break;
				}
				while(i!=4){
					tokens[i].replace("\"","" );
					i++;
				}
				addPhoto(tokens[1], tokens[2], tokens[3]);
				break;
			default:
					break;
			}
			//TODO Implement a condition-based statement to trigger the appropriate command.
		}
	}

	@Override
	public void setInteractiveModel(IPhotoModel model) {
		this.model = model;
	}

	@Override
	public void createAlbum(String name) {
		if(model.getAlbums().contains(name)){
			String error="album exists for user <"+userId+">:"+"\n<"+"tokens[1]";
			setErrorMessage(error);
			showError();
			break;
		}
		/*setAlbumId(String id);*/
		/*addAlbum(IAlbum album);*/
		/*create new album, add it to the user's album list*/
		IAlbum addMe=new IAlbum(name);
		addMe.setAlbumId(userId);
		model.IUser.addAlbum(addMe);
		String success="created album for user <"+userId+">:\n<"+name+">";
	}

	@Override
	public void deleteAlbum(String id) {
		// TODO Auto-generated method stub
		if(!model.getAlbums().contains(id)){
			String error="album does not exist for user <"+userId+">:\n<"+id+">";
			setErrorMessage(error);
			showError();
			break;
		}
		model.deleteAlbum(id);
		String msg="deleted album from user <"+userId+">:\n<"+id+"+>";

	}

	@Override
	public void listAlbums() {
		// TODO Auto-generated method stub
		Iterator<String> iter=model.getAlbums().iterator();
		String AlbumNames="";
		AlbumNames=AlbumNames+"\n"+iter.next();

	}

	@Override
	public void listPhotos(String albumId) {
		if(!model.getAlbums().contains(id)){
			String error="album does not exist for user <"+userId+">:\n<"+albumId+">";
			setErrorMessage(error);
			showError();
			break;
		}
		/*I've never extended a list before. Correct?*/
		Iterator<String> iter=model.getAlbums().get(albumId).List().iterator();
		
		String PhotoInfo="";
		PhotoInfo=PhotoInfo+"\n"+iter.next();
		

	}

	@Override
	public void addPhoto(String albumId, String photoFileName,
			String photoCaption) {
		/*assuming caption can't be empty
		 * Photo must be created before and?*/
		if(!model.getAlbums().contains(albumId)){
			String error="album does not exist for user <"+userId+">:\n<"+albumId+">";
			setErrorMessage(error);
			showError();
			break;
		}
		if()
		
		

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
