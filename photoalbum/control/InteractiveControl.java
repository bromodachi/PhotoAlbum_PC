package control;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import model.Album;
import model.IAlbum;
import model.IPhoto;
import model.IPhotoModel;
import model.Photo;

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
	class PhotoCompare implements Comparator<IPhoto>{
		public int compare(IPhoto x, IPhoto y){
			return x.getDate().compareTo(y.getDate());
		}
		
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
				tokens[1].replace("\"","");
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
					tokens[i].replace("\"","");
					i++;
				}
				addPhoto(tokens[1], tokens[2], tokens[3]);
				break;
			default:
					break;
			}
			//TODO Implement a condition-based statement to trigger the appropriate command.
		}
		logout();
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
		String uniqueID = UUID.randomUUID().toString();
		Album addMe=new Album(uniqueID, name);
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
		for(int i=0; i<album1.size(); i++){
			temp=album1.get(i);
			/*accessing the photo list now*/
			List<IPhoto> photoList=temp.getPhotoList();
			/*Let's compare*/
			Date lowest=photoList.get(0).getDate();
			Date highest=photoList.get(0).getDate();
			for(int j=1;j<photoList.size();i++){
				if(photoList.get(j).getDate().before(lowest)){
					lowest=photoList.get(j).getDate();
				}
				if(photoList.get(j).getDate().after(highest)){
					highest=photoList.get(j).getDate();
				}
			}
			/**/
			if(i==album1.size()-1){
				/*Do I even need this?*/
				AlbumNames=AlbumNames+"<"+album1.get(i).getAlbumName()+"> number of photos: <"+album1.get(i).getAlbumSize()+", <"+lowest+"> - <"+highest+">";
			}
			else{
			AlbumNames=AlbumNames+"<"+album1.get(i).getAlbumName()+"> number of photos: <"+album1.get(i).getAlbumSize()+", <"+lowest+"> - <"+highest+">\n";
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
		List<IPhoto> photoList=temp.getPhotoList();
		String PhotoInfo="";
		for(int i=0; i<photoList.size(); i++){
			if(i==photoList.size()-1){
				PhotoInfo=PhotoInfo+"<"+photoList.get(i).getFileName()+"> - <"+photoList.get(i).getDate()+">";
			}
			PhotoInfo=PhotoInfo+"<"+photoList.get(i).getFileName()+"> - <"+photoList.get(i).getDate()+">\n";
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
		Photo addMe=new Photo(userId, photoFileName, photoCaption);
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
		if(!source.getPhotoList().contains(photoId)){
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
		index=source.getPhotoList().indexOf(photoId);
		IPhoto moveMe= source.getPhotoList().get(index);
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
		if(!source.getPhotoList().contains(photoId)){
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
		if(!model.getUser(userId).Photo.contains(photoId)){
			String error="Photo <"+photoId+"> does not exist";
			setErrorMessage(error);
			showError();	
			return;
		}
		/*random guesses atm in how to access these data structures*/
		Photo getMe=model.getUser(userId).Photo.get(photoId);
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
			setErrorMessage(error);
			showError();	
			return;
		}
		if(model.Photo.contains(photoId)){
			int index=model.Photo.indexOf(photoId);
			Photo getMe=model.Photo.get(index);
			/*have to double check this*/
			String tag=getMe.getTagInjective();
			List<String> evenMore=getMe.getTagSurjective();
			/*contains more tagsz*/
			String tags=
			String AlbumNames="";
			AlbumNames=AlbumNames+"Photo file name: <"+getMe.getFileName()+">\nAlbum: <"+getMe.getAlbum()+"> "+"\nDate: <"+getMe.getDate()+">\nCaption: <"+getMe.getCaption()+"Tags:\n"+ 
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
	public void getPhotosByDate(String start, String end) {
		// TODO Auto-generated method stub
		Date begin=null;
		Date endz=null;
		/*check if valid dates have been passed for both of the variables*/
		try {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			dateFormat.setLenient(false);
			begin = dateFormat.parse(start);
			endz = dateFormat.parse(start);
			}
			catch (ParseException e) {
			  System.out.println("Error: <Invalid date for one of inputs>");
			}
		/*Not finished with this yet*/
		List<IPhoto> finalList=new ArrayList<IPhoto>();
		List<IAlbum> album1=model.getUser(userId).getAlbums();
		InteractiveControl.PhotoCompare comparePower=new InteractiveControl.PhotoCompare();
		for(int i=0; i<album1.size();i++){
			IAlbum temp=album1.get(i);
			List<IPhoto> photoList=temp.getPhotoList();
			Collections.sort(photoList, comparePower);
			for(int j=0; j<photoList.size();j++){
				if(photoList.get(j).getDate().after(begin) && photoList.get(j).getDate().before(endz)){
					finalList.add(photoList.get(j));
				}
			}
		}
		

	}

	@Override
	public void getPhotosByTag(String tagType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logout() {
		/*The control doesn't do the saving. Call the model?*/
	/*	try{
	        FileOutputStream saveFile=new FileOutputStream("savePhotos.dat");
	        ObjectOutputStream save=new ObjectOutputStream(saveFile);
	        save.writeObject(model.getUser(userId));
	        save.close();
	        }
	        catch(Exception exc){
	        	
	        	
	        }

	}*/
	}
}
