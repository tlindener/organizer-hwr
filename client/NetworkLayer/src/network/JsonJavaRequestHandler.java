/**
 * 
 */
package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import network.objects.ByProperty;
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
	
	/**
	 * Verbindungsdetails für Socket hinterlegen
	 */
	public JsonJavaRequestHandler() {
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
		String json = sendRequestToServer(getCmd);
		try{
			return (T) gson.fromJson(json, obj.getClass());
		}catch(JsonSyntaxException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	private String sendRequestToServer(String request) {
		
//		System.out.println(request);
//		String jsonString = "{\"CalendarId\":1,\"Description\":null,\"Duration\":180,\"EndDate\":\"\\/Date(1366979015630+0200)\\/\",\"Id\":0,\"OwnerId\":1,\"RoomId\":0,\"StartDate\":\"\\/Date(1366968215630+0200)\\/\",\"Title\":null}";
//		String jsonString2 = "[{\"CalendarId\":1,\"Description\":null,\"Duration\":180,\"EndDate\":\"\\/Date(1366979015630+0200)\\/\",\"Id\":0,\"OwnerId\":1,\"RoomId\":0,\"StartDate\":\"\\/Date(1366968215630+0200)\\/\",\"Title\":null},{\"CalendarId\":2,\"Description\":null,\"Duration\":1440,\"EndDate\":\"\\/Date(1367054619440+0200)\\/\",\"Id\":0,\"OwnerId\":1,\"RoomId\":0,\"StartDate\":\"\\/Date(1366968219440+0200)\\/\",\"Title\":null}]";
//		return jsonString;
		
		 try {
			connection =  (HttpURLConnection) (new URL("http://test:1234/OrganizerService.svc/"+request)).openConnection();
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
	}
	
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
			String json = sendRequestToServer(getCmd);
			
			List<JsonElement> tmp = gson.fromJson(json, new TypeToken<List<JsonElement>>(){}.getType());
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
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractOrganizerObject> List<T> requestObjects(T obj,
			ByProperty by) {
		
//		if(!Utils.isFieldOf(obj, by.getFieldName())) return null;
//		
//		Object result = null;
//		
//		if(obj instanceof CalendarEntry){
//			result = data.getAllCalendarEntries();
//		}if(obj instanceof Calendar){
//			result = data.getAllCalendar();
//		}if(obj instanceof Room){
//			result = data.getAllRooms();
//		}if(obj instanceof User){
//			result = data.getUserByProperty(by.getFieldName(), by.getValue());
//		}
//		if(result!=null){
//			return (List<T>)result;
//		}else{
//			return null;
//		}
		
		try{
			String getCmd = Utils.buildGetCommand(obj, by);
			String json = sendRequestToServer(getCmd);
			return (List<T>) gson.fromJson(json, obj.getClass());
		}catch(IllegalArgumentException ex){
			ex.printStackTrace();
		}catch(JsonSyntaxException ex){
			ex.printStackTrace();
		}
		return null;
	}
}
