package io.github.evilofsoul.openwol.core.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import io.github.evilofsoul.openwol.core.MacAddress;
import io.github.evilofsoul.openwol.core.Machine;

/**
 * Created by Yevhenii on 15.08.2016.
 */
public class MachineDAO extends GenericDAO<Machine> {
    public MachineDAO(DbHelper dbHelper) {
        super(dbHelper);
    }

    private String[] getProjection() {
        String[] projection = {
                DbContract.MachineEntry._ID,
                DbContract.MachineEntry.NAME,
                DbContract.MachineEntry.MAC,
                DbContract.MachineEntry.IP,
                DbContract.MachineEntry.PORT
        };
        return projection;
    }

    private Machine convertToMachine(Cursor query) {
        Machine machine = null;

        int machineId = query.getInt(0);
        String machineName = query.getString(1);
        MacAddress machineMac = new MacAddress();
        machineMac.set(query.getString(2));
        String machineIP = query.getString(3);
        int machinePort = query.getInt(4);

        return new Machine(
                machineId,
                machineName,
                machineMac,
                machineIP,
                machinePort
        );
    }

    @Override
    public Machine getById(int id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String condition = DbContract.MachineEntry._ID + "=?";
        String[] conditionArgs = {String.valueOf(id)};
        Cursor query = database.query(
                DbContract.MachineEntry.TABLE_NAME,
                this.getProjection(),
                condition,
                conditionArgs,
                null,
                null,
                null
        );

        Machine machine = null;
        if (query.moveToFirst()) {
            machine = this.convertToMachine(query);
        }

        query.close();
        database.close();
        return machine;
    }

    @Override
    public List<Machine> getAll() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor query = database.query(
                DbContract.MachineEntry.TABLE_NAME,
                this.getProjection(),
                null,
                null,
                null,
                null,
                null
        );

        List<Machine> machines = new ArrayList<>();
        if (query.moveToFirst()) {
            do {
                machines.add(this.convertToMachine(query));
            } while (query.moveToNext());
        }

        query.close();
        database.close();
        return machines;
    }

    @Override
    public int update(Machine element) {
        if (element.getId() == -1) {
            return -1;
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbContract.MachineEntry.NAME, element.getName());
        values.put(DbContract.MachineEntry.MAC, element.getMac().toString());
        values.put(DbContract.MachineEntry.IP, element.getIp());
        values.put(DbContract.MachineEntry.PORT, element.getPort());

        String condition = DbContract.MachineEntry._ID + "=?";
        String[] conditionArgs = {String.valueOf(element.getId())};

        int updatedMachineCount = database.update(
                DbContract.MachineEntry.TABLE_NAME,
                values,
                condition,
                conditionArgs
        );
        database.close();
        return updatedMachineCount;
    }

    @Override
    public int updateAll(List<Machine> elements) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        database.beginTransaction();
        int updatedMachineCount = 0;
        try {
            for (Machine element : elements) {  // loop through your records
                if (element.getId() == -1) {
                    continue;
                }

                ContentValues values = new ContentValues();
                values.put(DbContract.MachineEntry.NAME, element.getName());
                values.put(DbContract.MachineEntry.MAC, element.getMac().toString());
                values.put(DbContract.MachineEntry.IP, element.getIp());
                values.put(DbContract.MachineEntry.PORT, element.getPort());

                String condition = DbContract.MachineEntry._ID + "=?";
                String[] conditionArgs = {String.valueOf(element.getId())};

                updatedMachineCount += database.update(
                        DbContract.MachineEntry.TABLE_NAME,
                        values,
                        condition,
                        conditionArgs
                );
            }

            database.setTransactionSuccessful();

        } finally {
            database.endTransaction();
        }

        database.close();
        return updatedMachineCount;
    }

    @Override
    public int delete(Machine element) {
        if (element.getId() == -1) {
            return 0;
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String selection = DbContract.MachineEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(element.getId())};
        int deletedMachineCount = database.delete(
                DbContract.MachineEntry.TABLE_NAME,
                selection,
                selectionArgs
        );

        database.close();
        return  deletedMachineCount;
    }

    @Override
    public int deleteAll(List<Machine> elements) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        database.beginTransaction();
        int deletedMachineCount = 0;
        try {
            for (Machine element : elements) {  // loop through your records
                if (element.getId() == -1) {
                    continue;
                }

                String selection = DbContract.MachineEntry._ID + " = ?";
                String[] selectionArgs = {String.valueOf(element.getId())};
                deletedMachineCount += database.delete(
                        DbContract.MachineEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }

        database.close();
        return deletedMachineCount;
    }

    @Override
    public int insert(Machine element) {
        if (element.getId() != -1) {
            return -1;
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbContract.MachineEntry.NAME, element.getName());
        values.put(DbContract.MachineEntry.MAC, element.getMac().toString());
        values.put(DbContract.MachineEntry.IP, element.getIp());
        values.put(DbContract.MachineEntry.PORT, element.getPort());

        long machineId = database.insert(
                DbContract.MachineEntry.TABLE_NAME,
                null,
                values);

        database.close();

        return ((int) machineId);
    }

    @Override
    public List<Integer> insertAll(List<Machine> elements) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        database.beginTransaction();
        List<Integer> newMachineIds = new ArrayList<>();
        try {
            for (Machine element : elements) {  // loop through your records
                if (element.getId() != -1) {
                    continue;
                }

                ContentValues values = new ContentValues();
                values.put(DbContract.MachineEntry.NAME, element.getName());
                values.put(DbContract.MachineEntry.MAC, element.getMac().toString());
                values.put(DbContract.MachineEntry.IP, element.getIp());
                values.put(DbContract.MachineEntry.PORT, element.getPort());
                int id = (int) database.insert(
                        DbContract.MachineEntry.TABLE_NAME,
                        null,
                        values);
                newMachineIds.add(id);
            }

            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }

        database.close();
        return newMachineIds;
    }
}
