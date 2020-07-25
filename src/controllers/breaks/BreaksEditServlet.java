package controllers.breaks;

import java.io.IOException;
import java.sql.Time;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Break;
import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class BreaksEditServlet
 */
@WebServlet("/breaks/edit")
public class BreaksEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BreaksEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Break b = em.find(Break.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
        if(b != null && login_employee.getId() == b.getEmployee().getId()) {

            //休憩終了時間を現在の時刻で渡す
            b.setBreak_finish_time(new Time(System.currentTimeMillis()));
            //DB登録内容
            request.setAttribute("breaks", b);
            request.setAttribute("_token", request.getSession().getId());
            request.getSession().setAttribute("break_id", b.getId());

        }


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/breaks/edit.jsp");
        rd.forward(request, response);
    }

}
