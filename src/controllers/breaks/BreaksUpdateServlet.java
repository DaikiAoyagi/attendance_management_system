package controllers.breaks;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
//import java.util.List;

import javax.persistence.EntityManager;
//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Break;
//import models.validators.BreakValidator;
import utils.DBUtil;

/**
 * Servlet implementation class BreaksUpdateServlet
 */
@WebServlet("/breaks/update")
public class BreaksUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BreaksUpdateServlet() {
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

            Break b = em.find(Break.class, (Integer)(request.getSession().getAttribute("break_id")));

            //出勤日
            b.setWork_date(Date.valueOf(request.getParameter("work_date")));

            //勤怠時間更新
            b.setBreak_start_time(Time.valueOf(request.getParameter("break_start_time")+":00"));
            b.setBreak_finish_time(Time.valueOf(request.getParameter("break_finish_time")+":00"));


            /*
            List<String> errors = BreakValidator.validate(b);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("breaks", b);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/breaks/edit.jsp");
                rd.forward(request, response);
            } else {
            */
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "休憩更新しました。");

                request.getSession().removeAttribute("break_id");

                //response.sendRedirect(request.getContextPath() + "/attendances/edit?id=" + request.getSession().getAttribute("attendance_id"));
                response.sendRedirect(request.getContextPath() + "/");
            }
        }
    }

