package io.github.evilofsoul.openwol.utils;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Yevhenii on 09.09.2016.
 */
public abstract class TextValidator implements TextWatcher {

    private EditText editText;
    private TextInputLayout layout;

    public TextValidator(EditText editText) {
        this.editText = editText;
        if(editText.getParent() instanceof TextInputLayout){
            layout = (TextInputLayout) editText.getParent();
            layout.setErrorEnabled(true);
        }
    }

    public boolean validate(){
        if(validate(editText, editText.getText())){
            this.setError(null);
            return true;
        }
        return false;
    }

    protected abstract boolean validate(EditText editText, Editable editable);

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(layout != null && layout.getError() != null){
            layout.setError(null);
        } else if(editText.getError() != null) {
            editText.setError(null);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(validate(editText, editable)){
            this.setError(null);
        }
    }

    protected void setError(String msg){
        if(layout != null){
            if(layout.getError() == null) {
                layout.setError(msg);
            }
        } else if(editText.getError() == null) {
            editText.setError(msg);
        }
    }
}
