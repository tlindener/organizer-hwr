package view.renderer;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

public class CellRenderer extends DefaultTableCellRenderer{
	
	  Color color = new Color(252,255,170);
    @Override
    public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel)super.getTableCellRendererComponent(table, obj, isSelected,hasFocus, row, column);
       
        
        
        	if(!label.getText().equals("")&& column>0){
      	
        		label.setBackground(color);
        	 
        	}
        	else
        	{
        		label.setBackground(color.WHITE);
        	}

        
        return label;
    }
    
    
   
	 
	
}