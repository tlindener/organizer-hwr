package view;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.MyChangeListener;

import organizer.objects.types.User;

public class MyCheckBoxListRenderer implements ListCellRenderer<User>, ChangeListener {
//	public Component getListCellRendererComponent(JList<?> list, Object value,
//			int index, boolean isSelected, boolean hasFocus) {
//		
//		JCheckBox box = new JCheckBox();
//		
//		setEnabled(list.isEnabled());
//		setSelected(((checklistitem) value).isSelected());
//		setFont(list.getFont());
//		setBackground(list.getBackground());
//		setForeground(list.getForeground());
//		setText(value.toString());
//		return this;
//	}
	
	private HashMap<JCheckBox, User> map = new HashMap<JCheckBox, User>();
	private MyChangeListener listener = null;
	
	public MyCheckBoxListRenderer(MyChangeListener listener) {
		this.listener = listener;
	}
	
	
	@Override
	public Component getListCellRendererComponent(JList<? extends User> list,
			User value, int index, boolean isSelected, boolean cellHasFocus) {
		JCheckBox box = new JCheckBox(value.getGivenName() + " " + value.getSurname());
		map.put(box, value);
		box.setSelected(false);
		box.setEnabled(true);
		box.setVisible(true);
		box.addChangeListener(this);
		return box;
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		JCheckBox box = (JCheckBox) e.getSource();
		listener.stateChangedForUser(box.isSelected(), map.get(box));
	}
}