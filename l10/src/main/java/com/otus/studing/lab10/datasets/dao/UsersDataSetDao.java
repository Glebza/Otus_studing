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
 * Created by Glebza on 02.08.2017.
 */
public class UsersDataSetDao  {
    private Session session;

    public UsersDataSetDao(Session session) {
        this.session = session;
    }

    public void save(UserDataSet dataSet) {
        session.save(dataSet);
    }

    public UserDataSet read(long id) {
        return session.load(UserDataSet.class, id);
    }

    public UserDataSet readByParam(String param,String value) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        Root<UserDataSet> from = criteria.from(UserDataSet.class);
        criteria.where(builder.equal(from.get(param), value));
        Query<UserDataSet> query = session.createQuery(criteria);
        return query.uniqueResult();
    }

    public List<UserDataSet> readAll() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        criteria.from(UserDataSet.class);
        return session.createQuery(criteria).list();
    }

}
