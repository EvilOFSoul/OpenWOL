package io.github.evilofsoul.openwol.utils;

import android.os.AsyncTask;

import java.io.IOException;

import io.github.evilofsoul.openwol.core.Machine;
import io.github.evilofsoul.openwol.core.WakeOnLan;

/**
 * Created by Yevhenii on 27.09.2016.
 */

public class WakingUpMachineTask extends AsyncTask<Object,Object,Object> {

    Machine machine;

    public WakingUpMachineTask(Machine machine) {
        this.machine = machine;
    }

    @Override
    protected Object doInBackground(Object... objects) {
        WakeOnLan wakeOnLan = WakeOnLan.create(getWakeOnLanType(machine));
        try {
            wakeOnLan.wakeUp(machine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private WakeOnLan.Type getWakeOnLanType(Machine machine){
        if(machine.getIp().length() != 0){
            return WakeOnLan.Type.TARGET;
        }

        return WakeOnLan.Type.BROADCAST;
    }
}
