package com.otus.studing.lab10.datasets.impl;

import com.otus.studing.lab10.datasets.DataSet;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.DataFormatException;

@Entity
@Table(name = "users")
public class UserDataSet extends DataSet {



    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "age", nullable = false, unique = true)
    private int age;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userDataSet")
    private Set<PhoneDataSet> phoneDataSets = new HashSet<>(0);
    @OneToOne(mappedBy = "userDataSet")
    AddressesDataSet addressesDataSet;



    public UserDataSet(long id, String name, int age) {
        setId(id);
        this.age = age;
        this.name = name;
    }
    public UserDataSet(String name, int age) {
        this.age = age;
        this.name = name;
    }

    public UserDataSet(){}



    public String getName() {
        return name;
    }


    public Set<PhoneDataSet> getAddressesDataSets(){
        return this.phoneDataSets;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



    public Set<PhoneDataSet> getPhoneDataSets() {
        return phoneDataSets;
    }

    public void setPhoneDataSets(Set<PhoneDataSet> phoneDataSets) {
        this.phoneDataSets = phoneDataSets;
    }

    public AddressesDataSet getAddressesDataSet() {
        return addressesDataSet;
    }

    public void setAddressesDataSet(AddressesDataSet addressesDataSet) {
        this.addressesDataSet = addressesDataSet;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", phoneDataSets=" + phoneDataSets +
                ", addressesDataSet=" + addressesDataSet +
                '}';
    }
}

