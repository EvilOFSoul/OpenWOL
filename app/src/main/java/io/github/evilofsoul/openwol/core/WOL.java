package io.github.evilofsoul.openwol.core;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Yevhenii on 02.08.2016.
 */
public interface WOL {
    public void wakeUp(Machine machine) throws IOException;
}
