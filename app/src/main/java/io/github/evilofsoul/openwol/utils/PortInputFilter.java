package io.github.evilofsoul.openwol.utils;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by Yevhenii on 06.09.2016.
 */
public class PortInputFilter implements InputFilter {
    private final static int MAX_PORT_NUMBER = 65535;
    private final static int MIN_PORT_NUMBER = 1;
    @Override
    public CharSequence filter(CharSequence charSequence, int start, int end, Spanned spanned, int dStart, int dEnd) {
        try{
            String strValue = spanned.toString();
            strValue = strValue.substring(0,dStart)+charSequence.toString()
                    +strValue.substring(dEnd,strValue.length());

            int value = Integer.parseInt(strValue);
            if(value > MAX_PORT_NUMBER || value < MIN_PORT_NUMBER){
                return "";
            }
        } catch (Exception e){
            return "";
        }
        return null;
    }
}
