package com.otus.studing.lab10.datasets.dao;

import com.otus.studing.lab10.datasets.DataSet;
import com.otus.studing.lab10.datasets.impl.UserDataSet;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Glebza on 04.08.2017.
 */
public class DataSetDao<T extends DataSet> {
    private Session session;

    public DataSetDao(Session session) {
        this.session = session;
    }


    public <T extends DataSet> void save(T dataSet) {
        session.save(dataSet);
    }

    public <T extends DataSet> T read(long id, Class<T> dataSetType) {
        return (T) session.load(dataSetType, id);
    }

    public <T extends DataSet> T readByParam(String param, String value,Class<T> clazz) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        Root<T> from = criteria.from(clazz);
        criteria.where(builder.equal(from.get(param), value));
        Query<T> query = session.createQuery(criteria);
        return (T) query.uniqueResult();
    }

    public <T extends DataSet> List<T> readAll(Class<T> clazz) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        criteria.from(clazz);
        return session.createQuery(criteria).list();
    }
}
