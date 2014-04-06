package cs213.photoalbum.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class addName extends JDialog {
	private JPanel mainPanel;
	private boolean success;
	private JTextField info = new JTextField();
	private JButton yes = new JButton("Add");
	private JButton cancel = new JButton("Cancel");
	private String toAdd;

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			if (source == yes) {
				success = true;
				System.out.println("yes");
				toAdd = info.getText();
				setVisible(false);

			} else if (source == cancel) {
				success = false;
				setVisible(false);
			}
		}
	}

	public addName(JFrame frame, boolean modal) {
		super(frame, "Album to add", modal);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		JPanel thePanel = new JPanel(new GridLayout(0, 1));
		thePanel.setBorder(BorderFactory.createTitledBorder("Album to add"));
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

	public String getName() {
		return toAdd;
	}

	public boolean getBoolean() {
		return success;
	}
}
