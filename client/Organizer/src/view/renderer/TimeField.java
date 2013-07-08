package view.renderer;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

import view.renderer.TimeVerifier;

/**
 * This Class is a special JFormattedTextField that accepts as input just times in the valid format.
 * 
 * @author Jennifer Blumenthal
 *
 */
public class TimeField extends JFormattedTextField {

	/**
	 * Default constructor that initializes a TimeVerifier and creates an 
	 * empty TimeField.
	 */
    public TimeField() {
        super(TimeVerifier.getDefaultFormat());
        this.setInputVerifier(new TimeVerifier(this));
    }

    /**
     * Constructor that initializes a TimeField with a submitted date.
     * @param date
     */
    public TimeField(Date date) {
        this();
        this.setValue(date);
    }

    
    @Override
    protected void invalidEdit() {
        if (!this.getInputVerifier().verify(this)&&!this.getText().isEmpty()) {
        	boolean valid=super.isEditValid();
        	if(!valid)
        	{
        		JOptionPane.showMessageDialog(null, "Bitte geben Sie die Uhrzeit im Format (H)H:mm ein!");
        		this.setText("");
        	}
        }
    }
    
    /**
     * Returns the time that has been entered.
     * @return date.getTime()
     */
    public long getTime() 
    {
    	SimpleDateFormat format=new SimpleDateFormat("H:mm");
    	Date date= new Date();
    	try {
    		date=format.parse(this.getText());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return date.getTime();
    }
}
