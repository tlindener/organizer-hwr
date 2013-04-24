package run;

public class OrganizerObject<T extends DataPusher> {
	public OrganizerObject(){
		
	}
	public <T extends DataPusher> T returnType() throws InstantiationException, IllegalAccessException{
		return (T) DataPusher.class.newInstance();
	}
}
