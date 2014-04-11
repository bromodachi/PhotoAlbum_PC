package cs213.photoalbum.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cs213.photoalbum.model.Album;
import cs213.photoalbum.model.IAlbum;
import cs213.photoalbum.model.IPhoto;
import cs213.photoalbum.model.IPhotoModel;
import cs213.photoalbum.model.Photo;
import cs213.photoalbum.view.SingleAlbumUI;
import cs213.photoalbum.view.AddTag;
import cs213.photoalbum.view.AlbumCollectionUI;
import cs213.photoalbum.view.MovePhotoDialog;
import cs213.photoalbum.view.RecaptionDialog;
import cs213.photoalbum.view.SlideShowUI;

/**
 * @author Conrado Uraga
 * 
 *         <p>
 *         Control for general users and their associated albums and photos.
 *         </p>
 */
public class InteractiveControl implements IInteractiveControl {
	private IPhotoModel model;
	private String username;
	private AlbumCollectionUI view;
	private SingleAlbumUI singlealbumview;
	private SlideShowUI slideshow;
	private Date begin = null;
	private Date endz = null;

	public InteractiveControl(String username, IPhotoModel model,
			AlbumCollectionUI view) {
		this.username = username;
		this.model = model;
		this.view = view;
		this.view.setInteractiveControl(this);
		setup();
	}

	/**
	 * @author Mark Labrador
	 */
	private void setup() {
		this.view.registerFrameWindowListener(new WindowAdapter() { //TODO Open original login window after closing.
					@Override
					public void windowClosing(WindowEvent e) {
						model.saveCurrentSession();
					}

					@Override
					public void windowClosed(WindowEvent e) {
						model.saveCurrentSession();
					}
				});

	}

	class PhotoCompare implements Comparator<IPhoto> {
		public int compare(IPhoto x, IPhoto y) {
			return x.getDate().compareTo(y.getDate());
		}
	}

	class PhotoCompareForNames implements Comparator<IPhoto> {
		public int compare(IPhoto x, IPhoto y) {
			return x.getFileName().compareTo(y.getFileName());
		}

	}

	class AlbumCompare implements Comparator<IAlbum> {
		public int compare(IAlbum x, IAlbum y) {
			return x.getAlbumName().compareTo(y.getAlbumName());
		}

	}

	@Override
	public void setErrorMessage(String msg) {
		//	view.setMessage(msg);

	}

	@Override
	public void showError() {
		//		view.showMessage();

	}

	@Override
	public String[] readCommand() {
		String[] arg = null;
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					System.in));
			String output = input.readLine();
			arg = output.split(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)");
			return arg;
		} catch (IOException e) {
			return arg;
		}
	}

	@Override
	public void run() {
		view.initUI(model.getUser(username).getAlbums());
	}

	@Override
	public void createAlbum(String name) {
		List<IAlbum> album = model.getUser(username).getAlbums();
		InteractiveControl.AlbumCompare comparePower = new InteractiveControl.AlbumCompare();
		Collections.sort(album, comparePower);
		int index = Collections.binarySearch(album, name);
		if (!(index < 0)) {
			String error = "album exists for user " + username + "\n";
			setErrorMessage(error);
			showError();
			JButton showTextButton = new JButton();
			JOptionPane.showMessageDialog(showTextButton, error);
			return;

		}
		/* create new album, add it to the user's album list */
		Album addMe = new Album(name);
		addMe.createPanel(name);
		model.getUser(username).addAlbum(addMe);
		view.addElementToVector(addMe);
		String success = "created album for user " + username + ":\n" + name
				+ "";
		setErrorMessage(success);
		showError();
	}

	@Override
	public void renameAlbum(String formal, String id) {
		List<IAlbum> album = model.getUser(username).getAlbums();
		InteractiveControl.AlbumCompare comparePower = new InteractiveControl.AlbumCompare();
		Collections.sort(album, comparePower);
		int index = Collections.binarySearch(album, formal);
		if (index < 0) {
			String error = "album does not exist for user " + username + ":\n"
					+ id + "";
			setErrorMessage(error);
			System.out.println(error);
			showError();
			return;
		}
		int checker = Collections.binarySearch(album, id);
		if (checker >= 0) {
			String error = "album already exist for user " + username + ":\n"
					+ id + "";
			setErrorMessage(error);
			System.out.println(error);
			JButton showTextButton = new JButton();
			JOptionPane.showMessageDialog(showTextButton, error);
			showError();
			return;
		}
		System.out.println("test in control");
		IAlbum editAlbum = album.get(index);
		editAlbum.setAlbumName(id);
		//	int deleteIndex=view.getIndex();
		view.renameAlbumInVector();
	}

	public void deleteAlbum(String id) {
		List<IAlbum> album = model.getUser(username).getAlbums();
		InteractiveControl.AlbumCompare comparePower = new InteractiveControl.AlbumCompare();
		Collections.sort(album, comparePower);
		int index = Collections.binarySearch(album, id);
		if (index < 0) {
			String error = "album does not exist for user " + username + ":\n"
					+ id + "";
			setErrorMessage(error);
			showError();
			return;
		}
		model.getUser(username).deleteAlbum(id);
		String msg = "deleted album from user " + username + ":\n" + id + "";
		setErrorMessage(msg);
		int deleteIndex = view.getIndex();
		System.out.println("test in method");
		view.deleteElementFromVector(deleteIndex);
		showError();
		return;

	}

	@Override
	public void listAlbums() {
		String AlbumNames = "";
		List<IAlbum> album1 = model.getUser(username).getAlbums();
		if (album1.size() == 0) {
			String error = "No albums exist for user " + username + "";
			setErrorMessage(error);
			showError();
			return;
		}
		IAlbum temp = album1.get(0);

		for (int i = 0; i < album1.size(); i++) {
			temp = album1.get(i);
			/* accessing the photo list now */
			List<IPhoto> photoList = temp.getPhotoList();
			if (photoList.size() == 0) {
				AlbumNames = AlbumNames + "" + album1.get(i).getAlbumName()
						+ " number of photos: 0\n";
			}
			/* Let's compare */
			else {
				Date lowest = photoList.get(0).getDate();
				Date highest = photoList.get(0).getDate();
				for (int j = 0; j < photoList.size(); j++) {
					if (photoList.get(j).getDate().before(lowest)) {
						lowest = photoList.get(j).getDate();
					}
					if (photoList.get(j).getDate().after(highest)) {
						highest = photoList.get(j).getDate();
					}
				}

				/**/
				if (i == album1.size() - 1) {
					/* Do I even need this? */
					AlbumNames = AlbumNames + "" + album1.get(i).getAlbumName()
							+ " number of photos: "
							+ album1.get(i).getAlbumSize() + ", " + lowest
							+ " - " + highest + "";
				} else {
					AlbumNames = AlbumNames + "" + album1.get(i).getAlbumName()
							+ " number of photos: "
							+ album1.get(i).getAlbumSize() + ", " + lowest
							+ " - " + highest + "\n";
				}
			}
		}
		setErrorMessage(AlbumNames);
		showError();

	}

	@Override
	public void listPhotos(String albumId) {
		List<IAlbum> album = model.getUser(username).getAlbums();
		InteractiveControl.AlbumCompare comparePower = new InteractiveControl.AlbumCompare();
		Collections.sort(album, comparePower);
		int index = Collections.binarySearch(album, albumId);
		if (index < 0) {
			String error = "album does not exist for user " + username + ":\n"
					+ albumId + "";
			setErrorMessage(error);
			showError();
			return;
		}
		IAlbum temp = album.get(index);
		List<IPhoto> photoList = temp.getPhotoList();
		String PhotoInfo = "";
		for (int i = 0; i < photoList.size(); i++) {
			PhotoInfo = PhotoInfo + "" + photoList.get(i).getFileName() + " - "
					+ photoList.get(i).getDate() + "\n";
		}
		String success = "Photos for album " + albumId + ":\n" + PhotoInfo;
		setErrorMessage(success);
		showError();

	}

	@Override
	public void addPhoto(String albumId, String photoFileName,
			String photoCaption) {
		/* the Album gui does the adding. I should move it here though */

	}

	public void recaptionPhoto(String photoId, String reName) {
		List<IAlbum> albums = model.getUser(username).getAlbums();
		IPhoto editMe = null;
		for (int i = 0; i < model.getUser(username).getAlbums().size(); i++) {
			IAlbum temp = albums.get(i);
			List<IPhoto> photoList = temp.getPhotoList();
			InteractiveControl.PhotoCompareForNames comparePower2 = new InteractiveControl.PhotoCompareForNames();
			Collections.sort(photoList, comparePower2);
			int index2 = Collections.binarySearch(photoList, photoId);
			if (index2 >= 0) {
				editMe = photoList.get(index2);
				editMe.setCaption(reName);
				String success = "Photo " + photoId
						+ " caption has been renamed to:\n"
						+ editMe.getCaption() + "\n";
				setErrorMessage(success);
				singlealbumview.setCaption(reName);
				showError();
			}
		}
		if (editMe == null) {
			String error = "Photo <" + photoId + "> does not exist";
			setErrorMessage(error);
			showError();
			return;
		}
		editMe.setCaption(reName);
		String success = "Photo " + photoId + " caption has been renamed to:\n"
				+ editMe.getCaption() + "\n";
		setErrorMessage(success);
		singlealbumview.setCaption(reName);
		showError();
		return;

	}

	public IPhoto photoExistsInAlbum(Photo addMe, IAlbum curralbum) {
		List<IAlbum> album1 = model.getUser(username).getAlbums();
		IPhoto getMe = null;
		for (int i = 0; i < model.getUser(username).getAlbums().size(); i++) {
			IAlbum temp2 = album1.get(i);
			List<IPhoto> photoList2 = temp2.getPhotoList();
			if(curralbum.getAlbumName().equals(temp2.getAlbumName())){
				continue;
			}
			InteractiveControl.PhotoCompareForNames comparePower2 = new InteractiveControl.PhotoCompareForNames();
			Collections.sort(photoList2, comparePower2);
			int index3 = Collections.binarySearch(photoList2,
					addMe.getFileName());
			if (index3 < 0) {
				continue;
			}
			getMe = photoList2.get(index3);
			if(getMe!=null){
				//we found the photo we want. Let's break;
			break;
			}
		}
		if (getMe != null) {
			System.out.println("do I really work here"); //TODO Remove aux.
			addMe.setDate(getMe.getDate());
			addMe.setCaption(getMe.getCaption());
			addMe.setLocationTag(getMe.getLocationTag());
			for(int i=0; i<getMe.getPeopleTags().size();i++){
				System.out.println(i);
				//addMe.getPeopleTags().addAll(getMe.getPeopleTags());
				addMe.personTag(getMe.getPeopleTags().get(i));
			}
			return addMe;
		} else {
			System.out.println("adfasdf here"); //TODO Handle error on GUI.
			return addMe;
		}
	}

	public void movePhoto(String albumIdSrc, String albumIdDest, String photoId) {
		List<IAlbum> album1 = model.getUser(username).getAlbums();
		InteractiveControl.AlbumCompare comparePower = new InteractiveControl.AlbumCompare();
		Collections.sort(album1, comparePower);
		int index = Collections.binarySearch(album1, albumIdSrc);
		if (index < 0) {
			String error = "album does not exist for user " + username + ":\n"
					+ albumIdSrc + "";
			setErrorMessage(error);
			showError();
			return;
		}
		IAlbum source = album1.get(index);
		index = Collections.binarySearch(album1, albumIdDest);
		if (index < 0) {
			String error = "album does not exist for user " + username + ":\n"
					+ albumIdDest + "";
			System.out.println("here");
			setErrorMessage(error);
			showError();
			return;

		}
		IAlbum destination = album1.get(index);
		/* PhotoCompareForNames */
		InteractiveControl.PhotoCompareForNames comparePower2 = new InteractiveControl.PhotoCompareForNames();
		List<IPhoto> thePhotos = source.getPhotoList();
		Collections.sort(thePhotos, comparePower2);
		index = Collections.binarySearch(thePhotos, photoId);
		if (index < 0) {
			String error = "File " + photoId + " does not exist in "
					+ albumIdSrc + "";
			setErrorMessage(error);
			showError();
			System.out.println("here? why here?");
			return;
		}
		/* Once moved, the photo gets removed from the source */
		if (source.getAlbumName().equals(destination.getAlbumName())) {
			return;
		}
		/*if the destination album already contains that photo, it should
		 * be an invalid move*/
		List<IPhoto> thePhotos2 = destination.getPhotoList();
		Collections.sort(thePhotos2, comparePower2);
		int index2 = Collections.binarySearch(thePhotos2, photoId);
		if (index2 >= 0) {
			String error = "File " + photoId + " already exists in "
					+ albumIdSrc + "";
			JButton showTextButton = new JButton();
			JOptionPane.showMessageDialog(showTextButton, error);
			return;
		}
		IPhoto moveMe = thePhotos.get(index);
		destination.addPhoto(moveMe);
		if(thePhotos2.size()==0){
			
			destination.setPic((Photo)moveMe);
		}
		
		source.deletePhoto(photoId);
		/*update the album that now contains the photo
		 * that has been moved: */
		getDate(destination);
		destination.setDateRange(begin, endz);
		destination.setOldestPhoto(begin);
		int counter=0;
		thePhotos2 = destination.getPhotoList();
		/*counts all photos in O(N)*/
		for(int i=0; i<thePhotos2.size();i++){
			counter++;
		}
		destination.updateNumOfPhotos(counter);
		singlealbumview.deleteElementFromVector(singlealbumview.getIndex());
		String success = "Moved photo " + photoId + ":\n" + photoId
				+ " - From album " + albumIdSrc + " to album " + albumIdDest
				+ "";
		
		setErrorMessage(success);
		showError();
	}

	@Override
	public void removePhoto(String albumId, String photoId) {
		List<IAlbum> album1 = model.getUser(username).getAlbums();
		InteractiveControl.AlbumCompare comparePower = new InteractiveControl.AlbumCompare();
		Collections.sort(album1, comparePower);
		System.out.println(albumId);
		int index = Collections.binarySearch(album1, albumId);
		if (index < 0) {
			String error = "album does not exist for user " + username + ":\n"
					+ albumId + "";
			setErrorMessage(error);
			showError();
			System.out.println("here");
			return;
		}
		IAlbum source = album1.get(index);
		System.out.println(source.getAlbumName());
		InteractiveControl.PhotoCompareForNames comparePower2 = new InteractiveControl.PhotoCompareForNames();
		List<IPhoto> thePhotos = source.getPhotoList();
		Collections.sort(thePhotos, comparePower2);
		index = Collections.binarySearch(thePhotos, photoId);
		if (index < 0) {
			String error = "Photo " + photoId + " does not exist in " + albumId
					+ "";
			setErrorMessage(error);
			showError();
			System.out.println("here 2\n" + photoId);
			return;
		}
		/* I wonder if I can even do this.. */
		source.deletePhoto(photoId);
		singlealbumview.deleteElementFromVector(singlealbumview.getIndex());
		String success = "Removed photo:\n" + photoId + " - From album "
				+ albumId + "";
		setErrorMessage(success);
		showError();
	}

	@Override
	public void addTag(String photoId, String tagType, String tagValue) {
		/* in case photo doesn't exist */
		List<IAlbum> albums = model.getUser(username).getAlbums();
		IPhoto getMe = null;
		boolean working = false;
		/* for only the current photo */
		/* below if for all the photos */
		outerLoop: for (int i = 0; i < model.getUser(username).getAlbums()
				.size(); i++) {
			IAlbum temp = albums.get(i);
			List<IPhoto> photoList = temp.getPhotoList();
			InteractiveControl.PhotoCompareForNames comparePower2 = new InteractiveControl.PhotoCompareForNames();
			Collections.sort(photoList, comparePower2);
			int index = Collections.binarySearch(photoList, photoId);
			if (index >= 0) {
				getMe = photoList.get(index);
				switch (tagType.toLowerCase()) {
				case "location":
					String check = getMe.getLocationTag();
					if ((check == null || check.isEmpty())) {
						getMe.setLocationTag(tagValue);
						String success = "Added tag:\n" + photoId + "\n"
								+ tagType + ":" + tagValue + "";
						setErrorMessage(success);
						showError();
						singlealbumview.setLocationTagLabel(tagValue);
						working = true;
						break;
					} else if (!check.isEmpty()) {
						JButton showTextButton = new JButton();
						String error = "Tag already exists for " + photoId
								+ " " + tagType + ":" + tagValue + "";
						JOptionPane.showMessageDialog(showTextButton, error);
						callTagGui((Photo) getMe);
						setErrorMessage(error);
						return;
					} else if (!(check.equals(tagValue))) {
						getMe.setLocationTag(tagValue);
						String success = "Added tag:\n" + photoId + "\n"
								+ tagType + ":" + tagValue + "";
						setErrorMessage(success);
						JButton showTextButton = new JButton();
						showError();
						String error = "Tag already exists for " + photoId
								+ " " + tagType + ":" + tagValue + "";
						JOptionPane.showMessageDialog(showTextButton, error);
						callTagGui((Photo) getMe);
						setErrorMessage(error);
						return;
					} else {
						String error = "Tag already exists for " + photoId
								+ " " + tagType + ":" + tagValue + "";
						setErrorMessage(error);
						return;
					}
				case "person":
					if (getMe.getPeopleTags().contains(tagValue)) {
						String error = "Tag already exists for " + photoId
								+ " " + tagType + ":" + tagValue + "";
						JButton showTextButton = new JButton();
						JOptionPane.showMessageDialog(showTextButton, error);
						callTagGui((Photo) getMe);
						setErrorMessage(error);
						showError();
						return;
					}
					getMe.personTag(tagValue);
					String success2 = "Added tag:\n" + photoId + "\n" + tagType
							+ ":" + tagValue + "";
					setErrorMessage(success2);
					showError();
					singlealbumview.updateTagList();
					break;
				default:
					String error = "Error: Not a real tag";
					setErrorMessage(error);
					showError();
					return;
				}
			}

		}
		if (getMe == null) {
			String error = "Photo " + photoId + " does not exist";
			setErrorMessage(error);
			showError();
			return;
		}
		/* random guesses atm in how to access these data structures */
	}

	@Override
	public void deleteTag(String photoId, String tagType, String tagValue) {
		List<IAlbum> albums = model.getUser(username).getAlbums();
		/* only for single */

		/* for all */
		IPhoto editMe = null;
		outerLoop: for (int i = 0; i < model.getUser(username).getAlbums()
				.size(); i++) {
			IAlbum temp = albums.get(i);
			List<IPhoto> photoList = temp.getPhotoList();
			InteractiveControl.PhotoCompareForNames comparePower2 = new InteractiveControl.PhotoCompareForNames();
			Collections.sort(photoList, comparePower2);
			int index = Collections.binarySearch(photoList, photoId);
			if (index >= 0) {
				editMe = photoList.get(index);
				switch (tagType) {
				case "location":
					if (editMe.getLocationTag().isEmpty()
							|| editMe.getLocationTag() == null) {
						String error = "Tag does not exist for "
								+ editMe.getFileName() + " " + tagType + ">:"
								+ tagValue + "";
						setErrorMessage(error);
						showError();
						return;
					} else {
						String tempz = editMe.getLocationTag();
						if (tempz.equals(tagValue)) {
							editMe.setLocationTag("");
							String success = "Deleted tag:\n" + photoId + " "
									+ tagType + ":" + tempz + "";
							setErrorMessage(success);
							showError();
							singlealbumview.setLocationTagBackToNull();
							break;
						} else {
							String error = "Tag does not exist for "
									+ editMe.getFileName() + " " + tagType
									+ ":" + tagValue + "";
							setErrorMessage(error);
							showError();
							return;

						}
					}
				case "person":

					if ((editMe.getPeopleTags().isEmpty())
							|| (!editMe.getPeopleTags().contains(tagValue))) {
						String error = "Tag does not exist for "
								+ editMe.getFileName() + " " + tagType + ":"
								+ tagValue + "";
						setErrorMessage(error);
						showError();
						return;
					} else {
						if (editMe.removePersonTag(tagValue)) {
							String success = "Deleted tag:\n" + photoId + " "
									+ tagType + ":" + tagValue + "";
							setErrorMessage(success);
							showError();
							singlealbumview.updateTagList();
							break;
						} else {
							String error = "Tag does not exist for "
									+ editMe.getFileName() + " " + tagType
									+ ":" + tagValue + "";
							setErrorMessage(error);
							showError();
							return;
						}

					}
				default:
					String error = "Error <Tag value doesn't exist>";
					setErrorMessage(error);
					showError();
					return;
				}
			}

		}
		if (editMe == null) {
			String error = "Photo " + photoId + " does not exist";
			setErrorMessage(error);
			showError();
			return;

		}

	}

	@Override
	public String getPAlbumNames(List<IAlbum> album, String photoId) {
		List<IAlbum> albums = model.getUser(username).getAlbums();
		IPhoto editMe = null;
		String albumNames = "";
		int first = 0;
		/** iterate each album, get their names if it contains the photoId */
		for (int i = 0; i < model.getUser(username).getAlbums().size(); i++) {
			IAlbum temp = albums.get(i);
			List<IPhoto> photoList = temp.getPhotoList();
			InteractiveControl.PhotoCompareForNames comparePower = new InteractiveControl.PhotoCompareForNames();
			Collections.sort(photoList, comparePower);
			int index = Collections.binarySearch(photoList, photoId);
			if (index >= 0) {
				if (first == 0) {
					albumNames = albumNames + "Album: " + temp.getAlbumName()
							+ "";
					editMe = temp.getPhotoList().get(index);
					first = 1;
				} else {
					albumNames = albumNames + ", [" + temp.getAlbumName() + "]";
				}
			}

		}
		return albumNames;

	}

	public void getPhotoInfo(String photoId) {
		/*
		 * NOT DONE WITH THIS. DO SEARCH IF AN ALBUM CONTAINS A PHOTO OR NOT,
		 * ITS NAME. MOVE stuff
		 */
		List<IAlbum> albums = model.getUser(username).getAlbums();
		IPhoto editMe = null;
		String albumNames = "";
		int first = 0;
		/** iterate each album, get their names if it contains the photoId */
		for (int i = 0; i < model.getUser(username).getAlbums().size(); i++) {
			IAlbum temp = albums.get(i);
			List<IPhoto> photoList = temp.getPhotoList();
			InteractiveControl.PhotoCompareForNames comparePower2 = new InteractiveControl.PhotoCompareForNames();
			Collections.sort(photoList, comparePower2);
			int index = Collections.binarySearch(photoList, photoId);
			if (index >= 0) {
				if (first == 0) {
					albumNames = albumNames + "Album: " + temp.getAlbumName()
							+ "";
					editMe = photoList.get(index);
					first = 1;
				} else {
					albumNames = albumNames + ", " + temp.getAlbumName() + "";
				}
			}

		}
		if (editMe == null) {
			String error = "Error: Photo doesn't exist";
			setErrorMessage(error);
			showError();
			return;
		}
		albumNames = albumNames + "\n";
		String locationTagz = "";
		String evenMore = "";
		if (!(editMe.getLocationTag() == null || editMe.getLocationTag()
				.isEmpty())) {
			locationTagz = "Location:" + editMe.getLocationTag() + "\n";
		}

		for (int i = 0; i < editMe.getPeopleTags().size(); i++) {
			evenMore = evenMore + "Name of People:"
					+ editMe.getPeopleTags().get(i) + "\n";
		}
		/* contains more tagsz */
		String success = "Photo file name: " + editMe.getFileName() + "\n"
				+ albumNames + "Date: " + editMe.getDateString()
				+ "\nCaption: " + editMe.getCaption() + "\n";
		success = success + locationTagz + evenMore;
		setErrorMessage(success);
		showError();
		return;

	}

	@Override
	public void getPhotosByDate(String start, String end) {
		//not done with this yet
		Date begin = null;
		Date endz = null;
		/* check if valid dates have been passed for both of the variables */
		try {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			dateFormat.setLenient(false);
			begin = dateFormat.parse(start);
			endz = dateFormat.parse(end);
		} catch (ParseException e) {
			String error = "Error: Invalid date for one of inputs";
			setErrorMessage(error);
			showError();
			return;
		}
		if (begin.after(endz)) {
			String error = "Error: Invalid dates! Your start is after your end";
			setErrorMessage(error);
			showError();
			return;
		}
		/*
		 * I don't need the this if but I'm going to keep it to grind your gears
		 */
		if (endz.before(begin)) {
			String error = "Error: Invalid dates! Your end date is before your start";
			setErrorMessage(error);
			showError();
			return;
		}
		String listPhotos = "";
		List<IAlbum> album1 = model.getUser(username).getAlbums();
		String success = "";
		String albumNames = "";
		for (int i = 0; i < album1.size(); i++) {
			IAlbum temp = album1.get(i);
			List<IPhoto> photoList = temp.getPhotoList();
			InteractiveControl.PhotoCompare comparePower = new InteractiveControl.PhotoCompare();
			Collections.sort(photoList, comparePower);
			for (int j = 0; j < photoList.size(); j++) {
				if (photoList.get(j).getDate().after(begin)
						&& photoList.get(j).getDate().before(endz)) {
					albumNames = getPAlbumNames(album1, photoList.get(j)
							.getFileName());
					listPhotos = listPhotos + ""
							+ photoList.get(j).getCaption() + " - "
							+ albumNames + "- Date: "
							+ photoList.get(j).getDateString() + "\n";
				}
			}
		}
		success = "Photos for user " + username + " in range " + start + " to "
				+ end + ":\n" + listPhotos;
		setErrorMessage(success);
		showError();

	}

	@Override
	public void getPhotosByTag(String tagType, String tagValue, String ori) {
		tagValue = tagValue.replace("\"", "");
		/* Assuming that it's by albums only */
		List<IAlbum> getMe = model.getUser(username).getAlbums();
		String validPhotos = "";
		String albumNames = "";
		String success = "";
		List<IPhoto> tempValid = new ArrayList<IPhoto>();
		if (tagType.equals("location")) {
			if (!(tagValue.isEmpty())) {
				for (int i = 0; i < getMe.size(); i++) {
					IAlbum temp = getMe.get(i);
					List<IPhoto> getPhotos = temp.getPhotoList();
					for (int j = 0; j < getPhotos.size(); j++) {
						if (!(getPhotos.get(j).getLocationTag() == null || getPhotos
								.get(j).getLocationTag().isEmpty())) {
							/*
							 * have to get it by photo info. Look at
							 * documentation
							 */
							if (getPhotos.get(j).getLocationTag()
									.equals(tagValue)) {
								tempValid.add(getPhotos.get(j));
							}
						}
					}
				}
			} else {
				for (int i = 0; i < getMe.size(); i++) {
					IAlbum temp = getMe.get(i);
					List<IPhoto> getPhotos = temp.getPhotoList();
					for (int j = 0; j < getPhotos.size(); j++) {
						if (!(getPhotos.get(j).getLocationTag() == null || getPhotos
								.get(j).getLocationTag().isEmpty())) {
							/*
							 * have to get it by photo info. Look at
							 * documentation
							 */
							tempValid.add(getPhotos.get(j));
						}
					}
				}
			}
			for (int j = 0; j < tempValid.size(); j++) {
				albumNames = getPAlbumNames((getMe), tempValid.get(j)
						.getFileName());
				validPhotos = validPhotos + "" + tempValid.get(j).getCaption()
						+ " - Album: " + albumNames + "- Date: "
						+ tempValid.get(j).getDateString() + "\n";
			}
			success = "Photos for user " + username + " with tags " + ori
					+ ":\n" + validPhotos;
			setErrorMessage(success);
			showError();
			return;
		}
		if (tagType.equals("person")) {

			if (!(tagValue.isEmpty())) {
				for (int i = 0; i < getMe.size(); i++) {
					IAlbum temp = getMe.get(i);
					List<IPhoto> getPhotos = temp.getPhotoList();
					for (int j = 0; j < getPhotos.size(); j++) {
						if ((!getPhotos.get(j).getPeopleTags().isEmpty() && getPhotos
								.get(j).getPeopleTags().contains(tagValue))) {
							albumNames = getPAlbumNames((getMe),
									getPhotos.get(j).getFileName());
							validPhotos = validPhotos + ""
									+ getPhotos.get(j).getCaption()
									+ " - Album: " + albumNames + "- Date: "
									+ getPhotos.get(j).getDateString() + "\n";
						}
					}
				}
			} else {
				for (int i = 0; i < getMe.size(); i++) {
					IAlbum temp = getMe.get(i);
					List<IPhoto> getPhotos = temp.getPhotoList();
					for (int j = 0; j < getPhotos.size(); j++) {
						if (!(getPhotos.get(j).getPeopleTags().isEmpty())) {
							albumNames = getPAlbumNames((getMe),
									getPhotos.get(j).getFileName());
							validPhotos = validPhotos + ""
									+ getPhotos.get(j).getCaption()
									+ " - Album: " + albumNames + "- Date: "
									+ getPhotos.get(j).getDateString() + "\n";
						}
					}
				}
			}
		} else {
			for (int i = 0; i < getMe.size(); i++) {
				IAlbum temp = getMe.get(i);
				List<IPhoto> getPhotos = temp.getPhotoList();
				for (int j = 0; j < getPhotos.size(); j++) {
					List<String> tagz = getPhotos.get(j).getPeopleTags();
					if (!(getPhotos.get(j).getLocationTag() == null || getPhotos
							.get(j).getLocationTag().isEmpty())) {
						if ((getPhotos.get(j).getLocationTag().equals(tagValue))) {
							albumNames = getPAlbumNames((getMe),
									getPhotos.get(j).getFileName());
							validPhotos = validPhotos + ""
									+ getPhotos.get(j).getCaption()
									+ " - Album: " + albumNames + "- Date: "
									+ getPhotos.get(j).getDateString() + "\n";

						}
					}

					if (tagz.contains(tagValue)) {
						System.out.println("test");
						albumNames = getPAlbumNames((getMe), getPhotos.get(j)
								.getFileName());
						validPhotos = validPhotos + ""
								+ getPhotos.get(j).getCaption() + " - Album: "
								+ albumNames + "- Date: "
								+ getPhotos.get(j).getDateString() + "\n";
					}

				}
			}
		}
		success = "Photos for user " + username + " with tags " + ori + ":\n"
				+ validPhotos;
		setErrorMessage(success);
		showError();

	}

	public void getDate(IAlbum letsGo) {
		begin = null;
		endz = null;
		List<IPhoto> photoList = letsGo.getPhotoList();
		begin = photoList.get(0).getDate();
		System.out.println(photoList.size());
		if (photoList.size() == 1) {
			endz = photoList.get(0).getDate();
		}
		for (int j = 1; j < photoList.size(); j++) {
			if (photoList.get(j).getDate().after(begin) && endz == null) {
				endz = photoList.get(j).getDate();
			}
			if (photoList.get(j).getDate().before(begin) && endz == null) {
				endz = begin;
				begin = photoList.get(j).getDate();
			}
			if (photoList.get(j).getDate().before(begin)) {
				begin = photoList.get(j).getDate();
			}

			if (endz != null && photoList.get(j).getDate().after(endz)) {
				endz = photoList.get(j).getDate();
			}
		}

	}

	@Override
	public void logout() {
		model.saveCurrentSession();
	}

	public void callRecaptionGui(Photo photo) {
		JFrame frame = new JFrame();
		RecaptionDialog test = new RecaptionDialog(frame, true);
		if (test.getBoolean() == true) {
			recaptionPhoto(photo.getFileName(), test.getCaption());
		}

	}

	public void callTagGui(Photo photo) {
		JFrame frame = new JFrame();
		List<IAlbum> getMe = model.getUser(username).getAlbums();
		InteractiveControl.AlbumCompare comparePower = new InteractiveControl.AlbumCompare();
		Collections.sort(getMe, comparePower);
		AddTag tagMe = new AddTag(frame, true, photo);
		if (tagMe.getBoolean() == true) {
			if (tagMe.getTagType().equals("location")) {
				singlealbumview.setDeleteLocationButton(true);
			}
			addTag(photo.getFileName(), tagMe.getTagType(), tagMe.getTagValue());

		}

	}

	public void callMoveGui(IAlbum source, Photo photo) {
		JFrame frame = new JFrame();
		List<IAlbum> getMe = model.getUser(username).getAlbums();
		InteractiveControl.AlbumCompare comparePower = new InteractiveControl.AlbumCompare();
		Collections.sort(getMe, comparePower);
		MovePhotoDialog moving = new MovePhotoDialog(frame, true,
				source.getAlbumName(), getMe, photo);
		if (moving.getBoolean() == true) {
			movePhoto(source.getAlbumName(), moving.getDest(),
					photo.getFileName());

		}
	}

	/* AlbumUI is instantiated here. */
	public void changeGui(IAlbum album) {
		view.hideMe();
		this.singlealbumview = new SingleAlbumUI(this,
				this.model.getUser(username), album);
		this.slideshow = new SlideShowUI(singlealbumview.getWindow());
		this.slideshow.registerThumbnailListSelection(new ThumbnailSelection());
		this.singlealbumview.registerSlideShowAction(new SlideShowListener());
		singlealbumview.initUI(album.getPhotoList());
	}
	public IPhoto checkIfiExistAlready(Photo addMe){
		List<IAlbum> album1=model.getUser(username).getAlbums();
		IPhoto getMe=null;
		for (int i=0; i<model.getUser(username).getAlbums().size(); i++){
			IAlbum temp2= album1.get(i);
			List<IPhoto> photoList2=temp2.getPhotoList();
			/*Collections.binarySearch(this.peopleTags, personName);*/
			InteractiveControl.PhotoCompareForNames comparePower2=new InteractiveControl.PhotoCompareForNames();
			Collections.sort(photoList2, comparePower2);
			int index3=Collections.binarySearch(photoList2, addMe.getFileName());
			if(index3<0){
				return addMe;
			}
			getMe=photoList2.get(index3);
			break;
		}
		if(getMe!=null){
			System.out.println("here");
			addMe.setDate(getMe.getDate());
			addMe.setCaption(getMe.getCaption());
			addMe.setLocationTag(getMe.getLocationTag());
			addMe.getPeopleTags().addAll(getMe.getPeopleTags());
			return addMe;
		}
		else{
			System.out.println("adfasdf here");
			return addMe;}
	}

	/**
	 * Destroys the current singlealbumview and tries to return back to the view that is a AlbumCollectionUI.
	 */
	public void changeGuiBack() {
		singlealbumview.destroy();
		if (!singlealbumview.getphotoslistModel().isEmpty()) {
			Photo setMe = (Photo) singlealbumview.getphotoslistModel().get(0);
			System.out.println(setMe.getFileName() + "here "
					+ singlealbumview.getphotoslistModel().size());
			getDate(singlealbumview.getCurrentAlbum());
			singlealbumview.getCurrentAlbum().setDateRange(begin, endz);
			singlealbumview.getCurrentAlbum().setOldestPhoto(begin);
			singlealbumview.getCurrentAlbum().updateNumOfPhotos(
					singlealbumview.getNumOfPhotos());
			singlealbumview = null;
			view.setPic(setMe);
		} else {
			//set the pic to the default image
			String path = System.getProperty("user.dir");
			BufferedImage myPicture = null;
			try {
				myPicture = ImageIO.read(new File(
						IPhotoModel.defaultUserImgPath)); //TODO Retrieve path from model.
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<IPhoto> photoList=singlealbumview.getCurrentAlbum().getPhotoList();
				singlealbumview.getCurrentAlbum().setDateRange(null, null);
				singlealbumview.getCurrentAlbum().setOldestPhoto(null);
				singlealbumview.getCurrentAlbum().updateNumOfPhotos(
				0);
			

			BufferedImage reSized = singlealbumview.resizeImage(myPicture, 1,
					100, 100);
			view.setDefault(new ImageIcon(reSized));
			view.showMe();
		}
		singlealbumview = null;
		view.showMe();
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public void setUsername(String username) {
		if (this.model.userExists(username))
			this.username = username;
	}
	
	private class ThumbnailSelection implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			IPhoto curr = slideshow.getSelectedPhoto();
			if(curr != null) {
				slideshow.setMainPhoto(curr);
			}
		}
	}

	private class SlideShowListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			slideshow.setDefaultState();
			slideshow.setThumbnails(singlealbumview.getCurrentAlbum()
					.getPhotoList());
			slideshow.setVisible(true);
		}
	}
}