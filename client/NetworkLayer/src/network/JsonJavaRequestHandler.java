/**
 * 
 */
package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import network.objects.NetDateTimeAdapter;
import network.objects.TestData;
import network.objects.Utils;

import organizer.objects.AbstractOrganizerObject;
import organizer.objects.types.Calendar;
import organizer.objects.types.CalendarEntry;
import organizer.objects.types.Room;
import organizer.objects.types.User;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * Verbindungsschnittstelle um Daten abzufragen. Eingabeobjekte werden verändert
 * und zurückgeliefert. Die Verwendung der generischen Vaterklasse ermöglicht,
 * dass clientseitig keine Cast-Befehle nötig sind. Die Schnittstelle benötigt
 * Informationen zu den JSON Objects und der Java Objects um ein Mapping
 * vornehmen zu können
 * 
 * @author Steffen Baumann
 * @version 1.0
 * 
 */
public class JsonJavaRequestHandler extends RequestHandler {

	private Gson gson = null;
	TestData data = null;
	
	private HttpURLConnection connection = null;
	private String hostname = "";
	private int port = -1;
	
	/**
	 * Verbindungsdetails für Socket hinterlegen
	 */
	public JsonJavaRequestHandler(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		data = new TestData();
		init();
	}

	private void init() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new NetDateTimeAdapter());
		builder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
		gson = builder.create();
	}

	/**
	 * Abfrage eines Objekte, das die Vorgaben des Eingabeobjekts erfüllen.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractOrganizerObject> T requestObjectByOwnId(T obj) {
		
//		Object result = null;
//		
//		if(obj instanceof CalendarEntry){
//			result = data.getCalendarEntryById(obj.getID());
//		}if(obj instanceof Calendar){
//			result = data.getCalendarById(obj.getID());
//		}if(obj instanceof Room){
//			result = data.getRoomById(obj.getID());
//		}if(obj instanceof User){
//			result = data.getUserById(obj.getID());
//		}
//		if(result!=null){
//			return (T)result;
//		}else{
//			return null;
//		}
				
		String getCmd = Utils.buildGetByOwnIdCommand(obj);
		String json = sendGetToServer(getCmd);
		try{
			return (T) gson.fromJson(json, obj.getClass());
		}catch(JsonSyntaxException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	private String sendGetToServer(String request) {
		

//		String jsonString = "{\"CalendarId\":1,\"Description\":null,\"Duration\":180,\"EndDate\":\"\\/Date(1366979015630+0200)\\/\",\"Id\":0,\"OwnerId\":1,\"RoomId\":0,\"StartDate\":\"\\/Date(1366968215630+0200)\\/\",\"Title\":null}";
//		String jsonString2 = "[{\"CalendarId\":1,\"Description\":null,\"Duration\":180,\"EndDate\":\"\\/Date(1366979015630+0200)\\/\",\"Id\":0,\"OwnerId\":1,\"RoomId\":0,\"StartDate\":\"\\/Date(1366968215630+0200)\\/\",\"Title\":null},{\"CalendarId\":2,\"Description\":null,\"Duration\":1440,\"EndDate\":\"\\/Date(1367054619440+0200)\\/\",\"Id\":0,\"OwnerId\":1,\"RoomId\":0,\"StartDate\":\"\\/Date(1366968219440+0200)\\/\",\"Title\":null}]";
//		String jsonString3 = "[{\"CalendarEntries\":[{\"CalendarId\":1,\"Description\":null,\"Duration\":180,\"EndDate\":\"\\/Date(1367602369353+0200)\\/\",\"Id\":1,\"Invitees\":[],\"OwnerId\":1,\"RoomId\":0,\"StartDate\":\"\\/Date(1367591569353+0200)\\/\",\"Title\":\"Arbeit\"},{\"CalendarId\":2,\"Description\":null,\"Duration\":1440,\"EndDate\":\"\\/Date(1367677970180+0200)\\/\",\"Id\":2,\"Invitees\":[{\"CalendarIds\":[],\"GivenName\":\"Hans\",\"GroupIds\":[],\"Id\":2,\"InviteIds\":[],\"MailAddress\":\"tobias.lindener@gmail.com\",\"PhoneNumber\":\"01773071234\",\"Surname\":\"Dieter\"}],\"OwnerId\":1,\"RoomId\":0,\"StartDate\":\"\\/Date(1367591570180+0200)\\/\",\"Title\":null}],\"Description\":null,\"Id\":1,\"Name\":\"MyCalendar\",\"OwnerId\":1}]";
//		return jsonString3;
//		String obj = gson.toJson(new Date());
//		System.out.println("TEST: " + obj);
		
		try {
			connection =  (HttpURLConnection) (new URL("http://"+hostname+":"+port+"/OrganizerService.svc/"+request)).openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String jsonString = reader.readLine();
			connection.disconnect();
			return jsonString;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
//		System.out.println(request);
//		return "";
	}
//	
//	private String sendPostToServer(String request, String jsonParameter){
//		try {
//				connection =  (HttpURLConnection) (new URL("http://"+hostname+":"+port+"/OrganizerService.svc/"+request)).openConnection();
//				connection.setDoInput (true);
//				connection.setDoOutput (true);
//				connection.setUseCaches (false);
//				connection.connect();
//				
//				PrintWriter printout = new PrintWriter(new DataOutputStream(connection.getOutputStream ()));
//				printout.write(URLEncoder.encode(jsonParameter.toString(),"UTF-8"));
//				printout.flush ();
//				printout.close ();
//				
//				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//				String jsonString = reader.readLine();
//				connection.disconnect();
//				return jsonString;
//			} catch (MalformedURLException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return null;		
//	}
	
	
	/**
	 * Abfrage mehrerer Objekte, die die Vorgaben des Eingabeobjekts erfüllen.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractOrganizerObject> List<T> requestAllObjects(T obj) {
		
//		Object result = null;
//		
//		if(obj instanceof CalendarEntry){
//			result = data.getAllCalendarEntries();
//		}if(obj instanceof Calendar){
//			result = data.getAllCalendar();
//		}if(obj instanceof Room){
//			result = data.getAllRooms();
//		}if(obj instanceof User){
//			result = data.getAllUser();
//		}
//		if(result!=null){
//			return (List<T>)result;
//		}else{
//			return null;
//		}
		
		
		try{
			String getCmd = Utils.buildGetAllCommand(obj);
			String json = sendGetToServer(getCmd);
			List<JsonElement> tmp = gson.fromJson(json, new TypeToken<List<JsonElement>>(){}.getType());
			
			if(tmp == null) return new ArrayList<T>();
			
			List<T> result = new ArrayList<>();
			for(int i = 0; i < tmp.size(); i++){
				result.add((T) gson.fromJson(tmp.get(i), obj.getClass()));
			}		
			return  result;
		}catch(IllegalArgumentException ex){
			ex.printStackTrace();
		}catch(JsonSyntaxException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public <T extends AbstractOrganizerObject> T requestObjectByProperty(T obj) {
		
		List<T> result = requestAllObjectsByProperty(obj);
		if(result != null && !result.isEmpty()){
			return result.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractOrganizerObject> List<T> requestAllObjectsByProperty(
			T obj) {
		try{
			String getCmd = Utils.buildGetByPropertyCommand(obj);
			String json = sendGetToServer(getCmd);
			
			List<JsonElement> tmp = gson.fromJson(json, new TypeToken<List<JsonElement>>(){}.getType());
			if(tmp == null) return new ArrayList<T>();
			List<T> result = new ArrayList<>();
			for(int i = 0; i < tmp.size(); i++){
				result.add((T) gson.fromJson(tmp.get(i), obj.getClass()));
			}		
			return  result;
		}catch (IllegalArgumentException ex){
			ex.printStackTrace();
		}catch(JsonSyntaxException ex){
			ex.printStackTrace();
		}
		
		return null;
	}
	@Override
	public User registerNewUser(User user, String name, String password) {
		try{
			String getCmd = Utils.buildAddCommand(user);
			getCmd += "&username=\""+name+"\"&password=\""+Utils.hashPassword(password)+"\"";
			String json = sendGetToServer(getCmd);
			Integer id = gson.fromJson(json, int.class);
			if(id == null || id == -1) return null;
			user.setID(id);
			return user;
		}catch(IllegalArgumentException ex){
			ex.printStackTrace();
		}catch(JsonSyntaxException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public <T extends AbstractOrganizerObject> T addObject(T obj) {
		if(obj instanceof User) throw new UnsupportedOperationException("User must be added by method \"registerNewUser\"");
		try{
			String getCmd = Utils.buildAddCommand(obj);
			String json = sendGetToServer(getCmd);
			Integer id = gson.fromJson(json, int.class);
			if(id == null || id == -1) return null;
			obj.setID(id);
			return obj;
		}catch(IllegalArgumentException ex){
			ex.printStackTrace();
		}catch(JsonSyntaxException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public <T extends AbstractOrganizerObject> boolean removeObject(T obj) {
		String getCmd = Utils.buildRemoveCommand(obj);
		String json = sendGetToServer(getCmd);
		try{
			return (boolean) gson.fromJson(json, boolean.class);
		}catch(JsonSyntaxException ex){
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public User login(String mail, String password) {
		String cmd = Utils.buildLoginCommand(mail, password);
		String json = sendGetToServer(cmd);
		try{
			return (User) gson.fromJson(json, User.class);
		}catch(JsonSyntaxException ex){
			ex.printStackTrace();
		}
		return null;
	}
}
