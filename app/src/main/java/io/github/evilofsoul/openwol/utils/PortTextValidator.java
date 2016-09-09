package io.github.evilofsoul.openwol.utils;

import android.text.Editable;
import android.widget.EditText;

/**
 * Created by Yevhenii on 09.09.2016.
 */
public class PortTextValidator extends TextValidator {
    private final static int MAX_PORT_NUMBER = 65535;
    private final static int MIN_PORT_NUMBER = 1;

    public PortTextValidator(EditText editText) {
        super(editText);
    }

    @Override
    protected boolean validate(EditText editText, Editable editable) {
        if(editable.toString().trim().length() == 0){
            return true;
        }

        try {
            int port = Integer.parseInt(editable.toString().trim());
            if(port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER){
                this.setError("Port value must be between 1 and 65535");
                return false;
            }
        } catch (NumberFormatException nfe){
            this.setError("Port value must contain only decimal numbers");
            return false;
        }
        return true;
    }
}
