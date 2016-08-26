package io.github.evilofsoul.openwol.core.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Yevhenii on 15.08.2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "open_wol.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        List<String> queries = this.getCreateQueries();
        ListIterator<String> iterator = queries.listIterator();
        while (iterator.hasNext()){
            sqLiteDatabase.execSQL(iterator.next());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    private List<String> getCreateQueries(){
        List<String> queries = new ArrayList<>();
        String query = "CREATE TABLE "+DbContract.MachineEntry.TABLE_NAME+" ("+
                DbContract.MachineEntry._ID+" INTEGER PRIMARY KEY,"+
                DbContract.MachineEntry.NAME+" VARCHAR(30),"+
                DbContract.MachineEntry.MAC+" VARCHAR(17),"+
                DbContract.MachineEntry.IP+" VARCHAR(39),"+
                DbContract.MachineEntry.PORT+" INTEGER"+
                ")";
        queries.add(query);
        return  queries;
    }
}
