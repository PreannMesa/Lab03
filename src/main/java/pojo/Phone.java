package pojo;

import jakarta.persistence.*;
import org.hibernate.annotations.Columns;
import random.MyRandom;

import java.io.Serializable;

@Entity
@Table (name = "phone")
public class Phone implements Serializable {
    @Id
//    @Columns(columns = {@Column(name = "id"), @Column(name = "name"), @Column(name = "price"), @Column(name = "color"),
//            @Column(name = "color"), @Column(name = "country"), @Column(name = "quantity")})
    private String id;
    private String name;
    private int price;
    private String color;
    private String country;
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacture", referencedColumnName = "ID")
    private Manufacture manufacture;
    public Phone() {
        super();
    }
    public Phone(String name, int price, String color, String country, int quantity, Manufacture manufacture) {
        super();
        this.id = MyRandom.randomAlphaNumeric(6);
        this.name = name;
        this.price = price;
        this.color = color;
        this.country = country;
        this.quantity = quantity;
        this.manufacture = manufacture;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Manufacture getManufacture() {
        return manufacture;
    }

    public void setManufacture(Manufacture manufacture) {
        this.manufacture = manufacture;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", country='" + country + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
