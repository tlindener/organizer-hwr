package view.renderer;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFormattedTextField;

import view.renderer.TimeVerifier;

public class TimeField extends JFormattedTextField {

    public TimeField() {
        super(TimeVerifier.getDefaultFormat());
        this.setInputVerifier(new TimeVerifier(this));
    }

    public TimeField(Date date) {
        this();
        this.setValue(date);
    }

    @Override
    protected void invalidEdit() {
        if (!this.getInputVerifier().verify(this)) {
            super.invalidEdit();
        }
    }
    
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
