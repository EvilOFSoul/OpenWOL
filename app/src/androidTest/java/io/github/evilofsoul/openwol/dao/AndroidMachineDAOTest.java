package io.github.evilofsoul.openwol.dao;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;

import io.github.evilofsoul.openwol.core.MacAddress;
import io.github.evilofsoul.openwol.core.Machine;
import io.github.evilofsoul.openwol.core.dao.DbHelper;
import io.github.evilofsoul.openwol.core.dao.MachineDAO;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


/**
 * Created by Yevhenii on 20.09.2016.
 */
@RunWith(AndroidJUnit4.class)
public class AndroidMachineDAOTest {
    List<Machine> testMachineList = new ArrayList<>();
    MachineDAO machineDAO;

    public AndroidMachineDAOTest() {
        MacAddress macAddress= MacAddress.create("02:92:7A:75:EC:E3");
        Machine machine1 = new Machine();
        machine1.setName("Machine 1");
        machine1.setMac(macAddress);
        testMachineList.add(machine1);

        Machine machine2 = new Machine();
        machine2.setName("Machine 2");
        machine2.setMac(macAddress);
        machine2.setIp("192.168.0.1");
        testMachineList.add(machine2);

        Machine machine3 = new Machine();
        machine3.setName("Machine 3");
        machine3.setMac(macAddress);
        machine3.setPort(7);
        testMachineList.add(machine3);

        Machine machine4 = new Machine();
        machine4.setName("Machine 4");
        machine4.setMac(macAddress);
        machine2.setIp("192.168.0.2");
        machine4.setPort(9);
        testMachineList.add(machine4);
    }

    @Before
    public void setUp() {
        DbHelper dbHelper = new DbHelper(InstrumentationRegistry.getTargetContext());
        machineDAO = new MachineDAO(dbHelper);
    }

    @After
    public void tearDown(){
        InstrumentationRegistry.getTargetContext().deleteDatabase(DbHelper.DATABASE_NAME);
    }

    @Test
    public void getById_ValidId_Machine(){
        Machine machine = testMachineList.get(0);
        final int id = machineDAO.insert(machine);

        Machine machineFormDb = machineDAO.getById(id);
        assertNotNull(machineFormDb);
        assertThat(machineFormDb.getName(), is(machine.getName()));
        assertThat(machineFormDb.getMac().toString(), is(machine.getMac().toString()));
    }

    @Test
    public void getAll(){
        machineDAO.insertAll(testMachineList);
        List<Machine> machineListFormDb = machineDAO.getAll();
        assertThat(machineListFormDb, hasSize(testMachineList.size()));
    }

    @Test
    public void insert_Machine_Id(){
        Machine machine = testMachineList.get(0);
        final int id = machineDAO.insert(machine);
        assertThat(id,is(not(-1)));
    }

    @Test
    public void insertAll_MachineList_IdList(){
        final List<Integer> ids = machineDAO.insertAll(testMachineList);
        assertThat(ids,is(not(empty())));
        assertThat(ids,hasSize(testMachineList.size()));
    }

    @Test
    public void update_Machine_UpdatedCount(){
        Machine machine = testMachineList.get(0);
        final int id = machineDAO.insert(machine);
        machine = machineDAO.getById(id);

        String newMachineName = "Cool down";
        machine.setName(newMachineName);
        final int updatedCount = machineDAO.update(machine);
        assertThat(updatedCount,is(1));

        machine = machineDAO.getById(id);
        assertThat(machine.getName(),equalTo(newMachineName));
    }

    @Test
    public void  updateAll_MachineList_UpdatedCount(){
        machineDAO.insertAll(testMachineList);
        List<Machine> newMachineList = machineDAO.getAll();

        int updatedCount = machineDAO.updateAll(newMachineList);
        assertThat(updatedCount,is(testMachineList.size()));
    }

    @Test
    public void delete_Machine_DeletedCount(){
        final int id = machineDAO.insert(testMachineList.get(0));
        Machine machineFormDb = machineDAO.getById(id);
        final int deletedCount = machineDAO.delete(machineFormDb);
        assertThat(deletedCount, is(1));
    }

    @Test
    public void deleteAll_MachineList_DeletedCount(){
        machineDAO.insertAll(testMachineList);
        List<Machine> machinesFormDb = machineDAO.getAll();
        int deletedCount = machineDAO.deleteAll(machinesFormDb);
        assertThat(deletedCount, is(testMachineList.size()));
        assertThat(machineDAO.getAll().size(), is(0));

        machineDAO.insertAll(testMachineList);
        machinesFormDb = machineDAO.getAll();
        machinesFormDb.remove(machinesFormDb.get(0));
        deletedCount = machineDAO.deleteAll(machinesFormDb);
        assertThat(deletedCount, is(machinesFormDb.size()));
        assertThat(machineDAO.getAll().size(), is(1));
    }
}
