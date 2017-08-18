package com.otus.studing.lab10.datasets.impl;

import com.otus.studing.lab10.datasets.DataSet;

import javax.persistence.*;

/**
 * Created by Glebza on 04.08.2017.
 */
@Entity
@Table(name = "phones")
public class PhoneDataSet extends DataSet {
    public PhoneDataSet() {
    }

    public PhoneDataSet(int code, String number) {
        this.code = code;
        this.number = number;
    }

    public PhoneDataSet(int code, String number,UserDataSet userDataSet) {
        this.code = code;
        this.number = number;
        this.userDataSet = userDataSet;
    }

    @Column(name = "phone_code")
    private int code;
    @Column(name = "phone_number")
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",referencedColumnName="id")
    private UserDataSet userDataSet;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



    public UserDataSet getUserDataSet() {
        return this.userDataSet;
    }

    public void setUserDataSet(UserDataSet userDataSet) {
        this.userDataSet = userDataSet;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "code=" + code +
                ", number='" + number + '\'' +
                ", userDataSet=" + userDataSet +
                '}';
    }
}
