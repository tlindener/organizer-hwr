package view.renderer;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

class ButtonsEditor extends ButtonsPanel {

	private ActionListener myCon;
	private JTable table;

	public ButtonsEditor(JTable table, ActionListener AL) {

		myCon = AL;
		table = table;

		// <----

		buttons.get(0).addActionListener(myCon);

		buttons.get(1).addActionListener(myCon);
	}
}