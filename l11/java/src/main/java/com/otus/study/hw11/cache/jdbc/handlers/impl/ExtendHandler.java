package com.otus.study.hw11.cache.jdbc.handlers.impl;

import com.otus.study.hw11.cache.jdbc.handlers.TResultHandler;
import com.otus.study.hw11.cache.jdbc.user.DataSet;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Glebza on 31.07.2017.
 */
public class ExtendHandler implements TResultHandler {
    Logger logger = Logger.getLogger(ExtendHandler.class);

    @Override
    public DataSet handle(ResultSet resultSet, Class clazz) throws SQLException {
        DataSet dataSet = null;
        resultSet.next();
        try {
            try {
                dataSet = (DataSet) clazz.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException e) {
                logger.error(e);
            } catch (InvocationTargetException e) {
                logger.error(e);
            }
        } catch (InstantiationException e) {
            logger.error(e);
        } catch (IllegalAccessException e) {
            logger.error(e);
        }

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        Map<String, Field> classFields = new HashMap<>();
        getAllFields(classFields, dataSet.getClass());
        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
            String columnName = resultSetMetaData.getColumnName(i).toLowerCase();
            Field field = classFields.get(columnName);
            if (field != null) {
                String value = resultSet.getString(i);
                setValueOnType(dataSet, field, value);

            }

        }


        return dataSet;
    }

    private <T extends DataSet> void setValueOnType(T clazz, Field field, String value) {
        try {
            String typeName = field.getType().getTypeName().toLowerCase();
            switch (typeName) {
                case "java.lang.long": {
                    boolean isAccesible = field.isAccessible();
                    field.setAccessible(true);
                    field.set(clazz, Long.parseLong(value));
                    field.setAccessible(isAccesible ? true : false);
                    break;
                }
                case "java.lang.string": {
                    boolean isAccesible = field.isAccessible();
                    field.setAccessible(true);
                    field.set(clazz, value);
                    field.setAccessible(isAccesible ? true : false);
                    break;
                }
                case "int": {
                    boolean isAccesible = field.isAccessible();
                    field.setAccessible(true);
                    field.setInt(clazz, Integer.parseInt(value));
                    ;
                    field.setAccessible(isAccesible ? true : false);
                    break;
                }
            }

        } catch (IllegalAccessException e) {
            logger.error(e);
        }

    }

    public Map<String, Field> getAllFields(Map<String, Field> fields, Class<?> type) {
        for (Field field : type.getDeclaredFields()) {
            String fieldName = field.getName().toLowerCase();
            fields.put(fieldName, field);
        }

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }


}
