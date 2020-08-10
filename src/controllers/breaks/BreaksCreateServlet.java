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
import models.Employee;
//import models.validators.BreakValidator;
import utils.DBUtil;

/**
 * Servlet implementation class BreaksCreateServlet
 */
@WebServlet("/breaks/create")
public class BreaksCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BreaksCreateServlet() {
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

            Break b = new Break();

            b.setEmployee((Employee)request.getSession().getAttribute("login_employee"));

            //出勤日
            Date work_date = new Date(System.currentTimeMillis());
            String rd_str = request.getParameter("work_date");
            if(rd_str != null && !rd_str.equals("")) {
                //Stringで受け取った日付を Date 型へ変換
                work_date = Date.valueOf(request.getParameter("work_date"));
            }
            b.setWork_date(work_date);

            //出勤時刻
            Time times = new Time(System.currentTimeMillis());
            String times_str = request.getParameter("break_start_time");
            if(times_str != null && !times_str.equals("")) {
                //Stringで受け取った時間を Time 型へ変換
                times = Time.valueOf(request.getParameter("break_start_time")+":00");
            }
            b.setBreak_start_time(times);

            //ダミー入力
            Time d_times = Time.valueOf("00:00:00");
            b.setBreak_finish_time(d_times);


            /*
            List<String> errors = BreakValidator.validate(b);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("breaks", b);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/breaks/new.jsp");
                rd.forward(request, response);
            } else {
            */
                em.getTransaction().begin();
                em.persist(b);
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "休憩開始しました。");

                //response.sendRedirect(request.getContextPath() + "/attendances/edit?id=" + request.getSession().getAttribute("attendance_id"));
                response.sendRedirect(request.getContextPath() + "/");
            }
        }
    }
