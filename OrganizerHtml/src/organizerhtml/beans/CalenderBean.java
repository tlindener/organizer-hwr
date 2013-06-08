package organizerhtml.beans;

import java.util.Date;

import javax.faces.application.FacesMessage;

import org.primefaces.event.SelectEvent;

public class CalenderBean {
	private Date date;

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public void dateChange(SelectEvent event) {
		Date date = (Date) event.getObject();
		System.out.println("File Date: " + date);
		System.out.println("Hello... I am in DateChange");
	}

	public void handleDateSelect(SelectEvent event) {
		Date date = (Date) event.getObject();
		}
}
