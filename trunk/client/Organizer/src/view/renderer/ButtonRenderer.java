package view.renderer;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

import organizer.objects.types.Room;
import organizer.objects.types.User;

import logik.DataPusher;

public class ButtonRenderer extends JButton implements TableCellRenderer {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataPusher myDataPusher;

	public ButtonRenderer(DataPusher datapusher) {
//		public ButtonRenderer() {
		myDataPusher = datapusher;
	    setOpaque(true);
	  }

	  public Component getTableCellRendererComponent(JTable table, Object value,
	      boolean isSelected, boolean hasFocus, int row, int column) {
	    if (isSelected) {
	      setForeground(table.getSelectionForeground());
	      setBackground(table.getSelectionBackground());
	    } else {
	      setForeground(table.getForeground());
	      setBackground(UIManager.getColor("Button.background"));
	    }
//	    setText((value == null) ? "" : value.toString());
	    
	    
	    return this;
	  }

	
	}
