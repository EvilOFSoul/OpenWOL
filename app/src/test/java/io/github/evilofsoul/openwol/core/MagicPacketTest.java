package io.github.evilofsoul.openwol.core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Yevhenii on 15.09.2016.
 */
public class MagicPacketTest {
    private final static Byte[] MAGIC_PACKET_HEADER_BYTES = {-1,-1,-1,-1,-1,-1};
    private final static int MAC_INCLUSIONS_COUNT = 16;

    @Test
    public void create_ValidMacAddress(){
        MacAddress macAddress = MacAddress.create("00-08-74-4C-7F-1D");
        byte[] value = MagicPacket.create(macAddress);

        List<Byte> expectedValueAsList = new ArrayList<>();
        expectedValueAsList.addAll(Arrays.asList(MAGIC_PACKET_HEADER_BYTES));
        Byte tmpMac[] = toObjectByte(macAddress.get());
        for(int i = 0; i<MAC_INCLUSIONS_COUNT; i++){
            expectedValueAsList.addAll(Arrays.asList(tmpMac));
        }

        final Byte[] expectedValue = expectedValueAsList.toArray(new Byte[expectedValueAsList.size()]);

        assertThat(value,is(toPrimitiveByte(expectedValue)));
    }

    private byte[] toPrimitiveByte(Byte[] array){
        byte[] outArray = new byte[array.length];
        for(int i =0; i<array.length; i++){
            outArray[i] = array[i];
        }
        return outArray;
    }

    private Byte[] toObjectByte(byte[] array){
        Byte[] outArray = new Byte[array.length];
        for(int i =0; i<array.length; i++){
            outArray[i] = array[i];
        }
        return outArray;
    }
}
