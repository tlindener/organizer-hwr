package network.objects;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Parsing DateTime from C# to {@link Date} and backwards
 * @author Steffen Baumann
 * @version 1.0 
 *
 */
public class NetDateTimeAdapter extends TypeAdapter<Date> {
	/** Default Constructor*/
	public NetDateTimeAdapter() {}
	
    @Override
    public Date read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
  
        Date result = null;
        String str = reader.nextString();
        int start = str.indexOf("(") + 1;
        int plus = str.indexOf("+");
        int end = str.indexOf(")");
        
        String millis = str.substring(start, plus);
        String offset = str.substring(plus, end);
        
        result = new Date(Long.parseLong(millis));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"+offset);
		String tmp = format.format(result);
		try {
			result = format.parse(tmp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.err.println("Das geparste Datum ist: " + result);
		
//        str = str.replaceAll("([+][0-9]{4})|([^0-9])", "");
//        if (!str.equals("")) {
//            try {
//                result = new Date(Long.parseLong(str));
//            } catch (NumberFormatException e) {
//            }
//        }
        
        
        return result;
    }
	@Override
	public void write(JsonWriter out, Date value) throws IOException, UnsupportedOperationException {
		String date = Utils.parseDateToNetDateTime(value);
		System.out.println(date);
		out.value(date);
	}

}