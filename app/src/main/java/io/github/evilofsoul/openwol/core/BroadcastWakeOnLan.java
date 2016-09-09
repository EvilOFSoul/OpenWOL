package io.github.evilofsoul.openwol.core;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Yevhenii on 02.08.2016.
 */
public class BroadcastWakeOnLan extends WakeOnLan {
    private static final String BROADCAST_ADDRESS = "255.255.255.255";

    public BroadcastWakeOnLan() {
        type = Type.BROADCAST;
    }

    @Override
    public void wakeUp(Machine machine) throws IOException {
        byte[] magicPacket = MagicPacket.create(machine.getMac());

        final InetAddress address = InetAddress.getByName(BROADCAST_ADDRESS);
        final int port = machine.getPort() != 0 ? machine.getPort() : BroadcastWakeOnLan.DEFAULT_PORT;
        final DatagramPacket packet = new DatagramPacket(
                magicPacket,
                magicPacket.length,
                address,
                port
        );
        final DatagramSocket socket = new DatagramSocket();
        socket.setBroadcast(true);
        socket.send(packet);
        socket.close();
    }
}
