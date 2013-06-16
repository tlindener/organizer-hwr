package run;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.sound.midi.SysexMessage;

import network.RequestHandler;
import network.json.JsonJavaIISRequestHandler;
import network.json.JsonJavaRequestHandler;
import network.test.model.TestData;

import organizer.objects.AbstractOrganizerObject;
import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Group;
import organizer.objects.types.Invite;
import organizer.objects.types.Room;
import organizer.objects.types.User;

/**
 * Erste Testklasse
 * 
 * @author Steffen Baumann
 * @version 1.0 
 *
 */
public class Start {
	
	RequestHandler requester;
	
	public Start()	{
		
		requester = new JsonJavaIISRequestHandler("192.168.0.6",
				80);
		
//		createDefaultUsersWithCalendar();
//		
		if(!manualLogin()){
			System.err.println("ERROR");
			return;
		}
		
		CalendarEntry entry = makeDefaultEntryForLoggedInUser();
		if(entry==null){
			System.err.println("ERROR");
			return;
		}
		
		if(!addObject(entry)){
			System.err.println("ERROR");
			return;
		}
		
		Room room = makeDefaultRoom();
		if(!addObject(room)){
			System.err.println("ERROR");
			return;
		}
		
		if(!updateRoomForCalendarEntry(room, entry)){
			System.err.println("UNABLE TO UPDATE ROOM");
			return;
		}
		
		if(!requestAllEntriesOfUserByCalendar(loggedInUser)){
			System.err.println("ERROR");
			return;
		}
		
		if(!requestAllEntriesOfUserByUserID(loggedInUser)){
			System.err.println("ERROR");
			return;
		}
		
		if(!requestAllObjectsOfType(new User())){
			System.err.println("ERROR");
			return;
		}
		
		if(!manualInvites()){
			System.err.println("ERROR");
			return;
		}
		
		if(!manualLogin()){
			System.err.println("ERROR");
			return;
		}
		
		if(!manualInviteChange()){
			System.err.println("ERROR");
			return;
		}
		
		if(!manualLogin()){
			System.err.println("ERROR");
			return;
		}
		
		if(!manualInviteChange()){
			System.err.println("ERROR");
			return;
		}
		
		if(!manualLogin()){
			System.err.println("ERROR");
			return;
		}
		
		if(!manualRemoveEntry()){
			System.err.println("ERROR");
			return;
		}
		
		
//		
//		System.out.println("AddUser");
//		User u = requester.registerNewUser(user, "123456");
//		if(u==null){
//			System.out.println("Error User = null");
//			u = user;
//		}
//		System.out.println("UserID: " +u.getID());
//		User u2 = requester.registerNewUser(user2, "123456");
//		if(u2==null){
//			System.out.println("Error User = null");
//			u2 = user2;
//		}
//		System.out.println("UserID: " +u2.getID());
//		User u3 = requester.registerNewUser(user3, "123456");
//		if(u3==null){
//			System.out.println("Error User = null");
//			u3 = user3;
//		}
//		System.out.println("UserID: " +u3.getID());
//		
//		System.out.println("Log in " +u.getMailAddress());
//		User loggedInUser = requester.login(u.getMailAddress(), "123456");
//		
//		Room room = new Room();
//		room.setDescription("TestRaum1");
//		room.setLocation("HierUndDa");
//		room.setSeats(1478);
//		
//		System.out.println("AddRoom");
//		Room r = requester.addObject(room);
//		System.out.println("RoomID: " +r.getID());
//		
//		Calendar ca = new Calendar();
//		ca.setOwnerId(loggedInUser.getID());
//		ca.setDescription("Infos%20von%20undfuer" + loggedInUser.getGivenName());
//		ca.setName(loggedInUser.getGivenName()+"sCalendar");
//
//		System.out.println("AddCalendar");
//		Calendar c = requester.addObject(ca);
//		System.out.println("CalendarID: " +c.getID());
//		
//		System.out.println("Log in " +u2.getMailAddress());
//		loggedInUser = requester.login(u2.getMailAddress(), "123456");
//		
//		Calendar ca2 = new Calendar();
//		ca2.setOwnerId(loggedInUser.getID());
//		ca2.setDescription("Infos%20von%20undfuer" + loggedInUser.getGivenName());
//		ca2.setName(loggedInUser.getGivenName()+"sCalendar");
//		
//		Calendar c2 = requester.addObject(ca2);
//		System.out.println("CalendarID: " +c2.getID());
//		
//		System.out.println("Log in " +u3.getMailAddress());
//		loggedInUser = requester.login(u3.getMailAddress(), "123456");
//		
//		Calendar ca3 = new Calendar();
//		ca3.setOwnerId(loggedInUser.getID());
//		ca3.setDescription("Infos%20von%20undfuer" + loggedInUser.getGivenName());
//		ca3.setName(loggedInUser.getGivenName()+"sCalendar");
//
//		Calendar c3 = requester.addObject(ca3);
//		System.out.println("CalendarID: " +c3.getID());
//		
//		System.out.println("Log in " +u.getMailAddress());
//		loggedInUser = requester.login(u.getMailAddress(), "123456");	
//		
//		System.out.println("Request calendar of " + loggedInUser.getGivenName());
//		List<Integer> caIDs = loggedInUser.getCalendarIds();
//		int caID = -1;
//		if(caIDs.isEmpty()){
//			System.err.println("Error: No CalendarID was found in User-Object of " +loggedInUser.getGivenName());
//			return;
//		}else{
//			caID = caIDs.get(0);
//			System.out.println("Picked Calendar ID " + caID);
//		}
//		
//		System.out.println("Request all rooms");
//		List<Room> rooms = requester.requestAllObjects(new Room());
//		if(rooms.isEmpty()){
//			System.err.println("Error: No Room was found");
//			return;
//		}else{
//			room = rooms.get(0);
//			System.out.println("Rooms were found: Select room with ID " + room.getID());
//		}
//		
//		CalendarEntry ce = new CalendarEntry();
//		ce.setStartDate(new Date());
//		ce.setDescription(loggedInUser.getGivenName() +"sTermin");
//		ce.setTitle("Testterminvon%20" + loggedInUser.getGivenName());
//		ce.setRoomId(room.getID());
//		ce.setOwnerId(loggedInUser.getID());
//		ce.setCalendarId(caID);
//		ce.setEndDate(new Date());
//		
//		System.out.println("AddEntry");
//		CalendarEntry ce1 = requester.addObject(ce);
//		System.out.println("EntryId: " +ce1.getID());
//		
//		System.out.println("AddInvite for User 2");
//		Invite i = new Invite();
//		i.setCalendarEntryId(ce1.getID());
//		i.setOwnerId(2);
//		
//		i = requester.addObject(i);
//		System.out.println("InviteId: " +i.getID());
//		
//		System.out.println("AddInvite for User 3");
//		Invite i2 = new Invite();
//		i2.setCalendarEntryId(ce1.getID());
//		i2.setOwnerId(3);
//		
//		i2 = requester.addObject(i2);
//		System.out.println("InviteId: " +i2.getID());
//		
//		System.out.println("Log in " +u2.getMailAddress());
//		loggedInUser = requester.login(u2.getMailAddress(), "123456");
//		
//		System.out.println("Accept Invites");
//		for(int invite: loggedInUser.getInviteIds()){
//			Invite tmp = new Invite();
//			tmp.setID(invite);
//			Invite in = requester.requestObjectByOwnId(tmp);
//			if(in.isAccepted() != 1){
//				int result = requester.acceptInvite(in);
//				System.out.println("Invite "+invite + "--> neue EntryID: " + result);
//			}else{
//				System.out.println("Already Confirmed / Declined");
//			}
//		}
//		
//		System.out.println("Remove Entries with RoomID=1 from current User");
//		CalendarEntry room1Entry = new CalendarEntry();
//		room1Entry.setRequestProperty(CalendarEntry.ROOM_ID, ""+1);
//		List<CalendarEntry> entries = requester.requestAllObjectsByProperty(room1Entry);
//		boolean result = false;
//		for(CalendarEntry e: entries){
//			System.out.println("EntryID "+e.getID());
//			
//			System.out.println("Update Entry");
//			e.setTitle("Tobias seiner jetzt");
//			boolean updateCa = requester.updateObject(e);
//			System.out.println("updateresult: " + updateCa);
//			
//			
//			if(e.getOwnerId() == loggedInUser.getID()){
//				System.out.println("\tTry to remove Entry ID "+e.getID());
//				result = requester.removeObjectByOwnId(e);
//				if(!result){
//					break;
//				}
//			}
//		}
//		System.out.println("Result remove CalendarEntry: " +result);
//		
//		System.out.println("Request Calendar of " + loggedInUser.getMailAddress());
//		Calendar ca4 = new Calendar();
//		
//		if(loggedInUser.getCalendarIds().isEmpty()){
//			System.out.println("ERROR: no calendar for " +loggedInUser.getMailAddress());
//		}else{
//			ca4.setID(loggedInUser.getCalendarIds().get(0));
//			ca4 = requester.requestObjectByOwnId(ca4);
//			if(ca4 == null){
//				System.out.println("ERROR: Calendar mit ID " +loggedInUser.getCalendarIds().get(0) + " does not exist");
//				return;
//			}
//			System.out.println("Picked Calendar with ID " + ca4.getID() +" and name " +ca4.getName());
//		}
//		System.out.println("Request Entries of Calendar " +ca4.getName());
//		if(ca4.getCalendarEntries().isEmpty()){
//			System.out.println("No Elements in List");
//		}
//		else{
//			for(CalendarEntry entry : ca4.getCalendarEntries()){
//				System.out.println("EntryID: " + entry.getID());
//				System.out.println("\tTitle: "+entry.getTitle());
//				System.out.println("\tStart: " + entry.getStartHour()+":"+entry.getStartMinute());
//				System.out.println("\tEnd: " + entry.getEndHour()+":"+entry.getEndMinute());
//				System.out.println("Liste Teilnehmer: " + entry.getInvitees());
//			}
//		}
//		
//		System.out.println("Log in " +u3.getMailAddress());
//		loggedInUser = requester.login(u3.getMailAddress(), "123456");
//		
////		List<Integer> inviteIDs = loggedInUser.getInviteIds();
////		if(!inviteIDs.isEmpty()){
////			for(Integer inte : inviteIDs){
////				Invite tmp = new Invite();
////				tmp.setID(inte);
////				System.out.println("Invite declined: " + requester.declineInvite(tmp));
////			}
////		}
//		
//		if(!rooms.isEmpty()){
//			System.out.println("Remove Room " + rooms.get(0).getID());
//			boolean resultRemoveRoom = requester.removeObjectByOwnId(rooms.get(0));
//			System.out.println("Result: " + resultRemoveRoom);
//		}
//		
//		List<Integer> calendarIDs = loggedInUser.getCalendarIds();
//		if(!calendarIDs.isEmpty()){
//			for(Integer calendarIDCurrentUser : caIDs){
//				Calendar calendarToRemove = new Calendar();
//				calendarToRemove.setID(calendarIDCurrentUser);
//				System.out.println("Remove Calendar with ID "+calendarIDCurrentUser+" of " + loggedInUser.getMailAddress());
//				System.out.println("Result: " + requester.removeObjectByOwnId(calendarToRemove));
//			}
//		}
//		
//		System.out.println("Remove user " + loggedInUser.getMailAddress());
//		boolean resultRemoveUser = requester.removeObjectByOwnId(loggedInUser);
//		System.out.println("Result: " + resultRemoveUser);
//		
//		System.out.println("Log in " +u.getMailAddress());
//		loggedInUser = requester.login(u.getMailAddress(), "123456");
//		Calendar uCal = new Calendar();
//		uCal.setID(loggedInUser.getCalendarIds().get(0));
//		uCal = requester.requestObjectByOwnId(uCal);
//		
//		for(CalendarEntry userEntry: uCal.getCalendarEntries()){
//			System.out.println(userEntry);
//		}
//		
		
		
	}

	private boolean manualRemoveEntry(){
		boolean result = requestAllEntriesOfUserByCalendar(loggedInUser);
		if(!result){
			return false;
		}
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter EntryId to remove: ");
		int id = scan.nextInt();
		CalendarEntry entry = new CalendarEntry();
		entry.setID(id);
		result = requester.removeObjectByOwnId(entry);		
		return result;
	}
	
	private boolean manualInvites() {

		System.out.println("Enter the UserIDs you want to add: ");
		
		ArrayList<Integer> ints = new ArrayList<Integer>();
		int readIn = -1;
		Scanner scan = new Scanner(System.in);
		do{
			System.out.print("\t");
			ints.add(scan.nextInt());
		}while(readIn!=-1);
		System.out.println("Enter the ID of the Entry you want to invite to");
		int entryId = scan.nextInt();
		return sendInvitesTo(ints.toArray(new Integer[0]), entryId);
	}

	private boolean manualLogin() {
		System.out.print("MailAddress for the Login: ");
		Scanner scan = new Scanner(System.in);
		String mail = scan.nextLine();
		return login(mail, "123456");
	}

	private boolean manualInviteChange() {
		System.out.println("Invites of " +loggedInUser.getMailAddress());
		if(loggedInUser.getInviteIds().isEmpty()){
			System.out.println("\tEmtpy");
			return true;
		}
		for(int id: loggedInUser.getInviteIds()){
			Invite in = new Invite();
			in.setID(id);
			Invite tmp = requester.requestObjectByOwnId(in);
			if(tmp==null){
				System.err.println("ERROR Request of invite "+id+" was not successful");
			}else{
				System.out.println("\t"+id + " current state: "  + tmp.isAccepted());
			}
		}
		System.out.print("Set Invite Status");
		int goOn = -1;
		Scanner scan = new Scanner(System.in);
		do{
			System.out.print("Enter InviteID: ");
			int id = scan.nextInt();
			System.out.print("Enter accept(1) or decline(0): ");
			int state = scan.nextInt();
			Invite in = new Invite();
			in.setID(id);
			if(state==1){
				System.out.println("\t Invite "+ id+ " is accepted.");
				sendInviteStateFor(in, true);
			}else{
				System.out.println("\t Invite "+ id+ " is declined.");
				sendInviteStateFor(in, false);
			}
			System.out.print("To exit use -1, to go on use another int: ");
			goOn=scan.nextInt();
		}while(goOn!=-1);
		
		return true;
	}
	
	private boolean sendInviteStateFor(Invite invite, boolean state){
		if(state){
			requester.acceptInvite(invite);
		}else{
			requester.declineInvite(invite);
		}
		return state;
	}
	
	private void createDefaultUsersWithCalendar() {
		User user1 = new User();
		user1.setMailAddress("steffen.baumann@localhost.de");
		user1.setGivenname("Steffen");
		user1.setSurname("Baumann");
		user1.setPhoneNumber("666666666");
		System.out.println(addUserWithCalendar(user1));
		
		User user2 = new User();
		user2.setMailAddress("tobias.lindener@localhost.de");
		user2.setGivenname("Tobias");
		user2.setSurname("Lindener");
		user2.setPhoneNumber("666666666");
		System.out.println(addUserWithCalendar(user2));
		
		User user3 = new User();
		user3.setMailAddress("svenja.moehring@localhost.de");
		user3.setGivenname("Svenja");
		user3.setSurname("Möhring");
		user3.setPhoneNumber("666666666");
		System.out.println(addUserWithCalendar(user3));
	}

	private User loggedInUser = null;
	
	public boolean login(String mail, String pw){
		System.out.println("Log in " + mail);
		loggedInUser = requester.login(mail, pw);
		if(loggedInUser == null){
			return false;
		}
		return true;
	}
	
	public boolean addUserWithCalendar(User user){
		System.out.println("Add User \"" + user.getGivenName() + " " + user.getSurname() +"\" with " + user.getMailAddress());
		User tmp = requester.registerNewUser(user, "123456");
		if(tmp==null){
			return false;
		}
		login(tmp.getMailAddress(), "123456");
		System.out.println("\tAdd Calendar for " + user.getMailAddress());
		Calendar cal = new Calendar();
		cal.setOwnerId(tmp.getID());
		cal.setDescription("Testcalendar created by TestRun V2");
		cal.setName(user.getGivenName()+"'s Calendar");
		Calendar tmp2 = requester.addObject(cal);
		boolean result = tmp!=null && tmp2!=null;
		return result;
	}
	
	public boolean addObject(AbstractOrganizerObject object){
		System.out.println("Add Object " + object.getClass().getSimpleName());
		AbstractOrganizerObject obj = requester.addObject(object);
		return obj!=null?true:false;
	}
	
	public boolean updateRoomForCalendarEntry(Room room, CalendarEntry ce){
		System.out.println("Update CalendarEntry " + ce.getID() + "'s roomId=" +room.getID());
		ce.setRoomId(room.getID());
		return requester.updateObject(ce);
	}
	
	public CalendarEntry makeDefaultEntryForLoggedInUser(){
		if(loggedInUser == null || loggedInUser.getCalendarIds().isEmpty()){
			return null;
		}
		CalendarEntry ce = new CalendarEntry();
		ce.setStartDate(new Date());
		ce.setDescription(loggedInUser.getGivenName() +"s Termin");
		ce.setTitle("Testtermin von " + loggedInUser.getGivenName());
		ce.setOwnerId(loggedInUser.getID());
		ce.setCalendarId(loggedInUser.getCalendarIds().get(0));
		ce.setEndDate(new Date());
		return ce;
	}
	
	public Room makeDefaultRoom(){
		Room room = new Room();
		room.setDescription("Testroom default");
		room.setLocation("DE34AH3");
		room.setSeats(1234);
		return room;
	}
	
	public boolean sendInvitesTo(Integer[] integers, int calendarEntryId){
		for(int id: integers){
			Invite in = new Invite();
			in.setCalendarEntryId(calendarEntryId);
			in.setOwnerId(id);
			if(!addObject(in)){
				return false;
			}
		}
		return true;
	}
	
	public boolean requestAllEntriesOfUserByCalendar(User user){
		System.out.println("Get all CalendarEntries of user with mail " + user.getMailAddress());
		if(user.getCalendarIds().isEmpty()){
			System.err.println("ERROR: List of Calendar Ids is emtpy");
		}else{
			System.out.println("\tRequest Calendar");
			Calendar request = new Calendar();
			request.setID(user.getCalendarIds().get(0));
			Calendar cal = requester.requestObjectByOwnId(request);
			if(cal==null){
				return false;
			}
			System.out.println("\tPrint Entries:");
			for(CalendarEntry entry: cal.getCalendarEntries()){
				System.out.println("\t\t" + entry.getID() + " |" + entry.getDescription() + "|" +entry.getStartDate().toString());
			}
			return true;
		}
		return false;
	}
	
	public boolean requestAllEntriesOfUserByUserID(User user){
		System.out.println("Get all CalendarEntries of user with mail " + user.getMailAddress());
		System.out.println("\tRequest Entries by property OwnerID=" +user.getID());
		CalendarEntry request = new CalendarEntry();
		request.setRequestProperty(CalendarEntry.OWNER_ID, ""+user.getID());
		List<CalendarEntry> entryList = requester.requestAllObjectsByProperty(request);
		if(entryList.isEmpty()){
			return false;
		}
		for(CalendarEntry entry: entryList){
			System.out.println("\t\t" + entry.getID() + " |" + entry.getDescription() + "|" +entry.getStartDate().toString());
		}
		return true;
	}

	public boolean requestAllObjectsOfType(AbstractOrganizerObject obj){
		System.out.println("Request all objects of tpye " + obj.getClass().getSimpleName());
		List<AbstractOrganizerObject> list = requester.requestAllObjects(obj);
		if(list.isEmpty()){
			return false;
		}else{
			for(AbstractOrganizerObject element: list){
				System.out.println("\t"+element);
			}
			return true;
		}
	}
	
	public boolean removeCalendarEntry(int id){
		CalendarEntry request = new CalendarEntry();
		request.setID(id);
		return requester.removeObjectByOwnId(request);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Start();
//		new Start("");
	}

}
