package io.github.evilofsoul.openwol;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.View;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import io.github.evilofsoul.openwol.core.Machine;
import io.github.evilofsoul.openwol.core.TargetWOL;
import io.github.evilofsoul.openwol.core.WOL;
import io.github.evilofsoul.openwol.core.dao.DbHelper;
import io.github.evilofsoul.openwol.core.dao.MachineDAO;

public class MainActivity extends AppCompatActivity
        implements MachineListAdapter.MachineListOnItemClickListener {

    private List<Machine> machineList;
    private RecyclerView recyclerView;
    private MachineListAdapter adapter;
    private final static int MACHINE_SETTINGS_ACTIVITY_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initComponent();
    }

    private void initComponent(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMachineActivity.class);
                startActivityForResult(intent,MACHINE_SETTINGS_ACTIVITY_CODE);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .positionInsideItem(true)
                .drawable(R.drawable.line_divider)
                .build());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createItemTouchCallback(this));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        this.adapter = new MachineListAdapter();
        this.adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(this.adapter);

        MachineListLoader machineListLoader = new MachineListLoader(this, this.adapter);
        machineListLoader.execute();
    }

    private ItemTouchHelper.Callback createItemTouchCallback(final Context context){
        ItemTouchHelper.SimpleCallback itemTouchCallback =
            new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                      RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                    deleteItem(viewHolder.getAdapterPosition());
                    int index = viewHolder.getAdapterPosition();
                    List<Machine> machineList = adapter.getMachineList();
                    DeleteMachineTask deleteMachineTask = new DeleteMachineTask(
                            context,
                            adapter,
                            machineList.get(index)
                    );
                    deleteMachineTask.execute();
                }
            };
        return itemTouchCallback;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MACHINE_SETTINGS_ACTIVITY_CODE){
            if(resultCode != RESULT_OK){
                return;
            }
            Machine machine = (Machine) data.getSerializableExtra("Machine");
            if(machine != null){
                MachineSavingTask savingTask = new MachineSavingTask(this,adapter,machine);
                savingTask.execute();
            }
        }
    }

    @Override
    public void onSettingsButtonClick(Machine machine, int position) {
        Intent intent = new Intent(MainActivity.this, AddMachineActivity.class);
        intent.putExtra("Machine",machine);
        startActivityForResult(intent,MACHINE_SETTINGS_ACTIVITY_CODE);
    }

    @Override
    public void onItemClick(Machine machine, int position) {
        Snackbar.make(recyclerView, machine.getName(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
//        MachineWakeUpTask wakeUpTask = new MachineWakeUpTask(machine);
//        wakeUpTask.execute();
    }

    private class MachineListLoader extends AsyncTask<Object,Object,List<Machine>>{
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

    private class MachineSavingTask extends AsyncTask<Object,Object,Machine>{
        Machine machine;
        MachineListAdapter adapter;
        Context context;

        public MachineSavingTask(Context context, MachineListAdapter adapter, Machine machine) {
            this.machine = machine;
            this.adapter = adapter;
            this.context = context;
        }

        @Override
        protected Machine doInBackground(Object... objects) {
            MachineDAO machineDAO = new MachineDAO(new DbHelper(context));
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
            List<Machine> machineList = adapter.getMachineList();
            int index = -1;
            for (Machine item : machineList) {
                index++;
                if(item.getId() == machine.getId()){
                    break;
                }
            }

            if(index != -1){
                machineList.remove(index);
                machineList.add(index,machine);
                adapter.notifyItemChanged(index);
            } else {
                machineList.add(machine);
                adapter.notifyItemInserted(machineList.size()-1);
            }
        }
    }

    private class DeleteMachineTask extends AsyncTask<Object,Object,Integer>{
        Machine machine;
        MachineListAdapter adapter;
        Context context;

        public DeleteMachineTask(Context context, MachineListAdapter adapter, Machine machine) {
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

    private class MachineWakeUpTask extends AsyncTask<Object,Object,Object>{

        Machine machine;

        public MachineWakeUpTask(Machine machine) {
            this.machine = machine;
        }

        @Override
        protected Object doInBackground(Object... objects) {
            WOL wol = new TargetWOL();
            try {
                wol.wakeUp(machine);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
