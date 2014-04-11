package cs213.photoalbum.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.UnsupportedLookAndFeelException;

import cs213.photoalbum.control.InteractiveControl;
import cs213.photoalbum.model.IAlbum;
import cs213.photoalbum.model.Photo;

public class UserAlbumUI {
	private int i;
	private DefaultListModel vector;
	private JButton add;
	private JButton delete;
	private JButton rename;
	private JButton logout;
	private String setName = "";
	private JPanel mainPanel;
	private int getIndex;
	private InteractiveControl control;
	private JListWithImage jlwi;
	private JFrame frame;

	public UserAlbumUI() {
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

	public void initUI(java.util.List<IAlbum> list) {
		frame = new JFrame(UserAlbumUI.class.getSimpleName());
		//TODO When window closes the original login window should appear.
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				control.logout();
				System.exit(0);
			}
		});
		frame.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		vector = new<IAlbum> DefaultListModel();
		JPanel buttonPanel = new JPanel();
		add = new JButton("Add");
		delete = new JButton("Delete");
		rename = new JButton("Rename");
		logout = new JButton("Logout");
		delete.setEnabled(false);
		rename.setEnabled(false);
		buttonPanel.add(add);
		buttonPanel.add(delete);
		buttonPanel.add(rename);
		jlwi = new JListWithImage(vector);
		jlwi.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {
			//		System.out.println("I was double clicked!");
					getIndex = jlwi.getSelectedIndex();
					IAlbum test = (IAlbum) vector.get(getIndex);
			//		System.out.println(test.getAlbumName());
					control.changeGui(test);
				} else if (evt.getClickCount() == 3) { // Triple-click
					int index = list.locationToIndex(evt.getPoint());

				}
			}
		});
		jlwi.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (!vector.isEmpty()) {
					getIndex = jlwi.getSelectedIndex();
					if (getIndex != -1) {
						IAlbum test = (IAlbum) vector.get(getIndex);
				//		System.out.println(test.getAlbumName());
						delete.setEnabled(true);
						rename.setEnabled(true);
					}

				}
			}
		}

		);
		add.addActionListener(new ButtonListener());
		delete.addActionListener(new ButtonListener());
		rename.addActionListener(new ButtonListener());
		logout.addActionListener(new ButtonListener());
		JPanel contentPane = new JPanel(null);
		contentPane.setPreferredSize(new Dimension(500, 400));
		JScrollPane scrollPane = new JScrollPane(mainPanel);
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(50, 50, 400, 200);
		scrollPane.setMinimumSize(new Dimension(160, 200));
		scrollPane.setPreferredSize(new Dimension(100, 200));
		contentPane.add(scrollPane);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		frame.add(logout, gbc);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.ipady = 40; //make this component tall
		gbc.weightx = 0.0;
		frame.add(contentPane, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.SOUTH;
		frame.add(buttonPanel, gbc);
		frame.setSize(600, 600);
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				vector.addElement(list.get(i));
				jlwi.setListData(vector.toArray());
				mainPanel.add(jlwi);
				mainPanel.revalidate();
			}
		}
		frame.setVisible(true);

	}
	
	public void setInteractiveControl(InteractiveControl control) {
		this.control = control;
	}

	public void addElementToVector(IAlbum newPanel) {
		vector.addElement(newPanel);
		jlwi.setListData(vector.toArray());
		jlwi.setSelectedValue(0, true);
		delete.setEnabled(true);
		rename.setEnabled(true);
		mainPanel.add(jlwi);
		mainPanel.revalidate();

	}

	public void deleteElementFromVector(int i) {
		vector.remove(i);
		jlwi.setListData(vector.toArray());
		jlwi.setSelectedValue(0, true);
		if (vector.isEmpty()) {
			delete.setEnabled(false);
			rename.setEnabled(false);
		}
	//	System.out.println("I came here" + i);
		mainPanel.revalidate();
	}

	public void renameAlbumInVector() {
		jlwi.setListData(vector.toArray());
		jlwi.setSelectedValue(0, true);
		mainPanel.revalidate();
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			if (source == add) {
				JFrame frame = new JFrame();
				AddName test = new AddName(frame, true);
				if (test.getBoolean() == true) {
					control.createAlbum(test.getName());
				}
			} else if (source == delete) {
		//		System.out.println("test" + getIndex);
				if (getIndex != -1) {
					IAlbum test = (IAlbum) vector.get(getIndex);
		//			System.out.println("Testing" + test.getAlbumName()
		//					+ getIndex);
					control.deleteAlbum(test.getAlbumName());
				}
			} else if (source == rename) {
				if (getIndex != -1) {
					IAlbum testz = (IAlbum) vector.get(getIndex);
					JFrame frame = new JFrame();
					RenameAlbum test = new RenameAlbum(frame, true,
							testz.getAlbumName());
					if (test.getBoolean() == true) {
						control.renameAlbum(testz.getAlbumName(),
								test.getName());
					}
				}
			} else if (source == logout) {
				control.logout();
				frame.dispose();
			}
		}
	}

	public int getIndex() {
		return getIndex;
	}

	public void hideMe() {
		this.frame.setVisible(false);
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

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public void showMe() {
		this.frame.setVisible(true);
	}

	public void setPic(Photo setMe) {
		IAlbum setAlbum = (IAlbum) vector.get(getIndex);
		setAlbum.setPic(setMe);
	}

	public void setDefault(ImageIcon imageIcon) {
		IAlbum setAlbum = (IAlbum) vector.get(getIndex);
		setAlbum.setDefault(imageIcon);
	}

}
