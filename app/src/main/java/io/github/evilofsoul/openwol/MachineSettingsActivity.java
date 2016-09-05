package io.github.evilofsoul.openwol;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import io.github.evilofsoul.openwol.core.MacAddress;
import io.github.evilofsoul.openwol.core.Machine;

public class MachineSettingsActivity extends AppCompatActivity implements View.OnClickListener {
    Machine machine;
    EditText machineName;
    EditText machineMac;
    EditText machineIp;
    EditText machinePort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_settings);

        this.initComponent();

        Intent intent = this.getIntent();
        machine = (Machine) intent.getSerializableExtra("Machine");
        if(machine != null){
            this.machineName.setText(machine.getName());
            this.machineMac.setText(machine.getMac().toString());
            this.machineIp.setText(machine.getIp());
            this.machinePort.setText(Integer.toString(machine.getPort()));
        } else {
            this.machine = new Machine();
        }
    }

    private void initComponent(){
        machineName = (EditText) findViewById(R.id.machine_name);
        machineMac = (EditText) findViewById(R.id.machine_mac);
        machineIp = (EditText) findViewById(R.id.machine_ip);
        machinePort = (EditText) findViewById(R.id.machine_port);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_black_arrow_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.fab2){
            EditText machineName = (EditText) findViewById(R.id.machine_name);
            EditText machineMac = (EditText) findViewById(R.id.machine_mac);
            EditText machineIp = (EditText) findViewById(R.id.machine_ip);
            EditText machinePort = (EditText) findViewById(R.id.machine_port);

            this.machine.setName(machineName.getText().toString());
            this.machine.setIp(machineIp.getText().toString());
            this.machine.setPort(Integer.parseInt(machinePort.getText().toString()));
            MacAddress macAddress = new MacAddress();
            macAddress.set(machineMac.getText().toString());
            this.machine.setMac(macAddress);

            Intent intent = new Intent();
            intent.putExtra("Machine", this.machine);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
