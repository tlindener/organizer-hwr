import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.toedter.calendar.JCalendar;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class window_Hauptmenue extends JFrame {
	
	private JTable table_1;
	private JPanel contentPane;
	private DataPusher myDataPusher;
	private Renderer myRenderer;
	private ActionListener myCon;
	private JTextField textField;
	private JList list;
	private JTextArea textArea;
	private JButton btnTerminBearbeiten;
	private JButton btnTerminEntfernen;
	private JButton btnAbmelden;
	private String aktDateCali;
	private JCalendar cali;
	
	

	/**
	 * Launch the application.
	 */
	public window_Hauptmenue(DataPusher myDataPusher, ActionListener con)
	{
		myCon=con;
		this.myDataPusher=myDataPusher;
		myRenderer=new Renderer();
		
		init();
	}
	/**
	 * Create the frame.
	 * @param textArea 
	 * @param list 
	 */
	

	public void init() {
			
		setBounds(100, 100, 789, 572);
			
			JPanel panel = new JPanel();
			
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[] { 21, 145, 1, 342, 0, 0 };
			gbl_panel.rowHeights = new int[] { 9, 186, 0, 0, 0, 0, 110, 0, 0, 0, 31 };
			gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0,
					Double.MIN_VALUE };

			panel.setLayout(gbl_panel);

//			Kalender Konfiguration 
			cali = new JCalendar();

			GridBagConstraints gbc_cali = new GridBagConstraints();
			gbc_cali.anchor = GridBagConstraints.NORTHWEST;
			gbc_cali.insets = new Insets(0, 0, 5, 5);
			gbc_cali.gridx = 1;
			gbc_cali.gridy = 1;
			panel.add(cali, gbc_cali);
			
// Termintabelle
//			Speicherung der Beschreibungen im Modell über HashMap
		
			
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
					return 48;
				}

				@Override
				public Object getValueAt(int row, int col) {
					
					return myDataPusher.getBeschreibungen()[row][col];
					
				}
				public void setValueAt(Object value, int row, int col) {
						
					myDataPusher.getBeschreibungen()[row][col] = value;
				       fireTableCellUpdated(row, col);
				}
				
				
			};

			table_1 = new JTable(dataModel);
			table_1.setDefaultRenderer(Object.class, myRenderer);
			setRowHeightDyn();
			table_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JTable zwTab = (JTable)e.getSource();
					zwTab.getSelectedRow();
					String details=myDataPusher.getDetails((String) table_1.getValueAt(zwTab.getSelectedRow(),0 ));
//					System.out.println("Details: "+(String) table_1.getValueAt(zwTab.getSelectedRow(),1 ));
					textArea.setText(details);
					}  
			});
			
			JScrollPane scrollpane = new JScrollPane(table_1);
				
			table_1.getColumn("Uhrzeit").setPreferredWidth(35);
			table_1.getColumn("Uhrzeit").setMaxWidth(35);
			
			GridBagConstraints gbc_table_1 = new GridBagConstraints();
			gbc_table_1.gridheight = 3;
			gbc_table_1.insets = new Insets(0, 0, 5, 5);
			gbc_table_1.fill = GridBagConstraints.BOTH;
			
			gbc_table_1.gridx = 3;
			gbc_table_1.gridy = 1;
			panel.add(scrollpane, gbc_table_1);
			GridBagLayout gbl_panel_1 = new GridBagLayout();
			gbl_panel_1.columnWidths = new int[] { 0 };
			gbl_panel_1.rowHeights = new int[] { 0 };
			gbl_panel_1.columnWeights = new double[] { Double.MIN_VALUE };
//			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//			contentPane.setLayout(new BorderLayout(0, 0));
//			setContentPane(contentPane);
			getContentPane().add(panel);
		
		JLabel lblRaum = new JLabel("Raum");
		GridBagConstraints gbc_lblRaum = new GridBagConstraints();
		gbc_lblRaum.anchor = GridBagConstraints.WEST;
		gbc_lblRaum.insets = new Insets(0, 0, 5, 5);
		gbc_lblRaum.gridx = 1;
		gbc_lblRaum.gridy = 2;
		panel.add(lblRaum, gbc_lblRaum);
		
		textField = new JTextField();
		
		GridBagConstraints gbc_textField = new GridBagConstraints();
		
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 3;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblPersonen = new JLabel("Personen");
		GridBagConstraints gbc_lblPersonen = new GridBagConstraints();
		gbc_lblPersonen.anchor = GridBagConstraints.WEST;
		gbc_lblPersonen.insets = new Insets(0, 0, 5, 5);
		gbc_lblPersonen.gridx = 1;
		gbc_lblPersonen.gridy = 4;
		panel.add(lblPersonen, gbc_lblPersonen);
		
		JLabel lblDetails = new JLabel("Details");
		GridBagConstraints gbc_lblDetails = new GridBagConstraints();
		gbc_lblDetails.anchor = GridBagConstraints.WEST;
		gbc_lblDetails.insets = new Insets(0, 0, 5, 5);
		gbc_lblDetails.gridx = 3;
		gbc_lblDetails.gridy = 4;
		panel.add(lblDetails, gbc_lblDetails);
		
		list = new JList();
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridheight = 2;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 5;
		panel.add(list, gbc_list);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridheight = 2;
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 3;
		gbc_textArea.gridy = 5;
		panel.add(textArea, gbc_textArea);
		
		btnTerminBearbeiten = new JButton("Termin bearbeiten");
		btnTerminBearbeiten.addActionListener(myCon);
		btnTerminBearbeiten.setToolTipText("Bitte w\u00E4hlen Sie einen Termin aus um ihn zu bearbeiten. Ist ein Termin noch leer wird er mit \"Termin bearbeiten\" erschaffen.");
		GridBagConstraints gbc_btnTerminBearbeiten = new GridBagConstraints();
		gbc_btnTerminBearbeiten.anchor = GridBagConstraints.EAST;
		gbc_btnTerminBearbeiten.insets = new Insets(0, 0, 5, 5);
		gbc_btnTerminBearbeiten.gridx = 3;
		gbc_btnTerminBearbeiten.gridy = 7;
		panel.add(btnTerminBearbeiten, gbc_btnTerminBearbeiten);
		
		btnTerminEntfernen = new JButton("Termin entfernen.");
		btnTerminEntfernen.setToolTipText("W\u00E4hlen Sie einen Termin aus um ihn zu l\u00F6schen");
		GridBagConstraints gbc_btnTerminEntfernen = new GridBagConstraints();
		gbc_btnTerminEntfernen.anchor = GridBagConstraints.EAST;
		gbc_btnTerminEntfernen.insets = new Insets(0, 0, 5, 5);
		gbc_btnTerminEntfernen.gridx = 3;
		gbc_btnTerminEntfernen.gridy = 8;
		panel.add(btnTerminEntfernen, gbc_btnTerminEntfernen);
		
		btnAbmelden = new JButton("Abmelden");
		btnAbmelden.addActionListener(myCon);
		GridBagConstraints gbc_btnAbmelden = new GridBagConstraints();
		gbc_btnAbmelden.anchor = GridBagConstraints.EAST;
		gbc_btnAbmelden.insets = new Insets(0, 0, 5, 5);
		gbc_btnAbmelden.gridx = 3;
		gbc_btnAbmelden.gridy = 9;
		panel.add(btnAbmelden, gbc_btnAbmelden);
		setVisible(true);
		
	}
	
	public void setRowHeightDyn()
	{
		for(int i=table_1.getRowCount()-1;i>0; i--)
		{
			if (table_1.getValueAt(i, 0)!=null)
			{
				if(myDataPusher.getDauer((String) table_1.getValueAt(i, 0))!=null)
				{
					double dauer=(Double) myDataPusher.getDauer((String) table_1.getValueAt(i, 0));
					if(dauer>1)
					{
						for (int j=(int)Math.round(dauer)-1; j>0; j--)
						{ 
							System.out.println(j);
							table_1.setValueAt(table_1.getValueAt(i, 1), i+j, 1);
//							table_1.;
						}
						
					}
					/*
					 * Alternative Idee: Größe der Spalten verändern--> komplexere Anpassung des Datenmodells
					 * da keine Möglichkeit einfach Zeilen auszublenden
					 */
//					table_1.setRowHeight(i, (int)Math.round(20*dauer));
					
				}
				else
				{
					table_1.setRowHeight(i, 20);
				}
			}
		
		}
	}
	public JTable getTable_1() {
		return table_1;
	}
	public void setTable_1(JTable table_1) {
		this.table_1 = table_1;
	}
	public String getAktDateCali()
	{
		aktDateCali=cali.getDate().toString();
		System.out.println(aktDateCali);
		return aktDateCali;
	}
	public JButton getBtnTerminBearbeiten() {
		return btnTerminBearbeiten;
	}
	public void setBtnTerminBearbeiten(JButton btnTerminBearbeiten) {
		this.btnTerminBearbeiten = btnTerminBearbeiten;
	}
	public JButton getBtnTerminEntfernen() {
		return btnTerminEntfernen;
	}
	public void setBtnTerminEntfernen(JButton btnTerminEntfernen) {
		this.btnTerminEntfernen = btnTerminEntfernen;
	}
	public JButton getBtnAbmelden() {
		return btnAbmelden;
	}
	public void setBtnAbmelden(JButton btnAbmelden) {
		this.btnAbmelden = btnAbmelden;
	}
	

}
