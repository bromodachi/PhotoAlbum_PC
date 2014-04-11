package cs213.photoalbum.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cs213.photoalbum.model.Photo;

public class AddTag extends JDialog {
	private JPanel mainPanel;
	private boolean success;
	private JTextField info = new JTextField();
	private File file;
	private String tag;
	private JButton yes = new JButton("yes");
	private JButton cancel = new JButton("Cancel");
	private Photo toAdd;
	private String[] typeOf = { "location", "person" };
	private JComboBox typeOfTag;

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			if (source == yes) {
				success = true;
			//	System.out.println("yes");
				setVisible(false);

			} else if (source == cancel) {
				success = false;
				setVisible(false);
			}
		}
	}

	public String getTagType() {
		return (String) typeOfTag.getSelectedItem();
	}

	public String getTagValue() {
		return info.getText();
	}

	public AddTag(JFrame frame, boolean modal, Photo addMe) {
		super(frame, "Tag to add", modal);
		toAdd = addMe;
		mainPanel = new JPanel();
		typeOfTag = new JComboBox(typeOf);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		JPanel thePanel = new JPanel(new GridLayout(0, 1));
		thePanel.setBorder(BorderFactory.createTitledBorder("Tag to add"));
		thePanel.add(typeOfTag);
		thePanel.add(info);
		JPanel buttons = new JPanel();
		info.addActionListener(new ButtonListener());
		buttons.add(yes);
		buttons.add(cancel);
		yes.addActionListener(new ButtonListener());
		cancel.addActionListener(new ButtonListener());
		mainPanel.add(thePanel);
		mainPanel.add(buttons);
		getContentPane().add(mainPanel);
		setLocationRelativeTo(frame);
		pack();
		setVisible(true);
	}

	public boolean getBoolean() {
		return success;
	}

}