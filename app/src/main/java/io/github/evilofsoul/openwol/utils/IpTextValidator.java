package io.github.evilofsoul.openwol.utils;

import android.text.Editable;
import android.widget.EditText;

import io.github.evilofsoul.openwol.R;

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
                this.setError(context.getString(R.string.ip_text_validator_format_error));
                return false;
            }
            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < MIN_IP_PART_VALUE) || (i > MAX_IP_PART_VALUE) ) {
                    this.setError(context.getString(R.string.ip_text_vlidator_invalid_part_error));
                    return false;
                }
            }
            if ( ip.endsWith(".") ) {
                this.setError(context.getString(R.string.ip_text_validator_end_with_dor_error));
                return false;
            }
        } catch (NumberFormatException nfe) {
            this.setError(context.getString(R.string.ip_text_validator_format_error));
            return false;
        }

        return true;
    }
}
