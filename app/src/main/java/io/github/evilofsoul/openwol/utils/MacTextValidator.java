package io.github.evilofsoul.openwol.utils;

import android.text.Editable;
import android.widget.EditText;

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
            this.setError("MAC must contain six hex numbers separated by \":\" ");
            return false;
        }
        return true;
    }
}
