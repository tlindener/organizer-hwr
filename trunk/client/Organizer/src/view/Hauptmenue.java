package view;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import logik.DataPusher;
import view.renderer.CellRenderer;

import com.toedter.calendar.JCalendar;

/**
 * Mainframe that shows all calendarentries and its details.
 * 
 * @author Jennufer Blumenthal
 *
 */
public class Hauptmenue extends JFrame {


	private DataPusher myDataPusher;
	private CellRenderer myRenderer;
	private ActionListener myCon;
	private MouseListener myML;
	private PropertyChangeListener myPCL;
	private ListSelectionListener myLs;
	
	private JTextField txtRaum;
	private JTable table_1;
	private JTextArea txtADetails;
	private JLabel lblAnzahlEinladungen;
	private JList list;
	private Date aktDateCali;
	private JCalendar cali;
	private int rowCount;
	private JLabel picLabel;
	
	private JButton btnRaumErstellen;
	private JButton btnTerminBearbeiten;
	private JButton btnTerminEntfernen;
	private JButton btnAbmelden;
		
	private DefaultListModel listModel;
	private TableModel dataModel;
	private ListSelectionModel listSelectionModel;
	

	/**
	 * Default constructor that creates the main frames in which all entries are
	 * displayed and from where entries can be created, edited or deleted.
	 * 
	 * @param dp
	 * @param con
	 * @param mL
	 * @param pCL
	 * @param sL
	 */
	public Hauptmenue(DataPusher dp, ActionListener con, MouseListener mL,
			PropertyChangeListener pCL, ListSelectionListener sL) {
		myCon = con;
		myML = mL;
		myPCL = pCL;
		myLs = sL;
		this.myDataPusher = dp;
		myRenderer = new CellRenderer();

		init();
	}

	/**
	 * Create the frame.
	 * 
	 * @param txtADetails
	 * @param list
	 */

	public void init() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Metal".equals(info.getName())) {

					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {

		}
		setBounds(100, 100, 823, 620);

		JPanel panel = new JPanel();

		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 21, 145, 1, 342, 0, 0 };
		gbl_panel.rowHeights = new int[] { 30, 186, 0, 0, 0, 0, 110, 0, 0, 0,
				0, 0, 21 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };

		panel.setLayout(gbl_panel);

		cali = new JCalendar();
		cali.addPropertyChangeListener(myPCL);

		GridBagConstraints gbc_cali = Util.createGridBagContraints(1, 1, 1, 1,
				GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);
		panel.add(cali, gbc_cali);

		updateTable();

		table_1 = new JTable(dataModel);
		table_1.setDefaultRenderer(Object.class, myRenderer);
		listSelectionModel = table_1.getSelectionModel();
		listSelectionModel.addListSelectionListener(myLs);
		JScrollPane scrollpane = new JScrollPane(table_1);
		table_1.getColumn("Uhrzeit").setPreferredWidth(35);
		table_1.getColumn("bis").setPreferredWidth(40);
		table_1.getColumn("Uhrzeit").setMaxWidth(35);
		table_1.getColumn("bis").setMaxWidth(40);
		GridBagConstraints gbc_table_1 = Util.createGridBagContraints(1, 3, 3, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		panel.add(scrollpane, gbc_table_1);
		
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0 };
		gbl_panel_1.rowHeights = new int[] { 0 };
		gbl_panel_1.columnWeights = new double[] { Double.MIN_VALUE };
		getContentPane().add(panel);

		JLabel lblRaum = new JLabel("Raum");
		GridBagConstraints gbc_lblRaum = Util.createGridBagContraints(1, 2, GridBagConstraints.WEST, GridBagConstraints.NONE);
		panel.add(lblRaum, gbc_lblRaum);

		txtRaum = new JTextField();
		txtRaum.setEditable(false);
		GridBagConstraints gbc_txtRaum = Util.createGridBagContraints(1, 3, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		panel.add(txtRaum, gbc_txtRaum);
		

		JLabel lblPersonen = new JLabel("Personen");
		GridBagConstraints gbc_lblPersonen = Util.createGridBagContraints(1, 4, GridBagConstraints.WEST,GridBagConstraints.NONE);
		panel.add(lblPersonen, gbc_lblPersonen);

		JLabel lblDetails = new JLabel("Details");
		GridBagConstraints gbc_lblDetails = Util.createGridBagContraints(3, 4, GridBagConstraints.WEST, GridBagConstraints.NONE);
		panel.add(lblDetails, gbc_lblDetails);

		listModel = new DefaultListModel();
		list = new JList<String>(listModel);
		GridBagConstraints gbc_list = Util.createGridBagContraints(1, 2, 1, 5, GridBagConstraints.BOTH);
		panel.add(list, gbc_list);

		txtADetails = new JTextArea();
		txtADetails.setEditable(false);
		txtADetails.setLineWrap(true);
		GridBagConstraints gbc_textArea = Util.createGridBagContraints(1, 2, 3, 5, GridBagConstraints.BOTH);
		panel.add(txtADetails, gbc_textArea);

		btnTerminBearbeiten = new JButton("Termin erstellen");
		btnTerminBearbeiten.addActionListener(myCon);
		btnTerminBearbeiten
				.setToolTipText("Bitte w\u00E4hlen Sie einen Termin aus um ihn zu bearbeiten. Ist ein Termin noch leer wird er mit \"Termin bearbeiten\" erschaffen.");
		GridBagConstraints gbc_btnTerminBearbeiten = Util.createGridBagContraints(3, 7, GridBagConstraints.EAST,GridBagConstraints.NONE);
		panel.add(btnTerminBearbeiten, gbc_btnTerminBearbeiten);

		btnTerminEntfernen = new JButton("Termin entfernen");
		btnTerminEntfernen
				.setToolTipText("W\u00E4hlen Sie einen Termin aus um ihn zu l\u00F6schen");
		btnTerminEntfernen.addActionListener(myCon);
		GridBagConstraints gbc_btnTerminEntfernen = Util.createGridBagContraints( 3, 8,GridBagConstraints.EAST, GridBagConstraints.NONE);
		panel.add(btnTerminEntfernen, gbc_btnTerminEntfernen);
		
		
		lblAnzahlEinladungen = new JLabel("AnzahlEinladungen");
		GridBagConstraints gbc_lblAnzahlEinladungen = Util.createGridBagContraints(1, 8, GridBagConstraints.CENTER,GridBagConstraints.NONE);
		panel.add(lblAnzahlEinladungen, gbc_lblAnzahlEinladungen);


		btnRaumErstellen = new JButton("Raum erstellen");
		btnRaumErstellen.addActionListener(myCon);
		GridBagConstraints gbc_btnRaumErstellen = Util.createGridBagContraints(3, 9, GridBagConstraints.EAST, GridBagConstraints.NONE);
		panel.add(btnRaumErstellen, gbc_btnRaumErstellen);

		btnAbmelden = new JButton("Abmelden");
		btnAbmelden.addActionListener(myCon);
		GridBagConstraints gbc_btnAbmelden = Util.createGridBagContraints(3, 10, GridBagConstraints.EAST, GridBagConstraints.NONE);
		panel.add(btnAbmelden, gbc_btnAbmelden);

		URL filename = getClass().getResource("briefkasten.png");

		if (filename != null) {
			Image image = Toolkit.getDefaultToolkit().getImage(filename);
			image = image.getScaledInstance(50, 100, 0);
			picLabel = new JLabel(new ImageIcon(image));
			picLabel.addMouseListener(myML);

			GridBagConstraints gbc_lblImage = Util.createGridBagContraints(1, 4, 1, 8, GridBagConstraints.NONE);
			panel.add(picLabel, gbc_lblImage);
		} else {
			System.out.println("Ich habe das Foto nicht gefunden");
		}

		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	/**
	 * Updates the JTable with the newest data.
	 */
	public void updateTable() {
		dataModel = new AbstractTableModel() {

			@Override
			public String getColumnName(int column) {
				switch (column) {
				case 0:
					return "Uhrzeit";
				case 1:
					return "bis";
				case 2:
					return "Beschreibung";
				default:
					return "Error";
				}
			}

			public int getColumnCount() {
				return 3;
			}

			@SuppressWarnings("unused")
			public int getRowCountUpdate() {

				return rowCount;
			}

			public int getRowCount() {
				rowCount = 0;
				Object[][] obj = myDataPusher.getBeschreibungen();
				for (int i = 0; i < 48; i++) {

					if (obj[i][0] != null) {
						rowCount = rowCount + 1;
					}
				}
				return rowCount;
			}

			@Override
			public Object getValueAt(int row, int col) {

				return myDataPusher.getBeschreibungen()[row][col];

			}

		};
	}
	public JTextArea getTextArea() {
		return txtADetails;
	}

	public JButton getBtnRaumErstellen() {
		return btnRaumErstellen;
	}

	public void setBtnRaumErstellen(JButton btnRaumErstellen) {
		this.btnRaumErstellen = btnRaumErstellen;
	}

	public void setTextArea(JTextArea textArea) {
		this.txtADetails = textArea;
	}

	public JCalendar getCali() {
		return cali;
	}

	public void setCali(JCalendar cali) {
		this.cali = cali;
	}

	public JTable getTable_1() {
		return table_1;
	}

	public void setTable_1(JTable table_1) {
		this.table_1 = table_1;
	}

	public Date getAktDateCali() {
		aktDateCali = cali.getDate();
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

	public DefaultListModel getListModel() {
		return listModel;
	}

	public void setListModel(DefaultListModel listModel) {
		this.listModel = listModel;
	}

	public JTextField getTxtRaum() {
		return txtRaum;
	}

	public void setTxtRaum(JTextField textField) {
		this.txtRaum = textField;
	}

	public JLabel getPicLabel() {
		return picLabel;
	}

	public void setPicLabel(JLabel picLabel) {
		this.picLabel = picLabel;
	}

	public JLabel getLblAnzahlEinladungen() {
		return lblAnzahlEinladungen;
	}

	public void setLblAnzahlEinladungen(JLabel lblAnzahlEinladungen) {
		this.lblAnzahlEinladungen = lblAnzahlEinladungen;
	}

	public JList getList() {
		return list;
	}

	public void setList(JList list) {
		this.list = list;
	}
	public ListSelectionModel getListSelectionModel() {
		return listSelectionModel;
	}

	public void setListSelectionModel(ListSelectionModel listSelectionModel) {
		this.listSelectionModel = listSelectionModel;
	}
}
