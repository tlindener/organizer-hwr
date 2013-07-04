package de.lindener.androidorganizer.viewmodels;

/**
 * 
 * Model to easily bind data to views
 * @author TobiasLindener
 *
 */
public class UserViewModel {

        String givenName = null;        
     String surname =null;         
     String mailAddress = null;
     String phoneNumber = null;
     
     public UserViewModel()
     {
    	 
     }
     
     public UserViewModel(String givenName,String surname,String mailAddress,String phoneNumber)
     {
    	this.givenName = givenName;
    	this.surname=surname;
    	this.mailAddress = mailAddress;
     }

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
