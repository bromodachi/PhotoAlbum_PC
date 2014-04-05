package cs213.photoalbum.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cs213.photoalbum.model.IAlbum;
import cs213.photoalbum.model.Photo;


public class movePhotoDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private boolean success;
	private JLabel currentAlbum;
	private JComboBox moveTo;
	private JButton yes = new JButton("Add");
    private JButton cancel = new JButton("Cancel");
    private String destination;
    private String [] AlbumNames;
    private Photo thePhoto;
    
    private class ButtonListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		JButton source = (JButton)e.getSource();
    		if (source == yes) {
    			success=true;
        		System.out.println("yes");
        		destination=(String)moveTo.getSelectedItem();
                setVisible(false);
                
    			
    	}else if(source==cancel){
    		success=false;
            setVisible(false);
    	}
    }
   }
    public movePhotoDialog(JFrame frame, boolean modal, String source, java.util.List<IAlbum> list,Photo toMove){
    	super(frame, "Album to add", modal);
    	mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel thePanel = new JPanel(new GridLayout(0, 1));
        thePanel.setBorder(BorderFactory.createTitledBorder("Photo to move"));
        AlbumNames=new String[list.size()];
        for(int i=0;i<list.size();i++){
        	AlbumNames[i]=(list.get(i).getAlbumName());
        }
        thePhoto=toMove;
        thePanel.add(thePhoto);
        currentAlbum=new JLabel(source);
        thePanel.add(currentAlbum);
        moveTo=new JComboBox(AlbumNames);
        thePanel.add(moveTo);
        JPanel buttons=new JPanel();
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
    public String getDest(){
		return destination;
	}
    public boolean getBoolean(){
		return success;
	}

}
