package run;

public class Termin implements DataPusher{

	@Override
	public void setData(String... data) {
		for(String d: data){
			System.out.println(d);
		}
	}

}
