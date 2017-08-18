package com.otus.studing.lab10.datasets.impl;

import com.otus.studing.lab10.datasets.DataSet;

import javax.persistence.*;


/**
 * Created by Glebza on 04.08.2017.
 */

@Entity
@Table(name = "addresses")
public class AddressesDataSet extends DataSet {
    public AddressesDataSet() {
    }

    public AddressesDataSet(String street, int index) {
        this.street = street;
        this.index = index;
    }

    public AddressesDataSet(String street, int index, UserDataSet userDataSet) {
        this.street = street;
        this.index = index;
        this.userDataSet = userDataSet;
    }

    @Column
    private String street;
    @Column(name = "address_index")
    private int index;

    @OneToOne
    @JoinColumn(name = "user_id")
    UserDataSet userDataSet;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    @Override
    public void setId(long id) {
        super.setId(id);
    }

    public UserDataSet getUserDataSet() {
        return userDataSet;
    }

    public void setUserDataSet(UserDataSet userDataSet) {
        this.userDataSet = userDataSet;
    }

    @Override
    public String toString() {
        return "AddressesDataSet{" +
                "street='" + street + '\'' +
                ", index=" + index +
                ", userDataSet=" + userDataSet +
                '}';
    }
}
