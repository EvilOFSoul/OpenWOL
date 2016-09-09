package io.github.evilofsoul.openwol.utils;

import android.text.Editable;
import android.widget.EditText;

/**
 * Created by Yevhenii on 07.09.2016.
 */
public class RequiredFieldTextValidator extends TextValidator {

    public RequiredFieldTextValidator(EditText editText)
    {
        super(editText);
    }

    @Override
    protected boolean validate(EditText editText, Editable editable) {
        if(editable.toString().trim().length() == 0){
            this.setError("This field is required");
            return false;
        }
        return true;
    }
}
