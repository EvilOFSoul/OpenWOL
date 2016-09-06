package io.github.evilofsoul.openwol.utils;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by Yevhenii on 06.09.2016.
 */
public class MacInputFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence charSequence, int start, int end, Spanned spanned, int i2, int i3) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=start; i<end; i++){
            char tmpChar = charSequence.charAt(i);
            if(Character.digit(tmpChar,16) != -1 || this.isDivider(tmpChar)){
                stringBuilder.append(tmpChar);
            }
        }
        return stringBuilder.toString().toLowerCase();
    }

    private boolean isDivider(char input){
        if(input == '-'){
            return true;
        }
        return false;
    }
}
