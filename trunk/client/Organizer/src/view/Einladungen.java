package view;

import java.awt.Canvas;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * JFrame in which the invites for the persons are shown.
 * 
 * @author Jennifer Blumenthal
 * 
 */
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
	 * Default constructor that initializes the actionlistener and creates the
	 * frame.
	 * 
	 * @param Con
	 */
	public Einladungen(ActionListener Con) {
		myCon = Con;
		init();
	}

	/**
	 * Create the frame.
	 */
	public void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 143, 127, 0, 129, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 39, 0, 0, 0, 0, 0, 0, 0, 164,
				0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 1.0,
				1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblBeschreibung = new JLabel("Beschreibung");
		GridBagConstraints gbc_lblBeschreibung = Util.createGridBagContraints(
				2, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		contentPane.add(lblBeschreibung, gbc_lblBeschreibung);

		btnZusagen = new JButton("Zusagen");
		btnZusagen.addActionListener(myCon);
		GridBagConstraints gbc_btnZusagen = Util.createGridBagContraints(4, 1,
				GridBagConstraints.EAST, GridBagConstraints.NONE);
		contentPane.add(btnZusagen, gbc_btnZusagen);

		txtBeschreibung = new JTextField();
		txtBeschreibung.setEditable(false);
		GridBagConstraints gbc_txtBeschreibung = Util.createGridBagContraints(
				2, 1, 1, 2, GridBagConstraints.HORIZONTAL);
		contentPane.add(txtBeschreibung, gbc_txtBeschreibung);
		txtBeschreibung.setColumns(10);

		btnAbsagen = new JButton("Absagen");
		btnAbsagen.addActionListener(myCon);
		GridBagConstraints gbc_btnAbsagen = Util.createGridBagContraints(4, 2,
				GridBagConstraints.EAST, GridBagConstraints.NONE);
		contentPane.add(btnAbsagen, gbc_btnAbsagen);

		JLabel lblNewLabel = new JLabel("Eingeladen von");
		GridBagConstraints gbc_lblNewLabel = Util.createGridBagContraints(2, 1,
				1, 3, GridBagConstraints.WEST, GridBagConstraints.NONE);
		contentPane.add(lblNewLabel, gbc_lblNewLabel);

		txtEinladener = new JTextField();
		txtEinladener.setEditable(false);
		GridBagConstraints gbc_txtEinladener = Util.createGridBagContraints(2,
				1, 1, 4, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL);
		contentPane.add(txtEinladener, gbc_txtEinladener);
		txtEinladener.setColumns(10);

		JLabel lblVon = new JLabel("von");
		GridBagConstraints gbc_lblVon = Util.createGridBagContraints(1, 5,
				GridBagConstraints.WEST, GridBagConstraints.NONE);
		contentPane.add(lblVon, gbc_lblVon);

		JLabel lblBis = new JLabel("bis");
		GridBagConstraints gbc_lblBis = Util.createGridBagContraints(2, 5,
				GridBagConstraints.WEST, GridBagConstraints.NONE);
		contentPane.add(lblBis, gbc_lblBis);

		JLabel lblBelegt = new JLabel("belegt?");
		GridBagConstraints gbc_lblBelegt = Util.createGridBagContraints(4, 5,
				GridBagConstraints.EAST, GridBagConstraints.NONE);
		contentPane.add(lblBelegt, gbc_lblBelegt);

		txtVon = new JTextField();
		txtVon.setEditable(false);
		GridBagConstraints gbc_txtVon = Util.createGridBagContraints(1, 6,
				GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
		contentPane.add(txtVon, gbc_txtVon);
		txtVon.setColumns(10);

		txtBis = new JTextField();
		txtBis.setEditable(false);
		GridBagConstraints gbc_txtBis = Util.createGridBagContraints(2, 6,
				GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
		contentPane.add(txtBis, gbc_txtBis);
		txtBis.setColumns(10);

		canStatus = new Canvas();
		canStatus.setSize(20, 20);
		GridBagConstraints gbc_canStatus = Util.createGridBagContraints(4, 6,
				GridBagConstraints.EAST, GridBagConstraints.NONE);
		contentPane.add(canStatus, gbc_canStatus);

		JLabel lblDetails = new JLabel("Details");
		GridBagConstraints gbc_lblDetails = Util.createGridBagContraints(1, 7,
				GridBagConstraints.WEST, GridBagConstraints.NONE);
		contentPane.add(lblDetails, gbc_lblDetails);

		JLabel lblRaum = new JLabel("Raum");
		GridBagConstraints gbc_lblRaum = Util.createGridBagContraints(4, 7,
				GridBagConstraints.EAST, GridBagConstraints.NONE);
		contentPane.add(lblRaum, gbc_lblRaum);

		txtADetails = new JTextArea();
		txtADetails.setEditable(false);
		GridBagConstraints gbc_txtADetails = Util.createGridBagContraints(2, 1,
				1, 8, GridBagConstraints.BOTH);
		contentPane.add(txtADetails, gbc_txtADetails);

		txtRaum = new JTextField();
		txtRaum.setEditable(false);
		GridBagConstraints gbc_txtRaum = Util.createGridBagContraints(4, 8,
				GridBagConstraints.NONE, GridBagConstraints.HORIZONTAL);
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
