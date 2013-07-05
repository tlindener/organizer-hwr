package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.Canvas;
import java.awt.TextArea;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JButton;

public class Einladungen extends JFrame {

	private JPanel contentPane;
	private JTextField txtBeschreibung;
	private JTextField txtEinladener;
	private JTextField txtVon;
	private JTextField txtBis;
	private JTextField txtRaum;
	private ActionListener myCon;
	private JButton btnAbsagen;
	private JButton btnZusagen;
	private Canvas canStatus;
	private JTextArea txtADetails;
/**
 * Default constructor that initializes the actionlistener and creates the frame.
 * @param Con
 */
	public Einladungen(ActionListener Con) {
		myCon=Con;
		init();
	}
	/**
	 * Create the frame.
	 */
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 143, 127, 0, 129, 0, 0};
		gbl_contentPane.rowHeights = new int[]{39, 0, 0, 0, 0, 0, 0, 0, 164, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblBeschreibung = new JLabel("Beschreibung");
		GridBagConstraints gbc_lblBeschreibung = new GridBagConstraints();
		gbc_lblBeschreibung.gridwidth = 2;
		gbc_lblBeschreibung.anchor = GridBagConstraints.WEST;
		gbc_lblBeschreibung.insets = new Insets(0, 0, 5, 5);
		gbc_lblBeschreibung.gridx = 1;
		gbc_lblBeschreibung.gridy = 1;
		contentPane.add(lblBeschreibung, gbc_lblBeschreibung);
		
		btnZusagen = new JButton("Zusagen");
		btnZusagen.addActionListener(myCon);
		GridBagConstraints gbc_btnZusagen = new GridBagConstraints();
		gbc_btnZusagen.anchor = GridBagConstraints.EAST;
		gbc_btnZusagen.insets = new Insets(0, 0, 5, 5);
		gbc_btnZusagen.gridx = 4;
		gbc_btnZusagen.gridy = 1;
		contentPane.add(btnZusagen, gbc_btnZusagen);
		
		txtBeschreibung = new JTextField();
		txtBeschreibung.setEditable(false);
		GridBagConstraints gbc_txtBeschreibung = new GridBagConstraints();
		gbc_txtBeschreibung.gridwidth = 2;
		gbc_txtBeschreibung.insets = new Insets(0, 0, 5, 5);
		gbc_txtBeschreibung.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBeschreibung.gridx = 1;
		gbc_txtBeschreibung.gridy = 2;
		contentPane.add(txtBeschreibung, gbc_txtBeschreibung);
		txtBeschreibung.setColumns(10);
		
		btnAbsagen = new JButton("Absagen");
		btnAbsagen.addActionListener(myCon);
		GridBagConstraints gbc_btnAbsagen = new GridBagConstraints();
		gbc_btnAbsagen.anchor = GridBagConstraints.EAST;
		gbc_btnAbsagen.insets = new Insets(0, 0, 5, 5);
		gbc_btnAbsagen.gridx = 4;
		gbc_btnAbsagen.gridy = 2;
		contentPane.add(btnAbsagen, gbc_btnAbsagen);
		
		JLabel lblNewLabel = new JLabel("Eingeladen von");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 3;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		txtEinladener = new JTextField();
		txtEinladener.setEditable(false);
		GridBagConstraints gbc_txtEinladener = new GridBagConstraints();
		gbc_txtEinladener.anchor = GridBagConstraints.NORTH;
		gbc_txtEinladener.gridwidth = 2;
		gbc_txtEinladener.insets = new Insets(0, 0, 5, 5);
		gbc_txtEinladener.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEinladener.gridx = 1;
		gbc_txtEinladener.gridy = 4;
		contentPane.add(txtEinladener, gbc_txtEinladener);
		txtEinladener.setColumns(10);
		
		JLabel lblVon = new JLabel("von");
		GridBagConstraints gbc_lblVon = new GridBagConstraints();
		gbc_lblVon.anchor = GridBagConstraints.WEST;
		gbc_lblVon.insets = new Insets(0, 0, 5, 5);
		gbc_lblVon.gridx = 1;
		gbc_lblVon.gridy = 5;
		contentPane.add(lblVon, gbc_lblVon);
		
		JLabel lblBis = new JLabel("bis");
		GridBagConstraints gbc_lblBis = new GridBagConstraints();
		gbc_lblBis.anchor = GridBagConstraints.WEST;
		gbc_lblBis.insets = new Insets(0, 0, 5, 5);
		gbc_lblBis.gridx = 2;
		gbc_lblBis.gridy = 5;
		contentPane.add(lblBis, gbc_lblBis);
		
		JLabel lblBelegt = new JLabel("belegt?");
		GridBagConstraints gbc_lblBelegt = new GridBagConstraints();
		gbc_lblBelegt.anchor = GridBagConstraints.EAST;
		gbc_lblBelegt.insets = new Insets(0, 0, 5, 5);
		gbc_lblBelegt.gridx = 4;
		gbc_lblBelegt.gridy = 5;
		contentPane.add(lblBelegt, gbc_lblBelegt);
		
		txtVon = new JTextField();
		txtVon.setEditable(false);
		GridBagConstraints gbc_txtVon = new GridBagConstraints();
		gbc_txtVon.anchor = GridBagConstraints.NORTH;
		gbc_txtVon.insets = new Insets(0, 0, 5, 5);
		gbc_txtVon.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtVon.gridx = 1;
		gbc_txtVon.gridy = 6;
		contentPane.add(txtVon, gbc_txtVon);
		txtVon.setColumns(10);
		
		txtBis = new JTextField();
		txtBis.setEditable(false);
		GridBagConstraints gbc_txtBis = new GridBagConstraints();
		gbc_txtBis.anchor = GridBagConstraints.NORTH;
		gbc_txtBis.insets = new Insets(0, 0, 5, 5);
		gbc_txtBis.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBis.gridx = 2;
		gbc_txtBis.gridy = 6;
		contentPane.add(txtBis, gbc_txtBis);
		txtBis.setColumns(10);
		
		canStatus = new Canvas();
		canStatus.setSize(20, 20);
		GridBagConstraints gbc_canStatus = new GridBagConstraints();
		gbc_canStatus.anchor = GridBagConstraints.EAST;
		gbc_canStatus.insets = new Insets(0, 0, 5, 5);
		gbc_canStatus.gridx = 4;
		gbc_canStatus.gridy = 6;
		contentPane.add(canStatus, gbc_canStatus);
		
		JLabel lblDetails = new JLabel("Details");
		GridBagConstraints gbc_lblDetails = new GridBagConstraints();
		gbc_lblDetails.anchor = GridBagConstraints.WEST;
		gbc_lblDetails.insets = new Insets(0, 0, 5, 5);
		gbc_lblDetails.gridx = 1;
		gbc_lblDetails.gridy = 7;
		contentPane.add(lblDetails, gbc_lblDetails);
		
		JLabel lblRaum = new JLabel("Raum");
		GridBagConstraints gbc_lblRaum = new GridBagConstraints();
		gbc_lblRaum.anchor = GridBagConstraints.EAST;
		gbc_lblRaum.insets = new Insets(0, 0, 5, 5);
		gbc_lblRaum.gridx = 4;
		gbc_lblRaum.gridy = 7;
		contentPane.add(lblRaum, gbc_lblRaum);
		
		txtADetails = new JTextArea();
		txtADetails.setEditable(false);
		GridBagConstraints gbc_txtADetails = new GridBagConstraints();
		gbc_txtADetails.gridwidth = 2;
		gbc_txtADetails.insets = new Insets(0, 0, 0, 5);
		gbc_txtADetails.fill = GridBagConstraints.BOTH;
		gbc_txtADetails.gridx = 1;
		gbc_txtADetails.gridy = 8;
		contentPane.add(txtADetails, gbc_txtADetails);
		
		txtRaum = new JTextField();
		txtRaum.setEditable(false);
		GridBagConstraints gbc_txtRaum = new GridBagConstraints();
		gbc_txtRaum.anchor = GridBagConstraints.NORTH;
		gbc_txtRaum.insets = new Insets(0, 0, 0, 5);
		gbc_txtRaum.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRaum.gridx = 4;
		gbc_txtRaum.gridy = 8;
		contentPane.add(txtRaum, gbc_txtRaum);
		txtRaum.setColumns(10);
	}
	public JTextField getTxtBeschreibung() {
		return txtBeschreibung;
	}
	public void setTxtBeschreibung(JTextField txtBeschreibung) {
		this.txtBeschreibung = txtBeschreibung;
	}
	public JTextField getTxtEinladener() {
		return txtEinladener;
	}
	public void setTxtEinladener(JTextField txtEinladener) {
		this.txtEinladener = txtEinladener;
	}
	public JTextField getTxtVon() {
		return txtVon;
	}
	public void setTxtVon(JTextField txtVon) {
		this.txtVon = txtVon;
	}
	public JTextField getTxtBis() {
		return txtBis;
	}
	public void setTxtBis(JTextField txtBis) {
		this.txtBis = txtBis;
	}
	public JTextField getTxtRaum() {
		return txtRaum;
	}
	public void setTxtRaum(JTextField txtRaum) {
		this.txtRaum = txtRaum;
	}
	public JButton getBtnAbsagen() {
		return btnAbsagen;
	}
	public void setBtnAbsagen(JButton btnAbsagen) {
		this.btnAbsagen = btnAbsagen;
	}
	public JButton getBtnZusagen() {
		return btnZusagen;
	}
	public void setBtnZusagen(JButton btnZusagen) {
		this.btnZusagen = btnZusagen;
	}
	public Canvas getCanStatus() {
		return canStatus;
	}
	public void setCanStatus(Canvas canStatus) {
		this.canStatus = canStatus;
	}
	public JTextArea getTxtADetails() {
		return txtADetails;
	}
	public void setTxtADetails(JTextArea txtADetails) {
		this.txtADetails = txtADetails;
	}
	
	
	

}
