package zxc.tour.DBhandle;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import sun.rmi.runtime.Log;
import zxc.tour.bean.LoginData;

public class SaveDataToDB {

    private  SessionFactory sessionFactory;

    public static SaveDataToDB instance;

    public static SaveDataToDB getInstance(){

        if(instance == null){

            instance = new SaveDataToDB();

        }

        return instance;

    }

    public void save(String name,String psd) throws Exception {

        Configuration cfg = new Configuration();
        cfg.configure("source/Hibernate.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();
        LoginData loginData = new LoginData();
        loginData.setName(name);
        loginData.setPassword(psd);
        System.out.println("Sdfsdfa");

        Session session = sessionFactory.openSession();  //打开一个新的session
        Transaction tx = session.beginTransaction();  //开始事务

        session.save(loginData);

        tx.commit();  //提交事务
        session.close();  //关闭session，释放资源
    }

    public void isUser(String name,String pass) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try {
            System.out.println("***"+name);
            System.out.println("***"+pass);
            LoginData loginData = (LoginData) session.get(LoginData.class, name);

            System.out.println(loginData.getPassword());


        } catch (Exception e) {
        }/*finally {
            try {
                LoginData loginData = (LoginData) session.get(LoginData.class, pass);
            } catch (Exception e) {
                return false;
            }
        }*/
        tx.commit();
        session.close();

    }

}
