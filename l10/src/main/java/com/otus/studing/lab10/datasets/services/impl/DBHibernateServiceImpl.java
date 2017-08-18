package com.otus.studing.lab10.datasets.services.impl;

import com.otus.studing.lab10.datasets.DataSet;
import com.otus.studing.lab10.datasets.dao.DataSetDao;
import com.otus.studing.lab10.datasets.impl.AddressesDataSet;
import com.otus.studing.lab10.datasets.impl.PhoneDataSet;
import com.otus.studing.lab10.datasets.dao.UsersDataSetDao;
import com.otus.studing.lab10.datasets.impl.UserDataSet;
import com.otus.studing.lab10.datasets.services.DataBaseService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Glebza on 04.08.2017.
 */
public class DBHibernateServiceImpl implements DataBaseService {

    private final SessionFactory sessionFactory;

    public DBHibernateServiceImpl() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressesDataSet.class);
        configuration.addAnnotatedClass(DataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres");
        configuration.setProperty("hibernate.connection.username", "postgres");
        configuration.setProperty("hibernate.connection.password", "Orion123");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
    }

    public String getLocalStatus() {
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    @Override
    public <T extends DataSet> void save(T dataSet) {
        try (Session session = sessionFactory.openSession()) {
            DataSetDao dao = new DataSetDao(session);
            dao.save(dataSet);

        }
    }

    @Override
    public <T extends DataSet> T read(long id, Class<T> dataSetType) {
        return runInSession(session -> {
            DataSetDao dao = new DataSetDao(session);
            return (T) dao.read(id, dataSetType);
        });
    }

    @Override
    public <T extends DataSet> T readByParam(String name, String value,Class<T> tClass) {
        return runInSession(session -> {
            DataSetDao dao = new DataSetDao(session);
            return (T) dao.readByParam(name, value,tClass);
        });
    }

    @Override
    public <T extends DataSet> List<T> readAll(Class<T> tClass) {
        return runInSession(session -> {
            DataSetDao dao = new DataSetDao(session);
            return dao.readAll(tClass);
        });
    }

    public void shutdown() {
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

}
