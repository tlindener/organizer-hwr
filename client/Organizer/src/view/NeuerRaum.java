package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * This frame supports creating a new room. Therefore the fields name, location
 * and seats are required.
 * 
 * @author Jennifer Blumenthal
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
	 * Default constructor which initializes the submitted ActionListener and
	 * calls the init() method to create a new frame.
	 */
	public NeuerRaum(ActionListener aL) {
		myCon = aL;
		init();
	}

	/**
	 * Create the frame.
	 */
	public void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 514, 292);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0,
				0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblBeschreibung = new JLabel("Beschreibung");
		GridBagConstraints gbc_lblBeschreibung = Util.createGridBagContraints(
				1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		contentPane.add(lblBeschreibung, gbc_lblBeschreibung);

		JLabel lblNewLabel = new JLabel("Sitze");
		GridBagConstraints gbc_lblNewLabel = Util.createGridBagContraints(3, 1,
				GridBagConstraints.WEST, GridBagConstraints.NONE);
		contentPane.add(lblNewLabel, gbc_lblNewLabel);

		txtBeschreibung = new JTextField();
		GridBagConstraints gbc_txtBeschreibung = Util.createGridBagContraints(
				1, 2, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		contentPane.add(txtBeschreibung, gbc_txtBeschreibung);
		txtBeschreibung.setColumns(10);

		numberformat = NumberFormat.getNumberInstance();
		txtSitze = new JFormattedTextField(numberformat);
		txtSitze.setText("0");
		GridBagConstraints gbc_textField = Util.createGridBagContraints(3, 2,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		contentPane.add(txtSitze, gbc_textField);

		JLabel lblLage = new JLabel("Lage");
		GridBagConstraints gbc_lblLage = Util.createGridBagContraints(1, 3,
				GridBagConstraints.WEST, GridBagConstraints.NONE);
		contentPane.add(lblLage, gbc_lblLage);

		txtALage = new JTextArea();
		GridBagConstraints gbc_txtALage = Util.createGridBagContraints(3, 1, 1,
				4, GridBagConstraints.BOTH);
		contentPane.add(txtALage, gbc_txtALage);

		btnSpeichern = new JButton("Speichern");
		btnSpeichern.addActionListener(myCon);
		GridBagConstraints gbc_btnSpeichern = Util.createGridBagContraints(3,
				5, GridBagConstraints.EAST, GridBagConstraints.NONE);
		contentPane.add(btnSpeichern, gbc_btnSpeichern);

		btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.addActionListener(myCon);
		GridBagConstraints gbc_btnAbbrechen = Util.createGridBagContraints(3,
				6, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE);
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

	/**
	 * Proofs if all fields are filled in correct.
	 */
	public void pruefeFelder() {
		String lage = getTxtALage().getText();
		String beschreibung = getTxtBeschreibung().getText();
		String sitze = getTxtSitze().getText();

		if (lage.isEmpty() || beschreibung.isEmpty() || sitze.isEmpty()) {
			Color c = new Color(255, 86, 63);
			Border redline = BorderFactory.createLineBorder(c);

			JOptionPane.showMessageDialog(this,
					"Bitte füllen Sie alle Felder aus!");

			if (lage.isEmpty())
				getTxtALage().setBorder(redline);
			if (beschreibung.isEmpty())
				getTxtBeschreibung().setBorder(redline);
			if (sitze.isEmpty())
				getTxtSitze().setBorder(redline);
		}

	}

	/**
	 * Sets empty Strings to the textfields.
	 */
	public void loescheInhalte() {
		txtALage.setText("");
		txtBeschreibung.setText("");
		txtSitze.setText("");

	}
}
