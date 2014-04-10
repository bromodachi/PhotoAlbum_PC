package cs213.photoalbum.view;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import cs213.photoalbum.model.Photo;

public class AddPhoto extends JDialog {
	private JPanel mainPanel;
	private boolean success;
	private JTextField caption = new JTextField();
	private File file;
	private Photo thePhoto;
	private JButton yes = new JButton("yes");
	private JButton changePhoto = new JButton("Change Photo");
	private JButton cancel = new JButton("Cancel");
	JLabel picLabel;
	JLabel re;

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			if (source == changePhoto) {
				try {
					JFileChooser chooser = new JFileChooser();
					chooser.setFileFilter(new FileNameExtensionFilter(
							"JPG, PNG, & GIF Images", "jpg", "gif", "png"));
					chooser.showOpenDialog(null);
					file = chooser.getSelectedFile();
					BufferedImage myPicture = ImageIO.read(file);
					BufferedImage reSized = resizeImage(myPicture, 1, 140, 140);
					BufferedImage reSized2 = resizeImage(myPicture, 1, 400, 400);
					thePhoto.setIcon(new ImageIcon(reSized));
					thePhoto.setResized(new ImageIcon(reSized));
					thePhoto.setPhoto(new ImageIcon(reSized2));
					thePhoto.setFileName(chooser.getSelectedFile().getName());
					mainPanel.revalidate();
					long dateRaw = file.lastModified();
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(dateRaw);
					cal.set(Calendar.MILLISECOND, 0);
					thePhoto.setDate(cal.getTime());
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			} else if (source == cancel) {
				success = false;
				setVisible(false);
			} else if (source == yes) {
				success = true;
				System.out.println("yes");
				thePhoto.setCaption(caption.getText());
				setVisible(false);
			}
		}
	}

	public AddPhoto(JFrame frame, Photo toAdd, boolean modal) {
		super(frame, "Photo to add", modal);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		JPanel thePanel = new JPanel(new GridLayout(0, 1));
		thePanel.setBorder(BorderFactory.createTitledBorder("File to add"));
		thePhoto = toAdd;
		thePanel.add(thePhoto);
		thePanel.add(caption);
		JPanel buttons = new JPanel();
		buttons.add(yes);
		yes.addActionListener(new ButtonListener());
		buttons.add(cancel);
		cancel.addActionListener(new ButtonListener());
		buttons.add(changePhoto);
		changePhoto.addActionListener(new ButtonListener());
		mainPanel.add(thePanel);
		mainPanel.add(buttons);
		getContentPane().add(mainPanel);
		setLocationRelativeTo(frame);
		pack();
		setVisible(true);
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

	public String getCaption() {
		return caption.getText();
	}

	public Photo getPhoto() {
		return thePhoto;
	}

	public boolean getBoolean() {
		return success;
	}

}
