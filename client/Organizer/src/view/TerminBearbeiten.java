package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

import logik.DataPusher;

import organizer.objects.types.Room;
import organizer.objects.types.User;

import view.renderer.MyCheckBoxListRenderer;
import view.renderer.MyListSelectionModel;
import view.renderer.TimeField;
import controller.MyChangeListener;

public class TerminBearbeiten extends JFrame implements MyChangeListener {


	public final String BUTTON_NAME = "Button_Termin_Window";
	private final String DEFAULT_BTN_TEXT = "EDIT ENTRY";

	private DataPusher myDataPusher;
	private ActionListener myCon;
	private MouseListener myMl;

	private String btnText = "";

	private JTextField txtBeschreibung;
	private JButton btnTerminEintragen;
	private JTextArea txtADetails;
	private JList<Room> lstRaum;
	private JList<User> lstPersonen;
	private TimeField startUhrzeit;
	private TimeField endUhrzeit;

	private MyCheckBoxListRenderer combinedListener = new MyCheckBoxListRenderer(
			this);
	private List<User> selectedUsers = new ArrayList<User>();
	private Room selectedRoom = new Room();

	private JLabel lblRaeume;

	private JTextField txtRaum;

	private JButton btnAbbrechen;

	/**
	 * Default constructor that initializes the Listener and the interface as well as
	 * the ButtonText.
	 * 
	 * @param myDataPusher
	 * @param con
	 * @param mo
	 */
	public TerminBearbeiten(DataPusher myDataPusher, ActionListener con,
			MouseListener mo) {
		myCon = con;
		myMl = mo;
		this.myDataPusher = myDataPusher;
		setButtonText(DEFAULT_BTN_TEXT);
	}


	/**
	 * Opens an empty frame.
	 */
	public void openEmptyFrame() {
		
		openFrameWithValues("", "", "", "", null, null, " ");
	}

	/**
	 * Opens the frame with the submitted values.
	 * 
	 * @param startTime
	 * @param endTime
	 * @param beschreibung
	 * @param details
	 * @param rooms
	 * @param user
	 * @param raum
	 */
	public void openFrameWithValues(String startTime, String endTime,
			String beschreibung, String details, Room[] rooms, User[] user,
			String raum) {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 42, 57, 36, 100, 34, 100, 0,
				43, 43, 20, 24, 0 };
		gridBagLayout.rowHeights = new int[] { 20, 0, 32, 0, 0, 0, 0, 0, 94,
				41, 89, 31, 31, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblVon = new JLabel("von");
		GridBagConstraints gbc_lblVon = Util.createGridBagContraints(1, 1, 2, 1,
				GridBagConstraints.EAST, GridBagConstraints.NONE);
		getContentPane().add(lblVon, gbc_lblVon);

		startUhrzeit = erstelleTimefield(startTime);
		startUhrzeit
				.setToolTipText("Bitte geben Sie eine Uhrzeit im Format xx:xx ein.");
		GridBagConstraints gbc_textField = Util.createGridBagContraints(1, 1, 3, 1,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		getContentPane().add(startUhrzeit, gbc_textField);
		startUhrzeit.setColumns(10);

		JLabel lblBis = new JLabel("bis");
		GridBagConstraints gbc_lblBis = Util.createGridBagContraints(1, 1, 4, 1,
				GridBagConstraints.EAST, GridBagConstraints.NONE);
		getContentPane().add(lblBis, gbc_lblBis);

		endUhrzeit = erstelleTimefield(endTime);
		endUhrzeit
				.setToolTipText("Bitte geben Sie eine Uhrzeit im Format xx:xx ein.");
		GridBagConstraints gbc_textField_1 = Util.createGridBagContraints(1, 1, 5,
				1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		getContentPane().add(endUhrzeit, gbc_textField_1);
		endUhrzeit.setColumns(10);

		JLabel lblRaum = new JLabel("Raum");
		GridBagConstraints gbc_lblRaum = Util.createGridBagContraints(5, 1, 1, 3,
				GridBagConstraints.WEST, GridBagConstraints.NONE);
		getContentPane().add(lblRaum, gbc_lblRaum);

		JLabel lblBeschreibung = new JLabel("Beschreibung");
		GridBagConstraints gbc_lblBeschreibung = Util.createGridBagContraints(3, 1,
				7, 3, GridBagConstraints.WEST, GridBagConstraints.NONE);
		getContentPane().add(lblBeschreibung, gbc_lblBeschreibung);

		txtRaum = new JTextField(raum);
		txtRaum.setEditable(false);
		GridBagConstraints gbc_textField1 = Util.createGridBagContraints(5, 1, 1, 4,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		getContentPane().add(txtRaum, gbc_textField1);
		txtRaum.setColumns(10);

		txtBeschreibung = new JTextField(beschreibung);
		GridBagConstraints gbc_txtBeschreibung = Util.createGridBagContraints(3, 1,
				7, 4, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
		getContentPane().add(txtBeschreibung, gbc_txtBeschreibung);
		txtBeschreibung.setColumns(10);

		JLabel lblDetails = new JLabel("Details");
		GridBagConstraints gbc_lblDetails = Util.createGridBagContraints(3, 1, 7, 5,
				GridBagConstraints.WEST, GridBagConstraints.NONE);
		getContentPane().add(lblDetails, gbc_lblDetails);

		lblRaeume = new JLabel("R\u00E4ume");
		GridBagConstraints gbc_lblRume = Util.createGridBagContraints(5, 1, 1, 6,
				GridBagConstraints.WEST, GridBagConstraints.NONE);
		getContentPane().add(lblRaeume, gbc_lblRume);

		txtADetails = new JTextArea(details);
		GridBagConstraints gbc_txtADetails = Util.createGridBagContraints(3, 3, 7,
				6, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		getContentPane().add(txtADetails, gbc_txtADetails);

		lstRaum = new JList<Room>(myDataPusher.pushRoomList());
		lstRaum.setCellRenderer(combinedListener);
		lstRaum.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setSelectedValues(lstRaum, rooms);
		GridBagConstraints gbc_lstRaum = Util.createGridBagContraints(5, 2, 1, 7,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		getContentPane().add(new JScrollPane(lstRaum), gbc_lstRaum);

		JLabel lblPersonen = new JLabel("Personen");
		GridBagConstraints gbc_lblPersonen = Util.createGridBagContraints(5, 1, 1,
				9, GridBagConstraints.WEST, GridBagConstraints.NONE);
		getContentPane().add(lblPersonen, gbc_lblPersonen);

		lstPersonen = new JList<User>(myDataPusher.pushUserList());
		lstPersonen.setCellRenderer(combinedListener);
		lstPersonen
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstPersonen.setSelectionModel(new MyListSelectionModel());
		setSelectedValues(lstPersonen, user);
		GridBagConstraints gbc_lstPersonen = Util.createGridBagContraints(5, 2, 1,
				10, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		getContentPane().add(new JScrollPane(lstPersonen), gbc_lstPersonen);

		btnTerminEintragen = new JButton(btnText);
		btnTerminEintragen.addActionListener(myCon);
		btnTerminEintragen.addMouseListener(myMl);
		GridBagConstraints gbc_btnTerminEintragen = Util.createGridBagContraints(1,
				1, 9, 10, GridBagConstraints.EAST, GridBagConstraints.NONE);
		getContentPane().add(btnTerminEintragen, gbc_btnTerminEintragen);

		btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.addActionListener(myCon);
		GridBagConstraints gbc_btnAbbrechen = Util.createGridBagContraints(1, 1, 9,
				11, GridBagConstraints.EAST, GridBagConstraints.NONE);
		getContentPane().add(btnAbbrechen, gbc_btnAbbrechen);

		init();

	}

	
	/**
	 * Sets the submitted objects in the list as selected
	 * in the submitted awtList.
	 * 
	 * @param awtList
	 * @param list
	 */
	public <T> void setSelectedValues(JList<T> awtList, T[] list) {

		if (list == null || list.length == 0)
			return;

		for (Object obj : list) {

			awtList.setSelectedValue(obj, false);
			awtList.setValueIsAdjusting(false);
		}
	}

	/**
	 * Create the frame.
	 * 
	 */
	public void init() {
		setBounds(100, 100, 804, 472);
		JPanel panel = new JPanel();
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 21, 145, 1, 20, 0, 0 };
		gbl_panel.rowHeights = new int[] { 9, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);;
		this.setVisible(true);
	}

	
	/**
	 * Adds the User to the selectedUser list or removes him according to 
	 * the checkbox status.
	 *
	 */
	@Override
	public void stateChangedForUser(boolean state, User user) {
		if (state) {
			if (!selectedUsers.contains(user)) {
				selectedUsers.add(user);
			}
		} else {
			if (selectedUsers.contains(user)) {
				selectedUsers.remove(user);
			}
		}
	}

	/**
	 * Sets the room that has been chosen as selected. 
	 * If the room is chosen again nothing will happen.
	 * 
	 */
	@Override
	public void stateChangedForRoom(boolean state, Room room) {
		if (state) {
			if (selectedRoom != room) {
				selectedRoom = room;
			}
		} else {
			if (selectedRoom == room) {

			}
		}
		getTxtRaum().setText(
				selectedRoom.getLocation() + "; "
						+ selectedRoom.getDescription());
	}
	
/**
 * Proofs if all fields are filled and if the data ist inserted in the correct format.
 * Furthermore it proofs the logical correctness of the times.
 *  
 * @return fehler
 */
	public boolean pruefeFelder() {
		boolean fehler = false;

		Color c = new Color(255, 86, 63);
		Border redline = BorderFactory.createLineBorder(c);
		String endzeit = getEndUhrzeit().getText();
		String startzeit = getStartUhrzeit().getText();
		String beschreibungen = getBeschreibung().getText();
		if (endzeit.isEmpty() || startzeit.isEmpty()
				|| beschreibungen.isEmpty()) {
			if (endzeit.isEmpty())
				getEndUhrzeit().setBorder(redline);
			if (startzeit.isEmpty())
				getStartUhrzeit().setBorder(redline);
			if (beschreibungen.isEmpty())
				getBeschreibung().setBorder(redline);

			JOptionPane.showMessageDialog(this,
					"Bitte füllen Sie die Felder vollständig aus!");
			fehler = true;
		} else {

			long start = getStartUhrzeit().getTime();
			long ende = getEndUhrzeit().getTime();
			if (start > ende) {
				JOptionPane
						.showMessageDialog(this,
								"Die Endzeit muss zeitlich nach der Startzeit eines Termins liegen!");
				getEndUhrzeit().setBorder(redline);
				getEndUhrzeit().setText("");
				fehler = true;
			}

		}

		return fehler;

	}

	/**
	 * Creates a timefield that only accepts times in the format xx:xx.
	 * 
	 * @param time
	 * @return tf
	 */
	public TimeField erstelleTimefield(String time) {
		TimeField tf = new TimeField();
		if (time != null && !time.isEmpty()) {
			SimpleDateFormat format = new SimpleDateFormat("H:mm");
			Date date = new Date();
			try {
				date = format.parse(time);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tf = new TimeField(date);
		} else
			tf = new TimeField();

		return tf;
	}

	public void setSelectedRoom(Room selectedRoom) {
		setSelectedValues(this.lstRaum, new Room[]{selectedRoom});
		this.selectedRoom = selectedRoom;
		}

	public JList<Room> getLstRaum() {
		return lstRaum;
	}

	public void setLstRaum(JList<Room> lstRaum) {
		this.lstRaum = lstRaum;
	}

	public JList<User> getLstPersonen() {
		return lstPersonen;
	}

	public void setLstPersonen(JList<User> lstPersonen) {
		this.lstPersonen = lstPersonen;
	}

	public JButton getBtnAbbrechen() {
		return btnAbbrechen;
	}

	public void setBtnAbbrechen(JButton btnAbbrechen) {
		this.btnAbbrechen = btnAbbrechen;
	}

	public JTextField getBeschreibung() {
		return txtBeschreibung;
	}

	public JButton getBtnTerminEintragen() {
		return btnTerminEintragen;
	}

	public JTextArea getTxtADetails() {
		return txtADetails;
	}

	public Room getSelectedRoom() {
		return selectedRoom;
	}

	public List<User> getSelectedUsers() {
		return selectedUsers;
	}

	public TimeField getStartUhrzeit() {
		return startUhrzeit;
	}

	public TimeField getEndUhrzeit() {
		return endUhrzeit;
	}

	public JTextField getTxtRaum() {
		return txtRaum;
	}

	public void setTxtRaum(JTextField txtRaum) {
		this.txtRaum = txtRaum;
	}

	public void setButtonText(String buttonText) {
		btnText = buttonText;
	}


}
