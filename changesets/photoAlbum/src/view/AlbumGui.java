package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

import control.InteractiveControl;
import model.Album;
import model.IAlbum;
import model.IPhoto;
import model.IPhotoAdminModel;
import model.Photo;

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
    private JButton deleteLocationTag;
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
    private JLabel locationTag;
    private JPanel buttonsInfo;
    int getIndex;
    ImageIcon picLabel;
	Photo re;
	private IPhotoAdminModel model;
	private InteractiveControl control;
	private IAlbum currentAlbum;
	private Date begin=null;
	private Date endz=null;
	private int getTagIndex;
	private int numberOfPhotos;
	private BufferedImage myPicture400;
	
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
    //	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
            	control.changeGuiBack();
            	control.logout();
                System.exit(0);
            }
        } );
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
        BufferedImage myPicture;
		try {
//			System.out.println("delete element here?");
			String path= System.getProperty("user.dir");
	//		System.out.println(path+"\\photo\\default.jpg");
		//	myPicture = ImageIO.read(new File("C:\\Users\\User\\workspace\\photoAlbum\\vlcsnap-2014-01-26-22h22m21s223.png"));
			myPicture = ImageIO.read(new File(path+"\\photo\\default.png"));
			System.out.println(path+"\\photo\\default.png");
			BufferedImage reSized=resizeImage(myPicture, 1, 400,400);
			listInfo.setIcon(new ImageIcon(reSized));
		} catch (IOException e) {
			System.out.println("or here?");
			// TODO Auto-generated catch block
		}
        buttonsInfo=new JPanel();
        buttonsInfo.add(addTag);
        buttonsInfo.add(deleteTag);
        
        photoInfo.add(listInfo);
        locationTag=new JLabel("No location tag yet");
        
        date=new JLabel("No Date");
        caption=new JLabel("No Caption");
        photoInfo.add(date);
        photoInfo.add(caption);
        recaption=new JButton("Recaption");
        deleteLocationTag=new JButton("Delete Location Tag");
        recaption.setEnabled(false);
        photoInfo.add(recaption);
        photoInfo.add(locationTag);
        deleteLocationTag.setEnabled(false);
        photoInfo.add(deleteLocationTag);
        vector=new <Photo>DefaultListModel();
		jlwi= new JListWithImage(vector);
		tagz=new JList();
		tagz.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {		
				if(vector.isEmpty()){
					remove.setEnabled(false);
					move.setEnabled(false);
					recaption.setEnabled(false);
					deleteTag.setEnabled(false);
				}
				if(!vector.isEmpty()){
					
			//		deleteTag.setEnabled(true);
					Photo test=(Photo)vector.get(getIndex);
					if(!test.getPeopleTags().isEmpty()){
						getTagIndex=tagz.getSelectedIndex();
						deleteTag.setEnabled(true);
					}
					else{
						deleteTag.setEnabled(false);
					}
				}
			}
		}
			
		);
		TitledBorder title;
		title = BorderFactory.createTitledBorder("[People Tags]");
		title.setTitleJustification(TitledBorder.CENTER);
		tagz.setBorder(BorderFactory.createTitledBorder(title));
//		photoInfo.add(tagz);
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
					System.out.println("TESTING: "+currentAlbum +test.getCaption());
					if(test.getCaption()!=null && !test.getCaption().isEmpty()){
						caption.setText(test.getCaption());
						}else{
							caption.setText("No Caption for this Photo");
						}
					if(test.getLocationTag()!=null && !test.getLocationTag().isEmpty()){
						locationTag.setText(test.getLocationTag());
						deleteLocationTag.setEnabled(true);
						}
					else{
						locationTag.setText("No current location for this tag. Add one?");
						deleteLocationTag.setEnabled(false);
					}
					
					tagz.setListData(test.getPeopleTags().toArray());
					if(test.getPeopleTags().isEmpty()){
						deleteTag.setEnabled(false);
					}
					else{
						deleteTag.setEnabled(true);
					}
					
					
				//	System.out.println(test.getCaption());
					}
				
				}
					
			}
		}
			
		);
		jlwi.setAlignmentX(Component.LEFT_ALIGNMENT);
		JPanel tagListPanel = new JPanel(null);
        tagListPanel.setPreferredSize(new Dimension(200, 200));
        JScrollPane scrollPaneTag = new JScrollPane(tagz);
        scrollPaneTag.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneTag.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneTag.setBounds(50, 50, 150, 150);
      //  scrollPane.setMinimumSize(new Dimension(200, 200));
        scrollPaneTag.setPreferredSize(new Dimension(200, 200));
        tagListPanel.add(scrollPaneTag);
        tagListPanel.setAlignmentX( Component.CENTER_ALIGNMENT );
        photoInfo.add( tagListPanel);
        photoInfo.add(buttonsInfo);
		/*JPanel tagListPanel = new JPanel(null);
        tagListPanel.setPreferredSize(new Dimension(200, 520));
        JScrollPane scrollPaneTag = new JScrollPane(tagz);
        scrollPaneTag.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneTag.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneTag.setBounds(50, 50, 150, 150);
      //  scrollPane.setMinimumSize(new Dimension(200, 200));
        scrollPaneTag.setPreferredSize(new Dimension(200, 200));
        tagListPanel.add(scrollPaneTag);*/
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
		deleteTag.setEnabled(false);
		deleteTag.addActionListener(new ButtonListener()); 
		deleteLocationTag.addActionListener(new ButtonListener()); 
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
        		numberOfPhotos++;
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
		frame.setSize(1200,900);
    	frame.setVisible(true);
    }
    public void updateTagList(){
    	Photo test=(Photo)vector.get(getIndex);
    	tagz.setListData(test.getPeopleTags().toArray());
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
//				System.out.println("delete element here?");
				String path= System.getProperty("user.dir");
		//		System.out.println(path+"\\photo\\default.jpg");
			//	myPicture = ImageIO.read(new File("C:\\Users\\User\\workspace\\photoAlbum\\vlcsnap-2014-01-26-22h22m21s223.png"));
				myPicture = ImageIO.read(new File(path+"\\photo\\default.png"));
				System.out.println(path+"\\photo\\default.png");
				BufferedImage reSized=resizeImage(myPicture, 1, 400,400);
				listInfo.setIcon(new ImageIcon(reSized));
				numberOfPhotos=0;
			} catch (IOException e) {
				System.out.println("or here?");
				// TODO Auto-generated catch block
				return;
			}
		}
        numberOfPhotos--;
        System.out.println("I came here"+i);
        photos.revalidate();
    }
    public void setLocationTagLabel(String value){
    	locationTag.setText(value);
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
    			    if(file==null){
    			    	
    			    	//stupid fix
    			    	return;
    			    }
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
    					/*String albumId, String photoFileName,
			String photoCaption*/
    					boolean yes=currentAlbum.addPhoto(re);
    					if(yes==true){
    						re=(Photo) control.checkIfiExistAlready(re);
    					vector.addElement(re);
    					jlwi.setListData(vector.toArray());
    					jlwi.setSelectedValue(re, true);
    					//		photos.add(jlwi);
    					System.out.println("test");
    				/*	if(begin==null && endz==null){
    						begin=re.getDate();
    					}
    					if(re.getDate().after(begin)){
    						endz=re.getDate();
    					}
    					if(re.getDate().before(begin)){
    						begin=re.getDate();
    					}*/
    					/*photoList.get(j).getDate().after(begin) && photoList.get(j).getDate().before(endz)*/
    					photos.revalidate();
    					numberOfPhotos++;
    					}
    					else{
    						JButton showTextButton=new JButton();
							String error="Photo already exits for you!!";
							JOptionPane.showMessageDialog(showTextButton,error);
    					}
    				}
    			} catch (IOException e1) {
				
				return;
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
    			if(getIndex!=-1){
					Photo test=(Photo) vector.get(getIndex);
					System.out.println("Testing"+test.getFileName()+getIndex+currentAlbum.getAlbumName());
					control.callTagGui( test);
				}
    			
    		}
    		else if(source==move){
    			if(getIndex!=-1){
					Photo test=(Photo) vector.get(getIndex);
					System.out.println("Testing"+test.getFileName()+getIndex+currentAlbum.getAlbumName());
					control.callMoveGui(currentAlbum, test);
				}
    		}
    		else if(source==deleteTag){
    			if(getIndex!=-1){
					Photo test=(Photo) vector.get(getIndex);
					if(getTagIndex!=-1){
						/*deleteTag(String photoId, String tagType, String tagValue)*/
						String tagValue=test.getPeopleTags().get(getTagIndex);
						control.deleteTag(test.getFileName(), "person",tagValue);
					}
				//	control.callMoveGui(currentAlbum, test);
				}
    		}
    		else if(source==deleteLocationTag){
    			Photo test=(Photo) vector.get(getIndex);
				if(getTagIndex!=-1){
					/*deleteTag(String photoId, String tagType, String tagValue)*/
					String tagValue=test.getLocationTag();
					System.out.println("testing what is the tag: "+tagValue);
					control.deleteTag(test.getFileName(), "location",tagValue);
					deleteLocationTag.setEnabled(false);
				}
    			
    		}
    		else if(source==backToAlbums){
    			control.changeGuiBack();
    		}
    }
   }
    
    public void tryAgain(){
    	try {
			JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(null);
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "JPG, PNG, & GIF Images", "jpg", "gif", "png");
		    chooser.setFileFilter(filter);
		    file=chooser.getSelectedFile();
		    if(file==null){
		    	tryAgain();
		    	
		    	//stupid fix
		    	return;
		    }
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
				re=(Photo) control.checkIfiExistAlready(re);
				currentAlbum.addPhoto(re);
				vector.addElement(re);
				jlwi.setListData(vector.toArray());
				jlwi.setSelectedValue(re, true);
				//		photos.add(jlwi);
				System.out.println("test");
				/*if(begin==null && endz==null){
					begin=re.getDate();
				}
				if(re.getDate().after(begin)){
					endz=re.getDate();
				}
				if(re.getDate().before(begin)){
					begin=re.getDate();
				}*/
				/*photoList.get(j).getDate().after(begin) && photoList.get(j).getDate().before(endz)*/
				numberOfPhotos++;
				photos.revalidate();
			}
		} catch (IOException e1) {
		
			tryAgain();
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
    public int getNumOfPhotos(){
    	return numberOfPhotos;
    }
    public void setLocationTagBackToNull(){
    	locationTag.setText("No current location for this tag. Add one?");
    }
    public void setDeleteLocationButton(boolean b) {
		// TODO Auto-generated method stub
    	deleteLocationTag.setEnabled(b);
	}
    public void setDeleteTagButton(boolean b){
    	deleteTag.setEnabled(b);
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
