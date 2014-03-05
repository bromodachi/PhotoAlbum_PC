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
		if(model.getUser(userId).getAlbums().contains(name)){
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
		model.getUser(userId).addAlbum(addMe);
		String success="created album for user <"+userId+">:\n<"+name+">";
		setErrorMessage(success);
		showError();
	}

	@Override
	public void deleteAlbum(String id) {
		// TODO Auto-generated method stub
		if(!model.getUser(userId).getAlbums().contains(id)){
			String error="album does not exist for user <"+userId+">:\n<"+id+">";
			setErrorMessage(error);
			showError();
			return;
		}
		model.getUser(userId).deleteAlbum(id);
		String msg="deleted album from user <"+userId+">:\n<"+id+"+>";
		setErrorMessage(msg);
		showError();

	}

	@Override
	public void listAlbums() {
		// TODO Auto-generated method stub
		String AlbumNames="";
		List<IAlbum> album1=model.getUser(userId).getAlbums();
		IAlbum temp=album1.get(0);
		Date lowest=temp.get(0).getDate();
		Date highest=temp.get(0).getDate();
		for(int i=1; i<album1.size(); i++){
			temp=album1.get(i);
			/*accessing the photo list now*/
			/*Let's compare*/
			for(int j=0;j<temp.size();i++){
				if(temp.get(j).getDate().before(lowest)){
					lowest=temp.get(j).getDate();
				}
				if(temp.get(j).getDate().after(highest)){
					highest=temp.get(j).getDate();
				}
			}
			/**/
			if(i==album1.size()-1){
				/*must get the photos date. I can look at each photo to see which has
				 * the earliest date and latest date. Maybe sort the photos? Hmm..*/
				AlbumNames=AlbumNames+album1.get(i).getAlbumName()+"> number of photos: <"+album1.get(i).getAlbumSize()+", <"+lowest+"> - <"+highest+">";
			}
			else{
			AlbumNames=AlbumNames+album1.get(i).getAlbumName()+"> number of photos: <"+album1.get(i).getAlbumSize()+", <"+lowest+"> - <"+highest+">\n";
			}
		}
		setErrorMessage(AlbumNames);
		showError();
		

	}

	@Override
	public void listPhotos(String albumId) {
		if(!model.getUser(userId).getAlbums().contains(albumId)){
			String error="album does not exist for user <"+userId+">:\n<"+albumId+">";
			setErrorMessage(error);
			showError();
			return;
		}
		List<IAlbum> album1=model.getUser(userId).getAlbums();
		int index=album1.indexOf(albumId);
		IAlbum temp=album1.get(index);
		String PhotoInfo="";
		for(int i=0; i<temp.size(); i++){
			if(i==temp.size()-1){
				PhotoInfo=PhotoInfo+"<"+temp.get(i).getFileName()+"> - <"+temp.get(i).getDate()+">";
			}
			PhotoInfo=PhotoInfo+"<"+temp.get(i).getFileName()+"> - <"+temp.get(i).getDate()+">\n";
		}
		String success="Photos for album <"+albumId+">:\n"+PhotoInfo;
		setErrorMessage(success);
		showError();
		

	}

	@Override
	public void addPhoto(String albumId, String photoFileName,
			String photoCaption) {
		/*assuming caption can't be empty
		 * Photo must be created before and?*/
		if(!model.getUser(userId).getAlbums().contains(albumId)){
			String error="album does not exist for user <"+userId+">:\n<"+albumId+">";
			setErrorMessage(error);
			showError();
			return;
		}
		/*how to access photo? */
		if(!model.getUser(userId).IPhoto.contains(photoFileName)){
			String error="File <"+photoFileName+"> does not exist";
			setErrorMessage(error);
			showError();
			return;
			
		}
		/*add to album Photo class*/
		List<IAlbum> album1=model.getUser(userId).getAlbums();
		int index=album1.indexOf(albumId);
		IAlbum temp=album1.get(index);
		//how can I access the photos? Once I get the photo..
		IPhoto addMe=new IPhoto(photoFileName);
		addMe.setCaption(photoCaption);
		IAlbum objectiveAlbum=album1.get(index);
		objectiveAlbum.addPhoto(addMe);
		String success="Added photo <"+photoFileName+">:\n <"+photoCaption+"> - Album: <"+albumId+">"; 
		setErrorMessage(success);
		showError();

	}

	@Override
	public void movePhoto(String albumIdSrc, String albumIdDest, String photoId) {
		if((!model.getUser(userId).getAlbums().contains(albumIdSrc))){
			String error="album does not exist for user <"+userId+">:\n<"+albumIdSrc+">";
			setErrorMessage(error);
			return;
			
		}
		if(!model.getUser(userId).getAlbums().contains(albumIdDest)){
			String error="album does not exist for user <"+userId+">:\n<"+albumIdDest+">";
			setErrorMessage(error);
			return;
			
		}
		List<IAlbum> album1=model.getUser(userId).getAlbums();
		int index=album1.indexOf(albumIdSrc);
		IAlbum source=album1.get(index);
		index=album1.indexOf(albumIdDest);
		IAlbum destination=album1.get(index);
		if(!source.contains(photoId)){
			String error="File <"+photoId+"> does not exist in <"+albumIdSrc+">";
			setErrorMessage(error);
			showError();
			return;
		}
		/*if(!model.getUser(userId).getAlbums().get(albumIdSrc).Photo.contains(photoId)){
			String error="File <"+photoId+"> does not exist in <"+albumIdSrc+">";
			setErrorMessage(error);
			showError();
			return;
			
		}*/
		/*Once moved, the photo gets removed from the source*/
		index=source.indexOf(photoId);
		IPhoto moveMe= source.get(index);
		destination.addPhoto(moveMe);
		source.deletePhoto(photoId);
		String success= "Moved photo <"+photoId+">:\n<"+photoId+"> - From album <"+albumIdSrc+"> to album <"+albumIdDest+">";
		setErrorMessage(success);
		showError();
	}

	@Override
	public void removePhoto(String albumId, String photoId) {
		if(!model.getUser(userId).getAlbums().contains(albumId)){
			String error="album does not exist for user <"+userId+">:\n<"+albumId+">";
			setErrorMessage(error);
			showError();
			return;
		}
		int index=model.getUser(userId).getAlbums().indexOf(albumId);
		IAlbum source=model.getUser(userId).getAlbums().get(index);
		if(!source.contains(photoId)){
			String error="Photo <"+photoId+"> does not exist in <"+albumId+">";
			setErrorMessage(error);
			showError();
			return;
		}
		/*I wonder if I can even do this..*/
		source.deletePhoto(photoId);
		String success= "Removed photo:\n<"+photoId+"> - From album <"+albumId+">";
		setErrorMessage(success);
		showError();
	}

	@Override
	public <V> void addTag(String photoId, String tagType, V tagValue) {
		/*in case photo doesn't exist*/
		if(!model.getUser(userId).IPhoto.contains(photoId)){
			String error="Photo <"+photoId+"> does not exist";
			cmd.setErrorMessage(error);
			cmd.showError();	
			return;
		}
		/*random guesses atm in how to access these data structures*/
		Photo getMe=model.IPhoto.get(photoId);
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
		if(!model.Photo.contains(photoId)){
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
