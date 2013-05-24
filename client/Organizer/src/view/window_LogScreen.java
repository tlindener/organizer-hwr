package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PasswordView;

import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class window_LogScreen extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	
	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public JPasswordField getPasswort() {
		return passwort;
	}

	public void setPasswort(JPasswordField passwort) {
		this.passwort = passwort;
	}

	private JPasswordField passwort;
	private JButton btnAnmelden;
	
	private JMenuBar menuBar;
	private JMenu mnEinstellungen;
	private JMenuItem mntmServerkonfigurationen;
	
	public JMenuItem getMntmServerkonfigurationen() {
		return mntmServerkonfigurationen;
	}

	public void setMntmServerkonfigurationen(JMenuItem mntmServerkonfigurationen) {
		this.mntmServerkonfigurationen = mntmServerkonfigurationen;
	}

	private ActionListener myAL;
	private JButton btnRegistrieren;

	public JButton getBtnRegistrieren() {
		return btnRegistrieren;
	}

	public void setBtnRegistrieren(JButton btnRegistrieren) {
		this.btnRegistrieren = btnRegistrieren;
	}

	/**
	 * Launch the application.
	 */
	public window_LogScreen(ActionListener myCon) {
		myAL=myCon;
		init();
	}

	/**
	 * Create the frame.
	 */
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 456, 308);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnEinstellungen = new JMenu("Einstellungen");
		menuBar.add(mnEinstellungen);
		
		mntmServerkonfigurationen = new JMenuItem("Serverkonfigurationen");
		mntmServerkonfigurationen.addActionListener(myAL);
		mnEinstellungen.add(mntmServerkonfigurationen);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{78, 135, 136,78, 0};
		gbl_contentPane.rowHeights = new int[]{56, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblBenutzername = new JLabel("Email-Adresse");
		GridBagConstraints gbc_lblBenutzername = new GridBagConstraints();
		gbc_lblBenutzername.gridwidth = 2;
		gbc_lblBenutzername.anchor = GridBagConstraints.WEST;
		gbc_lblBenutzername.insets = new Insets(0, 0, 5, 5);
		gbc_lblBenutzername.gridx = 1;
		gbc_lblBenutzername.gridy = 1;
		contentPane.add(lblBenutzername, gbc_lblBenutzername);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblPasswort = new JLabel("Passwort");
		GridBagConstraints gbc_lblPasswort = new GridBagConstraints();
		gbc_lblPasswort.gridwidth = 2;
		gbc_lblPasswort.anchor = GridBagConstraints.WEST;
		gbc_lblPasswort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPasswort.gridx = 1;
		gbc_lblPasswort.gridy = 3;
		contentPane.add(lblPasswort, gbc_lblPasswort);
		
		passwort = new JPasswordField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 2;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 4;
		contentPane.add(passwort, gbc_textField_1);
		passwort.setColumns(10);
		
		btnAnmelden = new JButton("Anmelden");
		btnAnmelden.addActionListener(myAL);
		
		btnRegistrieren = new JButton("Registrieren");
		btnRegistrieren.addActionListener(myAL);
		GridBagConstraints gbc_btnRegistrieren = new GridBagConstraints();
		gbc_btnRegistrieren.insets = new Insets(0, 0, 0, 5);
		gbc_btnRegistrieren.gridx = 1;
		gbc_btnRegistrieren.gridy = 6;
		contentPane.add(btnRegistrieren, gbc_btnRegistrieren);
		GridBagConstraints gbc_btnAnmelden = new GridBagConstraints();
		gbc_btnAnmelden.insets = new Insets(0, 0, 0, 5);
		gbc_btnAnmelden.gridx = 2;
		gbc_btnAnmelden.gridy = 6;
		contentPane.add(btnAnmelden, gbc_btnAnmelden);
	}

	public JButton getBtnAnmelden() {
		return btnAnmelden;
	}

	public void setBtnAnmelden(JButton btnAnmelden) {
		this.btnAnmelden = btnAnmelden;
	}

}
