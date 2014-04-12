package view;
import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;



public class JListWithImage extends JList {
	public JListWithImage(DefaultListModel vector) { 
		   setCellRenderer(new CustomCellRenderer()); 
		   }
	class CustomCellRenderer implements ListCellRenderer {
		   public Component getListCellRendererComponent
		    (JList list, Object value, int index, 
		     boolean isSelected,boolean cellHasFocus) {
		     Component component = (Component)value;
		     component.setBackground
		      (isSelected ? Color.blue : Color.white);
		     component.setForeground
		      (isSelected ? Color.gray : Color.gray);
		     return component;
		     }
		   }

}

