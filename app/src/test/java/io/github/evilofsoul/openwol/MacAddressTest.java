package io.github.evilofsoul.openwol;

import org.junit.Before;
import org.junit.Test;

import io.github.evilofsoul.openwol.core.MacAddress;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Yevhenii on 14.09.2016.
 */
public class MacAddressTest{
    MacAddress macAddress;
    private static final int MAC_PART_COUNT = 6;

    static String[] validData = {
            "00-08-74-4C-7F-1D",
            "E2-A7-20-C5-39-A4",
            "0B-39-3F-AC-22-92",
            "EB:90:78:5C:65:4B",
            "02:92:7A:75:EC:E3"
    };

    static String[] nonValidData = {
            "123123123",
            "ss-as",
            "ss-as-as",
            "02:92:7A:75:GC:H3",
            "02:92:7A:75:EC:E3.",
            "02:92:7A:75:EC:E3:",
            "02:92:7A:75::EC:E3"
    };

    @Before
    public void init(){
        macAddress = new MacAddress();
    }

    @Test
    public void create_ValidData_MacAddress(){
        for (String mac : validData) {
            MacAddress.create(mac);
        }
    }

    @Test
    public void create_NonValidData(){
        int count = 0;
        for (String mac : nonValidData) {
            try {
                MacAddress.create(mac);
            } catch (IllegalArgumentException e){
                count++;
            }
        }
        if(count != nonValidData.length){
            fail();
        }
    }

    @Test
    public void setMacAddress_ValidData(){
        for (String mac : validData) {
            macAddress.set(mac);
            String formattedMac = mac.replace('-',':').toLowerCase();
        }
    }

    @Test
    public void setMacAddress_NonValidData(){
        int count = 0;
        for (String mac : nonValidData) {
            try {
                macAddress.set(mac);
            } catch (IllegalArgumentException e){
                count++;
            }
        }
        if(count != nonValidData.length){
            fail();
        }
    }

    @Test
    public void isValid_ValidData_True(){
        for (String mac : validData) {
            assertEquals(MacAddress.isValid(mac), true);
        }
    }

    @Test
    public void isValid_NonValidData_False(){
        for (String mac : nonValidData) {
            assertEquals(MacAddress.isValid(mac), false);
        }
    }

    @Test
    public void get(){
        macAddress.set("02:92:7A:75:EC:E3");
        byte[] byteMac = {2,-110,122,117,-20,-29};
        assertArrayEquals(macAddress.get(),byteMac);
    }

    @Test
    public void get_Index(){
        macAddress.set("02:92:7A:75:EC:E3");
        byte[] byteMac = {2,-110,122,117,-20,-29};
        for(int i=0; i<MAC_PART_COUNT; i++){
            assertEquals(macAddress.get(i), byteMac[i]);
        }
    }
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void get_InvalidIndex_Exception(){
        macAddress.get(MAC_PART_COUNT+1);
    }
}
