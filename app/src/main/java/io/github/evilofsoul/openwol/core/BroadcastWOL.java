package io.github.evilofsoul.openwol.core;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Yevhenii on 02.08.2016.
 */
public class BroadcastWOL implements WOL {
    private static final int PORT = 9;

    @Override
    public void wakeUp(Machine machine) throws IOException {
        byte[] magicPacket = MagicPacket.create(machine.getMac());

        final InetAddress address = InetAddress.getByName(machine.getIp());
        final DatagramPacket packet = new DatagramPacket(
                magicPacket,
                magicPacket.length,
                address, // TODO: 06.08.2016 change target to broadcast
                BroadcastWOL.PORT
        );
        final DatagramSocket socket = new DatagramSocket();
        socket.send(packet);
        socket.close();
    }
}
