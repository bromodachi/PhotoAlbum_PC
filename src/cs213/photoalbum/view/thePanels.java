package cs213.photoalbum.view;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class thePanels extends JPanel {
	int i;
	private String name;
	private JLabel albumName;
	private JLabel dateRange;
	private JLabel lastUpdated;
	JLabel picLabel;
	JLabel re;
	public thePanels(String nam){
		super();
		this.name=nam;
		/*newPanel = new JPanel();
        newPanel.setPreferredSize(new Dimension(380, 100));
        try {
			BufferedImage myPicture = ImageIO.read(new File("C:\\Users\\User\\Pictures\\vlcsnap-2014-01-26-22h22m21s223.png"));
			BufferedImage reSized=resizeImage(myPicture, 1, 100,100);
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			JLabel re = new JLabel(new ImageIcon(reSized));
			
			newPanel.add(re);
			i++;
			JLabel albumName = new JLabel("Default Name"+i);
			newPanel.add(albumName);
			dateRange= new JLabel("3-28-2014");
			lastUpdated= new JLabel("3-28-2014");
				} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}*/
	}
	public void createPanel(String name){
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(380, 150));
		GridBagConstraints gbc = new GridBagConstraints();
		try {
			BufferedImage myPicture = ImageIO.read(new File("C:\\Users\\User\\Pictures\\vlcsnap-2014-01-26-22h22m21s223.png"));
			BufferedImage reSized=resizeImage(myPicture, 1, 100,100);
			this.picLabel = new JLabel(new ImageIcon(myPicture));
			this.re = new JLabel(new ImageIcon(reSized));
			gbc.anchor = GridBagConstraints.WEST;
	        gbc.fill = GridBagConstraints.BOTH;
	        gbc.gridx = 0;
	        gbc.gridy = 1;
			this.add(re, gbc);
			albumName = new JLabel(name);
			gbc.anchor = GridBagConstraints.FIRST_LINE_START;
	        gbc.gridx = 1;
	        gbc.gridwidth = 2;
	        gbc.gridy = 1;
			this.add(albumName, gbc);
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
	public void renameAlbum(String rename){
		this.albumName.setText(rename);
	}
	public void setName(String rename){
		this.name=rename;
	}
	public String getName(){
		return name;
	}
	public JPanel returnPanel(){
		return this;
	}
    public static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT){
    	BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
    	Graphics2D g = resizedImage.createGraphics();
    	g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
    	g.dispose();
     
    	return resizedImage;
        }
}