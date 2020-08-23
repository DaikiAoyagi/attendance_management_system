package controllers.Attendances;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Attendance;
import models.Break;
import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class AttendancesShowServlet
 */
@WebServlet("/attendances/show_break_index")
public class AttendancesShowBreakIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendancesShowBreakIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //出勤情報showのDB呼び出し
        Attendance r = em.find(Attendance.class, Integer.parseInt(request.getParameter("id")));


        //休憩情報indexのDB呼び出し
        //Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
        Employee u_employee = r.getEmployee();
        System.out.println("出勤者"+u_employee);

        //出勤情報から出勤日を取得
        //Date wd = Date.valueOf("2020-07-01");

        Date wd = em.createNamedQuery("getMyWorkDayAttendances", Date.class)
                        //.setParameter("employee", login_employee)
                        .setParameter("employee", u_employee)
                        .setParameter("id", Integer.parseInt(request.getParameter("id")))
                        .getSingleResult();


        //出勤日をパラメータにセットして休憩時間リストを抽出
        List<Break> breaks = em.createNamedQuery("getMyAllBreaksPerDay", Break.class)
                                //.setParameter("employee", login_employee)
                                .setParameter("employee", u_employee)
                                .setParameter("work_date", wd)
                                .getResultList();

        //出勤情報showセット
        request.setAttribute("attendance", r);
        request.setAttribute("_token", request.getSession().getId());

        //休憩情報indexセット
        request.setAttribute("breaks", breaks);

        //休憩終了時間が「00:00」があれば、休憩中として、その休憩テーブルのidを渡す
        try{
            try {
                String strDate = "00:00:00";
                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                Date ft = dateFormat.parse(strDate);
                int nb = em.createNamedQuery("getMyNowBreaks", Integer.class)
                    //.setParameter("employee", login_employee)
                    .setParameter("employee", u_employee)
                    .setParameter("work_date", wd)
                    .setParameter("break_finish_time",ft )
                    .getSingleResult();

                request.getSession().setAttribute("break_id", nb);
                System.out.println(nb);

            } catch(NoResultException e){
            //休憩中がない場合は、0を返す
                request.getSession().setAttribute("break_id", 0);
            }
        } catch (ParseException e) {
        e.printStackTrace();
        }

        em.close();

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/attendances/show_break_index.jsp");
        rd.forward(request, response);


        }

}
