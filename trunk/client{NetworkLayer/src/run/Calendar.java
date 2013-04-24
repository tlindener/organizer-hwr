package run;

public class Calendar implements DataPusher{

	@Override
	public void setData(String... data) {
		for(String d: data){
			System.out.println(d);
		}
		
	}

	public void setData(String data, String data2) {
		// TODO Auto-generated method stub
		
	}

}
