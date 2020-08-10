package controllers.Attendances;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Attendance;
import utils.DBUtil;
/**
 * Servlet implementation class AttendancesDestroyServlet
 */
@WebServlet("/Attendances/destroy")
public class AttendancesDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendancesDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();
;
            Attendance r = em.find(Attendance.class, (Integer)(request.getSession().getAttribute("attendance_id")));

            em.getTransaction().begin();
            em.remove(r);
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "削除が完了しました。");

            request.getSession().removeAttribute("attendance_id");

            response.sendRedirect(request.getContextPath() + "/attendances/index");
        }
    }

}
