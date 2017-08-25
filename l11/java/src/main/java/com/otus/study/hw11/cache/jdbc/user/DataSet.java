package com.otus.study.hw11.cache.jdbc.user;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Glebza on 31.07.2017.
 */
public class DataSet {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
