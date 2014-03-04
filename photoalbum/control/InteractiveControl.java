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
			return;
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
			return;
		}
		model.deleteAlbum(id);
		String msg="deleted album from user <"+userId+">:\n<"+id+"+>";

	}

	@Override
	public void listAlbums() {
		// TODO Auto-generated method stub
		String AlbumNames="";
		List album1=model.getAlbums();
		for(int i=0; i<=album1.size(); i++){
			if(i==0){
				/*must get the photos date. I can look at each photo to see which has
				 * the earliest date and latest date. Maybe sort the photos? Hmm..*/
				AlbumNames=album1.get(i).getAlbumName()+"> number of photos: <"+album1.get(i).getAlbumSize()+", <\n";
			}
			AlbumNames=AlbumNames+album1.get(i).getAlbumName()+"> number of photos: <"+album1.get(i).getAlbumSize()+", <\n";
			
		}

	}

	@Override
	public void listPhotos(String albumId) {
		if(!model.getAlbums().contains(id)){
			String error="album does not exist for user <"+userId+">:\n<"+albumId+">";
			setErrorMessage(error);
			showError();
			return;
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
			cmd.setErrorMessage(error);
			cmd.showError();
			return;
		}
		/*how to access photo? */
		if(!model.Photo.contains(photoFileName)){
			String error="File <"+photoFileName+"> does not exist";
			cmd.setErrorMessage(error);
			cmd.showError();
			return;
			
		}
		/*add to album Photo class*/
		Photo addTo=model.PhotoModel.Photo.get(photoFileName);
		addTo.setCaption(photoCaption);
		Album objectiveAlbum=model.getAlbums(albumId)
		objectiveAlbum.addPhoto(addTo);
		String success="Added photo <"+photoFileName+">:\n <"+photoCaption+"> - Album: <"+albumId+">"; 
		cmd.setErrorMessage(success);
		cmd.showError();

	}

	@Override
	public void movePhoto(String albumIdSrc, String albumIdDest, String photoId) {
		if((!model.getAlbums().contains(albumIdSrc))){
			String error="album does not exist for user <"+userId+">:\n<"+albumIdSrc+">";
			cmd.setErrorMessage(error);
			return;
			
		}
		if(!model.getAlbums().contains(albumIdDest)){
			String error="album does not exist for user <"+userId+">:\n<"+albumIdDest+">";
			cmd.setErrorMessage(error);
			return;
			
		}
		if(!model.Photo.contains(photoID)){
			String error="File <"+photoId+"> does not exist";
			cmd.setErrorMessage(error);
			cmd.showError();
			return;
		}
		if(!model.getAlbums().get(albumIdSrc).Photo.contains(photoId)){
			String error="File <"+photoId+"> does not exist in <"+albumIdSrc+">";
			cmd.setErrorMessage(error);
			cmd.showError();
			return;
			
		}
		Album source=model.getAlbums().get(albumIdSrc);
		Album destination=model.getAlbums().get(albumIdDest);
		Photo moveMe= source.Photo.get(photoId);
		destination.addPhoto(moveMe);
		source.deletePhoto(photoId);
		String success= "Moved photo <"+photoId+">:\n<"+photoId+"> - From album <"+albumIdSrc+"> to album <"+albumIdDest+">";
		cmd.setErrorMessage(success);
		cmd.showError();
	}

	@Override
	public void removePhoto(String albumId, String photoId) {
		if(!model.getAlbums().contains(albumId)){
			String error="album does not exist for user <"+userId+">:\n<"+albumId+">";
			cmd.setErrorMessage(error);
			cmd.showError();
			return;
		}
		if(!model.getAlbums().get(albumId).Photo.contains(photoId)){
			String error="Photo <"+photoId+"> does not exist in <"+albumId+">";
			cmd.setErrorMessage(error);
			cmd.showError();
			return;
		}
		/*I wonder if I can even do this..*/
		model.getAlbums().get(albumId).deletePhoto(photoId);
		String success= "Removed photo:\n<"+photoId+"> - From album <"+albumId+">";
		cmd.setErrorMessage(success);
		cmd.showError();
	}

	@Override
	public <V> void addTag(String photoId, String tagType, V tagValue) {
		/*in case photo doesn't exist*/
		if(!model.IAlbum.Photo.contains(photoId)){
			String error="Photo <"+photoId+"> does not exist";
			cmd.setErrorMessage(error);
			cmd.showError();	
			return;
		}
		/*random guesses atm in how to access these data structures*/
		Photo getMe=model.IAlbum.Photo.get(photoId);
		switch(tagType.toLowerCase()){
		case "names": 
			V check=getMe.getInjective();
			if(!check.equals("")){
				String error="Tag already exists for <"+photoId+"> <"+tagType+">:<"+tagValue+">";
				cmd.setErrorMessage(error);
				cmd.showError();	
				return;
			}
			getMe.setTagInjective(tagType, tagValue);
			
			
			break;
		case "location":
			V check=getMe.getInjective();
			if(!check.equals("")){
				String error="Tag already exists for <"+photoId+"> <"+tagType+">:<"+tagValue+">";
				cmd.setErrorMessage(error);
				cmd.showError();	
				return;
			}
			getMe.setTagInjective(tagType, tagValue);
			
			break;
		default:
			/*perhaps edit later*/
			String error="Error <Not a real tag>";
			cmd.setErrorMessage(error);
			cmd.showError();	
			break;
		
		
		}
		if(check.equals(tagType)){
			String error="Tag already exists for <"+photoId+"> <"+tagType+":<"+tagValue+">";
			cmd.setErrorMessage(error);
			cmd.showError();
		}
		getMe.setTagInjective(check);
		String success="Added tag:\n<"+photoId+"> <"+tagType+">:<"+tagValue+">";
		cmd.setErrorMessage(success);
		cmd.showError();
	}

	@Override
	public void deleteTag(String photoId, String tagType) {
		// TODO Auto-generated method stub
		if(!model.IAlbum.Photo.contains(photoId)){
			String error="Photo <"+photoId+"> does not exist";
			cmd.setErrorMessage(error);
			cmd.showError();	
			return;
		}
		Photo getMe=model.IAlbum.Photo.get(photoId);
		if(tagType!=getMe.get)
	}

	@Override
	public void getPhotoInfo(String photoId) {
		if(!model.IAlbum.Photo.contains(photoId)){
			String error="Photo <"+photoId+"> does not exist";
			cmd.setErrorMessage(error);
			cmd.showError();	
			return;
		}
		if(model.getAlbums().contains(photoId)){
			Iterator<String> iter=model.getAlbums().iterator();
			String AlbumNames="";
			AlbumNames=AlbumNames+"\n"+iter.next();
			while(iter.hasNext()){
				if(it)
				
			} 
			
		}
		Photo getPhoto=model.IAlbum.Photo.get(photoId);
		String photoInfo="";
		photoInfo=photoInfo+"'Photo file name: <"+getPhoto.getFileName()+">\n";
		photoInfo="Album: <"+
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
