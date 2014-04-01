package cs213.photoalbum.model;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Album extends JPanel  implements IAlbum{
	private String albumName;
	private JLabel albumNameForLabel;
	private ArrayList<IPhoto> photoList;
	private JLabel dateRange;
	private JLabel lastUpdated;
	JLabel picLabel;
	JLabel re;
	
	public Album(String albumName) {
		super();
		this.albumName = albumName;
		this.photoList = new ArrayList<IPhoto>();
	}
	public void createPanel(String name){
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(380, 150));
		GridBagConstraints gbc = new GridBagConstraints();
		try {
			BufferedImage myPicture = ImageIO.read(new File("C:\\Users\\User\\workspace\\photoAlbum\\vlcsnap-2014-01-26-22h22m21s223.png"));
			BufferedImage reSized=resizeImage(myPicture, 1, 100,100);
			this.picLabel = new JLabel(new ImageIcon(myPicture));
			this.re = new JLabel(new ImageIcon(reSized));
			gbc.anchor = GridBagConstraints.WEST;
	        gbc.fill = GridBagConstraints.BOTH;
	        gbc.gridx = 0;
	        gbc.gridy = 1;
			this.add(re, gbc);
			albumNameForLabel = new JLabel(albumName);
			gbc.anchor = GridBagConstraints.FIRST_LINE_START;
	        gbc.gridx = 1;
	        gbc.gridwidth = 2;
	        gbc.gridy = 1;
			this.add(albumNameForLabel, gbc);
			dateRange= new JLabel("Range: 3-28-2014");
			gbc.anchor = GridBagConstraints.SOUTHWEST;
	        gbc.gridx = 0;
	        gbc.gridy = 3;
	        gbc.weightx = 0.33;
	        gbc.weighty = 0.5;
	        gbc.gridheight = 2;
			this.add(dateRange, gbc);
			lastUpdated= new JLabel("Last Updated: 3-28-2014");
			gbc.anchor=GridBagConstraints.SOUTH;
	        gbc.gridx = 1;
	        gbc.gridy = 3;
	        gbc.weightx = 0.33;
	        gbc.weighty = 0.5;
	        gbc.gridheight = 2;
	        
			this.add(lastUpdated, gbc);
			invalidate();
			validate();
		} catch (IOException e1) {
				
				e1.printStackTrace();
				}
	}

	@Override
	public String getAlbumName() {
		return this.albumName;
	}
	/*public void renameAlbum(String rename){
		this.albumName=rename;
		this.albumNameForLabel.setText(rename);
		
	}*/

	@Override
	public void setAlbumName(String albumName) {
		this.albumName=albumName;
		this.albumNameForLabel.setText(albumName);
	}

	@Override
	public int getAlbumSize() {
		return this.photoList.size();
	}

	@Override
	public void addPhoto(IPhoto photo) {
		Collections.sort(this.photoList, new PhotoComparator());
		int index = Collections.binarySearch(this.photoList, photo.getFileName());
		if(index < 0) {
			this.photoList.add(photo);
		}
		else if(!this.photoList.get(index).getFileName().equals(photo.getFileName())) {
			this.photoList.add(photo);
		}
	}

	@Override
	public void deletePhoto(String fileName) {
		Collections.sort(this.photoList, new PhotoComparator());
		int index = Collections.binarySearch(this.photoList, fileName);
		if(index >= 0 && this.photoList.get(index).getFileName().equals(fileName))	this.photoList.remove(index);
	}

	@Override
	public void recaptionPhoto(String id, String caption) {
		Collections.sort(this.photoList, new PhotoComparator());
		int index = Collections.binarySearch(this.photoList, id);
		if(index >= 0 && this.photoList.get(index).getFileName().equals(id))	this.photoList.get(index).setCaption(caption);
	}

	@Override
	public int compareTo(String o) {
		return this.albumName.compareTo(o);
	}

	@Override
	public List<IPhoto> getPhotoList() {
		ArrayList<IPhoto> defensiveCopy = new ArrayList<IPhoto>();
		defensiveCopy.addAll(this.photoList);
		return defensiveCopy;
	}
	
	private class PhotoComparator implements Comparator<IPhoto> {
		@Override
		public int compare(IPhoto o1, IPhoto o2) {
			return o1.getFileName().compareTo(o2.getFileName());
		}
	}
	
	@Override
	public void setPic(Photo setMe){
		this.re.setIcon(setMe.getResized());
	}
	
	public static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT){
    	BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
    	Graphics2D g = resizedImage.createGraphics();
    	g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
    	g.dispose();
     
    	return resizedImage;
        }
	@Override
	public void setIcon(Photo setMe) {
		// TODO Auto-generated method stub
		
	}
}