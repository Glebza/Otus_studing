package com.otus.study.hw9.jdbc.services;



import com.otus.study.hw9.jdbc.user.DataSet;

import java.util.List;

/**
 * Created by Glebza on 02.08.2017.
 */
public interface DataBaseService {

    public String getLocalStatus();
    public <T extends DataSet> void save(T dataSet);
    public <T extends DataSet>T read(long id, Class<T> dataSetType);
    public <T extends  DataSet>T readByParam(String param, String value, Class<T> tClass);
    public <T extends DataSet> List<T> readAll(Class<T> tClass);
    public void shutdown();
    

}
