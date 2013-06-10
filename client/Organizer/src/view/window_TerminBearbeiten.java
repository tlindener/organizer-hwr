package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import logik.DataPusher;

import organizer.objects.types.Room;
import organizer.objects.types.User;

import view.renderer.MyCheckBoxListRenderer;
import view.renderer.MyListSelectionModel;
import controller.MyChangeListener;

public class window_TerminBearbeiten extends JFrame implements MyChangeListener {

	private static final long serialVersionUID = 1L;

	public final String BUTTON_NAME = "Button_Termin_Window";
	private final String DEFAULT_BTN_TEXT = "EDIT ENTRY";

	private DataPusher myDataPusher;
	private ActionListener myCon;

	private String btnText = "";

	private JTextField txtBeschreibung;
	private JButton btnTerminEintragen;
	private JTextArea txtADetails;
	private JList<Room> lstRaum;
	private JList<User> lstPersonen;
	private JTextField startUhrzeit;
	private JTextField endUhrzeit;

	private MyCheckBoxListRenderer combinedListener = new MyCheckBoxListRenderer(
			this);
	private List<User> selectedUsers = new ArrayList<User>();
	private List<Room> selectedRooms = new ArrayList<Room>();

	private JLabel lblRaeume;

	private JTextField txtRaum;

	public window_TerminBearbeiten(DataPusher myDataPusher, ActionListener con) {
		myCon = con;
		this.myDataPusher = myDataPusher;
		setButtonText(DEFAULT_BTN_TEXT);
	}

	public void setButtonText(String buttonText) {
		btnText = buttonText;
	}

	public void openEmptyFrame() {
		openFrameWithValues("", "", "", "", null, null, " ");
	}

	public void openFrameWithValues(String startTime, String endTime,
			String beschreibung, String details, Room[] rooms, User[] user,
			String raum) {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 42, 57, 36, 100, 34, 100, 0,
				43, 43, 20, 24, 0 };
		gridBagLayout.rowHeights = new int[] { 20, 0, 32, 0, 0, 0, 0, 0, 94, 41,
				89, 31, 31, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblVon = new JLabel("von");
		GridBagConstraints gbc_lblVon = createGridBagContraints(1, 1, 2, 1,
				GridBagConstraints.EAST, GridBagConstraints.NONE);
		getContentPane().add(lblVon, gbc_lblVon);

		startUhrzeit = new JTextField(startTime);
		startUhrzeit
				.setToolTipText("Bitte geben Sie eine Uhrzeit im Format xx:xx ein.");
		GridBagConstraints gbc_textField = createGridBagContraints(1, 1, 3, 1,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		getContentPane().add(startUhrzeit, gbc_textField);
		startUhrzeit.setColumns(10);

		JLabel lblBis = new JLabel("bis");
		GridBagConstraints gbc_lblBis = createGridBagContraints(1, 1, 4, 1,
				GridBagConstraints.EAST, GridBagConstraints.NONE);
		getContentPane().add(lblBis, gbc_lblBis);

		endUhrzeit = new JTextField(endTime);
		endUhrzeit
				.setToolTipText("Bitte geben Sie eine Uhrzeit im Format xx:xx ein.");
		GridBagConstraints gbc_textField_1 = createGridBagContraints(1, 1, 5,
				1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		getContentPane().add(endUhrzeit, gbc_textField_1);
		endUhrzeit.setColumns(10);

		JLabel lblRaum = new JLabel("Raum");
		GridBagConstraints gbc_lblRaum = createGridBagContraints(5, 1, 1, 3,
				GridBagConstraints.WEST, GridBagConstraints.NONE);
		getContentPane().add(lblRaum, gbc_lblRaum);

		JLabel lblBeschreibung = new JLabel("Beschreibung");
		GridBagConstraints gbc_lblBeschreibung = createGridBagContraints(3, 1,
				7, 3, GridBagConstraints.WEST, GridBagConstraints.NONE);
		getContentPane().add(lblBeschreibung, gbc_lblBeschreibung);

		txtRaum = new JTextField(raum);
		GridBagConstraints gbc_textField1 = createGridBagContraints(5, 1, 1, 4,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		getContentPane().add(txtRaum, gbc_textField1);
		txtRaum.setColumns(10);

		txtBeschreibung = new JTextField(beschreibung);
		GridBagConstraints gbc_txtBeschreibung = createGridBagContraints(3, 1,
				7, 4, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
		getContentPane().add(txtBeschreibung, gbc_txtBeschreibung);
		txtBeschreibung.setColumns(10);

		JLabel lblDetails = new JLabel("Details");
		GridBagConstraints gbc_lblDetails = createGridBagContraints(3, 1, 7, 5,
				GridBagConstraints.WEST, GridBagConstraints.NONE);
		getContentPane().add(lblDetails, gbc_lblDetails);

		lblRaeume = new JLabel("R\u00E4ume");
		GridBagConstraints gbc_lblRume = createGridBagContraints(5, 1, 1, 6,
				GridBagConstraints.WEST, GridBagConstraints.NONE);
		getContentPane().add(lblRaeume, gbc_lblRume);

		txtADetails = new JTextArea(details);
		GridBagConstraints gbc_txtADetails = createGridBagContraints(3, 4, 7,
				6, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		getContentPane().add(txtADetails, gbc_txtADetails);

		lstRaum = new JList<Room>(myDataPusher.pushRoomList());
		lstRaum.setCellRenderer(combinedListener);
		lstRaum.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstRaum.setSelectionModel(new MyListSelectionModel());
		setSelectedValues(lstRaum, rooms);
		GridBagConstraints gbc_lstRaum = createGridBagContraints(5, 2, 1, 7, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		getContentPane().add(new JScrollPane(lstRaum), gbc_lstRaum);

		JLabel lblPersonen = new JLabel("Personen");
		GridBagConstraints gbc_lblPersonen = createGridBagContraints(5, 1, 1, 9,GridBagConstraints.WEST , GridBagConstraints.NONE);
		getContentPane().add(lblPersonen, gbc_lblPersonen);

		lstPersonen = new JList<User>(myDataPusher.pushUserList());
		lstPersonen.setCellRenderer(combinedListener);
		lstPersonen.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstPersonen.setSelectionModel(new MyListSelectionModel());
		setSelectedValues(lstPersonen, user);
		GridBagConstraints gbc_lstPersonen = createGridBagContraints(5, 2, 1,
				10, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		getContentPane().add(new JScrollPane(lstPersonen), gbc_lstPersonen);

		btnTerminEintragen = new JButton(this.btnText);
		btnTerminEintragen.addActionListener(this.myCon);
		GridBagConstraints gbc_btnTerminEintragen = createGridBagContraints(1, 1, 9, 11, GridBagConstraints.EAST, GridBagConstraints.NONE);
		getContentPane().add(btnTerminEintragen, gbc_btnTerminEintragen);
		init();
		// GridBagLayout gridBagLayout = new GridBagLayout();
		// gridBagLayout.columnWidths = new int[] { 42, 0, 100, 34, 100, 0, 43,
		// 43, 20, 24, 0 };
		// gridBagLayout.rowHeights = new int[] { 0, 0, 32, 0, 0, 0, 0, 0, 0, 0,
		// 31, 31, 0 }; 
		// gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
		// 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		// gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
		// 0.0,
		// 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		// getContentPane().setLayout(gridBagLayout);
		//
		// JLabel lblVon = new JLabel("von");
		// GridBagConstraints gbc_lblVon = createGridBagContraints(1, 1, 1, 1,
		// GridBagConstraints.EAST, GridBagConstraints.NONE);
		// getContentPane().add(lblVon, gbc_lblVon);
		//
		// startUhrzeit = new JTextField(startTime);
		// startUhrzeit
		// .setToolTipText("Bitte geben Sie eine Uhrzeit im Format xx:xx ein.");
		// GridBagConstraints gbc_textField = createGridBagContraints(1, 1, 2,
		// 1,
		// GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		// getContentPane().add(startUhrzeit, gbc_textField);
		// startUhrzeit.setColumns(10);
		//
		// JLabel lblBis = new JLabel("bis");
		// GridBagConstraints gbc_lblBis = createGridBagContraints(1, 1, 3, 1,
		// GridBagConstraints.EAST, GridBagConstraints.NONE);
		// getContentPane().add(lblBis, gbc_lblBis);
		//
		// endUhrzeit = new JTextField(endTime);
		// endUhrzeit
		// .setToolTipText("Bitte geben Sie eine Uhrzeit im Format xx:xx ein.");
		// GridBagConstraints gbc_textField_1 = createGridBagContraints(1, 1, 4,
		// 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		// getContentPane().add(endUhrzeit, gbc_textField_1);
		// endUhrzeit.setColumns(10);
		//
		// JLabel lblRaum = new JLabel("Raum");
		// GridBagConstraints gbc_lblRaum = createGridBagContraints(4, 1, 1, 3,
		// GridBagConstraints.WEST, GridBagConstraints.NONE);
		// getContentPane().add(lblRaum, gbc_lblRaum);
		//
		// JLabel lblBeschreibung = new JLabel("Beschreibung");
		// GridBagConstraints gbc_lblBeschreibung = createGridBagContraints(3,
		// 1,
		// 6, 3, GridBagConstraints.WEST, GridBagConstraints.NONE);
		// getContentPane().add(lblBeschreibung, gbc_lblBeschreibung);
		//
		// lstRaum = new JList<Room>(myDataPusher.pushRoomList());
		// lstRaum.setCellRenderer(combinedListener);
		// lstRaum.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// lstRaum.setSelectionModel(new MyListSelectionModel());
		// setSelectedValues(lstRaum, rooms);
		// GridBagConstraints gbc_lstRaum = createGridBagContraints(4, 3, 1, 4,
		// GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		// getContentPane().add(new JScrollPane(lstRaum), gbc_lstRaum);
		// setSelectedValues(lstRaum, rooms);
		//
		// txtBeschreibung = new JTextField(beschreibung);
		// GridBagConstraints gbc_txtBeschreibung = createGridBagContraints(3,
		// 1,
		// 6, 4, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
		// getContentPane().add(txtBeschreibung, gbc_txtBeschreibung);
		// txtBeschreibung.setColumns(10);
		//
		// JLabel lblDetails = new JLabel("Details");
		// GridBagConstraints gbc_lblDetails = createGridBagContraints(3, 1, 6,
		// 5,
		// GridBagConstraints.WEST, GridBagConstraints.NONE);
		// getContentPane().add(lblDetails, gbc_lblDetails);
		//
		// JLabel lblPersonen = new JLabel("Personen");
		// GridBagConstraints gbc_lblPersonen = createGridBagContraints(4, 1, 1,
		// 7, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		// getContentPane().add(lblPersonen, gbc_lblPersonen);
		//
		// lstPersonen = new JList<User>(myDataPusher.pushUserList());
		// lstPersonen.setCellRenderer(combinedListener);
		// lstPersonen.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// lstPersonen.setSelectionModel(new MyListSelectionModel());
		// setSelectedValues(lstPersonen, user);
		// GridBagConstraints gbc_lstPersonen = createGridBagContraints(4, 1, 1,
		// 8, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		// getContentPane().add(new JScrollPane(lstPersonen), gbc_lstPersonen);
		//
		//
		// txtADetails = new JTextArea(details);
		// GridBagConstraints gbc_txtADetails = createGridBagContraints(3, 3, 6,
		// 6, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		// getContentPane().add(txtADetails, gbc_txtADetails);
		//
		// btnTerminEintragen = new JButton(btnText);
		// btnTerminEintragen.setName(BUTTON_NAME);
		// btnTerminEintragen.addActionListener(this.myCon);
		// GridBagConstraints gbc_btnTerminEintragen =
		// createGridBagContraints(1,
		// 1, 8, 10, GridBagConstraints.EAST, GridBagConstraints.NONE);
		// getContentPane().add(btnTerminEintragen, gbc_btnTerminEintragen);
		//
		// init();
	}

	private GridBagConstraints createGridBagContraints(int gWidth, int gHeight,
			int gridx, int gridy, int anchor, int fill) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = anchor;
		gbc.gridwidth = gWidth;
		gbc.gridheight = gHeight;
		gbc.fill = fill;
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.insets = new Insets(0, 0, 5, 5);
		return gbc;
	}

	private <T> void setSelectedValues(JList<T> awtList, T[] list) {

		if (list == null || list.length == 0)
			return;

		for (Object obj : list) {
			System.out.println("Schleife");
			awtList.setSelectedValue(obj, false);
			awtList.setValueIsAdjusting(false);
		}
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
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public String getBeschreibung() {
		return txtBeschreibung.getText();
	}

	public JButton getBtnTerminEintragen() {
		return btnTerminEintragen;
	}

	public String getTxtADetails() {
		return txtADetails.getText();
	}

	public Room[] getSelectedRooms() {
		return selectedRooms.toArray(new Room[selectedRooms.size()]);
	}

	public User[] getSelectedUsers() {
		return selectedUsers.toArray(new User[selectedUsers.size()]);
	}

	public String getStartUhrzeit() {
		return startUhrzeit.getText();
	}

	public String getEndUhrzeit() {
		return endUhrzeit.getText();
	}

	/**
	 * Fügt den User zur Liste hinzu wenn der Status true ist und er noch nicht
	 * in der Liste steht Ist der Status false, wird er aus der Liste entfernt,
	 * falls er vorhanden ist
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
	 * Fügt den Raum zur Liste hinzu wenn der Status true ist und er noch nicht
	 * in der Liste steht Ist der Status false, wird er aus der Liste entfernt,
	 * falls er vorhanden ist
	 */
	@Override
	public void stateChangedForRoom(boolean state, Room room) {
		if (state) {
			if (!selectedRooms.contains(room)) {
				selectedRooms.add(room);
			}
		} else {
			if (selectedRooms.contains(room)) {
				selectedRooms.remove(room);
			}
		}
	}
}
