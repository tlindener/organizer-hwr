/**
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import view.window_TerminBearbeiten;
import data.DataPusher;
import data.model.MyModel;
import data.objects.types.Room;
import data.objects.types.User;

/**
 * @author Steffen Baumann
 * @version 1.0 
 *
 */
public class DefaultController implements DataPusher, ActionListener{

	MyModel model = new MyModel();
	window_TerminBearbeiten editEntry = null;
	/**
	 * 
	 */
	public DefaultController() {
		//TODO some Stuff to build up a connection
		model.refreshData();
		startEditEntryWindow();
		
		
	}

	private void startEditEntryWindow() {
//		editEntry = new window_TerminBearbeiten(this, this);
//		editEntry.openEmptyFrame();

//		Willst du Daten schon vorher mitgeben, nutze einfach die Methode
//		editEntry.openFrameWithValues(startTime, endTime, beschreibung, details);
//		Vorselektionen habe ich jetzt noch nicht drinne, kann ich dir dann aber bei helfen ;-)
		
//		Das war der alte Anfang deines Codes im Controller --> kann alles weg soweit
//		Vector<User> listUser = new Vector<User>();
//		for (Object refObj : myModel.createAllePersonen()) {
//			User u = (User) refObj;
//			listUser.add(u);
//		}
		
		editEntry = new window_TerminBearbeiten(this, this);
		User[] user = model.getListUser();
		
		List<User> preselected = new ArrayList<>();
		
		for(User u:user){
			if(u.getID()%4==0){
				preselected.add(u);
			}
		}
		editEntry.setButtonText("Hier kann ihre Werbung stehen");
		
		editEntry.openFrameWithValues("14:00","15:00", "beschreibung", "details", null, preselected.toArray(new User[0]));
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new DefaultController();
	}

	@Override
	public User[] pushUserList() {
		return model.getListUser();
	}

	@Override
	public Room[] pushRoomList() {
		return model.getListRoom();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(editEntry.getBtnTerminEintragen())){
			System.out.println("JA - richtiger BUTTON");
//			TODO: Daten aus dem Fenster abfragen und auf setVisible(false) setzen.
//			editEntry.getTxtBeschreibung()
		}
	}
}
