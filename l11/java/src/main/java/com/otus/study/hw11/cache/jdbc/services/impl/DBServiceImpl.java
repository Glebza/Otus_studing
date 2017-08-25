package com.otus.study.hw11.cache.jdbc.services.impl;


import com.otus.study.hw11.cache.jdbc.services.DataBaseService;
import com.otus.study.hw11.cache.jdbc.user.DataSet;
import com.otus.study.hw11.cache.jdbc.dao.UserDao;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Glebza on 04.08.2017.
 */
public class DBServiceImpl implements DataBaseService {
    private static Logger logger = Logger.getLogger(DBServiceImpl.class);
    private UserDao userDao;

    public DBServiceImpl(UserDao userDao) {
        this.userDao = userDao;

    }


    @Override
    public String getLocalStatus() {
        return userDao.getPostgresExecutor().getConnection().toString();
    }

    @Override
    public <T extends DataSet> void save(T dataSet) {

        userDao.save(dataSet);


    }

    @Override
    public <T extends DataSet> T read(long id, Class<T> dataSetType) {

        return (T) userDao.read(id, dataSetType);

    }

    @Override
    public <T extends DataSet> T readByParam(String name, String value, Class<T> tClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends DataSet> List<T> readAll(Class<T> tClass) {
        throw new UnsupportedOperationException();
    }

    public void shutdown() {
        try {
            userDao.getPostgresExecutor().getConnection().close();
        } catch (SQLException e) {
           logger.error(e);
        }
    }







}
