package io.github.evilofsoul.openwol.core;

import java.util.ArrayList;

/**
 * Created by Yevhenii on 02.08.2016.
 */
public class MagicPacket {
    private MagicPacket() {
    }

    public static byte[] create(MacAddress mac) {
        final byte[] packet = new byte[102];
        final byte[] macBytes = mac.get();
        // fill first 6 bytes
        for(int i=0; i<6; i++) {
            packet[i] = (byte) 0xff;
        }
        // fill remaining bytes with target MAC
        for(int i=6; i<packet.length; i+=macBytes.length) {
            System.arraycopy(macBytes, 0, packet, i, macBytes.length);
        }
        return  packet;
    }
}
