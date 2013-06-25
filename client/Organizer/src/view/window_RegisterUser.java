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

public class window_RegisterUser extends JFrame {

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

	private ActionListener myAL;

	/**
	 * Launch the application.
	 */
	public window_RegisterUser(ActionListener con)
	{
		myAL=con;
		init();
	}

	/**
	 * Create the frame.
	 */
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 403);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{40, 123, 18, 155, 40, 0};
		gbl_contentPane.rowHeights = new int[]{50, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 30, 50};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblEmailadresse = new JLabel("Email-Adresse");
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
		
		JLabel lblVorname = new JLabel("Vorname");
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
		
		JLabel lblNachname = new JLabel("Nachname");
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
		
		JLabel lblPasswort = new JLabel("Passwort");
		GridBagConstraints gbc_lblPasswort = new GridBagConstraints();
		gbc_lblPasswort.anchor = GridBagConstraints.WEST;
		gbc_lblPasswort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPasswort.gridx = 1;
		gbc_lblPasswort.gridy = 7;
		contentPane.add(lblPasswort, gbc_lblPasswort);
		
		txtPasswort = new JPasswordField();
		txtPasswort.setText("");
		GridBagConstraints gbc_txtPasswort = new GridBagConstraints();
		gbc_txtPasswort.insets = new Insets(0, 0, 5, 5);
		gbc_txtPasswort.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPasswort.gridx = 3;
		gbc_txtPasswort.gridy = 7;
		contentPane.add(txtPasswort, gbc_txtPasswort);
		txtPasswort.setColumns(10);
		
		JLabel lblPasswortBesttigen = new JLabel("Passwort best\u00E4tigen");
		GridBagConstraints gbc_lblPasswortBesttigen = new GridBagConstraints();
		gbc_lblPasswortBesttigen.anchor = GridBagConstraints.WEST;
		gbc_lblPasswortBesttigen.insets = new Insets(0, 0, 5, 5);
		gbc_lblPasswortBesttigen.gridx = 1;
		gbc_lblPasswortBesttigen.gridy = 9;
		contentPane.add(lblPasswortBesttigen, gbc_lblPasswortBesttigen);
		
		txtPasswortBest = new JPasswordField();
		GridBagConstraints gbc_txtPasswortBest = new GridBagConstraints();
		gbc_txtPasswortBest.insets = new Insets(0, 0, 5, 5);
		gbc_txtPasswortBest.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPasswortBest.gridx = 3;
		gbc_txtPasswortBest.gridy = 9;
		contentPane.add(txtPasswortBest, gbc_txtPasswortBest);
		txtPasswortBest.setColumns(10);
		
		btnRegistrieren = new JButton("Registrieren");
		btnRegistrieren.addActionListener(myAL);
		GridBagConstraints gbc_btnRegistrieren = new GridBagConstraints();
		gbc_btnRegistrieren.anchor = GridBagConstraints.EAST;
		gbc_btnRegistrieren.insets = new Insets(0, 0, 0, 5);
		gbc_btnRegistrieren.gridx = 3;
		gbc_btnRegistrieren.gridy = 11;
		contentPane.add(btnRegistrieren, gbc_btnRegistrieren);
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
		Border blackline = BorderFactory.createLineBorder(Color.black);
		if (getTxtEmailadresse().getText().equals("")) 
			getTxtEmailadresse().setBorder(redline);
		else
			getTxtEmailadresse().setBorder(blackline);

		if (getTxtNachname().getText().equals(""))
			getTxtNachname().setBorder(redline);
		else
			getTxtNachname().setBorder(blackline);
		
		if (getTxtVorname().getText().equals("")) 
			getTxtVorname().setBorder(redline);
		else
			getTxtVorname().setBorder(blackline);
	
		if (getTxtPasswort().getPassword().length==0) 
			getTxtPasswort().setBorder(redline);
		else
			getTxtPasswort().setBorder(blackline);
		if (getTxtPasswortBest().getPassword().length==0) 
			getTxtPasswortBest().setBorder(redline);
		else
			getTxtPasswortBest().setBorder(blackline);
		
		repaint();
		}
	}



}
