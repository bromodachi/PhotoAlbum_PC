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

import simpleview.CmdView;
import model.Album;
import model.IAlbum;
import model.IPhoto;
import model.IPhotoAdminModel;
import model.Photo;

/**
 * @author Conrado Uraga
 *
 */
public class InteractiveControl implements IInteractiveControl {
	private IPhotoAdminModel model;
	private String userId;
	private CmdView view;
	
	public InteractiveControl(String userId, IPhotoAdminModel model, CmdView view) {
		this.userId = userId;
		this.model = model;
		this.view =view;
	}
	class PhotoCompare implements Comparator<IPhoto>{
		public int compare(IPhoto x, IPhoto y){
			return x.getDate().compareTo(y.getDate());
		}
		
	}
	class PhotoCompareForNames implements Comparator<IPhoto>{
		public int compare(IPhoto x, IPhoto y){
			return x.getFileName().compareTo(y.getFileName());
		}
		
	}
	class AlbumCompare implements Comparator<IAlbum>{
		public int compare(IAlbum x, IAlbum y){
			return x.getAlbumName().compareTo(y.getAlbumName());
		}
		
	}

	@Override
	public void setErrorMessage(String msg) {
		view.setMessage(msg);

	}

	@Override
	public void showError() {
		view.showMessage();

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
		String splitMe;
		String []splitting;
		this.userId = userId;
		String cmd = "";
		int i=1;
		while(!cmd.equals("logout")) {
			String[] tokens = readCommand();
			cmd = tokens[0];
			i=1;
			switch(cmd){
			case "createAlbum":
				if(tokens.length>2|| tokens.length<2){
					String error="Error: <Incorrect Format>";
					setErrorMessage(error);
					showError();
					break;
				}
				/*replace with proper command. th*/
				tokens[1]=tokens[1].replace("\"","");
				createAlbum(tokens[1]);
				break;
			case "deleteAlbum":
				if(tokens.length>2 || tokens.length<2){
					String error="Error: <Incorrect Format>";
					setErrorMessage(error);
					showError();
					break;
				}
				tokens[1]=tokens[1].replace("<", "");
				tokens[1]=tokens[1].replace(">", "");
				tokens[1]=tokens[1].replace("\"","");
				System.out.println("testing: "+tokens[1]);
				deleteAlbum(tokens[1]);
				break;
			case "listAlbums":
				if(tokens.length>1||tokens.length<1){
					String error="Error: <Incorrect Format>";
					setErrorMessage(error);
					showError();
					break;
				}
				listAlbums();
				break;
			case "listPhotos":
				if(tokens.length>2||tokens.length<2){
					String error="Error: <Incorrect Format>";
					setErrorMessage(error);
					showError();
					break;
				}
				tokens[1]=tokens[1].replace("<", "");
				tokens[1]=tokens[1].replace(">", "");
				tokens[1]=tokens[1].replace("\"","");
				listPhotos(tokens[1]);
				break;
			case "addPhoto":
				if(tokens.length>4 || tokens.length<4){
					String error="Error: <Incorrect Format>";
					setErrorMessage(error);
					showError();
					break;
				}
				while(i!=4){
					System.out.println(i);
					tokens[i]=tokens[i].replace("\"","");
					tokens[i]=tokens[i].replace("<", "");
					tokens[i]=tokens[i].replace(">", "");
					i++;
				}
				/*public void addPhoto(String albumId, String photoFileName,
			String photoCaption)*/
				addPhoto(tokens[3], tokens[1], tokens[2]);
				break;
			case "movePhoto":
				if(tokens.length>4 ||tokens.length<4){
					String error="Error: <Incorrect Format>";
					setErrorMessage(error);
					showError();
					break;
				}
				while(i!=4){
					tokens[i]=tokens[i].replace("\"","");
					tokens[i]=tokens[i].replace("<", "");
					tokens[i]=tokens[i].replace(">", "");
					i++;
				}
				/*movePhoto(String albumIdSrc, String albumIdDest, String photoId) */
				movePhoto(tokens[2], tokens[3], tokens[1]);
				break;
			case "removePhoto":
				if(tokens.length>3 ||tokens.length <3){
					String error="Error: <Incorrect Format>";
					setErrorMessage(error);
					showError();
					break;
				}
				while(i!=3){
					tokens[i]=tokens[i].replace("\"","");
					tokens[i]=tokens[i].replace("<", "");
					tokens[i]=tokens[i].replace(">", "");
					i++;
				}
				/*public void removePhoto(String albumId, String photoId)*/
				removePhoto(tokens[2], tokens[1]);
				break;
			case "addTag":
				if(tokens.length>3 ||tokens.length<3){
					String error="Error: <Incorrect Format>";
					setErrorMessage(error);
					showError();
					break;
				}
				while(i!=3){
					tokens[i]=tokens[i].replace("\"","");
					tokens[i]=tokens[i].replace("<", "");
					tokens[i]=tokens[i].replace(">", "");
					i++;
				}
				splitMe=tokens[2];
				splitting=splitMe.split(":");
				
				addTag(tokens[1], splitting[0], splitting[1]);
				break;
			case "deleteTag":
				if(tokens.length>3 ||tokens.length<3){
					String error="Error: <Incorrect Format>";
					setErrorMessage(error);
					showError();
					break;
				}
				while(i!=3){
					tokens[i]=tokens[i].replace("\"","");
					tokens[i]=tokens[i].replace("<", "");
					tokens[i]=tokens[i].replace(">", "");
					i++;
				}
				splitMe=tokens[2];
				splitting=splitMe.split(":");
				deleteTag(tokens[1], splitting[0], splitting[1]);
				break;
			case "listPhotoInfo":
				if(tokens.length>2||tokens.length<2){
					String error="Error: <Incorrect Format>";
					setErrorMessage(error);
					showError();
					break;
				}
				tokens[1]=tokens[1].replace("<", "");
				tokens[1]=tokens[1].replace(">", "");
				tokens[1]=tokens[1].replace("\"","");
				getPhotoInfo(tokens[1]);
				break;
			case "getPhotosByDate":
				if(tokens.length>3||tokens.length<3){
					String error="Error: <Incorrect Format>";
					setErrorMessage(error);
					showError();
					break;
				}
				while(i!=3){
					tokens[i]=tokens[i].replace("\"","");
					tokens[i]=tokens[i].replace("<", "");
					tokens[i]=tokens[i].replace(">", "");
					i++;
				}
				getPhotosByDate(tokens[1],tokens[2]);
				break;
			case "getPhotosByTag":
				/*getPhotosByTag(String tagType, String tagValue, String ori)*/
				if(tokens.length>2||tokens.length<2){
					String error="Error: <Incorrect Format>";
					setErrorMessage(error);
					showError();
					break;
				}
					String cpy=tokens[1];
					tokens[1].replace("\"","");
					tokens[1].replace("<", "");
					tokens[1].replace(">", "");
					if(tokens[1].contains(":")){
						splitMe=tokens[1];
						splitting=splitMe.split(":");
						getPhotosByTag(splitting[0], splitting[1], cpy);
						break;
					}
					else{
						if(tokens[1].equals("people")|| tokens[1].equals("location")){
							getPhotosByTag(tokens[1], "", cpy);
						break;
						}
						getPhotosByTag("", tokens[1], cpy);
						break;
					}

					
					
			case "logout":
				
				continue;
			default:
				String error="Error: <Incorrect input. Try again>";
				setErrorMessage(error);
				showError();
					break;
			}
			//TODO Implement a condition-based statement to trigger the appropriate command.
		}
		logout();
	}

	@Override
	public void createAlbum(String name) {
		if(model.getUser(userId)==null){
			System.out.println("yes");
			return;
		}
		List<IAlbum> album=model.getUser(userId).getAlbums();
		InteractiveControl.AlbumCompare comparePower=new InteractiveControl.AlbumCompare();
		Collections.sort(album, comparePower);
		int index=Collections.binarySearch(album, name);
		if(!(index<0)){
			String error="album exists for user <"+userId+">:\n";
			setErrorMessage(error);
			showError();
			return;
			
		}
		/*setAlbumId(String id);*/
		/*addAlbum(IAlbum album);*/
		/*create new album, add it to the user's album list*/
		Album addMe = new Album(name);
		model.getUser(userId).addAlbum(addMe);
		String success="created album for user <"+userId+">:\n<"+name+">";
		setErrorMessage(success);
		showError();
	}

	@Override
	public void deleteAlbum(String id) {
		// TODO Auto-generated method stub
		/*Collections.binarySearch(this.peopleTags, personName);*/
		/*int index=Collections.binarySearch(photoList, photoId);
			if(index>=0){
				editMe=photoList.get(index);
				break outerLoop;
			}*/
		List<IAlbum> album=model.getUser(userId).getAlbums();
		//InteractiveControl.AlbumCompare comparePower=new InteractiveControl.AlbumCompare();
	//	Album temp=new Album("", id);
		InteractiveControl.AlbumCompare comparePower=new InteractiveControl.AlbumCompare();
		Collections.sort(album, comparePower);
		int index=Collections.binarySearch(album, id);
		if(index<0){
			String error="album does not exist for user <"+userId+">:\n<"+id+">";
			setErrorMessage(error);
			showError();
			return;
		}
		model.getUser(userId).deleteAlbum(id);
		String msg="deleted album from user <"+userId+">:\n<"+id+">";
		setErrorMessage(msg);
		showError();
		return;

	}

	@Override
	public void listAlbums() {
		// TODO Auto-generated method stub
		String AlbumNames="";
		List<IAlbum> album1=model.getUser(userId).getAlbums();
		if(album1.size()==0){
			String error="No albums exist for user <"+userId+">";
			setErrorMessage(error);
			showError();
			return;
		}
		IAlbum temp=album1.get(0);
		
		for(int i=0; i<album1.size(); i++){
			temp=album1.get(i);
			/*accessing the photo list now*/
			List<IPhoto> photoList=temp.getPhotoList();
			if(photoList.size()==0){
				AlbumNames=AlbumNames+"<"+album1.get(i).getAlbumName()+"> number of photos: 0\n";
			}
			/*Let's compare*/
			else{Date lowest=photoList.get(0).getDate();
			Date highest=photoList.get(0).getDate();
			for(int j=0;j<photoList.size();j++){
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
				AlbumNames=AlbumNames+"<"+album1.get(i).getAlbumName()+"> number of photos: <"+album1.get(i).getAlbumSize()+">, <"+lowest+"> - <"+highest+">";
			}
			else{
			AlbumNames=AlbumNames+"<"+album1.get(i).getAlbumName()+"> number of photos: <"+album1.get(i).getAlbumSize()+">, <"+lowest+"> - <"+highest+">\n";
			}
		}
		}
		setErrorMessage(AlbumNames);
		showError();
		

	}

	@Override
	public void listPhotos(String albumId) {
		List<IAlbum> album=model.getUser(userId).getAlbums();
		InteractiveControl.AlbumCompare comparePower=new InteractiveControl.AlbumCompare();
		Collections.sort(album, comparePower);
		int index=Collections.binarySearch(album, albumId);
		if(index<0){
			String error="album does not exist for user <"+userId+">:\n<"+albumId+">";
			setErrorMessage(error);
			showError();
			return;
		}
		IAlbum temp=album.get(index);
		List<IPhoto> photoList=temp.getPhotoList();
		String PhotoInfo="";
		for(int i=0; i<photoList.size(); i++){
		/*	if(i==photoList.size()-1){
				PhotoInfo=PhotoInfo+"<"+photoList.get(i).getFileName()+"> - <"+photoList.get(i).getDate()+">";
			}*/
			PhotoInfo=PhotoInfo+"<"+photoList.get(i).getFileName()+"> - <"+photoList.get(i).getDate()+">\n";
		}
		String success="Photos for album <"+albumId+">:\n"+PhotoInfo;
		setErrorMessage(success);
		showError();
		

	}

	@Override
	public void addPhoto(String albumId, String photoFileName,
			String photoCaption) {
		if(!(model.photoExists(photoFileName))){
			String error="File <"+photoFileName+"> does not exist";
			setErrorMessage(error);
			showError();
			return;
			
		}
		/*assuming caption can't be empty
		 * Photo must be created before and?*/
		List<IAlbum> album1=model.getUser(userId).getAlbums();
		//InteractiveControl.AlbumCompare comparePower=new InteractiveControl.AlbumCompare();
	//	Album temp=new Album("", id);
		InteractiveControl.AlbumCompare comparePower=new InteractiveControl.AlbumCompare();
		Collections.sort(album1, comparePower);
		int index=Collections.binarySearch(album1, albumId);
		if(index<0){
			String error="album does not exist for user <"+userId+">:\n<"+albumId+">";
			setErrorMessage(error);
			showError();
			return;
		}
		/*how to access photo? */
		
		/*if(!model.photoExists(photoFileName)){
			String error="File <"+photoFileName+"> does not exist";
			setErrorMessage(error);
			showError();
			return;
			
		}*/
		/*add to album Photo class*/
		
		IAlbum temp=album1.get(index);
		//how can I access the photos? Once I get the photo..
		/*String user, String photoId, String fileName*/
	//	String uniqueID = UUID.randomUUID().toString();
		Photo addMe=new Photo(userId, photoFileName);
		addMe.setDate(model.photoFileDate(photoFileName));
		addMe.setCaption(photoCaption);
		IAlbum objectiveAlbum=album1.get(index);
		objectiveAlbum.addPhoto(addMe);
		String success="Added photo <"+photoFileName+">:\n <"+photoCaption+"> - Album: <"+albumId+">"; 
		setErrorMessage(success);
		showError();

	}

	@Override
	public void movePhoto(String albumIdSrc, String albumIdDest, String photoId) {
		List<IAlbum> album1=model.getUser(userId).getAlbums();
		InteractiveControl.AlbumCompare comparePower=new InteractiveControl.AlbumCompare();
		Collections.sort(album1, comparePower);
		int index=Collections.binarySearch(album1, albumIdSrc);
		if(index<0){
			String error="album does not exist for user <"+userId+">:\n<"+albumIdSrc+">";
			setErrorMessage(error);
			showError();
			return;
			
		}
		IAlbum source=album1.get(index);
		index=Collections.binarySearch(album1, albumIdDest);
		if(index<0){
			String error="album does not exist for user <"+userId+">:\n<"+albumIdDest+">";
			setErrorMessage(error);
			showError();
			return;
			
		}
		IAlbum destination=album1.get(index);
		/*PhotoCompareForNames*/
		InteractiveControl.PhotoCompareForNames comparePower2=new InteractiveControl.PhotoCompareForNames();
		Collections.sort(source.getPhotoList(), comparePower2);
		index=Collections.binarySearch(source.getPhotoList(), photoId);
		if(index<0){
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
		IPhoto moveMe= source.getPhotoList().get(index);
		destination.addPhoto(moveMe);
		source.deletePhoto(photoId);
		String success= "Moved photo <"+photoId+">:\n<"+photoId+"> - From album <"+albumIdSrc+"> to album <"+albumIdDest+">";
		setErrorMessage(success);
		showError();
	}

	@Override
	public void removePhoto(String albumId, String photoId) {
		List<IAlbum> album1=model.getUser(userId).getAlbums();
		//InteractiveControl.AlbumCompare comparePower=new InteractiveControl.AlbumCompare();
	//	Album temp=new Album("", id);
		int index=Collections.binarySearch(album1, albumId);
		if(index<0){
			String error="album does not exist for user <"+userId+">:\n<"+albumId+">";
			setErrorMessage(error);
			showError();
			return;
		}
		IAlbum source=model.getUser(userId).getAlbums().get(index);
		index=Collections.binarySearch(source.getPhotoList(), photoId);
		if(index<0){
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
	public void addTag(String photoId, String tagType, String tagValue) {
		/*in case photo doesn't exist*/
		List<IAlbum> albums=model.getUser(userId).getAlbums();
		IPhoto getMe=null;
		outerLoop:
		for (int i=0; i<model.getUser(userId).getAlbums().size(); i++){
			IAlbum temp= albums.get(i);
			List<IPhoto> photoList=temp.getPhotoList();
			/*Collections.binarySearch(this.peopleTags, personName);*/
			/*int index=Collections.binarySearch(photoList, photoId);
				if(index>=0){
					editMe=photoList.get(index);
					break outerLoop;
				}*/
					int index=Collections.binarySearch(photoList, photoId);
				if(index>=0){
					getMe=photoList.get(index);
					break outerLoop;
				}
				
				
		}
		if(getMe==null){
			String error="Photo <"+photoId+"> does not exist";
			setErrorMessage(error);
			showError();	
			return;
		}
		/*random guesses atm in how to access these data structures*/
		switch(tagType.toLowerCase()){
		case "location": 
			String check=getMe.getLocationTag();
			/**/
			if((check==null|| check.isEmpty())){
				getMe.setLocationTag(tagValue);
				String success="Added tag:\n<"+photoId+">\n<"+tagType+">:<"+tagValue+">";
				setErrorMessage(success);	
				showError();
				return;
			}
			else if(!(check.equals(tagValue))){
				getMe.setLocationTag(tagValue);
				String success="Added tag:\n<"+photoId+">\n<"+tagType+">:<"+tagValue+">";
				setErrorMessage(success);
				showError();String error="Tag already exists for <"+photoId+"> <"+tagType+">:<"+tagValue+">";
				setErrorMessage(error);
				showError();	
				return;
			}
			else{
				String error="Tag already exists for <"+photoId+"> <"+tagType+">:<"+tagValue+">";
				setErrorMessage(error);
				showError();	
				return;
			}
		case "people":
			if(getMe.getPeopleTags().contains(tagValue)){
				String error="Tag already exists for <"+photoId+"> <"+tagType+">:<"+tagValue+">";
				setErrorMessage(error);
				showError();	
				return;
			}
			getMe.personTag(tagValue);
			/*edit this later with correct values*/
			String success2="Added tag:\n<"+photoId+">\n<"+tagType+">:<"+tagValue+">";
			setErrorMessage(success2);
			showError();
			
			return;
		default:
			/*perhaps edit later*/
			String error="Error <Not a real tag>";
			setErrorMessage(error);
			showError();	
			break;
		
		
		}
		/*if(check.equals(tagType)){
			String error="Tag already exists for <"+photoId+"> <"+tagType+":<"+tagValue+">";
			cmd.setErrorMessage(error);
			cmd.showError();
		}
		getMe.setTagInjective(check);
		String success="Added tag:\n<"+photoId+"> <"+tagType+">:<"+tagValue+">";
		cmd.setErrorMessage(success);
		cmd.showError();*/
	}

	@Override
	public void deleteTag(String photoId, String tagType, String tagValue) {
		// TODO Auto-generated method stub
		model.getUser(userId).getAlbums();
		List<IAlbum> albums=model.getUser(userId).getAlbums();
		IPhoto editMe=null;
		outerLoop:
		for (int i=0; i<model.getUser(userId).getAlbums().size(); i++){
			IAlbum temp= albums.get(i);
			List<IPhoto> photoList=temp.getPhotoList();
			/*Collections.binarySearch(this.peopleTags, personName);*/
			int index=Collections.binarySearch(photoList, photoId);
				if(index>=0){
					editMe=photoList.get(index);
					break outerLoop;
				}
				
				
		}
		if(editMe==null){
			String error="Photo <"+photoId+"> does not exist";
			setErrorMessage(error);
			showError();	
			return;
			
		}
		switch(tagType){
			case"location":
			if(editMe.getLocationTag().isEmpty()||editMe.getLocationTag()==null){
				String error="Tag does not exist for <"+editMe.getFileName()+"> <"+tagType+">:<"+tagValue+">";
				setErrorMessage(error);
				showError();
				return;
			}
			else{
				String temp=editMe.getLocationTag();
				editMe.setLocationTag("");
				String success="Deleted tag:\n<"+photoId+"> <"+tagType+">:<"+temp+">";
				setErrorMessage(success);
				showError();
				return;
			}
			/*maybe change below*/
			case"people":
				
			if((editMe.getPeopleTags().isEmpty())|| (!editMe.getPeopleTags().contains(tagValue))){
			/*replace tagtype by appropriate value*/
				String error="Tag does not exist for <"+editMe.getFileName()+"> <"+tagType+">:<"+tagValue+">";
				setErrorMessage(error);
				showError();
				return;
			}
			else{
				editMe.removePersonTag(tagValue);
				String success="Deleted tag:\n<"+photoId+"> <"+tagType+">:<"+tagValue+">";
				setErrorMessage(success);
				showError();
				return;
			
			}
			default:
				String error="Error <Tag value doesn't exist>";
				setErrorMessage(error);
				showError();
				return;
		}
		
			
		
	}
	

	@Override
	public String getPAlbumNames(List<IAlbum> album, String photoId){
		List<IAlbum> albums=model.getUser(userId).getAlbums();
		IPhoto editMe=null;
		String albumNames="";
		int first=0;
		/**iterate each album, get their names if it contains the photoId*/
		for (int i=0; i<model.getUser(userId).getAlbums().size(); i++){
			IAlbum temp= albums.get(i);
			List<IPhoto> photoList=temp.getPhotoList();
			InteractiveControl.PhotoCompareForNames comparePower=new InteractiveControl.PhotoCompareForNames();
			Collections.sort(photoList, comparePower);
			/*Collections.binarySearch(this.peopleTags, personName);*/
			int index=Collections.binarySearch(photoList, photoId);
				if(index>=0){
					if(first==0){
					albumNames=albumNames+"Album: <"+temp.getAlbumName()+">";
					editMe=temp.getPhotoList().get(index);
					first=1;
					}
					else{
						albumNames=albumNames+",<"+temp.getAlbumName()+">";
					}
				}
				
				
		}
		return albumNames;
		
		
	}
	public void getPhotoInfo(String photoId) {
	/*NOT DONE WITH THIS. DO SEARCH IF AN ALBUM CONTAINS A PHOTO OR NOT, ITS NAME. MOVE stuff*/
		/*if(!model.photoExists.contains(photoId)){
			String error="Photo <"+photoId+"> does not exist";
			setErrorMessage(error);
			showError();	
			return;
		}*/
		List<IAlbum> albums=model.getUser(userId).getAlbums();
		IPhoto editMe=null;
		String albumNames="";
		int first=0;
		/**iterate each album, get their names if it contains the photoId*/
		for (int i=0; i<model.getUser(userId).getAlbums().size(); i++){
			IAlbum temp= albums.get(i);
			List<IPhoto> photoList=temp.getPhotoList();
			InteractiveControl.PhotoCompareForNames comparePower2=new InteractiveControl.PhotoCompareForNames();
			Collections.sort(photoList, comparePower2);
			int index=Collections.binarySearch(photoList, photoId);
				if(index>=0){
					if(first==0){
					albumNames=albumNames+"Album: <"+temp.getAlbumName()+">";
					editMe=temp.getPhotoList().get(index);
					first=1;
					}
					else{
						albumNames=albumNames+",<"+temp.getAlbumName()+">";
					}
				}
				
				
		}
		if(editMe==null){
			System.out.println(photoId);
			System.out.println("yes");
			return;
		}
		albumNames=albumNames+"\n";
		String locationTagz="";
		String evenMore="";
		if(!(editMe.getLocationTag()==null|| editMe.getLocationTag().isEmpty())){
			locationTagz="<Location>:<"+editMe.getLocationTag()+">\n";
		}
		System.out.println(editMe.getPeopleTags().size());
			for (int i=0;i<editMe.getPeopleTags().size();i++){
				evenMore=evenMore+"<Name of People>:<"+editMe.getPeopleTags().get(i)+">\n";
				System.out.println("I came here");
			}
			/*contains more tagsz*/
			String success="Photo file name: <"+photoId+">\n"+albumNames+"Date: <"+editMe.getDateString()+">\nCaption: <"+editMe.getCaption()+">\n";
				success=success+locationTagz+evenMore;
				setErrorMessage(success);
				showError();
				return;
			
			
	}

	@Override
	public void getPhotosByDate(String start, String end) {
		// TODO Auto-generated method stub
		//not done with this yet
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
			  String error="Error: <Invalid date for one of inputs>";
			  setErrorMessage(error);
			  showError();
			}
		String listPhotos="";
		List<IAlbum> album1=model.getUser(userId).getAlbums();
		String success="";
		String albumNames="";
	//	InteractiveControl.PhotoCompare comparePower=new InteractiveControl.PhotoCompare();
		for(int i=0; i<album1.size();i++){
			IAlbum temp=album1.get(i);
			List<IPhoto> photoList=temp.getPhotoList();
		//	Collections.sort(photoList, comparePower);
			for(int j=0; j<photoList.size();j++){
				if(photoList.get(j).getDate().after(begin) && photoList.get(j).getDate().before(endz)){
					/* getPAlbumNames(List<IAlbum> albums, String photoId)*/
					albumNames=getPAlbumNames(album1, photoList.get(j).getFileName());
					listPhotos=listPhotos+"<"+photoList.get(j).getCaption()+"> - "+albumNames+"- Date: <"+photoList.get(j).getDateString()+">\n";
				}
			}
		}
		success="Photos for user <user id> in range <"+userId+"> in range <"+start+"> to <"+end+">:\n"+listPhotos;
		setErrorMessage(success);
		showError();
		

	}

	@Override
	public void getPhotosByTag(String tagType, String tagValue, String ori) {
		tagValue=tagValue.replace("\"","");
		/*Assuming that it's by albums only*/
		List <IAlbum> getMe=model.getUser(userId).getAlbums();
		String validPhotos="";
		String albumNames="";
		String success="";
		String listPhotos="";
		List <IPhoto> tempValid=new ArrayList<IPhoto>();
		if(tagType.equals("location")){
			for (int i=0; i<getMe.size();i++){
				IAlbum temp=getMe.get(i);
				List <IPhoto> getPhotos=temp.getPhotoList();
				for(int j=0; j<getPhotos.size(); j++){
					if(!(getPhotos.get(j).getLocationTag()==null ||getPhotos.get(j).getLocationTag().isEmpty()) ){
					/*have to get it by photo info. Look at documentation*/
						tempValid.add(getPhotos.get(j));
						}
				}
			}
			for(int j=0; j<tempValid.size();j++){
				albumNames=getPAlbumNames((getMe), tempValid.get(j).getFileName());
				validPhotos=validPhotos+"<"+tempValid.get(j).getCaption()+"> - Album: <"+albumNames+"- Date: <"+tempValid.get(j).getDateString()+">\n";
			}
			success="Photos for user <"+userId+"> with tags "+ori+":\n"+validPhotos;
			setErrorMessage(success);
			showError();
			return;
		}
		/*int index=Collections.binarySearch(photoList, photoId);
		if(index>=0){
			editMe=photoList.get(index);
			break outerLoop;
		}*/
		if(tagType.equals("person")){
		for (int i=0; i<getMe.size();i++){
			IAlbum temp=getMe.get(i);
			List <IPhoto> getPhotos=temp.getPhotoList();
				for(int j=0; j<getPhotos.size(); j++){
					albumNames=getPAlbumNames((getMe), getPhotos.get(j).getFileName());
					validPhotos=validPhotos+"<"+getPhotos.get(j).getCaption()+"> - Album: <"+albumNames+"- Date: <"+getPhotos.get(j).getDateString()+">\n";
				
				}
			}
		}
		else{
			System.out.println(tagValue);
			for (int i=0; i<getMe.size();i++){
				IAlbum temp=getMe.get(i);
				List <IPhoto> getPhotos=temp.getPhotoList();
				for(int j=0; j<getPhotos.size(); j++){
					List<String> tagz=getPhotos.get(j).getPeopleTags();
					if(!(getPhotos.get(j).getLocationTag()==null|| getPhotos.get(j).getLocationTag().isEmpty())){
						if((getPhotos.get(j).getLocationTag().equals(tagValue))){
							albumNames=getPAlbumNames((getMe), getPhotos.get(j).getFileName());
							validPhotos=validPhotos+"<"+getPhotos.get(j).getCaption()+"> - Album: <"+albumNames+"- Date: <"+getPhotos.get(j).getDateString()+">\n";
					
						
						}
					}
					
					if(tagz.contains(tagValue)){
						System.out.println(tagValue);
						albumNames=getPAlbumNames((getMe), getPhotos.get(j).getFileName());
						validPhotos=validPhotos+"<"+getPhotos.get(j).getCaption()+"> - Album: <"+albumNames+"- Date: <"+getPhotos.get(j).getDateString()+">\n";
					}
					
				}
			}
		}
		success="Photos for user <"+userId+"> with tags"+ori+":\n"+validPhotos;
		setErrorMessage(success);
		showError();


	}

	@Override
	public void logout() {
		model.saveCurrentSession();
	}
}