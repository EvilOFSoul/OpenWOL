package io.github.evilofsoul.openwol.utils;

import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Yevhenii on 05.09.2016.
 */
public class SimpleMaskTextWatcher implements TextWatcher {
    String current="";
    String mask="";
    EditText editText;
    String dividers = "";

    public SimpleMaskTextWatcher(String mask, EditText editText) {
        this.editText = editText;
        this.mask = mask;
        dividers = getDividers();
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
        dividers = getDividers();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int before, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        if(!charSequence.toString().equals(current)) {
            editText.removeTextChangedListener(this);

            String formatted = format(charSequence.toString());
            current = formatted;
            editText.setText(formatted);
            editText.setSelection(formatted.length());

            editText.addTextChangedListener(this);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private String format(String str){
        String pattern = java.util.regex.Pattern.quote(dividers);
        str = str.replaceAll("["+pattern+"]*","");
        String formattedStr = "";
        int indexOfChar = 0;

        for (char maskElement: mask.toCharArray()) {
            if (indexOfChar < str.length()) {
                if (maskElement != '#') {
                    formattedStr += maskElement;
                } else {
                    formattedStr += str.charAt(indexOfChar);
                    indexOfChar++;
                }
            } else {
                break;
            }
        }
        return formattedStr;
    }

    private String getDividers(){
        String maskWithoutPlaceholder = mask.replace("#","");
        List<Character> dividers = new ArrayList<>();
        for (char s : maskWithoutPlaceholder.toCharArray()) {
            if(dividers.indexOf(s) == -1) {
                dividers.add(s);
            }
        }
        return Joiner.on("").join(dividers);
    }
}
