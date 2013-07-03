package de.lindener.androidorganizer.preferences;

public class PreferenceData {

	public PreferenceData() {
	}

	public PreferenceData(String serverAddress, int serverPort, String mailAddress,
			String password) {
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.mailAddress = mailAddress;
		this.password = password;
				

	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	String serverAddress = "";
	int serverPort = 80;
	String mailAddress = "";
	String password = "";
}
