package com.otus.study.hw9.jdbc.user;

import com.otus.study.hw9.jdbc.executor.PostgresExecutor;
import com.otus.study.hw9.jdbc.handlers.TResultHandler;
import com.otus.study.hw9.jdbc.handlers.impl.ExtendHandler;
import com.otus.study.hw9.jdbc.handlers.impl.UserHandler;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Glebza on 29.07.2017.
 */
public class UserDao {
    private static Logger logger = Logger.getLogger(UserDao.class);
    private PostgresExecutor postgresExecutor;


    public <T extends DataSet> T read(long id, Class<T> clazz) {

        T userDataSet = null;


        try {
            TResultHandler userHandlerTResultHandler = new ExtendHandler();

            userDataSet = (T) postgresExecutor.execQuery("Select * from \"user\" where id = " + id, userHandlerTResultHandler,clazz);
        } catch (SQLException e) {
            logger.error(e);
        }


        return userDataSet;
    }




    public <T extends DataSet> void save(T userDataSet) {
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
