package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JButton;

public class RegisterUser extends JFrame {

	private JPanel contentPane;
	private JTextField txtEmailadresse;
	private JTextField txtVorname;
	private JTextField txtNachname;
	private JPasswordField txtPasswort;
	private JPasswordField txtPasswortBest;
	private JButton btnRegistrieren;
	
	public JButton getBtnRegistrieren() {
		return btnRegistrieren;
	}

	public void setBtnRegistrieren(JButton btnRegistrieren) {
		this.btnRegistrieren = btnRegistrieren;
	}

	private ActionListener myCon;
	private JButton btnAbbrechen;
	private JLabel lblTelefon;
	private JTextField txtTelefon;
	private JLabel lblpflichtfeld;

	/**
	 * Launch the application.
	 */
	public RegisterUser(ActionListener con)
	{
		myCon=con;
		init();
	}

	/**
	 * Create the frame.
	 */
	public void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 387, 483);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{40, 123, 18, 155, 40, 0};
		gbl_contentPane.rowHeights = new int[]{50, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 0, 50};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblEmailadresse = new JLabel("Email-Adresse*");
		GridBagConstraints gbc_lblEmailadresse = new GridBagConstraints();
		gbc_lblEmailadresse.anchor = GridBagConstraints.WEST;
		gbc_lblEmailadresse.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmailadresse.gridx = 1;
		gbc_lblEmailadresse.gridy = 1;
		contentPane.add(lblEmailadresse, gbc_lblEmailadresse);
		
		txtEmailadresse = new JTextField();
	
		GridBagConstraints gbc_txtEmailadresse = new GridBagConstraints();
		gbc_txtEmailadresse.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmailadresse.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmailadresse.gridx = 3;
		gbc_txtEmailadresse.gridy = 1;
		contentPane.add(txtEmailadresse, gbc_txtEmailadresse);
		txtEmailadresse.setColumns(10);
		
		JLabel lblVorname = new JLabel("Vorname*");
		GridBagConstraints gbc_lblVorname = new GridBagConstraints();
		gbc_lblVorname.anchor = GridBagConstraints.WEST;
		gbc_lblVorname.insets = new Insets(0, 0, 5, 5);
		gbc_lblVorname.gridx = 1;
		gbc_lblVorname.gridy = 3;
		contentPane.add(lblVorname, gbc_lblVorname);
		
		txtVorname = new JTextField();
		txtVorname.setColumns(10);
		GridBagConstraints gbc_txtVorname = new GridBagConstraints();
		gbc_txtVorname.insets = new Insets(0, 0, 5, 5);
		gbc_txtVorname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtVorname.gridx = 3;
		gbc_txtVorname.gridy = 3;
		contentPane.add(txtVorname, gbc_txtVorname);
		
		JLabel lblNachname = new JLabel("Nachname*");
		GridBagConstraints gbc_lblNachname = new GridBagConstraints();
		gbc_lblNachname.anchor = GridBagConstraints.WEST;
		gbc_lblNachname.insets = new Insets(0, 0, 5, 5);
		gbc_lblNachname.gridx = 1;
		gbc_lblNachname.gridy = 5;
		contentPane.add(lblNachname, gbc_lblNachname);
		
		txtNachname = new JTextField();
		GridBagConstraints gbc_txtNachname = new GridBagConstraints();
		gbc_txtNachname.insets = new Insets(0, 0, 5, 5);
		gbc_txtNachname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNachname.gridx = 3;
		gbc_txtNachname.gridy = 5;
		contentPane.add(txtNachname, gbc_txtNachname);
		txtNachname.setColumns(10);
		
		lblTelefon = new JLabel("Telefon");
		GridBagConstraints gbc_lblTelefon = new GridBagConstraints();
		gbc_lblTelefon.anchor = GridBagConstraints.WEST;
		gbc_lblTelefon.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefon.gridx = 1;
		gbc_lblTelefon.gridy = 7;
		contentPane.add(lblTelefon, gbc_lblTelefon);
		
		txtTelefon = new JTextField();
		GridBagConstraints gbc_txtTelefon = new GridBagConstraints();
		gbc_txtTelefon.insets = new Insets(0, 0, 5, 5);
		gbc_txtTelefon.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelefon.gridx = 3;
		gbc_txtTelefon.gridy = 7;
		contentPane.add(txtTelefon, gbc_txtTelefon);
		txtTelefon.setColumns(10);
		
		JLabel lblPasswort = new JLabel("Passwort*");
		GridBagConstraints gbc_lblPasswort = new GridBagConstraints();
		gbc_lblPasswort.anchor = GridBagConstraints.WEST;
		gbc_lblPasswort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPasswort.gridx = 1;
		gbc_lblPasswort.gridy = 9;
		contentPane.add(lblPasswort, gbc_lblPasswort);
		
		txtPasswort = new JPasswordField();
		txtPasswort.setText("");
		GridBagConstraints gbc_txtPasswort = new GridBagConstraints();
		gbc_txtPasswort.insets = new Insets(0, 0, 5, 5);
		gbc_txtPasswort.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPasswort.gridx = 3;
		gbc_txtPasswort.gridy = 9;
		contentPane.add(txtPasswort, gbc_txtPasswort);
		txtPasswort.setColumns(10);
		
		JLabel lblPasswortBesttigen = new JLabel("Passwort best\u00E4tigen*");
		GridBagConstraints gbc_lblPasswortBesttigen = new GridBagConstraints();
		gbc_lblPasswortBesttigen.anchor = GridBagConstraints.WEST;
		gbc_lblPasswortBesttigen.insets = new Insets(0, 0, 5, 5);
		gbc_lblPasswortBesttigen.gridx = 1;
		gbc_lblPasswortBesttigen.gridy = 11;
		contentPane.add(lblPasswortBesttigen, gbc_lblPasswortBesttigen);
		
		txtPasswortBest = new JPasswordField();
		GridBagConstraints gbc_txtPasswortBest = new GridBagConstraints();
		gbc_txtPasswortBest.insets = new Insets(0, 0, 5, 5);
		gbc_txtPasswortBest.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPasswortBest.gridx = 3;
		gbc_txtPasswortBest.gridy = 11;
		contentPane.add(txtPasswortBest, gbc_txtPasswortBest);
		txtPasswortBest.setColumns(10);
		
		btnRegistrieren = new JButton("Registrieren");
		btnRegistrieren.addActionListener(myCon);
		GridBagConstraints gbc_btnRegistrieren = new GridBagConstraints();
		gbc_btnRegistrieren.anchor = GridBagConstraints.EAST;
		gbc_btnRegistrieren.insets = new Insets(0, 0, 5, 5);
		gbc_btnRegistrieren.gridx = 3;
		gbc_btnRegistrieren.gridy = 13;
		contentPane.add(btnRegistrieren, gbc_btnRegistrieren);
		
		btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.addActionListener(myCon);
		
		lblpflichtfeld = new JLabel("*Pflichtfeld");
		GridBagConstraints gbc_lblpflichtfeld = new GridBagConstraints();
		gbc_lblpflichtfeld.anchor = GridBagConstraints.WEST;
		gbc_lblpflichtfeld.insets = new Insets(0, 0, 0, 5);
		gbc_lblpflichtfeld.gridx = 1;
		gbc_lblpflichtfeld.gridy = 14;
		contentPane.add(lblpflichtfeld, gbc_lblpflichtfeld);
		GridBagConstraints gbc_btnAbbrechen = new GridBagConstraints();
		gbc_btnAbbrechen.anchor = GridBagConstraints.EAST;
		gbc_btnAbbrechen.insets = new Insets(0, 0, 0, 5);
		gbc_btnAbbrechen.gridx = 3;
		gbc_btnAbbrechen.gridy = 14;
		contentPane.add(btnAbbrechen, gbc_btnAbbrechen);
	}

	

	public JTextField getTxtTelefon() {
		return txtTelefon;
	}

	public void setTxtTelefon(JTextField txtTelefon) {
		this.txtTelefon = txtTelefon;
	}

	public JButton getBtnAbbrechen() {
		return btnAbbrechen;
	}

	public void setBtnAbbrechen(JButton btnAbbrechen) {
		this.btnAbbrechen = btnAbbrechen;
	}

	public JTextField getTxtVorname() {
		return txtVorname;
	}

	public void setTxtVorname(JTextField txtVorname) {
		this.txtVorname = txtVorname;
	}

	public JTextField getTxtNachname() {
		return txtNachname;
	}

	public void setTxtNachname(JTextField txtNachname) {
		this.txtNachname = txtNachname;
	}

	

	public JPasswordField getTxtPasswort() {
		return txtPasswort;
	}

	public void setTxtPasswort(JPasswordField txtPasswort) {
		this.txtPasswort = txtPasswort;
	}

	public JPasswordField getTxtPasswortBest() {
		return txtPasswortBest;
	}

	public void setTxtPasswortBest(JPasswordField txtPasswortBest) {
		this.txtPasswortBest = txtPasswortBest;
	}

	public JTextField getTxtEmailadresse() {
		return txtEmailadresse;
	}

	public void setTxtEmailadresse(JTextField txtEmailadresse) {
		this.txtEmailadresse = txtEmailadresse;
	}
	
	public void clear()
	{
		getTxtEmailadresse().setText("");
		getTxtNachname().setText("");
		getTxtVorname().setText("");
		getTxtPasswort().setText("");
		getTxtPasswortBest().setText("");
		getTxtTelefon().setText("");
		}
	
	public void pruefeVollständigkeit()
	{
		if (getTxtEmailadresse().getText().isEmpty()||
			getTxtNachname().getText().isEmpty()||
			getTxtVorname().getText().isEmpty()||
			getTxtPasswort().getPassword().length==0||
			getTxtPasswortBest().getPassword().length==0)
		{
			JOptionPane.showMessageDialog(this,
					"Bitte füllen Sie alle Felder aus!", "Felder frei",
					JOptionPane.INFORMATION_MESSAGE);
			
		Color c = new Color(255, 86, 63);
		Border redline = BorderFactory.createLineBorder(c);
		
		if (getTxtEmailadresse().getText().equals("")) 
			getTxtEmailadresse().setBorder(redline);
		if (getTxtNachname().getText().equals(""))
			getTxtNachname().setBorder(redline);
		if (getTxtVorname().getText().equals("")) 
			getTxtVorname().setBorder(redline);
		if (getTxtPasswort().getPassword().length==0) 
			getTxtPasswort().setBorder(redline);
		if (getTxtPasswortBest().getPassword().length==0) 
			getTxtPasswortBest().setBorder(redline);
		
		repaint();
		}
	}
	
	public void loescheInhalte()
	{
		getTxtEmailadresse().setText("");
		getTxtNachname().setText("");
		getTxtVorname().setText("");
		getTxtPasswort().setText("");
		getTxtPasswortBest().setText("");
		getTxtTelefon().setText("");
		
	}



}
