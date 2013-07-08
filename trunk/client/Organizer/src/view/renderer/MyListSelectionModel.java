package view.renderer;

import javax.swing.DefaultListSelectionModel;
/**
 *Defines the characteristics of selection.
 *
 * @author Steffen Baumann
 *
 */
public class MyListSelectionModel extends DefaultListSelectionModel {

	
	
	boolean gestureStarted = false;
/**
 * Default constructor
 */
	public MyListSelectionModel() {
	}


	@Override
	public void setSelectionInterval(int index0, int index1) {
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

	/**
	 * Toggles the selection interval according to the submitted index.
	 * 
	 * @param index
	 */
	private void toggleSelection(int index) {
		if (super.isSelectedIndex(index)) {
			super.removeSelectionInterval(index, index);
		} else {
			super.addSelectionInterval(index, index);
		}
	}
}
