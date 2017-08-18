package com.otus.studing.tests;

import com.otus.studing.lab10.datasets.impl.AddressesDataSet;
import com.otus.studing.lab10.datasets.impl.PhoneDataSet;
import com.otus.studing.lab10.datasets.impl.UserDataSet;
import com.otus.studing.lab10.datasets.services.DataBaseService;
import com.otus.studing.lab10.datasets.services.impl.DBHibernateServiceImpl;
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
 * Created by Glebza on 04.08.2017.
 */
public class DBHibernateServiceTest {

    public static final String JOHNY_STREET = "JohnyStreet";
    public static final String CAGE_STREET = "CageStreet";
    public static final String PHONE_NUMBER = "49512345";
    public static final String PHONE_NUMBER2 = "4955599";
    private static final String USER_NAME4 = "SpiderMan";
    private static Logger logger = Logger.getLogger(DBHibernateServiceTest.class);
    public static final String USER_NAME = "Johny";
    public static final String USER_NAME2 = "Johny_Cage";
    public static final String USER_NAME3 = "Harry_Potter";
    public static long userIdentifier = 1;
    public static long phoneIdentifier = 1;
    public static long addressIdentifier = 1;
    private PGPoolingDataSource dataSource;
    private String testUsersTableName = "users";
    private String testPhonesTableName = "phones";
    private String testAddressesTableName = "addresses";
    private int dbPort = 5432;
    private String dbHost = "localhost";
    private String dbUser = "postgres";
    private String dbPassword = "Orion123";
    private String dbName = "postgres";
    private DataBaseService dataBaseService;

    @Before
    public void init() {
        logger.info("in init");

        dataSource = new PGPoolingDataSource();
        dataSource.setServerName(dbHost);
        dataSource.setDatabaseName(dbName);
        dataSource.setPortNumber(dbPort);
        dataSource.setUser(dbUser);
        dataSource.setPassword(dbPassword);


        try (Statement statement = dataSource.getConnection().createStatement()) {

            statement.execute("CREATE TABLE \"" + testUsersTableName + "\"  (id BIGSERIAL primary key not null ,name varchar(100),age integer not null default 0)");
            statement.execute("CREATE TABLE \"" + testPhonesTableName + "\"  (id BIGSERIAL primary key not null ,phone_number varchar(15) ,phone_code integer, userId BIGINT REFERENCES users (id))");
            statement.execute("CREATE TABLE \"" + testAddressesTableName + "\"  (id BIGSERIAL primary key not null ,address_index integer,street varchar(200), user_id BIGINT unique, FOREIGN KEY (\"user_id\")REFERENCES users (id) )");
        } catch (SQLException e) {
            logger.error(e);
        }
        dataBaseService = new DBHibernateServiceImpl();

    }

    @Test
    public void testUserSave() {

        UserDataSet userDataSet = new UserDataSet(USER_NAME, 23);
        UserDataSet userDataSet2 = new UserDataSet(USER_NAME2, 23);
        PhoneDataSet phoneDataSet = new PhoneDataSet(7, PHONE_NUMBER);
        PhoneDataSet phoneDataSet2 = new PhoneDataSet(7, PHONE_NUMBER2);
        AddressesDataSet addressesDataSet1 = new AddressesDataSet(JOHNY_STREET, 105555);
        AddressesDataSet addressesDataSet2 = new AddressesDataSet(CAGE_STREET, 105556);
        dataBaseService.save(userDataSet);
        dataBaseService.save(userDataSet2);
        dataBaseService.save(phoneDataSet);
        dataBaseService.save(phoneDataSet2);
        dataBaseService.save(addressesDataSet1);
        dataBaseService.save(addressesDataSet2);
        String actualName = null;
        String actualStreet = null;
        String actualPhone = null;
        actualName = getActualParam("name", testUsersTableName, userIdentifier);
        actualStreet = getActualParam("street", testAddressesTableName, addressIdentifier);
        actualPhone = getActualParam("phone_number", testPhonesTableName, phoneIdentifier);
        Assert.assertEquals(USER_NAME2, actualName);
        Assert.assertEquals(JOHNY_STREET, actualStreet);
        Assert.assertEquals(PHONE_NUMBER, actualPhone);


    }

    private String getActualParam(String paramName, String tableName, long id) {
        String actualName = null;
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement("select " + paramName + " from \"" + tableName + "\" where id=?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                actualName = resultSet.getString(paramName);
            }


        } catch (SQLException e) {
            logger.error(e);
        }
        userIdentifier++;
        return actualName;
    }

    @Test
    public void testUserLoad() {
        UserDataSet testUserDataSet = new UserDataSet(userIdentifier, USER_NAME3, 23);
        UserDataSet userDataSet = null;
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement("insert into \"" + testUsersTableName + "\"(name,age) values (?,?)")) {

            statement.setString(1, USER_NAME3);
            statement.setInt(2, 23);
            int resultSet = statement.executeUpdate();



        } catch (SQLException e) {
            logger.error(e);
        }
        userDataSet = dataBaseService.read(userIdentifier, UserDataSet.class);
        userIdentifier++;
        Assert.assertEquals(String.valueOf(testUserDataSet.getName()), String.valueOf(userDataSet.getName()));
        Assert.assertEquals(String.valueOf(testUserDataSet.getAge()), String.valueOf(userDataSet.getAge()));
        Assert.assertEquals(String.valueOf(testUserDataSet.getId()), String.valueOf(userDataSet.getId()));
    }


    @Test
    public void testReadByParam(){
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement("insert into \"" + testUsersTableName + "\"(name,age) values (?,?)")) {

            statement.setString(1, USER_NAME4);
            statement.setInt(2, 23);
            int resultSet = statement.executeUpdate();



        } catch (SQLException e) {
            logger.error(e);
        }
        UserDataSet userDataSet = dataBaseService.readByParam("name",USER_NAME4,UserDataSet.class);

        Assert.assertEquals(USER_NAME4,userDataSet.getName());

    }
    @Test
    public void testGetLocalStatus() {
        String status = dataBaseService.getLocalStatus();
        Assert.assertEquals("ACTIVE", status);
    }

    @After
    public void clearDataBase() {
        try (Statement statement = dataSource.getConnection().createStatement()) {


            statement.execute("drop table \"" + testPhonesTableName + "\"");
            statement.execute("drop table \"" + testAddressesTableName + "\"");
            statement.execute("drop table \"" + testUsersTableName + "\"");


        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
