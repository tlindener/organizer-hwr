package view.renderer;

import javax.swing.DefaultListSelectionModel;

public class MyListSelectionModel extends DefaultListSelectionModel {

	/**
	 * Must be added to ensure a serialization - just not necessary
	 */
	private static final long serialVersionUID = 1L;
	boolean gestureStarted = false;

	public MyListSelectionModel() {
	}

	@Override
	public void setSelectionInterval(int index0, int index1) {
		System.out.println(index0 + " " +index1);
		if (!gestureStarted) {
			if (index0 == index1) {
				toggleSelection(index0);
			}
			if (index0 != index1) {
				toggleSelection(index1);
			}
		}
		gestureStarted = true;
	}

	@Override
	public void setValueIsAdjusting(boolean isAdjusting) {
		if (isAdjusting == false) {
			gestureStarted = false;
		}
	}

	private void toggleSelection(int index) {
		if (super.isSelectedIndex(index)) {
			super.removeSelectionInterval(index, index);
		} else {
			super.addSelectionInterval(index, index);
		}
	}
}
