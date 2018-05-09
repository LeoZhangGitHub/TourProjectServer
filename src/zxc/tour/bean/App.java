package zxc.tour.bean;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class App {

    private static SessionFactory sessionFactory;
    private static String name;
    private static String psd;

    static {
        Configuration cfg = new Configuration();
        cfg.configure("source/Hibernate.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();
    }

    public App(String name,String password){
        this.name = name;
        this.psd = password;

        try {
            testSave(name, psd);

        } catch (Exception e) {

        }

    }

    public void testSave(String name,String psd) throws Exception {
        User user = new User();
        user.setName("zasan");

        Session session = sessionFactory.openSession();  //打开一个新的session
        Transaction tx = session.beginTransaction();  //开始事务

        session.save(user);

        tx.commit();  //提交事务
        session.close();  //关闭session，释放资源
    }

    public void testGet() throws Exception {
        Session session =sessionFactory.openSession();
        User user = session.get(User.class,1);
        System.out.println(user.getName());
    }

    public static void main(String[] args) {
        App app = new App(name,psd);
        try {
            app.testSave(name,psd);
            app.testGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


