package cs213.photoalbum.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JLabel;

public class Photo extends JLabel implements IPhoto {
	private static final long serialVersionUID = 1L;
	private String photoId;
	private String fileName;
	private String caption;
	private Date photoDate;
	private String locationTag;
	private ArrayList<String> peopleTags;
	private Icon myOriginalPhoto;
	private Icon myResized;

	public Photo(String photoId, String fileName, Icon image, Icon ori) {
		super(image);
		this.myResized = image;
		this.myOriginalPhoto = ori;
		this.photoId = photoId;
		this.fileName = fileName;
		this.locationTag = "test";
		this.peopleTags = new ArrayList<String>();
	}

	public Photo() {
		super();
	}

	@Override
	public int compareTo(String o) {
		return this.fileName.compareTo(o);
	}

	@Override
	public String getPhotoID() {
		return this.photoId;
	}

	@Override
	public void setPhotoID(String photoId) {
		this.photoId = photoId;
	}

	@Override
	public String getFileName() {
		return this.fileName;
	}

	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String getCaption() {
		return this.caption;
	}

	@Override
	public void setCaption(String caption) {
		this.caption = caption;
	}

	@Override
	public Date getDate() {
		return this.photoDate;
	}

	@Override
	public void setDate(Date photoDate) {
		this.photoDate = photoDate;
	}

	@Override
	public String getLocationTag() {
		return this.locationTag;
	}

	@Override
	public void setLocationTag(String locationTag) {
		this.locationTag = locationTag;
	}

	@Override
	public List<String> getPeopleTags() {
		ArrayList<String> defensiveCopy = new ArrayList<String>();
		defensiveCopy.addAll(this.peopleTags);
		return defensiveCopy;
	}

	@Override
	public Icon getPhoto() {
		return myOriginalPhoto;
	}

	@Override
	public Icon getResized() {
		return myResized;
	}

	@Override
	public void setPhoto(Icon setMe) {
		myOriginalPhoto = setMe;
	}

	@Override
	public void setResized(Icon setMe) {
		myResized = setMe;
	}

	@Override
	public void personTag(String personName) {
		Collections.sort(this.peopleTags);
		int index = Collections.binarySearch(this.peopleTags, personName);
		if (index < 0 || !this.peopleTags.get(index).equals(personName))
			this.peopleTags.add(personName);
	}

	@Override
	public boolean removePersonTag(String personName) {
		Collections.sort(this.peopleTags);
		int index = Collections.binarySearch(this.peopleTags, personName);
		if (index >= 0 && this.peopleTags.get(index).equals(personName)) {
			this.peopleTags.remove(index);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getDateString() {
		return new SimpleDateFormat("MM/dd/yyyy-HH:MM:SS")
				.format(this.photoDate);
	}

	@Override
	public String getFilePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFilePath(String filePath) {
		// TODO Auto-generated method stub
		
	}
}