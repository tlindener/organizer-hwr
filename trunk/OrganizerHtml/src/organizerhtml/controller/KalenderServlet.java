package organizerhtml.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import organizerhtml.model.client.Model;
import organizerhtml.model.database.objects.Calendar;
import organizerhtml.model.database.objects.CalendarEntry;
import organizerhtml.model.database.objects.Room;
import organizerhtml.network.JsonJavaRequestHandler;
import organizerhtml.network.RequestHandler;

/**
 * Servlet implementation class KalenderServlet
 */
@WebServlet("/KalenderServlet")
public class KalenderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * HTML input
	 */
	private ArrayList<String[]> table = new ArrayList<>();
	private String details;
	private int user;
	private List<String> personen;
	private String date;
	private String termin;
	private String room;
	private HttpSession session;
	/*
	 * Controller
	 */
	private Model myModel;
	private RequestHandler myRequester;
	private Calendar steffensCal;
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	// private Object[][] beschreibungsDaten;
	// private Object[][] terminDauer;
	private Date aktDate;
	// private int start = 0;
	// private Calendar steffensCal;
	// private Controller controller;

	/*
	 * Prüfelemente
	 */
	private Boolean timeSet = false;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public KalenderServlet() {
		super();
		// controller = new Controller();
		myRequester = new JsonJavaRequestHandler("", -1);
		myModel = new Model(aktDate);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		setSession(request.getSession());
		getData(request);
		doLogic();
		setData();
		parseAktDate();
		/*
		 * Mit oder ohne Dispatcher
		 */
		boolean dispat = false;
		if (dispat) {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("Kalender.jsp");
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect("Kalender.jsp");
			return;
		}
	}

	private void doLogic() {
		// fillTable();
		fillTableNetwork();
		fillDetails();
	}

	private void fillDetails() {
		boolean local = false;
		if (local) {
			details = "Das Projekt läuft einwandfrei :)";
			room = "6B.367";
			personen = new ArrayList<>();
			personen.add("Steffen");
			personen.add("Jenny");
			personen.add("Tobias");
			personen.add("Svenja");
			personen.add("Marcel");
		} else {
			try {
				befuelleModel();
			} catch (ParseException e) {
				System.out.println("Jenny ist doof");
			}
			details = (String) myModel.returnDetail(termin);
			room = myModel.returnRaum(termin);
			personen = myModel.returnPersonen(termin);
			if (personen == null) {
				System.out.println("\tPersonen sind Null");
			} else {
				if (!(personen.size() > 0)) {
					//personen.add("Owner");
				}
				for (String s : personen) {
					System.out.println(s);
				}
			}
			// List<CalendarEntry> temp = calender.getCalendarEntries();
			//
			// details=calender.getDescription();
			// // room= calender.get

		}

		setDetails(details);
		setRoom(room);
		setPersonen(personen);
	}

	private void getData(HttpServletRequest request) {
		setDate(request);
		setTermin(request);
		setUser(request);
	}

	/**
	 * TESTPHASE: Methode füllt die Tabelle zu Testzwecken mit Daten zur anzeige
	 * in dem JSP.
	 */
	private void fillTable() {
		// hier kommen die Kalenderdaten hin
		Object[][] temp = getBeschreibungenStatisch();
		// Object[][] temp = getBeschreibungen();
		int rows = temp.length;
		int cols = temp[0].length;

		ArrayList<String[]> parts = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rows; i++) {
			/*
			 * XXX die +1 muss weg, da Testcase von Jenny nur auf 2 Spalten
			 * aufgezogen ist.
			 */
			String[] listParts = new String[cols + 1];
			for (int j = 0; j < cols; j++) {
				listParts[j] = (String) temp[i][j];
				sb.append(temp[i][j]);
			}
			parts.add(listParts);
		}
		/*
		 * TODO: Testzwecke
		 */
		// for (int i = 0; i < parts.size(); i++) {
		// for (String s : parts.get(i)) {
		// System.out.print(s + " ");
		// }
		// System.out.println("");
		// }
		// XXX sicher stellen, dass es keine NULL speichert
		setTable(parts);

		// System.out.println(sb.toString());
	}

	/**
	 * hinterlegt alle Daten des Servlets in der Session für den nächsten JSP
	 * aufruf.
	 */
	private void setData() {
		session.setAttribute("table", table);
		session.setAttribute("termin", termin);
		session.setAttribute("user", user);
		session.setAttribute("date", date);
		if (timeSet) {
			// XXX GET Calender with DATE parameter
			session.setAttribute("details", details);
			session.setAttribute("personen", personen);
			session.setAttribute("room", room);

		}
	}

	/**
	 * Diese Methode zieht sich von dem NetworkLayer ein Kalender anhand der
	 * übergebenen UserID und listet alle Termine anhand des übergebenen Datums
	 * auf. Die Liste wird der View übergeben um diese anzeigbar zu machen.
	 */
	private void fillTableNetwork() {
		// XXX Hier die Netzwerkadaption durchführen
		ArrayList<String[]> parts = new ArrayList<>();
		steffensCal = new Calendar();
		steffensCal.setID(user);
		// XXX ist die Methode richtig gewählt ?
		Calendar temp = myRequester.requestObjectByOwnId(steffensCal);
		if (temp != null) {
			steffensCal = temp;
		}

		for (CalendarEntry c : steffensCal.getCalendarEntries()) {
			String[] tempEntries = new String[3];
			String tempDate = df.format(c.getStartDate());
			String beschreibung = "";
			String tempTermin;
			String tempEnd;
			if (date.equals(tempDate)) {
				tempTermin = parseEntryToString(c.getStartHour(),
						c.getStartMinute());
				tempEnd = parseEntryToString(c.getEndHour(), c.getEndMinute());

				beschreibung = c.getDescription();
				tempEntries[2] = beschreibung;
				tempEntries[0] = tempTermin;
				tempEntries[1] = tempEnd;
				if (beschreibung != null || beschreibung.trim() != "") {
					parts.add(tempEntries);
				}
			}

		}
		setTable(parts);
	}

	/**
	 * konvertiert zwei Variablen in einen String aus dem übergebenen
	 * CalenderObjekt
	 * 
	 * @param hour
	 * @param minute
	 * @return hour+minute Format: hh:mm
	 */
	private String parseEntryToString(int hour, int minute) {
		String tempTermin;
		if (hour < 10) {
			tempTermin = "0" + hour;
		} else {
			tempTermin = "" + hour;
		}
		if (minute < 10) {
			tempTermin += ":0" + minute;
		} else {
			tempTermin += ":" + minute;
		}
		return tempTermin;
	}

	/**
	 * 
	 * entnimmt der Session den aktuellen Termin und hinterlegt diesen im
	 * Servlet
	 * 
	 * @param request
	 */
	private void setTermin(HttpServletRequest request) {
		setTermin(request.getParameter("termin"));
		System.out.println("Terminzeit: " + termin);
		if (termin != null) {
			timeSet = true;
		}
	}

	/**
	 * 
	 * entnimmt der Session das aktuelle Datum und hinterlegt diesen im Servlet
	 * 
	 * @param request
	 */
	private void setDate(HttpServletRequest request) {
		String temp = request.getParameter("date");
		if (temp != null) {
			if (temp.trim() != "") {
				System.out.println("Termindatum: " + temp);
				setDate(temp);
			}
		}
	}

	/**
	 * entnimmt der Session den aktuellen User und hinterlegt diesen im Servlet
	 * 
	 * @param request
	 */
	private void setUser(HttpServletRequest request) {
		System.out.println("USERTEST");
		int temp = (int) session.getAttribute("user");
		if (temp > 0) {
			try {
				setUser(temp);
				System.out.println("der aktuelle User lautet: " + user);
			} finally {
			}
		} else {
			System.out.println("Als user wurde NULL übergeben");
		}
	}

	/**
	 * hinterlegt die zu dem aktuellen Termin zugehörigen Termine in dem Servlet
	 * 
	 * @param table
	 */
	public void setTable(ArrayList<String[]> table) {
		this.table = table;
	}

	/**
	 * hinterlegt die zu dem aktuellen Termin zugehörigen Details in dem Servlet
	 * 
	 * @param details
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * hinterlegt die zu dem aktuellen Termin zugehörigen Personen in dem
	 * Servlet
	 * 
	 * @param personen
	 */
	public void setPersonen(List<String> personen) {
		this.personen = personen;
	}

	/**
	 * hinterlegt das aktuelle Datum in dem Servlet
	 * 
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * hinterlegt den aktuellen Termin in dem Servlet
	 * 
	 * @param termin
	 */
	public void setTermin(String termin) {
		this.termin = termin;
	}

	/**
	 * hinterlegt Raum zu dem Termin in dem Servlet
	 * 
	 * @param room
	 */
	public void setRoom(String room) {
		this.room = room;
	}

	/**
	 * hinterlegt den aktuellen UserCalender in dem Servlet
	 * 
	 * @param calender
	 */
	public void setCalender(Calendar calender) {
		this.steffensCal = calender;
	}

	/**
	 * hinterlegt die aktuelle Session in dem Servlet
	 * 
	 * @param session
	 */
	public void setSession(HttpSession session) {
		this.session = session;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public Object[][] getBeschreibungenStatisch() {
		Object[][] beschreibungsDaten = new Object[][] { { "0.00", "0.00" },
				{ "0.30", "0.30" }, { "1.00", "1.00" }, { "1.30", "1.30" },
				{ "2.00", "2.00" }, { "2.30", "2.30" }, { "3.00", "3.00" },
				{ "3.30", "3.30" }, { "4.00", "4.00" }, { "4.30", "4.30" },
				{ "5.00", "5.00" }, { "5.30", "5.30" }, { "6.00", "6.00" },
				{ "6.30", "6.30" }, { "7.00", "7.00" }, { "7.30", "7.30" },
				{ "8.00", "8.00" }, { "8.30", "8.30" }, { "9.00", "9.00" },
				{ "9.30", "9.30" }, { "10.00", "10.00" }, { "10.30", "10.30" },
				{ "11.00", "11.00" }, { "11.30", "11.30" },
				{ "12.00", "12.00" }, { "12.30", "12.30" },
				{ "13.00", "13.00" }, { "13.30", "13.30" },
				{ "14.00", "14.00" }, { "14.30", "14.30" },
				{ "15.00", "15.00" }, { "15.30", "15.30" },
				{ "16.00", "16.00" }, { "16.30", "16.30" },
				{ "17.00", "17.00" }, { "17.30", "17.30" },
				{ "18.00", "18.00" }, { "18.30", "18.30" },
				{ "19.00", "19.00" }, { "19.30", "19.30" },
				{ "20.00", "20.00" }, { "20.30", "20.30" },
				{ "21.00", "21.00" }, { "21.30", "21.30" },
				{ "22.00", "22.00" }, { "22.30", "22.30" },
				{ "23.00", "23.00" }, { "23.30", "23.30" }, };
		return beschreibungsDaten;
	}

	public String parseDate(Date date) {
		String datum = "";
		datum = date.toString().substring(0, 11)
				+ date.toString().substring(20);
		return datum;
	}

	public void befuelleModel() throws ParseException {
		myModel.getBeschreibungen().clear();
		myModel.getDauer().clear();
		myModel.getPersonen().clear();
		myModel.getDetails().clear();
		myModel.getRaeume().clear();

		List myCes = steffensCal.getCalendarEntries();
		for (int i = 0; i < myCes.size(); i++) {
			CalendarEntry myCe = (CalendarEntry) myCes.get(i);
			String tempDate = df.format(myCe.getStartDate());
			if (date.equals(tempDate)) {
				{
					String zeit = "";
					int minuten = 0;
					int stunden = 0;
					stunden = myCe.getStartHour();
					minuten = myCe.getStartMinute();

					zeit = parseEntryToString(stunden, minuten);
					myModel.setAktDate(parseAktDate());
					myModel.setBeschreibungen(zeit, myCe.getTitle());
					myModel.setDauer(zeit, myCe.getDuration());
					myModel.setPersonen(zeit, myCe.getInvitees());
					myModel.setDetails(zeit, myCe.getDescription());
					Room r = new Room();
					r.setID(myCe.getRoomId());
					Room tmp = myRequester.requestObjectByOwnId(r);
					if (tmp != null) {
						myModel.setRaeume(zeit, tmp.getDescription());
					} else {
						myModel.setRaeume(zeit, "");
					}
				}
			}

		}
	}

	private Date parseAktDate() {
		String[] split = date.split("/");
		int[] temp = new int[3];
		for (int i = 0; i < split.length; i++) {
			// System.out.println(split[i]);
			temp[i] = Integer.parseInt(split[i]);
		}
		return new Date(temp[2], temp[0], temp[1]);
	}
}
