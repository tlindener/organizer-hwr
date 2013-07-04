package organizer.networklayer.network.utilities;

import java.io.IOException;
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
        String time = reader.nextString();
        Date date = ParseUtils.parseStringToDate(time);
        
        return date;
    }
	@Override
	public void write(JsonWriter out, Date value) throws IOException, UnsupportedOperationException {
		String date = ParseUtils.parseDateToNetDateTime(value);
		date = ParseUtils.parseStringToHTTP(date);
		out.value(date);
	}

}