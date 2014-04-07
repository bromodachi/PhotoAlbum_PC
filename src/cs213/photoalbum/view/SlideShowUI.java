package cs213.photoalbum.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import cs213.photoalbum.model.IPhoto;
import cs213.photoalbum.model.IPhotoModel;
import cs213.photoalbum.model.PhotoModel;

public class SlideShowUI extends JDialog{
	private static final long serialVersionUID = 1L;

	/*
	 * TODO Create thumbnail list to the left of the control.
	 * TODO Capture right and left arrow keys on the keyboard.
	 * TODO Create controls for right to left scroll transition.
	 * TODO Load photos into the thumbnail list & display by default no photo.
	 * TODO Create main staging area for the photo.
	 */
	private int defaultEmptyPhotoSize = 50;
	
	private JPanel mainphotoContainer;
	
	private GridBagConstraints mpc;
	private JLabel mainphoto;
	private JLabel emptyphoto;
	
	private DefaultListModel<IPhoto> thumbsModel;
	private JList<IPhoto> thumbslist;
	
	public SlideShowUI(Frame owner) {
		super(owner);
		setup();
	}
	
	private void setup() {
		/*Root Setup*/
		Dimension ssd = new Dimension(700, 700);
		this.setTitle("Slide Show");
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(ssd);
		this.setMinimumSize(ssd);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		
		/*Components Setup*/
		this.mainphotoContainer = new JPanel();
		
		this.mainphoto = new JLabel();
		this.emptyphoto = new JLabel();
		
		this.thumbsModel = new DefaultListModel<IPhoto>();
		this.thumbslist = new JList<IPhoto>(thumbsModel);
		
		/*Thumbnails Scroll Panel*/
		JScrollPane thumbsScroll = new JScrollPane();
		thumbsScroll.setAlignmentX(LEFT_ALIGNMENT);
		thumbsScroll.setViewportView(this.thumbslist);
		this.thumbslist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.thumbslist.setLayoutOrientation(JList.VERTICAL);
		this.thumbslist.setCellRenderer(new ThumbnailRenderer(this.thumbslist.getWidth()));
		
		/*Main Photo Container*/
		this.mainphotoContainer.setLayout(new GridBagLayout());
		
		try{
			this.emptyphoto.setIcon(PhotoModel.createIcon(IPhotoModel.defaultNoPhotoImgPath, defaultEmptyPhotoSize, defaultEmptyPhotoSize));
		} catch (Exception e) {
			this.emptyphoto.setText("No Image Available");
		}
		
		this.mpc = new GridBagConstraints();
		mpc.gridx = 0;
		mpc.gridy = 0;
		mpc.weightx = 1;
		mpc.weighty = 1;
		mpc.insets = new Insets(5,5,5,5);
		mpc.fill = GridBagConstraints.BOTH;
		mpc.anchor = GridBagConstraints.CENTER;
		this.mainphotoContainer.add(emptyphoto, mpc);
		
		/*Root Level*/
		GridBagConstraints rootc = new GridBagConstraints();
		rootc.gridx = 0;
		rootc.gridy = 0;
		rootc.weightx = 1.1;
		rootc.weighty = 1;
		rootc.insets = new Insets(5,5,5,5);
		rootc.fill = GridBagConstraints.BOTH;
		rootc.anchor = GridBagConstraints.CENTER;
		this.add(thumbsScroll,rootc);
		
		rootc.gridx = 1;
		rootc.gridy = 0;
		rootc.weightx = 1;
		rootc.weighty = 1;
		rootc.fill = GridBagConstraints.BOTH;
		rootc.anchor = GridBagConstraints.CENTER;
		this.add(this.mainphotoContainer, rootc);
	}
	
	public void setMainPhoto(IPhoto photo) {
		if(photo == null || photo.getPhoto() == null) {
			this.mainphotoContainer.removeAll();
			this.mainphotoContainer.add(emptyphoto);
		} else {
			try {
				this.mainphotoContainer.removeAll();
				this.mainphoto.setIcon(photo.getPhoto());
				this.mainphotoContainer.add(mainphoto);
			} catch(Exception e) {
				this.mainphotoContainer.removeAll();
				this.mainphotoContainer.add(emptyphoto);
			}
		}
		this.mainphotoContainer.repaint();
	}
	
	public void setDefaultState() {
		this.thumbsModel.clear();
		this.thumbslist.clearSelection();
		this.mainphotoContainer.removeAll();
	}
	
	public void setThumbnails(List<IPhoto> photos) {
		for(IPhoto p : photos) {
			this.thumbsModel.addElement(p);
		}
	}
	
	public IPhoto getSelectedPhoto() {
		return this.thumbslist.getSelectedValue();
	}
	
	public void registerThumbnailListSelection(ListSelectionListener thumbnailLSL) {
		this.thumbslist.getSelectionModel().addListSelectionListener(thumbnailLSL);
	}
	
	private class ThumbnailRenderer extends JLabel implements ListCellRenderer<IPhoto> {
		private static final long serialVersionUID = 1L;
		private int icon_size;
		
		public ThumbnailRenderer(int icon_size) {
			this.icon_size = icon_size;
		}

		@Override
		public Component getListCellRendererComponent(JList list, IPhoto value,
				int index, boolean isSelected, boolean cellHasFocus) {
			this.setBackground(isSelected ? Color.gray : Color.white);
			try{
				this.setIcon(value.getResized());
			} catch(Exception e) {
				this.setText("No Image Available");
			}
			setMainPhoto(value);
			return this;
		}
	}
}
