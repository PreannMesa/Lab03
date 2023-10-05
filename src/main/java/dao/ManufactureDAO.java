package dao;

import database.MySQLHibernateConnUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pojo.Manufacture;
import pojo.Phone;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class ManufactureDAO implements Repository {
    private Manufacture manufacture = new Manufacture();
    private List<Manufacture> manufactureList = new ArrayList<>();
    public Session session;
    private boolean isSuccess = false;
    private volatile static ManufactureDAO manufactureDAO;
    private ManufactureDAO() {}
    public static ManufactureDAO getInstance() {
        if (manufactureDAO == null){
            synchronized (PhoneDAO.class) {
                if (manufactureDAO == null)
                    manufactureDAO = new ManufactureDAO();
            }
        }
        return manufactureDAO;
    }

    @Override
    public boolean add(Object o) {
        if (o instanceof Manufacture)
            manufacture = (Manufacture) o;
        if (session == null || !session.isOpen())
            session = MySQLHibernateConnUtils.getFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.save(manufacture);

        transaction.commit();

        manufactureList = getAll();
        isSuccess = false;
        manufactureList.forEach(manufacture2 -> {
            if (manufacture2.getId().equals(manufacture.getId())){
                isSuccess = true;
            }
        });
        session.close();
        return isSuccess;
    }
    @Override
    public Manufacture get(String id) {
        if (session == null || !session.isOpen())
            session = MySQLHibernateConnUtils.getFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Manufacture WHERE id=:id";
        Query<Manufacture> query = session.createQuery(hql, Manufacture.class);
        query.setParameter("id", id);
        Manufacture manufacture1 = query.uniqueResult();

        transaction.commit();
        session.close();
        return manufacture1;
    }

    @Override
    public List<Manufacture> getAll() {
        if (session == null || !session.isOpen())
            session = MySQLHibernateConnUtils.getFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Manufacture ";
        Query<Manufacture> query = session.createQuery(hql, Manufacture.class);
        manufactureList = query.getResultList();

        transaction.commit();
        session.close();
        return manufactureList;

//        Transaction transaction = null;
//        try (Session session = MySQLHibernateConnUtils.getFactory().openSession()){
//            transaction = session.beginTransaction();
//
//            String hql = "FROM Manufacture ";
//            Query<Manufacture> query = session.createQuery(hql, Manufacture.class);
//            manufactureList = query.getResultList();
//
//            transaction.commit();
//            session.close();
//        }catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
//        return manufactureList;
    }

    @Override
    public boolean remove(String id) {
        if (session == null || !session.isOpen())
            session = MySQLHibernateConnUtils.getFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "DELETE FROM Manufacture WHERE id like :id";
        Query<Manufacture> query = session.createQuery(hql);
        query.setParameter("id", id);
        int affectedRows = query.executeUpdate();

        transaction.commit();
        session.close();
        return affectedRows != 0;
    }
    @Override
    public boolean remove(Object o) {
        if (o instanceof Manufacture)
            manufacture = (Manufacture) o;
        if (session == null || !session.isOpen())
            session = MySQLHibernateConnUtils.getFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "DELETE FROM Manufacture WHERE id like :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", manufacture.getId());
        int affectedRows = query.executeUpdate();

//        session.delete(manufacture);
//        List<Manufacture> manufactureList = getAll();
//        manufactureList.forEach(manufacture2 -> {
//            if (manufacture2.getId().equals(manufacture.getId())){
//                isRemove = true;
//            }
//        });

        transaction.commit();
        session.close();
        return affectedRows != 0;
//        return isRemove;
    }

    @Override
    public boolean update(Object o) {
        if (o instanceof Manufacture)
            manufacture = (Manufacture) o;
        if (session == null || !session.isOpen())
            session = MySQLHibernateConnUtils.getFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "UPDATE Manufacture SET name = :name, location = :location, employee = :employee WHERE id like :id";
        Query<Manufacture> query = session.createQuery(hql);
        query.setParameter("name", manufacture.getName());
        query.setParameter("location", manufacture.getLocation());
        query.setParameter("employee", manufacture.getEmployee());
        query.setParameter("id", manufacture.getId());
        int affectedRows = query.executeUpdate();

//        session.update(manufacture);
//        manufactureList = getAll();
//        manufactureList.forEach(phone1 -> {
//            if (phone1.getId().equals(manufacture.getId())){
//                isRemove = true;
//            }
//        });

        transaction.commit();
        session.close();
        return affectedRows != 0;
//        return isRemove;
    }

//    check whether all manufacturers have more than 100
    public boolean checkAllManufactureMuch100E() {
        if (session == null || !session.isOpen())
            session = MySQLHibernateConnUtils.getFactory().openSession();
        manufactureList = getAll();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Manufacture WHERE employee > 100";
        List<Manufacture> manufactures = session.createQuery(hql).list();

        transaction.commit();
        session.close();
        if (manufactures.size() == manufactureList.size())
            return true;
        return false;
    }

//    returns the sum of all employees of the manufactures.
    public int sumAllEmployee() {
        if (session == null || !session.isOpen())
            session = MySQLHibernateConnUtils.getFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT SUM(employee) FROM Manufacture";
        List<Integer> manufactures = session.createQuery(hql).list();

        transaction.commit();
        session.close();
        if (manufactures.size() > 0)
            return manufactures.get(0);
        return 0;
    }

//    A method that returns the last manufacturer in the list of manufacturers that meet
//    the criteria: based in the US. If there is no producer that meets the above criteria,
//    throw an InvalidOperationException.
    public Manufacture getLastManufacture() {
        if (session == null || !session.isOpen())
            session = MySQLHibernateConnUtils.getFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Manufacture WHERE location LIKE :location";
        Query query = session.createQuery(hql);
        query.setParameter("location", "US");
        List<Manufacture> manufactures = query.list();

        transaction.commit();
        session.close();
        if (manufactures.size() > 0)
            return manufactures.get(manufactures.size()-1);
        return null;
    }
}
