package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.UIManager;
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
import java.awt.event.MouseListener;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import logik.DataPusher;
import javax.swing.UIManager.*;

import view.renderer.*;
import java.awt.Canvas;

public class window_Hauptmenue extends JFrame {

	private JTable table_1;
	private JPanel contentPane;
	private DataPusher myDataPusher;
	private CellRenderer myRenderer;
	private ActionListener myCon;
	private MouseListener myML;
	private PropertyChangeListener myPCL;
	private JTextField txtRaum;

	private JList list;
	private DefaultListModel listModel;
	private JTextArea textArea;

	private JButton btnTerminBearbeiten;
	private JButton btnTerminEntfernen;
	private JButton btnAbmelden;
	private Date aktDateCali;
	private JCalendar cali;
	private int rowCount;
	private JLabel picLabel;
	
	private TableModel dataModel;
	private JLabel lblAnzahlEinladungen;

	/**
	 * Launch the application.
	 */
	public window_Hauptmenue(DataPusher myDataPusher, ActionListener con,
			MouseListener mL, PropertyChangeListener pCL) {
		myCon = con;
		myML = mL;
		myPCL = pCL;
		this.myDataPusher = myDataPusher;
		myRenderer = new CellRenderer();

		init();
	}

	public JList getList() {
		return list;
	}

	public void setList(JList list) {
		this.list = list;
	}

	/**
	 * Create the frame.
	 * 
	 * @param textArea
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
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}
		setBounds(100, 100, 823, 599);

		JPanel panel = new JPanel();

		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 21, 145, 1, 342, 0, 0 };
		gbl_panel.rowHeights = new int[] { 9, 186, 0, 0, 0, 0, 110, 0, 0, 0, 31 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };

		panel.setLayout(gbl_panel);

		// Kalender Konfiguration
		cali = new JCalendar();
		cali.addPropertyChangeListener(myPCL);

		GridBagConstraints gbc_cali = new GridBagConstraints();
		gbc_cali.anchor = GridBagConstraints.NORTHWEST;
		gbc_cali.insets = new Insets(0, 0, 5, 5);
		gbc_cali.gridx = 1;
		gbc_cali.gridy = 1;
		panel.add(cali, gbc_cali);

		updateTable();

		table_1 = new JTable(dataModel);

		table_1.setDefaultRenderer(Object.class, myRenderer);
		
//		table_1.getColumn("Beschreibung").setCellRenderer(new ButtonRenderer());
//	    table_1.getColumn("Beschreibung").setCellEditor(
//	        new ButtonEditor(new JCheckBox()));
		table_1.addMouseListener(myML);

		JScrollPane scrollpane = new JScrollPane(table_1);

		table_1.getColumn("Uhrzeit").setPreferredWidth(35);
		table_1.getColumn("bis").setPreferredWidth(40);
		table_1.getColumn("Uhrzeit").setMaxWidth(35);
		table_1.getColumn("bis").setMaxWidth(40);

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
		// contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		// contentPane.setLayout(new BorderLayout(0, 0));
		// setContentPane(contentPane);
		getContentPane().add(panel);

		JLabel lblRaum = new JLabel("Raum");
		GridBagConstraints gbc_lblRaum = new GridBagConstraints();
		gbc_lblRaum.anchor = GridBagConstraints.WEST;
		gbc_lblRaum.insets = new Insets(0, 0, 5, 5);
		gbc_lblRaum.gridx = 1;
		gbc_lblRaum.gridy = 2;
		panel.add(lblRaum, gbc_lblRaum);

		txtRaum = new JTextField();

		GridBagConstraints gbc_txtRaum = new GridBagConstraints();

		gbc_txtRaum.insets = new Insets(0, 0, 5, 5);
		gbc_txtRaum.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRaum.gridx = 1;
		gbc_txtRaum.gridy = 3;
		panel.add(txtRaum, gbc_txtRaum);
		txtRaum.setColumns(10);

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

		listModel = new DefaultListModel();
		list = new JList<String>(listModel);
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

		btnTerminBearbeiten = new JButton("Termin erstellen");
		btnTerminBearbeiten.addActionListener(myCon);
		btnTerminBearbeiten
				.setToolTipText("Bitte w\u00E4hlen Sie einen Termin aus um ihn zu bearbeiten. Ist ein Termin noch leer wird er mit \"Termin bearbeiten\" erschaffen.");
		GridBagConstraints gbc_btnTerminBearbeiten = new GridBagConstraints();
		gbc_btnTerminBearbeiten.anchor = GridBagConstraints.EAST;
		gbc_btnTerminBearbeiten.insets = new Insets(0, 0, 5, 5);
		gbc_btnTerminBearbeiten.gridx = 3;
		gbc_btnTerminBearbeiten.gridy = 7;
		panel.add(btnTerminBearbeiten, gbc_btnTerminBearbeiten);

		btnTerminEntfernen = new JButton("Termin entfernen");
		btnTerminEntfernen
				.setToolTipText("W\u00E4hlen Sie einen Termin aus um ihn zu l\u00F6schen");
		btnTerminEntfernen.addActionListener(myCon);
		
		lblAnzahlEinladungen = new JLabel("AnzahlEinladungen");
		GridBagConstraints gbc_lblAnzahlEinladungen = new GridBagConstraints();
		gbc_lblAnzahlEinladungen.anchor = GridBagConstraints.NORTH;
		gbc_lblAnzahlEinladungen.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnzahlEinladungen.gridx = 1;
		gbc_lblAnzahlEinladungen.gridy = 8;
		panel.add(lblAnzahlEinladungen, gbc_lblAnzahlEinladungen);
		
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
		
		
		URL filename = getClass().getResource("briefkasten.png");
		
		if (filename!= null)
		{
		Image image = Toolkit.getDefaultToolkit().getImage( filename );
		image=image.getScaledInstance(50, 100, 0);
		 picLabel = new JLabel(new ImageIcon(image));
		picLabel.addMouseListener(myML);
		
		GridBagConstraints gbc_lblImage = new GridBagConstraints();
		gbc_lblImage.anchor = GridBagConstraints.CENTER;
		gbc_lblImage.insets = new Insets(0, 0, 5, 5);
		gbc_lblImage.gridwidth=1;
		gbc_lblImage.gridheight=1;
		gbc_lblImage.gridx = 1;
		gbc_lblImage.gridy = 8;
		panel.add(picLabel, gbc_lblImage);
		}
		else
		{
			System.out.println("Ich habe das Foto nicht gefunden");
		}
		
		
		
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/*
	 * XXX Wie im anderen Fenster bereits gezeigt und gemacht:
	 * Du solltest in der Regel keine GUI-Elemente zurückgeben
	 * oder setzen. Das gilt auch für die anderen Fenster. 
	 */
	
	
	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
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

	public void updateTable()
	{
		dataModel = new AbstractTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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

			public void setValueAt(Object value, int row, int col) {

				myDataPusher.getBeschreibungen()[row][col] = value;
				fireTableCellUpdated(row, col);
			}

		};
	}

}
