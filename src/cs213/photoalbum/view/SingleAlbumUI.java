package cs213.photoalbum.view;

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
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
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

import cs213.photoalbum.control.InteractiveControl;

import cs213.photoalbum.model.IAlbum;

import cs213.photoalbum.model.IPhoto;

import cs213.photoalbum.model.IPhotoModel;

import cs213.photoalbum.model.IUser;

import cs213.photoalbum.model.Photo;



/**
 * @author Conrado Uraga & Mark Labrador
 * 
 *         Secondary window that is triggered by the UserAlbumUI.
 */
public class SingleAlbumUI {
	//TODO Following functions belong in the AlbumCollectionUI because it is a search across all photos.
//	private JButton searchDATE;
//	private JButton searchTAGS;
	private int getIndex;
	private int getTagIndex;
	private int numberOfPhotos;
	
	private JFrame frame;
	private JPanel buttonsPhoto;
	private JPanel photoInfo;
	private JPanel photos;
	private JPanel buttonsInfo;
	
	private JButton add;
	private JButton remove;
	private JButton move;
	
	/*Mark's Slide Show Implementation*/
	private JButton slideshow;
	
	private JButton recaption;
	private JButton addTag;
	private JButton deleteTag;
	private JButton deleteLocationTag;
	private JButton backToAlbums;
	
	/*Lists*/
	private JListWithImage photoslist;
	private JList peopletagslist;	
	private DefaultListModel<IPhoto> photoslistModel;
	
	/*Labels*/
	private JLabel listInfo;
	private JLabel date;
	private JLabel caption;
	private JLabel locationTag;
	private JLabel photosLbl;

	private ImageIcon picLabel;
	private Photo re;
	
	private InteractiveControl control;
	private IAlbum curralbum;
	private IUser curruser;

	/* Have a class the extends the JLabels so we can remove them */
	/**
	 * The view controls the control instead of the other way around.
	 * 
	 * @param control A control.
	 * @param curruser The current user this albumUI is running for.
	 * @param curralbum Album to be accessed under a specific user.
	 */
	public SingleAlbumUI(InteractiveControl control, IUser curruser, IAlbum curralbum) {
		
		this.control = control;
		this.curruser = curruser;
		this.curralbum = curralbum;
		setup();
	/*	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}*/
	}

	private void setup() {
		this.frame = new JFrame(this.curruser.getUsername() + " Album: " + this.curralbum.getAlbumName());
		frame.addWindowListener(new WindowAdapter() {//TODO Restrict closing of window to exit the program.  Have it return to the login view.
			@Override
			public void windowClosing(WindowEvent we) {
				control.changeGuiBack(); //TODO Figure out how to bring this event outside of the view.
			}
		});
		this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		photosLbl = new JLabel("<html><body><font size=\"7\">Photos</font></body></html>");
		add = new JButton("Add");
		remove = new JButton("remove");
		backToAlbums = new JButton("Back to Albums");
		remove.setEnabled(false);
		move = new JButton("move");
		move.setEnabled(false);
		slideshow = new JButton("Slideshow");
		recaption = new JButton("Recaption");
		addTag = new JButton("Add Tag");
		deleteTag = new JButton("Delete Tag");
		buttonsPhoto = new JPanel();
		photoInfo = new JPanel();
		
		/*Button ActionListeners*/
		ButtonListener stateChange = new ButtonListener();
		add.addActionListener(stateChange);
		remove.addActionListener(stateChange);
		move.addActionListener(stateChange);
		recaption.addActionListener(stateChange);
		addTag.addActionListener(stateChange);
		backToAlbums.addActionListener(stateChange);
		
		photoInfo.setLayout(new BoxLayout(photoInfo, BoxLayout.PAGE_AXIS));
		photoInfo.setBorder(BorderFactory.createTitledBorder("<html><body><font size=\"6\">[Photo Info]</font></body></html>"));
		listInfo = new JLabel();
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File(IPhotoModel.defaultUserImgPath));
			BufferedImage reSized = resizeImage(myPicture, 1, 400, 400);
			listInfo.setIcon(new ImageIcon(reSized));
		} catch (IOException e) {
			//TODO Handle errors and exceptions in the AlbumUI window.
	//		System.out.println("or here?");
		}
		buttonsInfo = new JPanel();
		buttonsInfo.add(addTag);
		buttonsInfo.add(deleteTag);

		photoInfo.add(listInfo);
		locationTag = new JLabel("No location tag yet");

		date = new JLabel("No Date");
		caption = new JLabel("No Caption");
		photoInfo.add(date);
		photoInfo.add(caption);
	//	recaption = new JButton("Recaption");
		deleteLocationTag = new JButton("Delete Location Tag");
		recaption.setEnabled(false);
		addTag.setEnabled(false);
		photoInfo.add(recaption);
		photoInfo.add(locationTag);
		deleteLocationTag.setEnabled(false);
		photoInfo.add(deleteLocationTag);
		photoslistModel = new<Photo> DefaultListModel();
		photoslist = new JListWithImage(photoslistModel);
		peopletagslist = new JList();
		peopletagslist.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (photoslistModel.isEmpty()) {
					remove.setEnabled(false);
					move.setEnabled(false);
					recaption.setEnabled(false);
					deleteTag.setEnabled(false);
					
				}
				if (!photoslistModel.isEmpty()) {
					Photo test = (Photo) photoslistModel.get(getIndex);
					if (!test.getPeopleTags().isEmpty()) {
						getTagIndex = peopletagslist.getSelectedIndex();
						deleteTag.setEnabled(true);
					} else {
						deleteTag.setEnabled(false);
					}
				}
			}
		}

		);
		TitledBorder title;
		title = BorderFactory.createTitledBorder("<html><body><font size=\"4\">[People Tags]</font></body></html>");
		title.setTitleJustification(TitledBorder.CENTER);
		peopletagslist.setBorder(BorderFactory.createTitledBorder(title));
		peopletagslist.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		peopletagslist.setVisibleRowCount(-1);
		peopletagslist.setAlignmentX(Component.LEFT_ALIGNMENT);
		photoslist
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		photoslist.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		photoslist.setVisibleRowCount(-1);
		photoslist.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
			//	System.out.println(getIndex);
				if (photoslistModel.isEmpty()) {
					remove.setEnabled(false);
					move.setEnabled(false);
					recaption.setEnabled(false);
					addTag.setEnabled(false);
					deleteTag.setEnabled(false);
				}
				if (!photoslistModel.isEmpty()) {
					remove.setEnabled(true);
					move.setEnabled(true);
					recaption.setEnabled(true);
					addTag.setEnabled(true);
					getIndex = photoslist.getSelectedIndex();
					if (getIndex != -1) {
						Photo test = (Photo) photoslistModel.get(getIndex);
						listInfo.setIcon(test.getPhoto());
						String date1 = test.getDateString();
						date.setText(date1);
				//		System.out.println(test.getFileName());
				//		System.out.println("TESTING: " + curralbum
				//				+ test.getCaption());
						if (test.getCaption() != null
								&& !test.getCaption().isEmpty()) {
							caption.setText(test.getCaption());
						} else {
							caption.setText("No Caption for this Photo");
						}
						if (test.getLocationTag() != null
								&& !test.getLocationTag().isEmpty()) {
							locationTag.setText(test.getLocationTag());
							deleteLocationTag.setEnabled(true);
						} else {
							locationTag
									.setText("No current location for this tag. Add one?");
							deleteLocationTag.setEnabled(false);
						}

						peopletagslist.setListData(test.getPeopleTags().toArray());
						if (test.getPeopleTags().isEmpty()) {
							deleteTag.setEnabled(false);
						} 
					}

				}

			}
		}

		);
		photoslist.setAlignmentX(Component.LEFT_ALIGNMENT);
		JPanel tagListPanel = new JPanel(null);
		tagListPanel.setPreferredSize(new Dimension(200, 200));
		JScrollPane scrollPaneTag = new JScrollPane(peopletagslist);
		scrollPaneTag
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneTag
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneTag.setBounds(80, 50, 400, 150);
		scrollPaneTag.setPreferredSize(new Dimension(200, 200));
		tagListPanel.add(scrollPaneTag);
		tagListPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		photoInfo.add(tagListPanel);
		photoInfo.add(buttonsInfo);
		buttonsPhoto.add(add);
		buttonsPhoto.add(remove);
		buttonsPhoto.add(move);
		buttonsPhoto.add(slideshow);
		buttonsPhoto.add(backToAlbums);
		photos = new JPanel(new GridLayout(0, 3));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;

		deleteTag.setEnabled(false);
		deleteTag.addActionListener(new ButtonListener());
		deleteLocationTag.addActionListener(new ButtonListener());
		frame.add(photosLbl, gbc);
		JPanel contentPane = new JPanel(null);
		contentPane.setPreferredSize(new Dimension(500, 500));
		JScrollPane scrollPane = new JScrollPane(photoslist);
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(50, 50, 450, 450);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		contentPane.add(scrollPane);

		/* This belongs in place of "MOVED to setup()" in initUI */
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.gridx = 0;
		gbc.gridy = 1;
		frame.add(contentPane, gbc);

		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		frame.add(buttonsPhoto, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		frame.add(photoInfo, gbc);
		gbc.gridx = 2;
		gbc.gridy = 2;
		frame.setSize(1200, 900);
	}

	public void initUI(List<IPhoto> list) {


		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				numberOfPhotos++;
				photoslistModel.addElement(list.get(i));
				photoslist.setListData(photoslistModel.toArray());
				photos.revalidate();
			}
		}
		/* MOVED to setup() */
		frame.setVisible(true);
	}

	public void updateTagList() {
		Photo test = (Photo) photoslistModel.get(getIndex);
		peopletagslist.setListData(test.getPeopleTags().toArray());
	}

	public void deleteElementFromVector(int i) {
		photoslistModel.remove(i);
		photoslist.setListData(photoslistModel.toArray());
		photoslist.setSelectedIndex(0);
		if (photoslistModel.isEmpty()) {
			remove.setEnabled(false);
			move.setEnabled(false);
			recaption.setEnabled(false);
			addTag.setEnabled(false);
			locationTag.setText("No location tag yet");
			DefaultListModel listmodel=new DefaultListModel();
			peopletagslist.setListData(listmodel.toArray());
			deleteLocationTag.setEnabled(false);
			deleteTag.setEnabled(false);
			BufferedImage myPicture;
			try {
				caption.setText("No Photos");
				date.setText("No Photos");
				String path = System.getProperty("user.dir");
				myPicture = ImageIO
						.read(new File(IPhotoModel.defaultUserImgPath));
			//	System.out.println(IPhotoModel.defaultUserImgPath);
				BufferedImage reSized = resizeImage(myPicture, 1, 400, 400);
				listInfo.setIcon(new ImageIcon(reSized));
				numberOfPhotos = 0;
			} catch (IOException e) {
			//	System.out.println("or here?");
				return;
			}
		}
		numberOfPhotos--;
	//	System.out.println("I came here" + i);
		photos.revalidate();
	}

	public void setLocationTagLabel(String value) {
		locationTag.setText(value);
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			if (source == add) {
				try {
					/* Setup the JFileChooser. */
					JFileChooser fc = new JFileChooser();
					fc.setFileFilter(new FileNameExtensionFilter(
							"JPG, PNG, & GIF Images", "jpg", "gif", "png"));
					fc.showOpenDialog(frame);
					File file = fc.getSelectedFile();
					if (file == null) {

						//stupid fix
						return;
					}
					BufferedImage myPicture = ImageIO.read(file);
					BufferedImage myPictureForInfo = resizeImage(myPicture, 1,
							400, 400);
					BufferedImage reSized = resizeImage(myPicture, 1, 140, 140);
					picLabel = new ImageIcon(myPictureForInfo);
					re = new Photo(curralbum.getAlbumName(), fc
							.getSelectedFile().getName(),
							new ImageIcon(reSized), picLabel);

					long dateRaw = file.lastModified();
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(dateRaw);
					cal.set(Calendar.MILLISECOND, 0);
					re.setDate(cal.getTime());

					JFrame testz = new JFrame();
					AddPhoto test = new AddPhoto(testz, re, true);
					if (test.getBoolean() == true) {
						/*
						 * String albumId, String photoFileName, String
						 * photoCaption
						 */
						boolean yes = curralbum.addPhoto(re);
						if (yes == true) {
							re = (Photo) control.photoExistsInAlbum(re, curralbum);
			//				System.out.println(re.getCaption());
							photoslistModel.addElement(re);
							photoslist.setListData(photoslistModel.toArray());
							photoslist.setSelectedValue(re, true);
							updateTagList();
							photos.revalidate();
							numberOfPhotos++;
						} else {
							JButton showTextButton = new JButton();
							String error = "Photo already exists for you.";
							JOptionPane
									.showMessageDialog(showTextButton, error);
						}
					}
				} catch (IOException e1) {
					return;
				}
			} else if (source == remove) {
				if (getIndex != -1) {
					Photo test = (Photo) photoslistModel.get(getIndex);
		//			System.out.println("Testing" + test.getFileName()
		//					+ getIndex + curralbum.getAlbumName()); //TODO Remove aux.
					control.removePhoto(curralbum.getAlbumName(),
							test.getFileName());
				}
			} else if (source == recaption) {
		//		System.out.println("I was clicked");
				
				if (getIndex != -1) {
					Photo test = (Photo) photoslistModel.get(getIndex);
			//		System.out.println("Testing recpation" + test.getFileName()
			//				+ getIndex + curralbum.getAlbumName()); //TODO Remove aux.
					control.callRecaptionGui(test);
				}
			} else if (source == addTag) {
			//	System.out.println("I was clicked for tags" );
				if (getIndex != -1) {
					Photo test = (Photo) photoslistModel.get(getIndex); //TODO Disable add tag if no photo is currently selected.
			//		System.out.println("Testing" + test.getFileName()
			//				+ getIndex + curralbum.getAlbumName()); //TODO Remove aux.
					control.callTagGui(test);
				}

			} else if (source == move) {
				if (getIndex != -1) {
					Photo test = (Photo) photoslistModel.get(getIndex);
		//			System.out.println("Testing" + test.getFileName()
			//				+ getIndex + curralbum.getAlbumName()); //TODO Remove aux.
					control.callMoveGui(curralbum, test);
				}
			} else if (source == deleteTag) {
				if (getIndex != -1) {
					Photo test = (Photo) photoslistModel.get(getIndex);
					if (getTagIndex != -1) {
						String tagValue = test.getPeopleTags().get(getTagIndex);
						control.deleteTag(test.getFileName(), "person",
								tagValue);
						if(test.getPeopleTags().isEmpty()){
							deleteTag.setEnabled(false);
						}
					}
				}
			} else if (source == deleteLocationTag) {
				Photo test = (Photo) photoslistModel.get(getIndex);
				if (getTagIndex != -1) {
					String tagValue = test.getLocationTag();
		//			System.out.println("testing what is the tag: " + tagValue); //TODO Remove aux.
					control.deleteTag(test.getFileName(), "location", tagValue);
					deleteLocationTag.setEnabled(false);
				}
			} else if (source == backToAlbums) {
				control.changeGuiBack();
			}
		}
	}

	public void tryAgain() {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(null);
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"JPG, PNG, & GIF Images", "jpg", "gif", "png");
			chooser.setFileFilter(filter);
			File file = chooser.getSelectedFile();
			if (file == null) {
				tryAgain();

				//stupid fix
				return;
			}
			BufferedImage myPicture = ImageIO.read(file);
			BufferedImage myPictureForInfo = resizeImage(myPicture, 1, 400, 400);
			BufferedImage reSized = resizeImage(myPicture, 1, 140, 140);
			picLabel = new ImageIcon(myPictureForInfo);
			re = new Photo(curralbum.getAlbumName(), chooser
					.getSelectedFile().getName(), new ImageIcon(reSized),
					picLabel);
			long dateRaw = file.lastModified();
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(dateRaw);
			cal.set(Calendar.MILLISECOND, 0);
			re.setDate(cal.getTime());
			JFrame testz = new JFrame();
			AddPhoto test = new AddPhoto(testz, re, true);
	//		System.out.println(test.getBoolean());
	//		System.out.println(chooser.getSelectedFile().getName());
			if (test.getBoolean() == true) {
				re = (Photo) control.photoExistsInAlbum(re, curralbum);
				curralbum.addPhoto(re);
				photoslistModel.addElement(re);
				photoslist.setListData(photoslistModel.toArray());
				photoslist.setSelectedValue(re, true);
		//		System.out.println("test");
				numberOfPhotos++;
				photos.revalidate();
			}
		} catch (IOException e1) {
			tryAgain();
		}

	}

	public BufferedImage resizeImage(BufferedImage originalImage, int type,
			int IMG_WIDTH, int IMG_HEIGHT) {
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT,
				type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();

		return resizedImage;
	}

	public IAlbum getCurrentAlbum() {
		return this.curralbum;
	}

	public DefaultListModel<IPhoto> getphotoslistModel() {
		return this.photoslistModel;
	}

	public void setCaption(String setMe) {
		caption.setText(setMe);
	}

	public int getIndex() {
		return getIndex;
	}

	public int getNumOfPhotos() {
		return numberOfPhotos;
	}

	public void setLocationTagBackToNull() {
		locationTag.setText("No current location for this tag. Add one?");
	}

	public void setDeleteLocationButton(boolean b) {
		deleteLocationTag.setEnabled(b);
	}

	public void setDeleteTagButton(boolean b) {
		deleteTag.setEnabled(b);
	}

	/**
	 * @author Mark Labrador
	 * @param slideshowAL
	 *            Respond to the slideshow control being activated.
	 */
	public void registerSlideShowAction(ActionListener slideshowAL) {
		this.slideshow.addActionListener(slideshowAL);
	}

	/**
	 * @author Mark Labrador
	 * @param frameWL
	 *            Respond to the frame's window events.
	 */
	public void registerFrameWindow(WindowListener frameWL) {
		this.frame.addWindowListener(frameWL);
	}

	public void destroy() {
		frame.dispose();
	}
	
	/**
	 * @author Mark Labrador
	 * @return Returns the frame responsible for displaying this control.
	 */
	public JFrame getWindow() {
		return this.frame;
	}
}