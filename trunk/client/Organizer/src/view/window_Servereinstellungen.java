package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

public class window_Servereinstellungen extends JFrame {

	private JPanel contentPane;
	private JTextField txtAdresse;
	
	private JLabel lblServeradresse;
	private JLabel lblPort;
	private JButton btnSpeichern;
	private ActionListener myAL;
	private JTextField txtPort;
	private NumberFormat formatport;
	
	public window_Servereinstellungen(ActionListener myCon) {
		myAL=myCon;
		init();
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

	/**
	 * Create the frame.
	 */
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 438, 192);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{53, 185, 37, 53, 49, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblServeradresse = new JLabel("Serveradresse");
		GridBagConstraints gbc_lblServeradresse = new GridBagConstraints();
		gbc_lblServeradresse.anchor = GridBagConstraints.WEST;
		gbc_lblServeradresse.insets = new Insets(0, 0, 5, 5);
		gbc_lblServeradresse.gridx = 1;
		gbc_lblServeradresse.gridy = 1;
		contentPane.add(lblServeradresse, gbc_lblServeradresse);
		
		lblPort = new JLabel("Port");
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.anchor = GridBagConstraints.WEST;
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.gridx = 3;
		gbc_lblPort.gridy = 1;
		contentPane.add(lblPort, gbc_lblPort);
		
		txtAdresse = new JTextField("localhost");
		GridBagConstraints gbc_txtAdresse = new GridBagConstraints();
		gbc_txtAdresse.insets = new Insets(0, 0, 5, 5);
		gbc_txtAdresse.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAdresse.gridx = 1;
		gbc_txtAdresse.gridy = 2;
		contentPane.add(txtAdresse, gbc_txtAdresse);
		txtAdresse.setColumns(10);
		
		
//		NumberFormat format = NumberFormat.getInstance();
//        JFormattedTextField field = new JFormattedTextField(format);
//        ((NumberFormatter)field.getFormatter()).setAllowsInvalid(false);
		
//		formatport =NumberFormat.getNumberInstance();
		txtPort = new JTextField("48585",5);
//				JFormattedTextField(formatport);	
//		numberField.setColumns(5);
		
//		numberField.setMaximumSize(5);
		
		GridBagConstraints gbc_txtPort = new GridBagConstraints();
		gbc_txtPort.insets = new Insets(0, 0, 5, 5);
		gbc_txtPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPort.gridx = 3;
		gbc_txtPort.gridy = 2;
		contentPane.add(txtPort, gbc_txtPort);
		txtPort.setColumns(10);
		
		btnSpeichern = new JButton("Speichern");
		btnSpeichern.addActionListener(myAL);
		GridBagConstraints gbc_btnSpeichern = new GridBagConstraints();
		gbc_btnSpeichern.insets = new Insets(0, 0, 0, 5);
		gbc_btnSpeichern.gridx = 3;
		gbc_btnSpeichern.gridy = 4;
		contentPane.add(btnSpeichern, gbc_btnSpeichern);
	}

}
