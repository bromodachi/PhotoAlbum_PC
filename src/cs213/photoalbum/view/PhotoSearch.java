package cs213.photoalbum.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentListener;

import cs213.photoalbum.model.IPhoto;
import cs213.photoalbum.model.TagType;

public class PhotoSearch extends JDialog implements IErrorView{
	private static final long serialVersionUID = 1L;
	private JLabel errorLbl;
	private JLabel errorMsg;
	private JLabel searchCritLbl;
	
	private JComboBox<TagType> tagTypeSelect;
	
	private JTextField searchCrit;
	private DatePanel searchDate;
	
	private PhotoSearchList photoSearchList;
	
	private JButton searchBtn;
	private JButton cancelBtn;
	
	private Dimension standardDim;
	private Dimension resultsDim;
	private Dimension datesDim;
	
	public PhotoSearch(Frame owner) {
		super(owner);
		setup();
	}
	
	private void setup() {
		this.standardDim = new Dimension(400, 200);
		this.resultsDim = new Dimension(400, 400);
		this.datesDim = new Dimension(400, 300);
		
		this.setPreferredSize(standardDim);
		this.setMinimumSize(standardDim);
		this.setLayout(new GridBagLayout());
		
		this.errorLbl = new JLabel("Error");
		this.errorMsg = new JLabel();
		this.searchCritLbl = new JLabel("Search Criteria");
		
		this.tagTypeSelect = new JComboBox<TagType>();
		
		this.searchCrit = new JTextField();
		this.searchDate = new DatePanel(this.getWidth(), this.getHeight());
		
		this.photoSearchList = new PhotoSearchList();
		
		this.searchBtn = new JButton("Search");
		this.cancelBtn = new JButton("Cancel");
		

		
		for(TagType t : TagType.values()) {
			this.tagTypeSelect.addItem(t);
		}
		
		GridBagConstraints rootsc = new GridBagConstraints();
		rootsc.gridx = 0;
		rootsc.gridy = 0;
		rootsc.weightx = 0;
		rootsc.weighty = 0.33;
		rootsc.gridwidth = 1;
		rootsc.insets = new Insets(5,5,5,5);
		rootsc.fill = GridBagConstraints.NONE;
		rootsc.anchor = GridBagConstraints.EAST;
		this.add(errorLbl, rootsc);
		
		rootsc.gridx = 1;
		rootsc.gridy = 0;
		rootsc.weightx = 1;
		rootsc.weighty = 0.33;
		rootsc.gridwidth = 1;
		rootsc.insets = new Insets(5,5,5,5);
		rootsc.fill = GridBagConstraints.HORIZONTAL;
		rootsc.anchor = GridBagConstraints.WEST;
		this.add(errorMsg, rootsc);
		
		rootsc.gridx = 0;
		rootsc.gridy++;
		rootsc.weightx = 1;
		rootsc.weighty = 0.33;
		rootsc.gridwidth = 2;
		rootsc.insets = new Insets(5,5,5,5);
		rootsc.fill = GridBagConstraints.HORIZONTAL;
		rootsc.anchor = GridBagConstraints.CENTER;
		this.add(tagTypeSelect, rootsc);
		
		//Search Label
		rootsc.gridx = 0;
		rootsc.gridy++;
		rootsc.weightx = 0;
		rootsc.weighty = 0;
		
		rootsc.gridwidth = 1;
		rootsc.fill = GridBagConstraints.HORIZONTAL;
		rootsc.anchor = GridBagConstraints.CENTER;
		this.add(searchCritLbl, rootsc);
		
		//Search Text Field
		rootsc.gridx = 1;

		rootsc.weightx = 1;
		rootsc.weighty = 0.33;
		
		rootsc.fill = GridBagConstraints.HORIZONTAL;
		rootsc.anchor = GridBagConstraints.CENTER;
		this.add(searchCrit, rootsc);
		
		/*Search Date Panel*/
		rootsc.gridx = 0;
		rootsc.gridy++;
		rootsc.weightx = 1;
		rootsc.weighty = 0;
		rootsc.gridwidth = 2;
		rootsc.fill = GridBagConstraints.BOTH;
		rootsc.anchor = GridBagConstraints.CENTER;
		this.add(searchDate, rootsc);
		
		/*Search Operation Panel*/
		JPanel searchOps = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		searchOps.add(searchBtn);
		searchOps.add(cancelBtn);
		
		rootsc.gridx = 0;
		rootsc.gridy++;
		rootsc.weightx = 1;
		rootsc.weighty = 0;
		rootsc.gridwidth = 2;
		rootsc.fill = GridBagConstraints.BOTH;
		rootsc.anchor = GridBagConstraints.CENTER;
		this.add(searchOps, rootsc);
		
		/*Photo Search List Results*/
		rootsc.gridx = 0;
		rootsc.gridy++;
		rootsc.weightx = 1;
		rootsc.weighty = 0.5;
		
		rootsc.fill = GridBagConstraints.BOTH;
		rootsc.anchor = GridBagConstraints.CENTER;
		this.add(photoSearchList, rootsc);
		
		searchDate.setVisible(false);
		hideError();
	}
	
	public void setDefaultState() {
		this.hideError();
		this.hideDateSearch();
		this.tagTypeSelect.setSelectedIndex(0);
		this.photoSearchList.setDefaultState();
		this.photoSearchList.setPreferredSize(standardDim);
		this.photoSearchList.setVisible(false);
	}
	
	@Override
	public boolean isError() {
		return this.errorMsg.isVisible();
	}
	
	@Override
	public void showError(String errorMsg) {
		this.errorMsg.setText(errorMsg);
		this.errorLbl.setVisible(true);
		this.errorMsg.setVisible(true);
	}
	
	@Override
	public void hideError() {
		this.errorMsg.setText("");
		this.errorLbl.setVisible(false);
		this.errorMsg.setVisible(false);
	}
	
	public TagType getSearchType() {
		return (TagType)this.tagTypeSelect.getSelectedItem();
	}
	
	public Date getStartDate() {
		try {
			return this.searchDate.getStartDate();
		} catch (ParseException e) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(0);
			Date today = cal.getTime();
			return today;
		}
	}
	
	public Date getEndDate() {
		try {
			return this.searchDate.getEndDate();
		} catch (ParseException e) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(0);
			Date today = cal.getTime();
			return today;
		}
	}
	
	public String getTag() {
		return this.searchCrit.getText();
	}
	
	public void showTagSearch() {
		this.photoSearchList.setMinimumSize(standardDim);
		this.photoSearchList.setPreferredSize(standardDim);
		this.searchCritLbl.setVisible(true);
		this.searchCrit.setVisible(true);
	}
	
	public void hideTagSearch() {
		this.photoSearchList.setMinimumSize(standardDim);
		this.photoSearchList.setPreferredSize(standardDim);
		this.searchCritLbl.setVisible(false);
		this.searchCrit.setVisible(false);
		this.searchCrit.setText("");
	}
	
	public void showDateSearch() {
		this.photoSearchList.setMinimumSize(datesDim);
		this.photoSearchList.setPreferredSize(datesDim);
		this.searchDate.setVisible(true);
	}
	
	public void hideDateSearch() {
		this.photoSearchList.setMinimumSize(standardDim);
		this.photoSearchList.setPreferredSize(standardDim);
		this.searchDate.setVisible(false);
	}
	
	public void showPhotoResults() {
		this.photoSearchList.setMinimumSize(resultsDim);
		this.photoSearchList.setPreferredSize(resultsDim);
		this.photoSearchList.setVisible(true);
	}
	
	public void hidePhotoResults() {
		this.photoSearchList.setMinimumSize(standardDim);
		this.photoSearchList.setPreferredSize(standardDim);
		this.photoSearchList.setDefaultState();;
		this.photoSearchList.setVisible(false);
	}
	
	public void enableSearch() {
		this.searchBtn.setEnabled(true);
	}
	
	public void disableSearch() {
		this.searchBtn.setEnabled(false);
	}
	
	public void loadPhotoResults(List<IPhoto> photos) {
		this.photoSearchList.loadPhotoSearchResults(photos);
	}
	
	public List<IPhoto> getSelectedPhotos() {
		return this.photoSearchList.getSelectedPhotos();
	}
	
	public void registerSearchCritDocument(DocumentListener searchDoc) {
		this.searchCrit.getDocument().addDocumentListener(searchDoc);
	}
	
	public void registerTagTypeAction(ActionListener tagAL) {
		this.tagTypeSelect.addActionListener(tagAL);
	}
	
	public void registerSearchAction(ActionListener searchAL) {
		this.searchBtn.addActionListener(searchAL);
	}
	
	public void registerCancelAction(ActionListener cancelAL) {
		this.cancelBtn.addActionListener(cancelAL);
	}
	
	/********************************
	 * Helper Classes
	 ********************************/
	private class PhotoSearchList extends JPanel implements IErrorView {
		private static final long serialVersionUID = 1L;
		private DefaultListModel<IPhoto> photosModel;
		private JScrollPane photosScroll;
		private JList<IPhoto> photos;
		
		private JButton createAlbum;
		private JButton cancel;
		
		private JLabel errorLbl;
		private JLabel errorMsg;
		
		private PhotoSearchList() {
			setup();
		}
		
		private void setup() {
			this.setLayout(new GridBagLayout());
			this.photosModel = new DefaultListModel<IPhoto>();
			this.photos = new JList<IPhoto>(this.photosModel);
			this.photosScroll = new JScrollPane(this.photos);
			this.createAlbum = new JButton("Create Album");
			this.cancel = new JButton("Cancel");
			this.errorLbl = new JLabel("Error");
			this.errorMsg = new JLabel();
			
			/*Photo List Setup*/
			this.photosScroll.setAlignmentX(LEFT_ALIGNMENT);
			this.photosScroll.setViewportView(this.photos);
			this.photos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			this.photos.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			this.photos.setCellRenderer(new SearchImageItem());
			GridBagConstraints pslc = new GridBagConstraints();
			
			/*Error Components*/
			pslc.gridx = 0;
			pslc.gridy = 0;
			pslc.weightx = 0;
			pslc.weighty = 0;
			pslc.gridwidth = 1;
			pslc.insets = new Insets(5,5,5,5);
			pslc.fill = GridBagConstraints.NONE;
			pslc.anchor = GridBagConstraints.EAST;
			this.add(errorLbl, pslc);
			
			pslc.gridx = 1;
			pslc.gridy = 0;
			pslc.weightx = 1;
			pslc.weighty = 0;
			pslc.gridwidth = 1;
			pslc.insets = new Insets(5,5,5,5);
			pslc.fill = GridBagConstraints.HORIZONTAL;
			pslc.anchor = GridBagConstraints.CENTER;
			this.add(errorMsg, pslc);
			
			/*Photo Scroll*/
			pslc.gridx = 0;
			pslc.gridy++;
			pslc.weightx = 1;
			pslc.weighty = 1;
			pslc.gridwidth = 2;
			pslc.insets = new Insets(5,5,5,5);
			pslc.fill = GridBagConstraints.BOTH;
			pslc.anchor = GridBagConstraints.CENTER;
			photosScroll.setBorder(BorderFactory.createLineBorder(Color.blue));
			this.add(photosScroll, pslc);
			
			/*Photo Search Operations*/
			JPanel photosearchOps = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			photosearchOps.add(this.createAlbum);
			pslc.gridx = 0;
			pslc.gridy++;
			pslc.weightx = 1;
			pslc.weighty = 0;
			pslc.gridwidth = 2;
			pslc.insets = new Insets(5,5,5,5);
			pslc.fill = GridBagConstraints.BOTH;
			pslc.anchor = GridBagConstraints.CENTER;
			this.add(photosearchOps, pslc);
			hideError();
		}
		
		@Override
		public boolean isError() {
			return this.errorMsg.isVisible();
		}

		@Override
		public void showError(String errorMsg) {
			this.errorMsg.setText(errorMsg);
			this.errorLbl.setVisible(true);
			this.errorMsg.setVisible(true);
		}

		@Override
		public void hideError() {
			this.errorMsg.setText("");
			this.errorLbl.setVisible(false);
			this.errorMsg.setVisible(false);
		}
		
		public void setDefaultState() {
			hideError();
			this.photosModel.clear();
			this.createAlbum.setEnabled(false);
			this.cancel.setEnabled(true);
		}
		
		public void loadPhotoSearchResults(List<IPhoto> results) {
			this.photosModel.clear();
			for(IPhoto p : results) {
				this.photosModel.addElement(p);
			}
		}
		
		public List<IPhoto> getSelectedPhotos() {
			return this.photos.getSelectedValuesList();
		}
		
		private class SearchImageItem extends JPanel implements ListCellRenderer<IPhoto> {
			private static final long serialVersionUID = 1L;
			private int margin = 20;
			private JLabel icon;
			
			public SearchImageItem() {
				this.setLayout(new FlowLayout(FlowLayout.CENTER));
				this.icon = new JLabel();
				this.add(icon);
			}

			@Override
			public Component getListCellRendererComponent(
					JList<? extends IPhoto> list, IPhoto value, int index,
					boolean isSelected, boolean cellHasFocus) {
				this.setMinimumSize(new Dimension(value.getResized().getIconWidth() + margin, value.getResized().getIconHeight() + margin));
				this.setBackground(isSelected ? Color.gray : Color.white);
				this.icon.setIcon(value.getResized());
				return this;
			}
		}
	}
	
	private class DatePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private JLabel startLbl;
		private JLabel endLbl;
		
		private DateTextField start;
		private DateTextField end;
		
		public DatePanel(int width, int height) {
			this.setSize(new Dimension(width, height));
			setup();
		}
		
		private void setup() {
			this.setLayout(new GridBagLayout());
			this.startLbl = new JLabel("Start |");
			this.endLbl = new JLabel("End |");
			this.start = new DateTextField();
			this.end = new DateTextField();
			
			GridBagConstraints dpc = new GridBagConstraints();
			dpc.gridx = 0;
			dpc.gridy = 0;
			dpc.weightx = 0;
			dpc.weighty = 0;
			dpc.insets = new Insets(5, 5, 5, 5);
			dpc.fill = GridBagConstraints.NONE;
			dpc.anchor = GridBagConstraints.EAST;
			this.add(startLbl, dpc);
			
			dpc.gridx = 1;
			dpc.gridy = 0;
			dpc.weightx = 1;
			dpc.weighty = 0;
			dpc.insets = new Insets(5, 5, 5, 5);
			dpc.fill = GridBagConstraints.BOTH;
			dpc.anchor = GridBagConstraints.CENTER;
			this.add(start, dpc);
			
			dpc.gridx = 0;
			dpc.gridy = 1;
			dpc.weightx = 0;
			dpc.weighty = 0;
			dpc.insets = new Insets(5, 5, 5, 5);
			dpc.fill = GridBagConstraints.NONE;
			dpc.anchor = GridBagConstraints.EAST;
			this.add(endLbl, dpc);
			
			dpc.gridx = 1;
			dpc.gridy = 1;
			dpc.weightx = 1;
			dpc.weighty = 0;
			dpc.insets = new Insets(5, 5, 5, 5);
			dpc.fill = GridBagConstraints.BOTH;
			dpc.anchor = GridBagConstraints.CENTER;
			this.add(end, dpc);
		}
		
		public Date getStartDate() throws ParseException {
			return start.getDate();
		}
		
		public Date getEndDate() throws ParseException {
			return end.getDate();
		}
		
		public void setDefaultState() {
			this.start.reset();
			this.end.reset();
		}
	}
	
	private class DateTextField extends JPanel {
		private static final long serialVersionUID = 1L;
		private JLabel monthLbl;
		private JLabel dayLbl;
		private JLabel yearLbl;
		private JComboBox<String> month;
		private JComboBox<String> day; 
		private JComboBox<String> year;
		
		public DateTextField() {
			setup();
		}
		
		private void setup() {
			this.setLayout(new GridBagLayout());
			
			this.monthLbl = new JLabel("Month");
			this.dayLbl = new JLabel("Day");
			this.yearLbl = new JLabel("Year");
			
			this.month = new JComboBox<String>();
			this.day = new JComboBox<String>();
			this.year = new JComboBox<String>();
			
			GridBagConstraints rootdts = new GridBagConstraints();
			rootdts.gridx = 0;
			rootdts.gridy = 0;
			rootdts.weightx = 0;
			rootdts.weighty = 0;
			rootdts.insets = new Insets(5,5,5,5);
			rootdts.fill = GridBagConstraints.NONE;
			rootdts.anchor = GridBagConstraints.EAST;
			this.add(monthLbl, rootdts);
			
			rootdts.gridx++;
			rootdts.gridy = 0;
			rootdts.weightx = 0.33;
			rootdts.weighty = 0;
			rootdts.insets = rootdts.insets;
			rootdts.fill = GridBagConstraints.HORIZONTAL;
			rootdts.anchor = GridBagConstraints.WEST;
			this.add(month, rootdts);
			
			rootdts.gridx++;
			rootdts.gridy = 0;
			rootdts.weightx = 0;
			rootdts.weighty = 0;
			rootdts.insets = rootdts.insets;
			rootdts.fill = GridBagConstraints.NONE;
			rootdts.anchor = GridBagConstraints.EAST;
			this.add(dayLbl, rootdts);
			
			
			rootdts.gridx++;
			rootdts.gridy = 0;
			rootdts.weightx = 0.33;
			rootdts.weighty = 0;
			rootdts.insets = rootdts.insets;
			rootdts.fill = GridBagConstraints.HORIZONTAL;
			rootdts.anchor = GridBagConstraints.WEST;
			this.add(day, rootdts);
			
			rootdts.gridx++;
			rootdts.gridy = 0;
			rootdts.weightx = 0;
			rootdts.weighty = 0;
			rootdts.insets = rootdts.insets;
			rootdts.fill = GridBagConstraints.NONE;
			rootdts.anchor = GridBagConstraints.EAST;
			this.add(yearLbl, rootdts);
			
			rootdts.gridx++;
			rootdts.gridy = 0;
			rootdts.weightx = 0.33;
			rootdts.weighty = 0;
			rootdts.insets = rootdts.insets;
			rootdts.fill = GridBagConstraints.HORIZONTAL;
			rootdts.anchor = GridBagConstraints.WEST;
			this.add(year, rootdts);
			
			fillInitialFields();
		}
		
		public Date getDate() throws ParseException {
			String dateString = (String)this.month.getSelectedItem() + "." + (String)this.day.getSelectedItem() + "." + (String)this.year.getSelectedItem(); 
			Date date = new SimpleDateFormat("MM.dd.yyyy").parse(dateString);
			return date;
		}
		
		public void reset() {
			this.month.removeAll();
			this.day.removeAll();
			this.year.removeAll();
			fillInitialFields();
		}
		
		private void fillInitialFields() {
			/*Months*/
			for(int i = 1; i <= 12; i++) {
				this.month.addItem(Integer.toString(i));
			}
			/*Days*/
			for(int i = 1; i <= 31; i++) {
				this.day.addItem(Integer.toString(i));
			}
			
			/*Years*/
			for(int i = Calendar.getInstance().get(Calendar.YEAR); i >= 0; i--) {
				this.year.addItem(Integer.toString(i));
			}
		}
	}
}
