package io.github.evilofsoul.openwol.core;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Yevhenii on 06.08.2016.
 */
public class TargetWOL implements WOL {
    @Override
    public void wakeUp(Machine machine) throws IOException {
        byte[] magicPacket = MagicPacket.create(machine.getMac());

        final InetAddress address = InetAddress.getByName(machine.getIp());
        final DatagramPacket packet = new DatagramPacket(
                magicPacket,
                magicPacket.length,
                address,
                machine.getPort()
        );
        final DatagramSocket socket = new DatagramSocket();
        socket.send(packet);
        socket.close();
    }
}
