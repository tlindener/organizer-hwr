package view;

import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JList;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

import logik.DataPusher;


public class window_TerminBearbeiten extends JFrame {
	private DataPusher myDataPusher;
	private ActionListener myCon;
	private JTextField txtBeschreibung;
	private JButton btnTerminEintragen;
	private JTextArea txtADetails;
	private JList lstRaum;
	private JTextField startUhrzeit;
	private JTextField endUhrzeit;
	private JList lstPersonen;
	
	public JList getLstPersonen() {
		return lstPersonen;
	}

	public void setLstPersonen(JList lstPersonen) {
		this.lstPersonen = lstPersonen;
	}

	public window_TerminBearbeiten(DataPusher myDataPusher, ActionListener con) {
		myCon = con;
		this.myDataPusher = myDataPusher;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 42, 0, 100, 34, 100, 0, 43, 43, 20, 24, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 32, 0, 0, 0, 0, 0, 0, 0, 31, 31,
				0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblVon = new JLabel("von");
		GridBagConstraints gbc_lblVon = new GridBagConstraints();
		gbc_lblVon.anchor = GridBagConstraints.EAST;
		gbc_lblVon.insets = new Insets(0, 0, 5, 5);
		gbc_lblVon.gridx = 1;
		gbc_lblVon.gridy = 1;
		getContentPane().add(lblVon, gbc_lblVon);
		
		startUhrzeit = new JTextField();
		startUhrzeit.setToolTipText("Bitte geben Sie eine Uhrzeit im Format xx:xx ein.");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		getContentPane().add(startUhrzeit, gbc_textField);
		startUhrzeit.setColumns(10);
		
		JLabel lblBis = new JLabel("bis");
		GridBagConstraints gbc_lblBis = new GridBagConstraints();
		gbc_lblBis.anchor = GridBagConstraints.EAST;
		gbc_lblBis.insets = new Insets(0, 0, 5, 5);
		gbc_lblBis.gridx = 3;
		gbc_lblBis.gridy = 1;
		getContentPane().add(lblBis, gbc_lblBis);
		
		endUhrzeit = new JTextField();
		endUhrzeit.setToolTipText("Bitte geben Sie eine Uhrzeit im Format xx:xx ein.");
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 4;
		gbc_textField_1.gridy = 1;
		getContentPane().add(endUhrzeit, gbc_textField_1);
		endUhrzeit.setColumns(10);

		JLabel lblRaum = new JLabel("Raum");
		GridBagConstraints gbc_lblRaum = new GridBagConstraints();
		gbc_lblRaum.gridwidth = 4;
		gbc_lblRaum.anchor = GridBagConstraints.WEST;
		gbc_lblRaum.insets = new Insets(0, 0, 5, 5);
		gbc_lblRaum.gridx = 1;
		gbc_lblRaum.gridy = 3;
		getContentPane().add(lblRaum, gbc_lblRaum);

		JLabel lblBeschreibung = new JLabel("Beschreibung");
		GridBagConstraints gbc_lblBeschreibung = new GridBagConstraints();
		gbc_lblBeschreibung.gridwidth = 3;
		gbc_lblBeschreibung.anchor = GridBagConstraints.WEST;
		gbc_lblBeschreibung.insets = new Insets(0, 0, 5, 5);
		gbc_lblBeschreibung.gridx = 6;
		gbc_lblBeschreibung.gridy = 3;
		getContentPane().add(lblBeschreibung, gbc_lblBeschreibung);

		lstRaum = new JList();
		GridBagConstraints gbc_lstRaum = new GridBagConstraints();
		gbc_lstRaum.gridwidth = 4;
		gbc_lstRaum.gridheight = 3;
		gbc_lstRaum.insets = new Insets(0, 0, 5, 5);
		gbc_lstRaum.fill = GridBagConstraints.BOTH;
		gbc_lstRaum.gridx = 1;
		gbc_lstRaum.gridy = 4;
		getContentPane().add(lstRaum, gbc_lstRaum);

		txtBeschreibung = new JTextField();
		GridBagConstraints gbc_txtBeschreibung = new GridBagConstraints();
		gbc_txtBeschreibung.gridwidth = 3;
		gbc_txtBeschreibung.anchor = GridBagConstraints.NORTH;
		gbc_txtBeschreibung.insets = new Insets(0, 0, 5, 5);
		gbc_txtBeschreibung.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBeschreibung.gridx = 6;
		gbc_txtBeschreibung.gridy = 4;
		getContentPane().add(txtBeschreibung, gbc_txtBeschreibung);
		txtBeschreibung.setColumns(10);

		JLabel lblDetails = new JLabel("Details");
		GridBagConstraints gbc_lblDetails = new GridBagConstraints();
		gbc_lblDetails.gridwidth = 3;
		gbc_lblDetails.anchor = GridBagConstraints.WEST;
		gbc_lblDetails.insets = new Insets(0, 0, 5, 5);
		gbc_lblDetails.gridx = 6;
		gbc_lblDetails.gridy = 5;
		getContentPane().add(lblDetails, gbc_lblDetails);

		JLabel lblPersonen = new JLabel("Personen");
		GridBagConstraints gbc_lblPersonen = new GridBagConstraints();
		gbc_lblPersonen.gridwidth = 4;
		gbc_lblPersonen.anchor = GridBagConstraints.WEST;
		gbc_lblPersonen.insets = new Insets(0, 0, 5, 5);
		gbc_lblPersonen.gridx = 1;
		gbc_lblPersonen.gridy = 7;
		getContentPane().add(lblPersonen, gbc_lblPersonen);

		lstPersonen = new JList();
		GridBagConstraints gbc_lstPersonen = new GridBagConstraints();
		gbc_lstPersonen.gridwidth = 4;
		gbc_lstPersonen.insets = new Insets(0, 0, 5, 5);
		gbc_lstPersonen.fill = GridBagConstraints.BOTH;
		gbc_lstPersonen.gridx = 1;
		gbc_lstPersonen.gridy = 8;
		getContentPane().add(lstPersonen, gbc_lstPersonen);

		txtADetails = new JTextArea();
		GridBagConstraints gbc_txtADetails = new GridBagConstraints();
		gbc_txtADetails.gridwidth = 3;
		gbc_txtADetails.gridheight = 3;
		gbc_txtADetails.insets = new Insets(0, 0, 5, 5);
		gbc_txtADetails.fill = GridBagConstraints.BOTH;
		gbc_txtADetails.gridx = 6;
		gbc_txtADetails.gridy = 6;
		getContentPane().add(txtADetails, gbc_txtADetails);

		btnTerminEintragen = new JButton("Termin Eintragen");
		GridBagConstraints gbc_btnTerminEintragen = new GridBagConstraints();
		gbc_btnTerminEintragen.anchor = GridBagConstraints.EAST;
		gbc_btnTerminEintragen.insets = new Insets(0, 0, 5, 5);
		gbc_btnTerminEintragen.gridx = 8;
		gbc_btnTerminEintragen.gridy = 10;
		getContentPane().add(btnTerminEintragen, gbc_btnTerminEintragen);
		init();
	}

	/**
	 * Create the frame.
	 * 
	 * @param textArea
	 * @param list
	 */

	public void init() {

		setBounds(100, 100, 804, 472);

		JPanel panel = new JPanel();

		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 21, 145, 1, 342, 0, 0 };
		gbl_panel.rowHeights = new int[] { 9, 186, 0, 0, 0, 0, 110, 59 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };

		panel.setLayout(gbl_panel);

	}
	
	

	public JTextField getTxtBeschreibung() {
		return txtBeschreibung;
	}

	public void setTxtBeschreibung(JTextField txtBeschreibung) {
		this.txtBeschreibung = txtBeschreibung;
	}

	public JButton getBtnTerminEintragen() {
		return btnTerminEintragen;
	}

	public void setBtnTerminEintragen(JButton btnTerminEintragen) {
		this.btnTerminEintragen = btnTerminEintragen;
	}

	public JTextArea getTxtADetails() {
		return txtADetails;
	}

	public void setTxtADetails(JTextArea txtADetails) {
		this.txtADetails = txtADetails;
	}

	public JList getLstRaum() {
		return lstRaum;
	}

	public void setLstRaum(JList lstRaum) {
		this.lstRaum = lstRaum;
	}

	public JTextField getStartUhrzeit() {
		return startUhrzeit;
	}

	public void setStartUhrzeit(JTextField startUhrzeit) {
		this.startUhrzeit = startUhrzeit;
	}

	public JTextField getEndUhrzeit() {
		return endUhrzeit;
	}

	public void setEndUhrzeit(JTextField endUhrzeit) {
		this.endUhrzeit = endUhrzeit;
	}
}
