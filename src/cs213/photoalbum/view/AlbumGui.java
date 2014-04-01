package cs213.photoalbum.view;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import cs213.photoalbum.control.IInteractiveControl;
import cs213.photoalbum.control.InteractiveControl;
import cs213.photoalbum.model.Album;
import cs213.photoalbum.model.IAlbum;
import cs213.photoalbum.model.IPhoto;
import cs213.photoalbum.model.IPhotoAdminModel;
import cs213.photoalbum.model.Photo;

public class AlbumGui {
	
	private JButton add;
    private JButton remove;
    private JButton move;
    private JButton slideshow;
    private JButton searchDATE;
    private JButton searchTAGS;
    private JButton recaption;
    private JButton addTag;
    private JButton deleteTag;
    private JButton backToAlbums;
    private JFrame frame;
    private JPanel buttonsPhoto;
    private JPanel photoInfo;
    private JPanel photos;
    private JListWithImage jlwi;
    private DefaultListModel vector;
    private File file;
    private JLabel listInfo;
    private JLabel date;
    private JLabel caption;
    private JList tagz;
    private JPanel buttonsInfo;
    int getIndex;
    ImageIcon picLabel;
	Photo re;
	private IPhotoAdminModel model;
	private InteractiveControl control;
	private IAlbum currentAlbum;
	/*Have a class the extends the JLabels so we can remove them*/
	public  AlbumGui(InteractiveControl controller, IPhotoAdminModel modelz, IAlbum album){
    	this.model=modelz;
    	this.control=controller;
    	this.currentAlbum=album;
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
         //   new AlbumGui().initUI();
        //    new UserAlbum().initUI();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	
    }
    public void initUI(List<IPhoto> list){
    	frame = new JFrame("default name");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setLayout(new GridBagLayout());
    	JLabel label = new JLabel("Photos");
    	add = new JButton("Add");
        remove = new JButton("remove");
        backToAlbums=new JButton("Back to Albums");
        remove.setEnabled(false);
        move = new JButton("move");
        move.setEnabled(false);
        slideshow = new JButton("Slideshow");
        addTag=new JButton("Add Tag");
        deleteTag=new JButton("Delete Tag");
        buttonsPhoto= new JPanel();
        photoInfo=new JPanel();
        photoInfo.setLayout(new BoxLayout(photoInfo,BoxLayout.PAGE_AXIS));
        photoInfo.setBorder(BorderFactory.createTitledBorder("[Photo Info]"));
        listInfo=new JLabel();
        buttonsInfo=new JPanel();
        buttonsInfo.add(addTag);
        buttonsInfo.add(deleteTag);
        
        photoInfo.add(listInfo);
        date=new JLabel("No Date");
        caption=new JLabel("No Caption");
        photoInfo.add(date);
        photoInfo.add(caption);
        recaption=new JButton("Recaption");
        recaption.setEnabled(false);
        photoInfo.add(recaption);
        vector=new <Photo>DefaultListModel();
		jlwi= new JListWithImage(vector);
		tagz=new JList();
		tagz.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {		
				if(vector.isEmpty()){
					remove.setEnabled(false);
					move.setEnabled(false);
					recaption.setEnabled(false);
				}
			}
		}
			
		);
		photoInfo.add(buttonsInfo);
		TitledBorder title;
		title = BorderFactory.createTitledBorder("[Tags]");
		title.setTitleJustification(TitledBorder.CENTER);
		tagz.setBorder(BorderFactory.createTitledBorder(title));
		photoInfo.add(tagz);
		jlwi.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jlwi.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		jlwi.setVisibleRowCount(-1);
		jlwi.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {		
				System.out.println(getIndex);
				if(vector.isEmpty()){
					remove.setEnabled(false);
					move.setEnabled(false);
					recaption.setEnabled(false);
				}
				if(!vector.isEmpty()){
					remove.setEnabled(true);
					move.setEnabled(true);
					recaption.setEnabled(true);
					getIndex=jlwi.getSelectedIndex();
					if(getIndex!=-1){
					Photo test=(Photo)vector.get(getIndex);
					listInfo.setIcon(test.getPhoto());
					String date1=test.getDateString();
					date.setText(date1);
					System.out.println(test.getFileName());
					if(test.getCaption()!=null && !test.getCaption().isEmpty()){
						caption.setText(test.getCaption());
						}
					else{
						caption.setText("No Caption for this Photo");
					}
					tagz.setListData(test.getPeopleTags().toArray());
					
					
				//	System.out.println(test.getCaption());
					}
				
				}
					
			}
		}
			
		);
        buttonsPhoto.add(add);
        buttonsPhoto.add(remove);
        buttonsPhoto.add(move);
        buttonsPhoto.add(slideshow);
        buttonsPhoto.add(backToAlbums);
        photos=new JPanel(new GridLayout(0,3));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
		gbc.gridy = 0;
		add.addActionListener(new ButtonListener()); 
		remove.addActionListener(new ButtonListener()); 
		move.addActionListener(new ButtonListener()); 
		recaption.addActionListener(new ButtonListener()); 
		addTag.addActionListener(new ButtonListener()); 
		backToAlbums.addActionListener(new ButtonListener()); 
		frame.add(label, gbc);
		JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(500, 500));
        JScrollPane scrollPane = new JScrollPane(jlwi);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(50, 50, 450, 450);
      //  scrollPane.setMinimumSize(new Dimension(200, 200));
        scrollPane.setPreferredSize(new Dimension(500, 500));
        contentPane.add(scrollPane);
        if(!list.isEmpty()){
        	for(int i=0; i<list.size();i++){
        		vector.addElement(list.get(i));
        		jlwi.setListData(vector.toArray());  
        	//	mainPanel.add(jlwi);
        		photos.revalidate();
        	}
        }
        gbc.anchor=GridBagConstraints.FIRST_LINE_START;
		gbc.gridx = 0;
		gbc.gridy = 1;
	//	gbc.anchor = GridBagConstraints.WEST;
		frame.add(contentPane, gbc);

	/*	gbc.gridx = 0;
		gbc.gridy = 2;
		frame.add(tableButtonPanel, gbc);*/
		gbc.anchor=GridBagConstraints.FIRST_LINE_START;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
	//	gbc.anchor = GridBagConstraints.WEST;
		frame.add(buttonsPhoto, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		frame.add(photoInfo, gbc);
		gbc.gridx = 2;
		gbc.gridy = 2;
	//	frame.add(buttonsInfo, gbc);
		frame.setSize(1200,700);
    	frame.setVisible(true);
    }
    public void deleteElementFromVector(int i){
    	vector.remove(i);
    //	jlwi.setListData(vector);
    	jlwi.setListData(vector.toArray());
        jlwi.setSelectedIndex(0);
        if(vector.isEmpty()){
			remove.setEnabled(false);
			move.setEnabled(false);
			recaption.setEnabled(false);
			BufferedImage myPicture;
			try {
				caption.setText("No Photos");
				date.setText("No Photos");
				System.out.println("delete element here?");
				myPicture = ImageIO.read(new File("C:\\Users\\User\\workspace\\photoAlbum\\vlcsnap-2014-01-26-22h22m21s223.png"));
				BufferedImage reSized=resizeImage(myPicture, 1, 400,400);
				listInfo.setIcon(new ImageIcon(reSized));
			} catch (IOException e) {
				System.out.println("or here?");
				// TODO Auto-generated catch block
				return;
			}
		}
        System.out.println("I came here"+i);
        photos.revalidate();
    }
    private class ButtonListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		JButton source = (JButton)e.getSource();
    		if (source == add) {
    			try {
    				JFileChooser chooser = new JFileChooser();
    				chooser.showOpenDialog(null);
    			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
    			        "JPG, PNG, & GIF Images", "jpg", "gif", "png");
    			    chooser.setFileFilter(filter);
    			    file=chooser.getSelectedFile();
    				BufferedImage myPicture = ImageIO.read(file);
    				BufferedImage myPictureForInfo=resizeImage(myPicture, 1, 400,400);
    				BufferedImage reSized=resizeImage(myPicture, 1, 140,140);
    				picLabel = new ImageIcon(myPictureForInfo);
    				//(String photoId, String fileName, Icon image)
    				re = new Photo(currentAlbum.getAlbumName(),chooser.getSelectedFile().getName(), new ImageIcon(reSized), picLabel);
    				/*vector=new <JLabels>DefaultListModel();
    				 * jlwi= new JListWithImage(vector);*/
    				long dateRaw=file.lastModified();
    				Calendar cal = Calendar.getInstance();
    				cal.setTimeInMillis(dateRaw);
    				cal.set(Calendar.MILLISECOND, 0);
    				re.setDate(cal.getTime());
    				JFrame testz=new JFrame();
    				addPhoto test=new addPhoto(testz, re, true);
    				System.out.println(test.getBoolean());
    				System.out.println(chooser.getSelectedFile().getName());
    				if(test.getBoolean()==true){
    					currentAlbum.addPhoto(re);
    					vector.addElement(re);
    					jlwi.setListData(vector.toArray());
    					jlwi.setSelectedValue(re, true);
    					//		photos.add(jlwi);
    					System.out.println("test");
    					photos.revalidate();
    				}
    			} catch (IOException e1) {
				
				e1.printStackTrace();
				}
    	}
    		else if(source==remove){
    			if(getIndex!=-1){
					Photo test=(Photo) vector.get(getIndex);
					System.out.println("Testing"+test.getFileName()+getIndex+currentAlbum.getAlbumName());
					control.removePhoto(currentAlbum.getAlbumName(), test.getFileName());
					}
    		}
    		else if(source==recaption){
    			if(getIndex!=-1){
					Photo test=(Photo) vector.get(getIndex);
					System.out.println("Testing"+test.getFileName()+getIndex+currentAlbum.getAlbumName());
					control.callRecaptionGui(test);
					}
    		}
    		else if(source==addTag){
    			JFrame testz=new JFrame();
    			Photo thePhoto=(Photo)vector.get(getIndex);
    			addTag test=new addTag(testz, true, thePhoto);
    			if(test.getBoolean()==true){
    				tagz.setListData(thePhoto.getPeopleTags().toArray());
					photoInfo.revalidate();
				}
    			
    		}
    		else if(source==move){
    			if(getIndex!=-1){
					Photo test=(Photo) vector.get(getIndex);
					System.out.println("Testing"+test.getFileName()+getIndex+currentAlbum.getAlbumName());
					control.callMoveGui(currentAlbum, test);
				}
    		}
    		else if(source==backToAlbums){
    			control.changeGuiBack();
    		}
    }
   }
    public BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT){
    	BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
    	Graphics2D g = resizedImage.createGraphics();
    	g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
    	g.dispose();
     
    	return resizedImage;
        }
    public IAlbum getCurrentAlbum(){
    	return this.currentAlbum;
    }
    public DefaultListModel getVector(){
    	return this.vector;
    }
    public void die(){
    	frame.dispose();
    }
    public void setCaption(String setMe){
    	caption.setText(setMe);
    }
    public int getIndex(){
    	return getIndex;
    }
	 public static void main(String[] args) {
	        try {
	            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	         //   new AlbumGui().initUI();
	        //    new UserAlbum().initUI();
	        } catch (ClassNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (InstantiationException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IllegalAccessException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (UnsupportedLookAndFeelException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	

}
