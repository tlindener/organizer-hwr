package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

/**
 * This frame supports creating a new room.
 * Therefore the fields name, location and seats are required.
 * 
 * @author Jenny
 *
 */
public class NeuerRaum extends JFrame {

	private JPanel contentPane;
	private JTextField txtBeschreibung;
	private JTextField txtSitze;
	private JTextArea txtALage;
	
	private ActionListener myCon;
	private JButton btnSpeichern;
	private JButton btnAbbrechen;
	
	private NumberFormat numberformat;

	/**
	 * Launch the application.
	 */
	public NeuerRaum(ActionListener aL)
	{
					myCon= aL;
					init();

				
	}
	/**
	 * Create the frame.
	 */
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		setBounds(100, 100, 514, 292);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblBeschreibung = new JLabel("Beschreibung");
		GridBagConstraints gbc_lblBeschreibung = new GridBagConstraints();
		gbc_lblBeschreibung.anchor = GridBagConstraints.WEST;
		gbc_lblBeschreibung.insets = new Insets(0, 0, 5, 5);
		gbc_lblBeschreibung.gridx = 1;
		gbc_lblBeschreibung.gridy = 1;
		contentPane.add(lblBeschreibung, gbc_lblBeschreibung);
		
		JLabel lblNewLabel = new JLabel("Sitze");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 1;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		txtBeschreibung = new JTextField();
		GridBagConstraints gbc_txtBeschreibung = new GridBagConstraints();
		gbc_txtBeschreibung.insets = new Insets(0, 0, 5, 5);
		gbc_txtBeschreibung.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBeschreibung.gridx = 1;
		gbc_txtBeschreibung.gridy = 2;
		contentPane.add(txtBeschreibung, gbc_txtBeschreibung);
		txtBeschreibung.setColumns(10);
		
		numberformat=NumberFormat.getNumberInstance();
		txtSitze = new JFormattedTextField(numberformat);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 2;
		contentPane.add(txtSitze, gbc_textField);
		txtSitze.setColumns(10);
		
		JLabel lblLage = new JLabel("Lage");
		GridBagConstraints gbc_lblLage = new GridBagConstraints();
		gbc_lblLage.anchor = GridBagConstraints.WEST;
		gbc_lblLage.insets = new Insets(0, 0, 5, 5);
		gbc_lblLage.gridx = 1;
		gbc_lblLage.gridy = 3;
		contentPane.add(lblLage, gbc_lblLage);
		
		txtALage = new JTextArea();
		GridBagConstraints gbc_txtALage = new GridBagConstraints();
		gbc_txtALage.gridwidth = 3;
		gbc_txtALage.insets = new Insets(0, 0, 5, 5);
		gbc_txtALage.fill = GridBagConstraints.BOTH;
		gbc_txtALage.gridx = 1;
		gbc_txtALage.gridy = 4;
		contentPane.add(txtALage, gbc_txtALage);
		
		btnSpeichern = new JButton("Speichern");
		btnSpeichern.addActionListener(myCon);
		GridBagConstraints gbc_btnSpeichern = new GridBagConstraints();
		gbc_btnSpeichern.anchor = GridBagConstraints.EAST;
		gbc_btnSpeichern.insets = new Insets(0, 0, 5, 5);
		gbc_btnSpeichern.gridx = 3;
		gbc_btnSpeichern.gridy = 5;
		contentPane.add(btnSpeichern, gbc_btnSpeichern);
		
		btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.addActionListener(myCon);
		GridBagConstraints gbc_btnAbbrechen = new GridBagConstraints();
		gbc_btnAbbrechen.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnAbbrechen.insets = new Insets(0, 0, 0, 5);
		gbc_btnAbbrechen.gridx = 3;
		gbc_btnAbbrechen.gridy = 6;
		contentPane.add(btnAbbrechen, gbc_btnAbbrechen);
	}
	public JTextField getTxtBeschreibung() {
		return txtBeschreibung;
	}
	public void setTxtBeschreibung(JTextField txtBeschreibung) {
		this.txtBeschreibung = txtBeschreibung;
	}
	public JTextField getTxtSitze() {
		return txtSitze;
	}
	public void setTxtSitze(JTextField textField) {
		this.txtSitze = textField;
	}
	public JTextArea getTxtALage() {
		return txtALage;
	}
	public void setTxtALage(JTextArea txtALage) {
		this.txtALage = txtALage;
	}
	public JButton getBtnSpeichern() {
		return btnSpeichern;
	}
	public void setBtnSpeichern(JButton btnSpeichern) {
		this.btnSpeichern = btnSpeichern;
	}
	public JButton getBtnAbbrechen() {
		return btnAbbrechen;
	}
	public void setBtnAbbrechen(JButton btnAbbrechen) {
		this.btnAbbrechen = btnAbbrechen;
	}
	
	public void pruefeFelder()
	{
		String lage=getTxtALage().getText();
		String beschreibung=getTxtBeschreibung().getText();
		String sitze= getTxtSitze().getText();
		
		if(lage.isEmpty()||beschreibung.isEmpty()||sitze.isEmpty())
		{
			Color c = new Color(255, 86, 63);
			Border redline = BorderFactory.createLineBorder(c);
			
			JOptionPane.showMessageDialog(this, "Bitte füllen Sie alle Felder aus!");
			
			if(lage.isEmpty())
				getTxtALage().setBorder(redline);
			if(beschreibung.isEmpty())
				getTxtBeschreibung().setBorder(redline);
			if(sitze.isEmpty())
				getTxtSitze().setBorder(redline);	
		}	
		
	}
}
