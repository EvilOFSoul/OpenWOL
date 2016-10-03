package io.github.evilofsoul.openwol.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import io.github.evilofsoul.openwol.utils.MachineListDeleteMachineTask;
import io.github.evilofsoul.openwol.utils.MachineListAdapter;
import io.github.evilofsoul.openwol.R;
import io.github.evilofsoul.openwol.core.Machine;
import io.github.evilofsoul.openwol.utils.MachineListLoader;
import io.github.evilofsoul.openwol.utils.MachineNameComparator;
import io.github.evilofsoul.openwol.utils.QuickReturnScrollListener;
import io.github.evilofsoul.openwol.utils.MachineListSavingMachineTask;
import io.github.evilofsoul.openwol.utils.WakingUpMachineTask;

public class MainActivity extends AppCompatActivity
        implements MachineListAdapter.OnItemClickListener {

    private RecyclerView machineListView;
    private MachineListAdapter adapter;
    private FloatingActionButton fab;
    private final static int MACHINE_SETTINGS_ACTIVITY_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initComponents();
    }

    private void initComponents(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.simple_grow);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.startAnimation(animation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MachineSettingsActivity.class);
                startActivityForResult(intent,MACHINE_SETTINGS_ACTIVITY_CODE);
            }
        });

        initMachineListView();

        MachineListLoader machineListLoader = new MachineListLoader(this, this.adapter,new MachineNameComparator());
        machineListLoader.execute();
    }

    private void initMachineListView(){

        machineListView = (RecyclerView) findViewById(R.id.machine_list);
        machineListView.setHasFixedSize(true);
        machineListView.setLayoutManager(new LinearLayoutManager(this));
        machineListView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .positionInsideItem(true)
                .drawable(R.drawable.line_divider)
                .build());
        machineListView.addOnScrollListener(new QuickReturnScrollListener() {
            @Override
            public void show() {
                fab.animate()
                        .translationY(0)
                        .setInterpolator(new DecelerateInterpolator(2))
                        .start();
            }

            @Override
            public void hide() {
                fab.animate()
                        .translationY(
                                fab.getHeight() + getResources().getDimension(R.dimen.default_margin)
                        )
                        .setInterpolator(new AccelerateInterpolator(2))
                        .start();
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createItemTouchCallback(this));
        itemTouchHelper.attachToRecyclerView(machineListView);

        this.adapter = new MachineListAdapter();
        this.adapter.setOnItemClickListener(this);
        machineListView.setAdapter(this.adapter);
    }

    private ItemTouchHelper.Callback createItemTouchCallback(final Context context){
        ItemTouchHelper.SimpleCallback itemTouchCallback =
            new ItemTouchHelper.SimpleCallback(0,
                    ItemTouchHelper.LEFT) {

                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                      RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                    int index = viewHolder.getAdapterPosition();
                    List<Machine> machineList = adapter.getMachineList();
                    MachineListDeleteMachineTask deleteMachineTask = new MachineListDeleteMachineTask(
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MACHINE_SETTINGS_ACTIVITY_CODE){
            if(resultCode != RESULT_OK){
                return;
            }
            Machine machine = data.getParcelableExtra("Machine");
            if(machine != null){
                MachineListSavingMachineTask savingTask = new MachineListSavingMachineTask(this,
                        adapter,machine, new MachineNameComparator());
                savingTask.execute();
            }
        }
    }

    @Override
    public void onSettingsButtonClick(Machine machine, int position) {
        Intent intent = new Intent(MainActivity.this, MachineSettingsActivity.class);
        intent.putExtra("Machine",machine);
        startActivityForResult(intent,MACHINE_SETTINGS_ACTIVITY_CODE);
    }

    @Override
    public void onItemClick(Machine machine, int position) {
        WakingUpMachineTask wakingUpMachineTask = new WakingUpMachineTask(machine);
        wakingUpMachineTask.execute();
        String msg = getString(R.string.main_wake_up, machine.getName());
        Snackbar.make(machineListView, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
