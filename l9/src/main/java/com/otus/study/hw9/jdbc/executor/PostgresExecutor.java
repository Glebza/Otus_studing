package com.otus.study.hw9.jdbc.executor;

import com.otus.study.hw9.jdbc.handlers.TResultHandler;
import com.otus.study.hw9.jdbc.user.DataSet;
import org.apache.log4j.Logger;
import org.postgresql.ds.PGPoolingDataSource;

import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.*;

/**
 * Created by Glebza on 27.07.2017.
 */
public class PostgresExecutor {
    private static Logger logger = Logger.getLogger(PostgresExecutor.class);
    private PGPoolingDataSource dataSource;


    public Connection getConnection() {
        Connection connection = null;


        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public <T extends DataSet> T execQuery(String query, TResultHandler<T> handler,Class<T> clazz) throws SQLException {
        Statement stmt = dataSource.getConnection().createStatement();
        stmt.execute(query);
        ResultSet result = stmt.getResultSet();
        T value = handler.handle(result,clazz);
        result.close();
        stmt.close();

        return value;
    }

    public <T> void execInsert(T clazz) throws IllegalAccessException {
        String insertQuery = prepareInsertString(clazz);
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(insertQuery)) {
            Field[] fields = clazz.getClass().getDeclaredFields();
            int queryPos = 1;
            for  (int position = 0;position < fields.length;position++) {
                    String typeName = fields[position].getGenericType().getTypeName().toLowerCase();
                    logger.debug(typeName);
                if (fields[position].getAnnotation(GeneratedValue.class) == null) {

                    switch (typeName) {
                        case "long": {
                            boolean isAccesible = fields[position].isAccessible();
                            fields[position].setAccessible(true);
                            Long value = fields[position].getLong(clazz);
                            preparedStatement.setLong(queryPos, value );
                            fields[position].setAccessible(isAccesible ? true : false);
                            break;
                        }
                        case "java.lang.string": {
                            boolean isAccessible = fields[position].isAccessible();
                            fields[position].setAccessible(true);
                            preparedStatement.setString(queryPos, String.valueOf(fields[position].get(clazz)));
                            fields[position].setAccessible(isAccessible ? true : false);
                            break;
                        }
                        case "int": {
                            boolean isAccesible = fields[position].isAccessible();
                            fields[position].setAccessible(true);
                            preparedStatement.setInt(queryPos, fields[position].getInt(clazz));
                            fields[position].setAccessible(isAccesible ? true : false);
                            break;
                        }
                    }
                    queryPos++;
                }
            }
            logger.info(preparedStatement.toString());
            preparedStatement.execute();
        } catch (SQLException e) {
           logger.error(e);
        }


    }

    private <T> String prepareInsertString(T clazz) {
        String tableName = clazz.getClass().getAnnotation(Table.class).name();
        String request = "INSERT INTO \"" + tableName + "\" (";
        String quotas = "(";
        for (Field field : clazz.getClass().getDeclaredFields()) {

            if (field.getAnnotation(GeneratedValue.class) == null) {

                request += field.getName() + ",";
                quotas += "?,";
            }
        }
        request = request.substring(0, request.length() - 1) + ") values";
        quotas = quotas.substring(0, quotas.length() - 1) + ")";
        request += quotas;
        return request;
    }

    public PGPoolingDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(PGPoolingDataSource dataSource) {
        this.dataSource = dataSource;
    }


}
