import java.awt.Frame;

import javax.swing.JApplet;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXMonthView;

import com.toedter.calendar.JCalendar;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class kalender_oberflaeche_applet extends JApplet {
	private JTable table_1;

	/**
	 * Create the applet.
	 */
	public kalender_oberflaeche_applet() {

		
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 21, 145, 1, 342, 0 };
		gbl_panel.rowHeights = new int[] { 186, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JCalendar cali = new JCalendar();

		GridBagConstraints gbc_cali = new GridBagConstraints();
		gbc_cali.anchor = GridBagConstraints.NORTHWEST;
		gbc_cali.insets = new Insets(0, 0, 0, 5);
		gbc_cali.gridx = 1;
		gbc_cali.gridy = 0;
		panel.add(cali, gbc_cali);

		TableModel dataModel = new AbstractTableModel() {
			@Override
			public String getColumnName(int column) {
				switch (column) {
				case 0:
					return "Uhrzeit";
				case 1:
					return "Beschreibung";
				default:
					return "Error";
				}
			}

			public int getColumnCount() {
				return 2;
			}

			public int getRowCount() {
				return 10;
			}

			@Override
			public Object getValueAt(int arg0, int arg1) {
				return 3;
			}
		};

		table_1 = new JTable(dataModel);

		JScrollPane scrollpane = new JScrollPane(table_1);
		table_1.getValueAt(0, 1);
	
		table_1.getColumn("Uhrzeit").setPreferredWidth(20);
		table_1.getColumn("Uhrzeit").setMaxWidth(20);
		
		GridBagConstraints gbc_table_1 = new GridBagConstraints();
		gbc_table_1.fill = GridBagConstraints.BOTH;
		
		gbc_table_1.gridx = 3;
		gbc_table_1.gridy = 0;
		panel.add(scrollpane, gbc_table_1);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0 };
		gbl_panel_1.rowHeights = new int[] { 0 };
		gbl_panel_1.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);
	}

}
