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

public class recaptionDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private boolean success;
	private JTextField info = new JTextField();
	private JButton confirm = new JButton("confirm");
	private JButton cancel = new JButton("Cancel");

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			if (source == confirm) {
				success = true;
				System.out.println("yes");
				setVisible(false);

			} else if (source == cancel) {
				success = false;
				setVisible(false);
			}
		}
	}

	public recaptionDialog(JFrame frame, boolean modal) {
		super(frame, "Rcaption", modal);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		JPanel thePanel = new JPanel(new GridLayout(0, 1));
		thePanel.setBorder(BorderFactory
				.createTitledBorder("Recaption current photo"));
		thePanel.add(info);
		JPanel buttons = new JPanel();
		buttons.add(confirm);
		confirm.addActionListener(new ButtonListener());
		buttons.add(cancel);
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

	public String getCaption() {
		return info.getText();
	}

}
