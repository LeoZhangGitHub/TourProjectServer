package zxc.tour.DBhandle;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import zxc.tour.bean.ArticleBean;
import zxc.tour.bean.LoginData;
import zxc.tour.bean.PhoneNumber;

public class SelectDB {
    private static SessionFactory sessionFactory;

    public static SelectDB instance;

    private static Session session;

    private static Transaction tx;

    public static SelectDB getInstance(){

        if(instance == null){

            instance = new SelectDB();

        }

        return instance;

    }
    static {
        Configuration cfg = new Configuration();
        cfg.configure("source/Hibernate.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();

        session = sessionFactory.openSession();
        tx = session.beginTransaction();
    }
    public boolean isUser(String name,String pass) throws Exception {

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

    public String getUserId(String name) {

        LoginData loginData = (LoginData) session.get(LoginData.class, name);

        tx.commit();
        session.close();

        return loginData.getId().toString();

    }

    public String getUserPhoneNumber(String id) {

        PhoneNumber phoneNumber = (PhoneNumber) session.get(PhoneNumber.class, id);

        tx.commit();
        session.close();

        return phoneNumber.getPhonenumber().toString();

    }

    public String getArticleTilte(String id) {

        ArticleBean articleBean = (ArticleBean) session.get(ArticleBean.class, id);

       /* tx.commit();
        session.close();*/

        return articleBean.getArticleTitle().toString();
    }
    public String getArticleContent(String id) {
        ArticleBean articleBean = (ArticleBean) session.get(ArticleBean.class, id);

        tx.commit();
        session.close();

        return articleBean.getArticleContent().toString();
    }
}
