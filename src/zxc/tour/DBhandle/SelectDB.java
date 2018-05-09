package zxc.tour.DBhandle;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import zxc.tour.bean.LoginData;

public class SelectDB {
    private SessionFactory sessionFactory;

    public static SelectDB instance;

    public static SelectDB getInstance(){

        if(instance == null){

            instance = new SelectDB();

        }

        return instance;

    }
    public boolean isUser(String name,String pass) throws Exception {

        Configuration cfg = new Configuration();
        cfg.configure("source/Hibernate.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try {

            LoginData loginData = (LoginData) session.get(LoginData.class, name);

            if (!pass.equals(loginData.getPassword())) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        tx.commit();
        session.close();

        return true;

    }
}
