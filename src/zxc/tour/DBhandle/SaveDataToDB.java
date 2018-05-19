package zxc.tour.DBhandle;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import sun.rmi.runtime.Log;
import zxc.tour.bean.ArticleBean;
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

    public void saveToArticle(String title,String content,String id) throws Exception {

        Configuration cfg = new Configuration();
        cfg.configure("source/Hibernate.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();
        ArticleBean articleBean = new ArticleBean();
        articleBean.setArticleTitle(title);
        articleBean.setArticleContent(content);
        articleBean.setId(id);

        Session session = sessionFactory.openSession();  //打开一个新的session
        Transaction tx = session.beginTransaction();  //开始事务

        session.save(articleBean);

        tx.commit();  //提交事务
        session.close();  //关闭session，释放资源
    }



}
