package pojo;


import jakarta.persistence.*;
import org.hibernate.annotations.Columns;
import random.MyRandom;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "manufacture")
public class Manufacture implements Serializable {
    @Id
//    @Columns(columns = {@Column(name = "id"), @Column(name = "name"), @Column(name = "location"), @Column(name = "employee") })
    @Column()
    private String id;
    @Column()
    private String name;
    @Column()
    private String location;
    @Column()
    private int employee;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manufacture")
    private Set<Phone> phoneS = new HashSet<>();
    public Manufacture() {
        super();
    }
    public Manufacture(String name, String location, int employee) {
        this.id = MyRandom.randomAlphaNumeric(6);
        this.name = name;
        this.location = location;
        this.employee = employee;
    }

    public Manufacture(String id, String name, String location, int employee) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.employee = employee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getEmployee() {
        return employee;
    }

    public void setEmployee(int employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Manufacture{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", employee=" + employee +
                '}';
    }
}
