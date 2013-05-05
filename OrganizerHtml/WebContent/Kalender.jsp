<%@page import="org.eclipse.jdt.internal.compiler.ast.CastExpression"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.TreeSet"%>
<%@page import="java.util.SortedSet"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.HashMap"%>
<%//@page import="Model.Model"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Organizer</title>
<link rel="stylesheet" href="style.css" type="text/css">
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.3.js"></script>
<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker();
	});
</script>
<%!//getTerminArray
	private boolean test = false;

	private String createHTML(ArrayList<String[]> liste) {
		StringBuilder sb = new StringBuilder();
		//füllt die Tabelle mit dem Key & Value
		for (String[] s : liste) {
			sb.append("<tr>");
			sb.append("<td class=tleft><form method=post action=KalenderServlet>  <input type=submit value="
					+ s[0] + " name=termin /></form></td>");
			sb.append("<td class=tleft>" + s[1] + "</td>");
			sb.append("<td class=tright>" + s[2] + "</td>");
			sb.append("</tr>");
		}
		return sb.toString();
	}%>
</head>


<body>

	<%
		session.setAttribute("user", 1);
		// XXX noch keine Nutzerverwaltung. Deshalb Standardwert 1
	%>

	<!-- Der gesamthellgraue Hintergrund -->
	<div id="innerbody">
		<h1>Organizer</h1>
		<h2>Termine planen und mehr</h2>
		<hr size="3" noshade>

		<!-- Das Menü -->
		<div id="Menue">
			<h4>
				<a href="Main.html"> Home </a>
			</h4>
			<h4>
				<a href="Kalender.jsp"> Kalender </a>
			</h4>
		</div>

		<!-- Hier beginnt die linke Hälfte der Seite mit Terminauswahl, Raum- und Personendetails -->
		<div id="mainpartleft">
			<h3>Auswahl des Datums</h3>


			<!--  Auswahl des Tages - muss in Java ausgeführt werden-->
			<!-- <form method="post" action="Kalender.jsp"> -->
			<form method="post" action="KalenderServlet">
				Date: <input type="text" id="datepicker" name="date" /> <input
					type="submit" />
			</form>



			<h3>Raum</h3>
			<div id="raumkasten">
				<!-- Hier muss der zugehörige Raum zum Termin eingefügt werden -->
				<%
					String room = "";
					Object temp = session.getAttribute("room");
					if (temp != null) {
						room = (String) temp;
					}

					//TODO: Ausgabefeld vergrößern
					out.println(room);
				%>


			</div>
			<h3>Personen</h3>
			<div id="personenkasten">
				<!-- Hier müssen die zugehörigen Personen zum Termin eingefügt werden -->
				<%
					// ArrayInitiieren
					ArrayList<String> personen = new ArrayList<String>();
					// ArrayList aus Session füllen
					temp = session.getAttribute("personen");
					if (temp != null) {
						personen = (ArrayList<String>) temp;
					}
					// wenn NULL Testcase füllen
					//TODO: Testcase rausnehmen für den Durchstich
					if (test) {
						personen= new ArrayList<String>();
						personen.add("Array");
						personen.add("war");
						personen.add("leider");
						personen.add("leer");
						personen.add(":(");
					}
					// Personen in das Kästchen schreiben
					for (String s : personen) {
						out.println(s + "<br>");
					}
				%>



			</div>
		</div>

		<!-- Die rechte Seite mit Tagesübersicht und Termindetails -->
		<div id="mainpartright">
			<%
				String tagesanzeige;
				String date = (String) session.getAttribute("date");
				String termin = (String) session.getAttribute("termin");
				if (date != null && date.trim() != "") {
					tagesanzeige = "Tagesanzeige des " + date;
				} else {
					tagesanzeige = "Tagesanzeige";
				}
			%>
			<h3><%=tagesanzeige%></h3>

			<table border="1">
				<thead>
					<tr>
						<th class="tlefthead">Startzeit</th>
						<th class="tlefthead">Entzeit</th>
						<th class="trighthead">Beschreibung</th>
					</tr>
				</thead>
			</table>
			<div id="tabellenkasten">
				<table border="1">


					<tbody>
						<%
							if (date != null && date.trim() != "") {
								//out.print(createHTML());
								//XXX ArrayList<String[]> übergeben für die Liste zu füllen
								ArrayList<String[]> entries = (ArrayList) session
										.getAttribute("table");
								if (entries == null) {
									entries = new ArrayList();
								}

								out.print(createHTML(entries));
							}
						%>


					</tbody>
				</table>
			</div>
			<h3>Details</h3>
			<div id="detailkasten">
				<!-- Hier müssen die Details zum Termin eingefügt werden -->
				<%
					String details = "";
					temp = session.getAttribute("details");
					if (temp != null) {
						details = (String) temp;
					}
				%>
				<%=details%>
			</div>
		</div>
	</div>
</body>
</html>