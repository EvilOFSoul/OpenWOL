package io.github.evilofsoul.openwol.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import io.github.evilofsoul.openwol.R;
import io.github.evilofsoul.openwol.core.MacAddress;
import io.github.evilofsoul.openwol.core.Machine;
import io.github.evilofsoul.openwol.utils.validators.IpTextValidator;
import io.github.evilofsoul.openwol.utils.MacInputFilter;
import io.github.evilofsoul.openwol.utils.validators.MacTextValidator;
import io.github.evilofsoul.openwol.utils.validators.PortTextValidator;
import io.github.evilofsoul.openwol.utils.validators.RequiredFieldTextValidator;
import io.github.evilofsoul.openwol.utils.SimpleMaskTextWatcher;
import io.github.evilofsoul.openwol.utils.validators.TextValidator;

public class MachineSettingsActivity extends AppCompatActivity implements View.OnClickListener {
    Machine machine;
    EditText machineName;
    EditText machineMac;
    EditText machineIp;
    EditText machinePort;
    
    List<TextValidator> validatorList = new ArrayList<>();

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
            if(machine.getPort() != 0) {
                this.machinePort.setText(Integer.toString(machine.getPort()));
            }
        } else {
            this.machine = new Machine();
        }
    }

    private void initComponent(){
        machineName = (EditText) findViewById(R.id.machine_name);
        machineMac = (EditText) findViewById(R.id.machine_mac);
        machineIp = (EditText) findViewById(R.id.machine_ip);
        machinePort = (EditText) findViewById(R.id.machine_port);

        this.initValidators();

        final String machineMacMask = "##:##:##:##:##:##";
        machineMac.addTextChangedListener(new SimpleMaskTextWatcher(machineMacMask, machineMac));
        machineMac.setFilters(new InputFilter[]{new MacInputFilter()});

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_white_arrow_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(this);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.simple_grow);
        fab.setAnimation(animation);
    }

    private void initValidators(){
        TextValidator requiredName = new RequiredFieldTextValidator(machineName);
        machineName.addTextChangedListener(requiredName);
        validatorList.add(requiredName);

        TextValidator requiredMac = new RequiredFieldTextValidator(machineMac);
        machineMac.addTextChangedListener(requiredMac);
        validatorList.add(requiredMac);

        TextValidator macValidator = new MacTextValidator(machineMac);
        machineMac.addTextChangedListener(macValidator);
        validatorList.add(macValidator);

        TextValidator ipValidator = new IpTextValidator(machineIp);
        machineIp.addTextChangedListener(ipValidator);
        validatorList.add(ipValidator);

        TextValidator portValidator = new PortTextValidator(machinePort);
        machinePort.addTextChangedListener(portValidator);
        validatorList.add(portValidator);
    }

    private boolean validateForm(){
        boolean isValid = true;
        for (TextValidator validator : validatorList) {
            if(!validator.validate()){
                isValid = false;
            }
        }
        return isValid;
    }
    
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.fab2){
            if(!validateForm()){
                return;
            }

            this.machine.setName(machineName.getText().toString());
            this.machine.setIp(machineIp.getText().toString());
            if(machinePort.getText().length() != 0){
                int port = Integer.parseInt(machinePort.getText().toString());
                this.machine.setPort(port);
            }

            MacAddress macAddress = MacAddress.create(machineMac.getText().toString());
            this.machine.setMac(macAddress);

            Intent intent = new Intent();
            intent.putExtra("Machine", this.machine);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
