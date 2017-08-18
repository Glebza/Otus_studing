package com.otus.study.hw9;

import com.otus.study.hw9.jdbc.services.impl.DBServiceImpl;
import com.otus.study.hw9.jdbc.user.UserDataSet;
import com.otus.study.hw9.jdbc.executor.PostgresExecutor;
import com.otus.study.hw9.jdbc.user.UserDao;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.ds.PGPoolingDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Glebza on 28.07.2017.
 */
public class JDBCTest {
    private static Logger logger = Logger.getLogger(JDBCTest.class);
    public static final String USER_NAME = "Johny";
    public static long userIdentifier = 1;
    private PGPoolingDataSource dataSource;
    private PostgresExecutor postgresExecutor;
    private String testTableName = "user";
    private int dbPort = 5432;
    private String dbHost = "localhost";
    private String dbUser = "postgres";
    private String dbPassword = "Orion123";
    private String dbName = "postgres";
    private UserDao userDao;
    private DBServiceImpl dbService;

    @Before
    public void init() {
        logger.info("in init");
        postgresExecutor = new PostgresExecutor();
        dataSource = new PGPoolingDataSource();
        dataSource.setServerName(dbHost);
        dataSource.setDatabaseName(dbName);
        dataSource.setPortNumber(dbPort);
        dataSource.setUser(dbUser);
        dataSource.setPassword(dbPassword);
        postgresExecutor.setDataSource(dataSource);
        userDao = new UserDao();
        userDao.setPostgresExecutor(postgresExecutor);
        this.dbService = new DBServiceImpl(userDao);
        try (Statement statement = dataSource.getConnection().createStatement()) {

            statement.execute("CREATE TABLE \"user\"  (id BIGSERIAL primary key not null ,name varchar(100),age integer not null default 0)");
        } catch (SQLException e) {
            logger.error(e);
        }


    }

    @Test
    public void testUserSave() {

        UserDataSet userDataSet = new UserDataSet(USER_NAME, 23);
        UserDataSet userDataSet2 = new UserDataSet(USER_NAME, 23);

        dbService.save(userDataSet);
        dbService.save(userDataSet2);
        String actualName = null;
        actualName = getActualName(userIdentifier);
        Assert.assertEquals(USER_NAME, actualName);


    }

    private String getActualName(long id) {
        String actualName = null;
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement("select name from \"" + testTableName + "\" where id=?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                actualName = resultSet.getString("name");
            }


        } catch (SQLException e) {
            logger.error(e);
        }
        userIdentifier++;
        return actualName;
    }

    @Test
    public void testUserLoad() {
        UserDataSet testUserDataSet = new UserDataSet(userIdentifier, USER_NAME, 23);
        UserDataSet userDataSet = null;
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement("insert into \"" + testTableName + "\"(name,age) values (?,?)")) {

            statement.setString(1, USER_NAME);
            statement.setInt(2, 23);
            int resultSet = statement.executeUpdate();


            userDataSet = dbService.read(userIdentifier, UserDataSet.class);

        } catch (SQLException e) {
            logger.error(e);
        }
        userIdentifier++;
        Assert.assertEquals(String.valueOf(testUserDataSet.getName()), String.valueOf(userDataSet.getName()));
        Assert.assertEquals(String.valueOf(testUserDataSet.getAge()), String.valueOf(userDataSet.getAge()));
        Assert.assertEquals(String.valueOf(testUserDataSet.getId()), String.valueOf(userDataSet.getId()));
    }


    @After
    public void clearDataBase() {
        try (Statement statement = dataSource.getConnection().createStatement()) {


            boolean isSuccessful = statement.execute("drop table \"" + testTableName + "\"");


        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
