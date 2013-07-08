package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * JFrame in which the Serverkonfigurations (address and port) can be changed.
 * 
 * @author Jennifer Blumenthal
 *
 */
public class Servereinstellungen extends JFrame {

	private JPanel contentPane;
	private JTextField txtAdresse;
	
	private JLabel lblServeradresse;
	private JLabel lblPort;
	private JButton btnSpeichern;
	private ActionListener myAL;
	private JTextField txtPort;
	private NumberFormat formatport;
	
	/**
	 * Default constructor that initializes the submitted ActionListener and 
	 * calls the init() method to create the Frame.
	 * 
	 * @param myCon
	 */
	public Servereinstellungen(ActionListener myCon) {
		myAL=myCon;
		init();
	}

	

	/**
	 * Create the frame.
	 */
	public void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 438, 192);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{53, 185, 37, 53, 49, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblServeradresse = new JLabel("Serveradresse");
		GridBagConstraints gbc_lblServeradresse = Util.createGridBagContraints(1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		contentPane.add(lblServeradresse, gbc_lblServeradresse);
		
		lblPort = new JLabel("Port");
		GridBagConstraints gbc_lblPort = Util.createGridBagContraints(3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		contentPane.add(lblPort, gbc_lblPort);
		
		txtAdresse = new JTextField("localhost");
		GridBagConstraints gbc_txtAdresse = Util.createGridBagContraints(1,1,1, 2, GridBagConstraints.BOTH);
		contentPane.add(txtAdresse, gbc_txtAdresse);
		
		
		
		txtPort = new JTextField("80",5);
		GridBagConstraints gbc_txtPort = Util.createGridBagContraints(1,1, 3, 2, GridBagConstraints.HORIZONTAL);
		contentPane.add(txtPort, gbc_txtPort);
		txtPort.setColumns(10);
		
		btnSpeichern = new JButton("Speichern");
		btnSpeichern.addActionListener(myAL);
		GridBagConstraints gbc_btnSpeichern = Util.createGridBagContraints(3, 5, GridBagConstraints.CENTER, GridBagConstraints.NONE);
		contentPane.add(btnSpeichern, gbc_btnSpeichern);
	}

	/**
	 * Proofs if the fields are filled.
	 * @return fehler
	 */
	public boolean pruefeFelder()
	{
		boolean fehler=false;
		String adresse=txtAdresse.getText();
		String port= txtPort.getText();
		Color c = new Color(255, 86, 63);
		Border redline = BorderFactory.createLineBorder(c);
		if(adresse.isEmpty()||port.isEmpty())
		{
			if(adresse.isEmpty())
				txtAdresse.setBorder(redline);
			if(port.isEmpty())
				txtPort.setBorder(redline);
			fehler=true;
		}
		return fehler;
	}
	
	public JTextField getTxtPort() {
		return txtPort;
	}


	public void setTxtPort(JTextField txtPort) {
		this.txtPort = txtPort;
	}


	public JTextField getTxtAdresse() {
		return txtAdresse;
	}


	public void setTxtAdresse(JTextField txtAdresse) {
		this.txtAdresse = txtAdresse;
	}

	public JButton getBtnSpeichern() {
		return btnSpeichern;
	}

	public void setBtnSpeichern(JButton btnSpeichern) {
		this.btnSpeichern = btnSpeichern;
	}
}
