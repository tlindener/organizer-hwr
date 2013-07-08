package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
/**
 * LogScreen in which the User can log in with valid user id and password.
 * 
 * @author Jennifer Blumenthal
 *
 */
public class LogScreen extends JFrame {

	private JPanel contentPane;
	private JTextField txtAdresse;

	private JPasswordField passwort;
	private JButton btnAnmelden;
	
	private JMenuBar menuBar;
	private JMenu mnEinstellungen;
	private JMenuItem mntmServerkonfigurationen;
	private ActionListener myAL;
	private JButton btnRegistrieren;


	/**
	 * Default constructor that initializes the submitted ActionListener and 
	 * calls the init() method to create the frame.
	 */
	public LogScreen(ActionListener myCon) {
		myAL=myCon;
		init();
	}

	/**
	 * Create the frame.
	 */
	public void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		GridBagConstraints gbc_lblBenutzername = Util.createGridBagContraints(2, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		contentPane.add(lblBenutzername, gbc_lblBenutzername);
		
		txtAdresse = new JTextField();
		GridBagConstraints gbc_textField = Util.createGridBagContraints(2, 1, 1, 2, GridBagConstraints.HORIZONTAL);
		contentPane.add(txtAdresse, gbc_textField);
		txtAdresse.setColumns(10);
		
		JLabel lblPasswort = new JLabel("Passwort");
		GridBagConstraints gbc_lblPasswort =Util.createGridBagContraints(2, 1, 1, 3, GridBagConstraints.WEST, GridBagConstraints.NONE);
		contentPane.add(lblPasswort, gbc_lblPasswort);
		
		passwort = new JPasswordField();
		GridBagConstraints gbc_textField_1 = Util.createGridBagContraints(2, 1, 1, 4, GridBagConstraints.HORIZONTAL);
		contentPane.add(passwort, gbc_textField_1);
		passwort.setColumns(10);
		
		btnAnmelden = new JButton("Anmelden");
		btnAnmelden.addActionListener(myAL);
		GridBagConstraints gbc_btnAnmelden = Util.createGridBagContraints(2, 6, GridBagConstraints.CENTER, GridBagConstraints.NONE);
		contentPane.add(btnAnmelden, gbc_btnAnmelden);
		
		btnRegistrieren = new JButton("Registrieren");
		btnRegistrieren.addActionListener(myAL);
		GridBagConstraints gbc_btnRegistrieren = Util.createGridBagContraints(1, 6, GridBagConstraints.CENTER, GridBagConstraints.NONE);
		contentPane.add(btnRegistrieren, gbc_btnRegistrieren);
		
	}

	public JButton getBtnAnmelden() {
		return btnAnmelden;
	}

	public void setBtnAnmelden(JButton btnAnmelden) {
		this.btnAnmelden = btnAnmelden;
	}
	public JTextField getTextField() {
		return txtAdresse;
	}

	public void setTextField(JTextField textField) {
		this.txtAdresse = textField;
	}

	public JPasswordField getPasswort() {
		return passwort;
	}

	public void setPasswort(JPasswordField passwort) {
		this.passwort = passwort;
	}
	public JMenuItem getMntmServerkonfigurationen() {
		return mntmServerkonfigurationen;
	}

	public void setMntmServerkonfigurationen(JMenuItem mntmServerkonfigurationen) {
		this.mntmServerkonfigurationen = mntmServerkonfigurationen;
	}
	
	public JButton getBtnRegistrieren() {
		return btnRegistrieren;
	}

	public void setBtnRegistrieren(JButton btnRegistrieren) {
		this.btnRegistrieren = btnRegistrieren;
	}


}
