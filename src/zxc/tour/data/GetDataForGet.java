package zxc.tour.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import zxc.tour.DBhandle.SaveDataToDB;
import zxc.tour.DBhandle.SelectDB;
import zxc.tour.bean.App;
import zxc.tour.bean.LoginData;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletForGETMethod
 */
@WebServlet("/ServletForGETMethod")
public class GetDataForGet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDataForGet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String psd = request.getParameter("pwd");


        try {
            /*SaveDataToDB.getInstance().save(name, psd);*/
            if (SelectDB.getInstance().isUser(name, psd)) {
                out.print("1");
            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
