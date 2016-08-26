package io.github.evilofsoul.openwol.core.dao;

import java.util.List;

/**
 * Created by Yevhenii on 15.08.2016.
 */
public abstract class GenericDAO <Type extends Object> {
    protected DbHelper dbHelper;

    public GenericDAO(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public abstract Type getById(int id);
    public abstract List<Type> getAll();
    public abstract int update(Type element);
    public abstract int updateAll(List<Type> elements);
    public abstract int delete(Type element);
    public abstract int deleteAll(List<Type> elements);
    public abstract int insert(Type element);
    public abstract List<Integer> insertAll(List<Type> elements);
}
