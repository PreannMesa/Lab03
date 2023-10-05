package database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import pojo.Manufacture;
import pojo.Phone;

import java.util.Properties;

public class MySQLHibernateConnUtils {
    private static final SessionFactory FACTORY;
    static{
        Configuration configuration = new Configuration();
//        C1:code cung cap thong tin cau hinh
        Properties properties = new Properties();
//        properties.put(Environment.HBM2DDL_AUTO, "create");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
        properties.put(Environment.URL,"jdbc:mysql://localhost:3306/PhoneManufacture");
        properties.put(Environment.USER,"root");
        properties.put(Environment.PASS,"");
        properties.put(Environment.SHOW_SQL,"true");
        configuration.setProperties(properties);

//        C2: cung cấp thông tin cấu hình file xml
//        configuration.configure("hibernate.cfg.xml");

        configuration.addAnnotatedClass(Phone.class);
        configuration.addAnnotatedClass(Manufacture.class);
        ServiceRegistry registry= new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        FACTORY = configuration.buildSessionFactory(registry);
    }
    public static SessionFactory getFactory() {
        return FACTORY;
    }
    public static void close(){
        getFactory().close();
    }
}
