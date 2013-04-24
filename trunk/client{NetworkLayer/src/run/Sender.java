package run;

public class Sender{
	
//	@SuppressWarnings("hiding")
	public <T extends DataPusher> T requestObject(T obj) {
		
		if(obj instanceof Calendar){
			obj.setData("Calendar","Owner");
		}else if(obj instanceof Termin){
			obj.setData("14:00","17:00");
		}
		return obj;
	}
}
