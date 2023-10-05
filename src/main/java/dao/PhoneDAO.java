package dao;

import database.MySQLHibernateConnUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pojo.Phone;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class PhoneDAO implements Repository {
    private Phone phone;
    private List<Phone> phoneList = new ArrayList<>();
    private Session session;
    private boolean isAction = false;
    private volatile static PhoneDAO phoneDAO;
    private PhoneDAO() {}
    public static PhoneDAO getInstance() {
        if (phoneDAO == null){
            synchronized (PhoneDAO.class) {
                if (phoneDAO == null)
                    phoneDAO = new PhoneDAO();
            }
        }
        return phoneDAO;
    }
    @Override
    public boolean add(Object o) {
        if (o instanceof Phone)
            phone = (Phone) o;
        if (session == null || !session.isOpen())
            session = MySQLHibernateConnUtils.getFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.save(phone);

        transaction.commit();
        session.close();
        List<Phone> phoneList = getAll();
        isAction = false;
        phoneList.forEach(phone1 -> {
            if (phone1.getId().equals(phone.getId())){
                isAction = true;
            }
        });
        return isAction;
    }
    @Override
    public Object get(String id) {
        if (session == null || !session.isOpen())
            session = MySQLHibernateConnUtils.getFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Phone WHERE id like :id";
        Query<Phone> query = session.createQuery(hql);
        query.setParameter("id", id);
        Phone phone1 = query.uniqueResult();

        transaction.commit();
        session.close();
        return phone1;
    }
    @Override
    public List<Phone> getAll() {
        if (session == null || !session.isOpen())
            session = MySQLHibernateConnUtils.getFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Phone ";
        Query<Phone> query = session.createQuery(hql);
        phoneList = query.list();

        transaction.commit();
        session.close();
        return phoneList;
    }
    @Override
    public boolean remove(String id) {
        if (session == null || !session.isOpen())
            session = MySQLHibernateConnUtils.getFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "DELETE FROM Phone WHERE id like :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        int affectedRows = query.executeUpdate();

        transaction.commit();
        session.close();
        return affectedRows != 0;
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof Phone)
            phone = (Phone) o;
        if (session == null || !session.isOpen())
            session = MySQLHibernateConnUtils.getFactory().openSession();
        Transaction transaction = session.beginTransaction();

//        String hql = "DELETE FROM Phone WHERE id like :id";
//        Query query = session.createQuery(hql);
//        query.setParameter("id");
//        int affectedRows = query.executeUpdate();

        session.delete(phone);
        List<Phone> phoneList = getAll();
        phoneList.forEach(phone1 -> {
            if (phone1.getId().equals(phone.getId())){
                isAction = true;
            }
        });

        transaction.commit();
        session.close();
        return isAction;
    }

    @Override
    public boolean update(Object o) {
        if (o instanceof Phone)
            phone = (Phone) o;
        if (session == null || !session.isOpen())
            session = MySQLHibernateConnUtils.getFactory().openSession();
        phoneList = getAll();
        Transaction transaction = session.beginTransaction();

//        String hql = "UPDATE Phone SET name = :name, price = :price, color = :color, country = :country, quantity = :quantity, manufacture = :manufacture WHERE ID LIKE :ID";
//        Query query = session.createQuery(hql);
//        query.setParameter("name", phone.getName());
//        query.setParameter("price", phone.getPrice());
//        query.setParameter("color", phone.getColor());
//        query.setParameter("country", phone.getCountry());
//        query.setParameter("quantity", phone.getQuantity());
//        query.setParameter("manufacture", phone.getManufacture());
//        query.setParameter("id", phone.getID());
//        int affectedRows = query.executeUpdate();

        session.update(phone);
        phoneList.forEach(phone1 -> {
            if (phone1.getId().equals(phone.getId())){
                isAction = true;
            }
        });

        transaction.commit();
        session.close();
//        return affectedRows != 0;
        return isAction;
    }

//    returns the phone with the highest selling price
    public Phone getPhoneHighestSellingPrice() {
        if (session == null || !session.isOpen())
            session = MySQLHibernateConnUtils.getFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Phone AS p ORDER BY p.price DESC";
        phoneList = session.createQuery(hql).list();

        transaction.commit();
        session.close();
        return phoneList.get(0);
    }

//    get a list of phones sorted by country name, if two phones have the
//    same country, sort descending by price.
    public List<Phone> getPhoneCountryPriceDESC() {
        if (session == null || !session.isOpen())
            session = MySQLHibernateConnUtils.getFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Phone AS p ORDER BY p.country DESC, p.price DESC ";
        phoneList = session.createQuery(hql).list();

        transaction.commit();
        session.close();
        return phoneList;
    }

//    check if there is a phone priced above 50 million VND.
    public boolean checkPhoneAbove50() {
        if (session == null || !session.isOpen())
            session = MySQLHibernateConnUtils.getFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Phone AS p WHERE p.price > 50";
        phoneList = session.createQuery(hql).list();

        transaction.commit();
        session.close();
        return !phoneList.isEmpty();
    }

//    get the first phone in the list that meets the criteria: has the color 'Pink' and costs over 15 million.
//    If there are no matching phones, return null.
    public Phone getFirstPhonePinkOver15() {
        if (session == null || !session.isOpen())
            session = MySQLHibernateConnUtils.getFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Phone AS p WHERE p.color = :color AND p.price > 15";
        Query<Phone> query = session.createQuery(hql);
        query.setParameter("color", "Pink");
        phoneList = query.list();

        transaction.commit();
        session.close();
        if (phoneList.isEmpty()) {
            return null;
        }
        return phoneList.get(0);
    }
}
