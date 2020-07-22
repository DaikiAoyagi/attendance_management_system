package controllers.Attendances;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Attendance;
import models.validators.AttendanceValidator;
import utils.DBUtil;

/**
 * Servlet implementation class AttendancesUpdateServlet
 */
@WebServlet("/attendances/update")
public class AttendancesUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendancesUpdateServlet() {
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

            Attendance r = em.find(Attendance.class, (Integer)(request.getSession().getAttribute("attendance_id")));

            //出勤日
            r.setWork_date(Date.valueOf(request.getParameter("work_date")));

            //勤怠時間更新
            r.setStart_time(Time.valueOf(request.getParameter("start_time")+":00"));
            r.setBreak_start_time(Time.valueOf(request.getParameter("break_start_time")+":00"));
            r.setBreak_finish_time(Time.valueOf(request.getParameter("break_finish_time")+":00"));
            r.setFinish_time(Time.valueOf(request.getParameter("finish_time")+":00"));

            /*
            休憩時間。休憩開始時刻と休憩終了時刻で時間量を出す
            */
            //テキスト文字列をLocalTime変換後、Durationクラスで時間量を計算。
            LocalTime lbst = LocalTime.parse((request.getParameter("break_start_time")+":00"), DateTimeFormatter.ofPattern("HH:mm:ss"));
            LocalTime lbft = LocalTime.parse((request.getParameter("break_finish_time")+":00"), DateTimeFormatter.ofPattern("HH:mm:ss"));
            Duration dbt = Duration.between(lbst,lbft);

            //計算結果をLocalTimeに再変換後、文字列に変換
            LocalTime lbt = LocalTime.MIDNIGHT.plus(dbt);
            String sbt = DateTimeFormatter.ofPattern("HH:mm:ss").format(lbt);
            //休憩時間
            r.setBreak_time(Time.valueOf(sbt));

            /*
             勤務時間。出勤時刻と退勤時刻で時間量を出し、休憩時間を引く。
             */
            //テキスト文字列をLocalTime変換後、Durationクラスで時間量を計算。。
            LocalTime lst = LocalTime.parse((request.getParameter("start_time")+":00"), DateTimeFormatter.ofPattern("HH:mm:ss"));
            LocalTime lft = LocalTime.parse((request.getParameter("finish_time")+":00"), DateTimeFormatter.ofPattern("HH:mm:ss"));
            Duration dwt = Duration.between(lst,lft);

            //休憩時間があるときは勤務時間から休憩時間を引く。
            if(sbt != null && !sbt.equals("")) {
                dwt = dwt.minus(dbt);
            }
            //計算結果をLocalTimeに再変換後、文字列に変換
            LocalTime lwt = LocalTime.MIDNIGHT.plus(dwt);
            String swt = DateTimeFormatter.ofPattern("HH:mm:ss").format(lwt);
            //勤務時間
            r.setWorking_hours(Time.valueOf(swt));


            r.setContent(request.getParameter("content"));
            r.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            List<String> errors = AttendanceValidator.validate(r);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("attendance", r);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/attendances/edit.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "更新が完了しました。");

                request.getSession().removeAttribute("attendance_id");

                response.sendRedirect(request.getContextPath() + "/attendances/index");
            }
        }
    }
}
