package view.renderer;

import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

class ButtonsPanel extends JPanel {
	  public final List<JButton> buttons =  Arrays.asList(new JButton("view"), new JButton("edit"));
	  public ButtonsPanel() {
	    super();
	    setOpaque(true);
	    for(JButton b: buttons) {
	      b.setFocusable(false);
	      b.setRolloverEnabled(false);
	      add(b);
	    }
	  }
	}

