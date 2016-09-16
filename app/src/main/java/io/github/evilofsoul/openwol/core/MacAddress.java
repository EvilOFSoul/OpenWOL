package io.github.evilofsoul.openwol.core;

import android.support.annotation.NonNull;

import com.google.common.base.Joiner;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yevhenii on 02.08.2016.
 */
public class MacAddress implements Serializable{
    private byte mac[] = new byte[6];

    public static MacAddress create(String mac){
        MacAddress macAddress = new MacAddress();
        macAddress.set(mac);
        return macAddress;
    }

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
            str[i] = String.format("%02x",mac[i]);
        }
        return Joiner.on(":").join(str);
    }

    public void set(String[] mac) {
        String tmpMac = Joiner.on(":").join(mac);
        if(!MacAddress.isValid(tmpMac)){
            throw new IllegalArgumentException(); // TODO: 02.08.2016 write description
        }
        this.mac = this.stringToByte(mac);
    }

    public void set(String mac) {
        if(!MacAddress.isValid(mac)) {
            throw new IllegalArgumentException(); // TODO: 02.08.2016 write description
        }
        this.mac = this.stringToByte(this.splitMac(mac));
    }

    @NonNull
    private String[] splitMac(String mac){
        return mac.split("[-:]");
    }

    public static boolean isValid(String mac){
        final Pattern pat = Pattern.compile("^((([0-9a-fA-F]){2}[-:]){5}([0-9a-fA-F]){2})$");
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
