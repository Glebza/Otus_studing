package com.otus.study.hw9.jdbc.handlers;

import com.otus.study.hw9.jdbc.user.DataSet;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Glebza on 29.07.2017.
 */
public interface TResultHandler<T extends DataSet> {
    T handle(ResultSet resultSet,Class<T> clazz) throws SQLException;


}
