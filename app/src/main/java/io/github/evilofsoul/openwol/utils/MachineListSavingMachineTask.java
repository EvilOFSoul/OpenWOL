package io.github.evilofsoul.openwol.utils;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

import io.github.evilofsoul.openwol.core.Machine;
import io.github.evilofsoul.openwol.core.dao.DbHelper;
import io.github.evilofsoul.openwol.core.dao.MachineDAO;

/**
 * Created by Yevhenii on 27.09.2016.
 */

public class MachineListSavingMachineTask extends AsyncTask<Object,Object,Machine> {
    Machine machine;
    MachineListAdapter adapter;
    Context context;
    Comparator<Machine> machineComparator;

    public MachineListSavingMachineTask(Context context, MachineListAdapter adapter, Machine machine) {
        this.machine = machine;
        this.adapter = adapter;
        this.context = context;
    }

    public MachineListSavingMachineTask(Context context, MachineListAdapter adapter, Machine machine,
                                        Comparator<Machine> machineComparator) {
        this.machine = machine;
        this.adapter = adapter;
        this.context = context;
        this.machineComparator = machineComparator;
    }

    @Override
    protected Machine doInBackground(Object... objects) {
        final MachineDAO machineDAO = new MachineDAO(new DbHelper(context));
        if(machine.getId() == -1){
            int id = machineDAO.insert(machine);
            if(id != -1){
                machine = new Machine(
                        id,
                        machine.getName(),
                        machine.getMac(),
                        machine.getIp(),
                        machine.getPort()
                );
            }
        } else {
            machineDAO.update(machine);
        }
        return machine;
    }

    @Override
    protected void onPostExecute(Machine machine) {
        final List<Machine> machineList = adapter.getMachineList();
        int index = -1;
        final ListIterator<Machine> iterator = machineList.listIterator();
        while(iterator.hasNext()){
            Machine item = iterator.next();
            if(item.getId() == machine.getId()){
                index = iterator.previousIndex();
                break;
            }
        }

        if(index != -1){
            machineList.set(index,machine);
            adapter.notifyItemChanged(index);
        } else {
            machineList.add(machine);
            if(machineComparator != null){
                Collections.sort(machineList,machineComparator);
                index = machineList.indexOf(machine);
            } else {
                index = machineList.size()-1;
            }
            adapter.notifyItemInserted(index);
        }
    }
}
