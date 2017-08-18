package com.otus.studing.lab10.datasets;
import javax.persistence.*;

/**
 * Created by Glebza on 31.07.2017.
 */
@MappedSuperclass
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
