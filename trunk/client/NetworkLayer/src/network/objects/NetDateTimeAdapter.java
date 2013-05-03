package network.objects;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Parsing DateTime from C# to long
 * @author Steffen Baumann
 * @version 1.0 
 *
 */
public class NetDateTimeAdapter extends TypeAdapter<Date> {
	
	public NetDateTimeAdapter() {
	}
	
    @Override
    public Date read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        Date result = null;
        String str = reader.nextString();
        str = str.replaceAll("([+][0-9]{4})|([^0-9])", "");
        if (!str.equals("")) {
            try {
                result = new Date(Long.parseLong(str));
            } catch (NumberFormatException e) {
            }
        }
        return result;
    }
	@Override
	public void write(JsonWriter out, Date value) throws IOException, UnsupportedOperationException {
		String date = "\\/Date("+value.getTime()+"+0000)\\/";
		System.out.println(date);
		out.value(date);
	}

}