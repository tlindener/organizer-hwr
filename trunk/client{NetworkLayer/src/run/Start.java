package run;


public class Start {

	public Start() {
	
		Sender s = new Sender();
		Calendar c = s.requestObject(new Calendar());
		Termin t = s.requestObject(new Termin());

		
		System.out.println(c);
		System.out.println(t);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Start();
	}

}
