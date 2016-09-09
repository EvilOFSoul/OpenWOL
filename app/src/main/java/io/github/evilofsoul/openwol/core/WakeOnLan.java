package io.github.evilofsoul.openwol.core;

import java.io.IOException;

/**
 * Created by Yevhenii on 02.08.2016.
 */
public abstract class WakeOnLan {
    protected final static int DEFAULT_PORT = 9;
    protected Type type;

    public enum  Type {
        TARGET,
        BROADCAST
    }

    public abstract void wakeUp(Machine machine) throws IOException;

    public Type getType() {
        return type;
    }

    public static WakeOnLan create(Type type){
        if(type == Type.TARGET){
            return new TargetWakeOnLan();
        }
        if(type == Type.BROADCAST){
            return new BroadcastWakeOnLan();
        }
        return null;
    }
}
