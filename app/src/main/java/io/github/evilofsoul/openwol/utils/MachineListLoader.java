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

public class MachineListLoader extends AsyncTask<Object,Object,List<Machine>> {
    MachineListAdapter adapter;
    Context context;

    public MachineListLoader(Context context, MachineListAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected List<Machine> doInBackground(Object... objects) {
        MachineDAO machineDAO = new MachineDAO(new DbHelper(context));
        return machineDAO.getAll();
    }

    @Override
    protected void onPostExecute(List<Machine> machines) {
        adapter.setMachineList(machines);
    }
}
