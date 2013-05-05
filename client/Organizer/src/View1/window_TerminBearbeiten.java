package View1;

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

import Logik1.DataPusher;

public class window_TerminBearbeiten extends JFrame {
	private DataPusher myDataPusher;
	private ActionListener myCon;
	private JTextField txtBeschreibung;
	private JButton btnTerminEintragen;
	private JTextArea txtADetails;
	private JList lstRaum;
	
	public window_TerminBearbeiten(DataPusher myDataPusher, ActionListener con) {
		myCon = con;
		this.myDataPusher = myDataPusher;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 42, 175, 0, 509, 24, 0 };
		gridBagLayout.rowHeights = new int[] { 32, 0, 0, 0, 0, 0, 0, 0, 31, 31,
				0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblRaum = new JLabel("Raum");
		GridBagConstraints gbc_lblRaum = new GridBagConstraints();
		gbc_lblRaum.anchor = GridBagConstraints.WEST;
		gbc_lblRaum.insets = new Insets(0, 0, 5, 5);
		gbc_lblRaum.gridx = 1;
		gbc_lblRaum.gridy = 1;
		getContentPane().add(lblRaum, gbc_lblRaum);

		JLabel lblBeschreibung = new JLabel("Beschreibung");
		GridBagConstraints gbc_lblBeschreibung = new GridBagConstraints();
		gbc_lblBeschreibung.anchor = GridBagConstraints.WEST;
		gbc_lblBeschreibung.insets = new Insets(0, 0, 5, 5);
		gbc_lblBeschreibung.gridx = 3;
		gbc_lblBeschreibung.gridy = 1;
		getContentPane().add(lblBeschreibung, gbc_lblBeschreibung);

		lstRaum = new JList();
		GridBagConstraints gbc_lstRaum = new GridBagConstraints();
		gbc_lstRaum.gridheight = 3;
		gbc_lstRaum.insets = new Insets(0, 0, 5, 5);
		gbc_lstRaum.fill = GridBagConstraints.BOTH;
		gbc_lstRaum.gridx = 1;
		gbc_lstRaum.gridy = 2;
		getContentPane().add(lstRaum, gbc_lstRaum);

		txtBeschreibung = new JTextField();
		GridBagConstraints gbc_txtBeschreibung = new GridBagConstraints();
		gbc_txtBeschreibung.anchor = GridBagConstraints.NORTH;
		gbc_txtBeschreibung.insets = new Insets(0, 0, 5, 5);
		gbc_txtBeschreibung.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBeschreibung.gridx = 3;
		gbc_txtBeschreibung.gridy = 2;
		getContentPane().add(txtBeschreibung, gbc_txtBeschreibung);
		txtBeschreibung.setColumns(10);

		JLabel lblDetails = new JLabel("Details");
		GridBagConstraints gbc_lblDetails = new GridBagConstraints();
		gbc_lblDetails.anchor = GridBagConstraints.WEST;
		gbc_lblDetails.insets = new Insets(0, 0, 5, 5);
		gbc_lblDetails.gridx = 3;
		gbc_lblDetails.gridy = 3;
		getContentPane().add(lblDetails, gbc_lblDetails);

		JLabel lblPersonen = new JLabel("Personen");
		GridBagConstraints gbc_lblPersonen = new GridBagConstraints();
		gbc_lblPersonen.anchor = GridBagConstraints.WEST;
		gbc_lblPersonen.insets = new Insets(0, 0, 5, 5);
		gbc_lblPersonen.gridx = 1;
		gbc_lblPersonen.gridy = 5;
		getContentPane().add(lblPersonen, gbc_lblPersonen);

		JList lstPersonen = new JList();
		GridBagConstraints gbc_lstPersonen = new GridBagConstraints();
		gbc_lstPersonen.insets = new Insets(0, 0, 5, 5);
		gbc_lstPersonen.fill = GridBagConstraints.BOTH;
		gbc_lstPersonen.gridx = 1;
		gbc_lstPersonen.gridy = 6;
		getContentPane().add(lstPersonen, gbc_lstPersonen);

		txtADetails = new JTextArea();
		GridBagConstraints gbc_txtADetails = new GridBagConstraints();
		gbc_txtADetails.gridheight = 3;
		gbc_txtADetails.insets = new Insets(0, 0, 5, 5);
		gbc_txtADetails.fill = GridBagConstraints.BOTH;
		gbc_txtADetails.gridx = 3;
		gbc_txtADetails.gridy = 4;
		getContentPane().add(txtADetails, gbc_txtADetails);

		btnTerminEintragen = new JButton("Termin Eintragen");
		GridBagConstraints gbc_btnTerminEintragen = new GridBagConstraints();
		gbc_btnTerminEintragen.anchor = GridBagConstraints.EAST;
		gbc_btnTerminEintragen.insets = new Insets(0, 0, 5, 5);
		gbc_btnTerminEintragen.gridx = 3;
		gbc_btnTerminEintragen.gridy = 8;
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
}
