package io.github.evilofsoul.openwol.utils;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.github.evilofsoul.openwol.core.Machine;
import io.github.evilofsoul.openwol.core.dao.DbHelper;
import io.github.evilofsoul.openwol.core.dao.MachineDAO;

/**
 * Created by Yevhenii on 27.09.2016.
 */

public class MachineListLoader extends AsyncTask<Object,Object,List<Machine>> {
    WeakReference<MachineListAdapter> adapterWeakReference;
    WeakReference<Context> contextWeakReference;
    Comparator<Machine> machineComparator;

    public MachineListLoader(Context context, MachineListAdapter adapter) {
        this.contextWeakReference = new WeakReference<>(context);
        this.adapterWeakReference = new WeakReference<>(adapter);
    }

    public MachineListLoader(Context context, MachineListAdapter adapter,
                             Comparator<Machine> machineComparator) {

        this.contextWeakReference = new WeakReference<>(context);
        this.adapterWeakReference = new WeakReference<>(adapter);
        this.machineComparator = machineComparator;
    }

    @Override
    protected List<Machine> doInBackground(Object... objects) {
        Context context = contextWeakReference.get();
        if(context == null){
            return  null;
        }
        MachineDAO machineDAO = new MachineDAO(new DbHelper(context));
        return machineDAO.getAll();
    }

    @Override
    protected void onPostExecute(List<Machine> machines) {
        MachineListAdapter adapter = adapterWeakReference.get();
        if(adapter != null) {
            if(machineComparator != null) {
                Collections.sort(machines, new MachineNameComparator());
            }
            adapter.setMachineList(machines);
        }
    }
}
