package controllers.Attendances;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Attendance;
import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class AttendancesIndexServlet
 */
@WebServlet("/attendances/index_monthly")
public class AttendancesIndexMonthlyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendancesIndexMonthlyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //リクエストパラメータの日付を文字列からDate型に変換。NamedQueryはutil型でないと受けられないらしい
        try {
            String strDate = request.getParameter("yyyymm") + "-01";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date swd = dateFormat.parse(strDate);

            //月末日
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(swd);
            int last = calendar.getActualMaximum(Calendar.DATE);
            calendar.set(Calendar.DATE, last);

            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);

            Date lwd = calendar.getTime();
            System.out.println("月末"+lwd);

            //ログイン者
            Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

            //DBからyyyymmに一致するリスト抽出
            List<Attendance> attendances = em.createNamedQuery("getMyYYYYMMAttendances", Attendance.class)
                                            .setParameter("start_work_date", swd)
                                            .setParameter("last_work_date", lwd)
                                            .setParameter("employee", login_employee)
                                            .getResultList();

            long attendances_count = (long)em.createNamedQuery("getMyYYYYMMAttendancesCount", Long.class)
                                            .setParameter("start_work_date", swd)
                                            .setParameter("last_work_date", lwd)
                                            .setParameter("employee", login_employee)
                                            .getSingleResult();

            em.close();

            request.setAttribute("attendances", attendances);
            request.setAttribute("attendances_count", attendances_count);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        //日時：request.setAttribute("attendances_count", attendances_count);
        request.setAttribute("login_name", request.getSession().getId()); //ログイン者

        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response);
    }

}
