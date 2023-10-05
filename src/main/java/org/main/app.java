package org.main;

import dao.ManufactureDAO;
import dao.PhoneDAO;
import pojo.Manufacture;
import pojo.Phone;

public class app {
    private static PhoneDAO phoneDAO;
    private static ManufactureDAO manufactureDAO;

    public static void main(String[] args) {
        phoneDAO = PhoneDAO.getInstance();
        manufactureDAO = ManufactureDAO.getInstance();

//        add Manufacture
//        System.out.println(manufactureDAO.add(new Manufacture("Lenovo", "US", 101)));
//        System.out.println(manufactureDAO.add(new Manufacture("Asus", "VN", 99)));

//        get all() Manufacture
//        System.out.println(manufactureDAO.getAll());

//        remove Manufacture id
//        System.out.println(manufactureDAO.remove("T6EcaN"));

//        update Manufacture
//        Manufacture manufacture = manufactureDAO.get("trPyIZ");
//        manufacture.setName("Apple");
//        manufacture.setEmployee(200);
//        System.out.println(manufactureDAO.update(manufacture));

//        add phone
//        System.out.println(phoneDAO.add(new Phone("Xiaomi", 600, "pink", "VN", 1, new Manufacture("trPyIZ", "Apple", "US", 200))));
//        System.out.println(phoneDAO.add(new Phone("Samsung", 500, "blue", "VN", 2, new Manufacture("trPyIZ", "Apple", "US", 200))));

//        Remove Phone
        System.out.println(phoneDAO.remove("fYmnrq"));
    }
}