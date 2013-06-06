package view.renderer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;

public class MyListSelectionModel extends DefaultListSelectionModel {

	/**
	 * Must be added to ensure a serialization - just not necessary
	 */
	private static final long serialVersionUID = 1L;

	int index0_old = -1;
	int index1_old = -1;

	public MyListSelectionModel() {
	}


//	@Override
//	public void setSelectionInterval(int index0, int index1) {
//
//		if (!mouseCheckFlag)
//			return;
//		
//		System.out.println("old: " + index0_old + "|" + index1_old);
//		System.out.println("new: " + index0 + "|" + index1);
//		System.out.println("________________________");
//
//		
//
//		if (index0_old == index0 && index1_old == index1) {
//			toggleSelection(index0, index1);
//		} else {
//
//			if (index0 == index1) {
//				toggleSelection(index0, index1);
//				index0_old = index0;
//				index1_old = index1;
//
//			} else {
////				if (index0 > index1) {
////					addToSelection(index0 - 1, index1);
////				}
////				if (index0 < index1) {
////					addToSelection(index0 + 1, index1);
////				}
//				
//			}
//		}
//	}
	
	 boolean gestureStarted = false;

	    @Override
	    public void setSelectionInterval(int index0, int index1) {
	        if(!gestureStarted){
	        	
	        	if (index0_old == index0 && index1_old == index1) {
	    			toggleSelection(index0, index1);
	    		} else {
	    
	    			if (index0 == index1) {
	    				toggleSelection(index0, index1);
	    				index0_old = index0;
	    				index1_old = index1;
	    
	    			} else {
	    					    				
	    			}
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
	
//	private void addToSelection(int index0, int index1) {
//		int first = getMin(index0, index1);
//		int last = getMax(index0, index1);
//		for (int i = first; i <= last; i++) {
//			list.addSelectionInterval(i, i);
//		}
//	}
//
	private void toggleSelection(int index0, int index1) {
		int first = getMin(index0, index1);
		int last = getMax(index0, index1);
		for (int i = first; i <= last; i++) {
			if (super.isSelectedIndex(i)) {
				super.removeSelectionInterval(i, i);
			} else {
				super.addSelectionInterval(i, i);
			}
		}
	}

	private int getMax(int a, int b) {
		return a >= b ? a : b;
	}

	private int getMin(int a, int b) {
		return a <= b ? a : b;
	}
}
