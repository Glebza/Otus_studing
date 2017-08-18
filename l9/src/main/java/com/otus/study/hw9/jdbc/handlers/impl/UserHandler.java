package com.otus.study.hw9.jdbc.handlers.impl;

import com.otus.study.hw9.jdbc.handlers.TResultHandler;
import com.otus.study.hw9.jdbc.user.DataSet;
import com.otus.study.hw9.jdbc.user.UserDataSet;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Glebza on 29.07.2017.
 */
public class UserHandler implements TResultHandler {


    @Override
    public DataSet handle(ResultSet resultSet,Class clazz) throws SQLException {
        resultSet.next();
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        DataSet userDataSet = new UserDataSet(id, name, age);

        return userDataSet;

    }



}
