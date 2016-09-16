package io.github.evilofsoul.openwol.utils;

import android.text.Editable;
import android.widget.EditText;

import io.github.evilofsoul.openwol.R;
import io.github.evilofsoul.openwol.core.MacAddress;

/**
 * Created by Yevhenii on 09.09.2016.
 */
public class MacTextValidator extends TextValidator {
    public MacTextValidator(EditText editText) {
        super(editText);
    }

    @Override
    protected boolean validate(EditText editText, Editable editable) {
        if(editable.toString().trim().length() == 0){
            return true;
        }

        if(!MacAddress.isValid(editable.toString())){
            this.setError(context.getString(R.string.mac_text_validator_format_error));
            return false;
        }
        return true;
    }
}
