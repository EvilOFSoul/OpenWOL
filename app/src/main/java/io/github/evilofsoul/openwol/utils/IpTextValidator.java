package io.github.evilofsoul.openwol.utils;

import android.text.Editable;
import android.widget.EditText;

/**
 * Created by Yevhenii on 09.09.2016.
 */
public class IpTextValidator extends TextValidator {
    private final static int MAX_IP_PART_VALUE = 255;
    private final static int MIN_IP_PART_VALUE = 0;

    public IpTextValidator(EditText editText) {
        super(editText);
    }

    @Override
    protected boolean validate(EditText editText, Editable editable) {
        if(editable.length() == 0){
            return true;
        }

        String ip = editable.toString().trim();

        try {
            String[] parts = ip.split( "\\." );
            if ( parts.length != 4 ) {
                this.setError("IP must consist of 4 parts, which are separated by dot");
                return false;
            }
            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < MIN_IP_PART_VALUE) || (i > MAX_IP_PART_VALUE) ) {
                    this.setError("Even part of IP must be between 0 and 255");
                    return false;
                }
            }
            if ( ip.endsWith(".") ) {
                this.setError("IP must not end with dot");
                return false;
            }
        } catch (NumberFormatException nfe) {
            this.setError("IP must contain four decimal numbers separated by dots");
            return false;
        }

        return true;
    }
}
