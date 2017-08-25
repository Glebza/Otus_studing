package com.otus.study.hw11.cache.jdbc.dao;

import com.otus.study.hw11.cache.cachemodule.CacheWrapper;
import com.otus.study.hw11.cache.jdbc.executor.PostgresExecutor;
import com.otus.study.hw11.cache.jdbc.handlers.TResultHandler;
import com.otus.study.hw11.cache.jdbc.handlers.impl.ExtendHandler;
import com.otus.study.hw11.cache.jdbc.user.DataSet;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * Created by Glebza on 29.07.2017.
 */
public class UserDao<T extends DataSet> {
    private static Logger logger = Logger.getLogger(UserDao.class);
    private PostgresExecutor postgresExecutor;

    protected CacheWrapper<Long, T> cache;

    public void setCache(final CacheWrapper<Long, T> cache) {
        this.cache = cache;
    }

    public  T read(long id, Class<T> clazz) {

        T userDataSet = null;

        if ((userDataSet = (T) cache.get(id)) == null) {
            try {
                TResultHandler userHandlerTResultHandler = new ExtendHandler();

                userDataSet = (T) postgresExecutor.execQuery("Select * from \"user\" where id = " + id, userHandlerTResultHandler,clazz);
            } catch (SQLException e) {
                logger.error(e);
            }
            if (userDataSet  != null) {
                cache.put(id, userDataSet );
            }
        }



        return userDataSet;
    }




    public  void save(T userDataSet) {
        try {
            logger.info(userDataSet);
            postgresExecutor.execInsert(userDataSet);


        } catch (IllegalAccessException e) {
            logger.error(e);
        }

    }

    public PostgresExecutor getPostgresExecutor() {
        return postgresExecutor;
    }

    public void setPostgresExecutor(PostgresExecutor postgresExecutor) {
        this.postgresExecutor = postgresExecutor;
    }
}
