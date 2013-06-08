package organizerhtml.beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class LoginBean {

	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String login() {
		if (verifyUser()) {
			return "home";
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("username", new FacesMessage(
					"Invalid UserName and Password"));
			return "login";
		}
	}
	
	private boolean verifyUser(){
		//TODO: hier die Prüfung durchführen
		return false;
	}
}
