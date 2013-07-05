package view.renderer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

class TimeVerifier extends InputVerifier {

    private static List<SimpleDateFormat> validForms =
        new ArrayList<SimpleDateFormat>();


    static {
        validForms.add(new SimpleDateFormat("H:mm"));
    }
    private JFormattedTextField txtField;
    private Date date;

    public TimeVerifier(JFormattedTextField tf) {
        this.txtField = tf;
    }

    @Override
    public boolean verify(JComponent input) {
        boolean result = false;
        if (input == txtField) {
            String text = txtField.getText();
            for (SimpleDateFormat format : validForms) {
                try {
                    date = format.parse(text);
                    result |= true;
                } catch (ParseException pe) {
//                	JOptionPane.showMessageDialog(null, "Bitte geben Sie die Uhrzeit im Format (H)H:mm ein!");
                    result |= false;
                }
            }
        }
        return result;
    }

    @Override
    public boolean shouldYieldFocus(JComponent input) {
        if (verify(input)) {
            txtField.setValue(date);
            return true;
        } else {
            return false;
        }
    }

    public static SimpleDateFormat getDefaultFormat() {
        return validForms.get(0);
    }

	
}

