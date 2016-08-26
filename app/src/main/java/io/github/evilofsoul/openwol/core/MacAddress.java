package io.github.evilofsoul.openwol.core;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yevhenii on 02.08.2016.
 */
public class MacAddress implements Serializable{ // TODO: 06.08.2016 refactor this class!!!! mac string array to byte
    private byte mac[] = new byte[6];

    public byte[] get() {
        return mac;
    }

    public byte get(int index) {
        if(index >= 0 && index < mac.length) {
            return mac[index];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public String toString() {
        String str[] = new String[this.mac.length];
        for(int i=0; i<this.mac.length; i++){
            String tmpStr = Integer.toHexString((int)mac[i]);
            if(tmpStr.length() == 1){
                tmpStr = "0"+ tmpStr;
            }
            str[i] = tmpStr;
        }
        return this.joinSrings(str,":");
    }

    public void set(String[] mac) {
        String tmpMac = this.joinSrings(mac, ":");// TODO: 02.08.2016 Remove calling of this function
        if(!MacAddress.isValid(tmpMac)){
            throw new IllegalArgumentException(); // TODO: 02.08.2016 write description
        }
        this.mac = this.stringToByte(mac);
    }

    public void set(String mac) {
        mac = this.preValidate(mac);
        if(!MacAddress.isValid(mac)) {
            throw new IllegalArgumentException(); // TODO: 02.08.2016 write description
        }
        this.mac = this.stringToByte(this.splitMac(mac));
    }

    private final String joinSrings(String[] array, String delimiter){ // TODO: 02.08.2016 THIS IS SHIT!!!
        String result = "";
        for (int i = 0; i < array.length-1; i++) {
            result += array[i]+delimiter;
        }
        result+=array[array.length-1];
        return result;
    }

    private String preValidate(String mac){
        mac = mac.toLowerCase();
        if(mac.matches("([a-z0-9]){12}")){
            String tmpMac = "";
            for(int i=0; i<mac.length(); i++){
                if((i > 1) && (i % 2 == 0)) {
                    tmpMac += ":";
                }
                tmpMac += mac.charAt(i);
            }
            mac = tmpMac;
        }
        return mac;
    }

    @NonNull
    private String[] splitMac(String mac){
        return mac.split("[-:]");
    }

    public static boolean isValid(String mac){
        final Pattern pat = Pattern.compile("((([0-9a-fA-F]){2}[-:]){5}([0-9a-fA-F]){2})");
        final Matcher m = pat.matcher(mac);

        if(m.find()) {
            return true;
        }
        return false;
    }

    private byte[] stringToByte(String[] strings){
        byte[] result = new byte[strings.length];

        for (int i = 0; i < strings.length; i++) {
            result[i] = (byte) Integer.parseInt(strings[i], 16);
        }
        return result;
    }

}
