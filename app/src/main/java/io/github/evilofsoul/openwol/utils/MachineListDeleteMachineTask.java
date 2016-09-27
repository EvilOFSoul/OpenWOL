package io.github.evilofsoul.openwol.utils;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import io.github.evilofsoul.openwol.core.Machine;
import io.github.evilofsoul.openwol.core.dao.DbHelper;
import io.github.evilofsoul.openwol.core.dao.MachineDAO;

/**
 * Created by Yevhenii on 27.09.2016.
 */

public class MachineListDeleteMachineTask extends AsyncTask<Object,Object,Integer> {
    Machine machine;
    MachineListAdapter adapter;
    Context context;

    public MachineListDeleteMachineTask(Context context, MachineListAdapter adapter, Machine machine) {
        this.machine = machine;
        this.adapter = adapter;
        this.context = context;
    }

    @Override
    protected Integer doInBackground(Object... objects) {
        MachineDAO machineDAO = new MachineDAO(new DbHelper(context));
        return machineDAO.delete(machine);
    }

    @Override
    protected void onPostExecute(Integer count) {
        if(count > 0){
            List<Machine> machineList = adapter.getMachineList();
            int index = machineList.indexOf(machine);
            machineList.remove(index);
            adapter.notifyItemRemoved(index);
        }
    }
}
