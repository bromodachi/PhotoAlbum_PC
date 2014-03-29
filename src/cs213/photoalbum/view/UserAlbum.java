package cs213.photoalbum.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.UnsupportedLookAndFeelException;
import cs213.photoalbum.control.InteractiveControl;
import cs213.photoalbum.model.IPhotoAdminModel;
import java.awt.Graphics2D; 

public class UserAlbum  {
	private int i;
	private DefaultListModel vector;
    private JButton add;
    private JButton delete;
    private JButton rename;
    private JButton logout;
    private String setName="";
    private JPanel mainPanel;
    private IPhotoAdminModel model;
    private int getIndex;
    private InteractiveControl control;
    private JListWithImage jlwi;
    private JFrame frame;
    public UserAlbum(InteractiveControl controller, IPhotoAdminModel modelz){
    	this.model=modelz;
    	this.control=controller;
    }
    public void initUI() {
        frame = new JFrame(UserAlbum.class.getSimpleName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        mainPanel=new JPanel();
	//	mainPanel.add(qScoller);
    //    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
     //   mainPanel.setPreferredSize(new Dimension(150,150));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        vector=new <thePanels>DefaultListModel();
        JPanel buttonPanel = new JPanel();
        add = new JButton("Add");
        delete = new JButton("Delete");
        rename = new JButton("Rename");
        logout = new JButton("Logout");
        buttonPanel.add(add);
        buttonPanel.add(delete);
        buttonPanel.add(rename);
        //jlwi = new JListWithImage();
        jlwi= new JListWithImage(vector);
        jlwi.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    System.out.println("I was double cliced!");
                } else if (evt.getClickCount() == 3) {   // Triple-click
                    int index = list.locationToIndex(evt.getPoint());

                }
            }
        });
        jlwi.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				if(!vector.isEmpty()){
					getIndex=jlwi.getSelectedIndex();
					if(getIndex!=-1){
					thePanels test=(thePanels)vector.get(getIndex);
					System.out.println(test.getName());
			//		test.renameAlbum("kodomo");
					}
				
				}
			}
			}
			
		);
        add.addActionListener(new ButtonListener()); 
        delete.addActionListener(new ButtonListener()); 
        rename.addActionListener(new ButtonListener()); 
        logout.addActionListener(new ButtonListener()); 
       /* JListWithImage jlwi = new JListWithImage();
        jlwi.setListData(vector);
        mainPanel.add(jlwi);*/
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(500, 400));
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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
		gbc.ipady = 40;      //make this component tall
		gbc.weightx = 0.0;
        frame.add(contentPane, gbc);
        gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.SOUTH;
        frame.add(buttonPanel, gbc);
     //   frame.add(delete, BorderLayout.SOUTH);
        frame.setSize(600,600);
        frame.setVisible(true);
        
    }
    
    
    public void addElementToVector(String name){
    		thePanels newPanel = new thePanels(name);
        	newPanel.createPanel(name);
        	newPanel.setName(name);
        	vector.addElement(newPanel);
             jlwi.setListData(vector.toArray());
             jlwi.setSelectedValue(0, true);
             mainPanel.add(jlwi);
             mainPanel.revalidate();
    	
    }
    public void deleteElementFromVector(int i){
    	vector.remove(i);
    //	jlwi.setListData(vector);
    	jlwi.setListData(vector.toArray());
        jlwi.setSelectedValue(0, true);
        System.out.println("I came here"+i);
        mainPanel.revalidate();
    }
    public void renameAlbumInVector(int i, String id){
    	thePanels test=(thePanels) vector.get(i);
    	test.renameAlbum(id);
    	test.setName(id);
    	System.out.println("I was called"+id+test.getName());
    	jlwi.setListData(vector.toArray());
        jlwi.setSelectedValue(0, true);
    //	mainPanel.add(jlwi);
        mainPanel.revalidate();
    }
    private class ButtonListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		JButton source = (JButton)e.getSource();
    		if (source == add) {
                setName = (String)JOptionPane.showInputDialog("Enter input");
                control.createAlbum(setName);
        /*        thePanels newPanel = new thePanels();
                newPanel= (thePanels) newPanel.createPanel(setName);
                vector.addElement(newPanel);
                     jlwi.setListData(vector);
                     jlwi.setSelectedValue(0, true);
                     mainPanel.add(jlwi);
                     mainPanel.revalidate();*/
    		} else if (source == delete) {
    			System.out.println("test"+getIndex);
    			if(getIndex!=-1){
					thePanels test=(thePanels) vector.get(getIndex);
					System.out.println("Testing"+test.getName()+getIndex);
					control.deleteAlbum(test.getName());
					}
    		} else if (source == rename) {
    			if(getIndex!=-1){
    				setName = (String)JOptionPane.showInputDialog("Enter input");
					thePanels test=(thePanels) vector.get(getIndex);
					System.out.println("Rename: "+test.getName()+getIndex);
					control.renameAlbum(test.getName(),setName);
					}
    		}
    		else if(source==logout){
    		//	control.logout();
    			frame.dispose();
    		}
    	}
    }
    public int getIndex(){
    	return getIndex;
    }
    public BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT){
    	BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
    	Graphics2D g = resizedImage.createGraphics();
    	g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
    	g.dispose();
     
    	return resizedImage;
        }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
