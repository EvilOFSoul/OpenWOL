package io.github.evilofsoul.openwol.utils.validators;

import android.text.Editable;
import android.widget.EditText;

import io.github.evilofsoul.openwol.R;

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
            this.setError(context.getString(R.string.required_field_text_validator_error));
            return false;
        }
        return true;
    }
}
