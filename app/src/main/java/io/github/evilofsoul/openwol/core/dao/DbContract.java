package io.github.evilofsoul.openwol.core.dao;

import android.provider.BaseColumns;

import io.github.evilofsoul.openwol.core.MacAddress;

/**
 * Created by Yevhenii on 15.08.2016.
 */
public final class DbContract {
    public DbContract() {}

    public static abstract class MachineEntry implements BaseColumns {
        public static final String TABLE_NAME = "machines";
        public static final String MAC = "mac";
        public static final String NAME = "name";
        public static final String IP = "ip";
        public static final String PORT = "port";
    }
}
