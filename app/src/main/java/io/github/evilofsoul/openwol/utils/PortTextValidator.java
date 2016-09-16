package io.github.evilofsoul.openwol.utils;

import android.text.Editable;
import android.widget.EditText;

import io.github.evilofsoul.openwol.R;

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
                this.setError(context.getString(R.string.port_text_validator_out_of_range_error));
                return false;
            }
        } catch (NumberFormatException nfe){
            this.setError(context.getString(R.string.port_text_validator_format_error));
            return false;
        }
        return true;
    }
}
