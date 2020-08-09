package controllers.Attendances;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import models.Break;
import models.Employee;
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
            r.setWork_date(java.sql.Date.valueOf(request.getParameter("work_date")));

            //勤怠時間更新
            r.setStart_time(Time.valueOf(request.getParameter("start_time")+":00"));
            //r.setBreak_start_time(Time.valueOf(request.getParameter("break_start_time")+":00"));
            //r.setBreak_finish_time(Time.valueOf(request.getParameter("break_finish_time")+":00"));
            r.setFinish_time(Time.valueOf(request.getParameter("finish_time")+":00"));

            //休憩テーブルから、ユーザー・日付をキーに休憩開始時間と休憩終了時間のリストを取得
            //ユーザー取得
            Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

            try {
            //リクエストパラメータの日付を文字列からDate型に変換。NamedQueryはutil型でないと受けられないらしい
            String strDate = request.getParameter("work_date");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date wd = dateFormat.parse(strDate);

            //出勤日をパラメータにセットして休憩時間リストを抽出
            List<Break> breaks = em.createNamedQuery("getMyAllBreaksPerDay", Break.class)
                                    .setParameter("employee", login_employee)
                                    .setParameter("work_date",wd)
                                    .getResultList();

            //休憩回数分の繰り返しで、休憩レコード毎に休憩時間を算出
            //合計時間の初期値
            LocalTime sumBreak = LocalTime.of(0,0);
                System.out.println("初期値"+sumBreak);

            //それぞれの休憩時間を加算
            for(Break break_time: breaks){
                //休憩開始と休憩終了を取り出して、LocalTime変換
                LocalTime lbst = break_time.getBreak_start_time().toLocalTime();
                LocalTime lbft = break_time.getBreak_finish_time().toLocalTime();
                    System.out.println("休憩開始"+lbst);
                    System.out.println("休憩終了"+lbft);
                //休憩時間算出
                Duration dbt = Duration.between(lbst,lbft);
                    System.out.println("休憩時間Duration"+dbt);
                //計算結果をLocalTimeに再変換
                LocalTime lbt = LocalTime.MIDNIGHT.plus(dbt);
                    System.out.println("休憩時間LocalTime"+lbt);
                //休憩時間の合計値に加算 sumBreakに追加
                    sumBreak=sumBreak.plusHours(lbt.getHour()).plusMinutes(lbt.getMinute());

                    System.out.println(lbt.getHour());
                    System.out.println(lbt.getMinute());
                    System.out.println("累計"+sumBreak);
            }

            /*
            //休憩時間。休憩開始時刻と休憩終了時刻で時間量を出す
            //テキスト文字列をLocalTime変換後、Durationクラスで時間量を計算。
            LocalTime lbst = LocalTime.parse((request.getParameter("break_start_time")+":00"), DateTimeFormatter.ofPattern("HH:mm:ss"));
            LocalTime lbft = LocalTime.parse((request.getParameter("break_finish_time")+":00"), DateTimeFormatter.ofPattern("HH:mm:ss"));
            Duration dbt = Duration.between(lbst,lbft);
            */

            //計算結果をLocalTimeに再変換後、文字列に変換
            //LocalTime lbt = LocalTime.MIDNIGHT.plus(dbt);

            String sbt = DateTimeFormatter.ofPattern("HH:mm:ss").format(sumBreak);
            //休憩時間
            r.setBreak_time(Time.valueOf(sbt));


            /*
             勤務時間。出勤時刻と退勤時刻で時間量を出し、休憩時間を引く。
             */
            //テキスト文字列をLocalTime変換後、Durationクラスで時間量を計算。。
            LocalTime lst = LocalTime.parse((request.getParameter("start_time")+":00"), DateTimeFormatter.ofPattern("HH:mm:ss"));
            LocalTime lft = LocalTime.parse((request.getParameter("finish_time")+":00"), DateTimeFormatter.ofPattern("HH:mm:ss"));
            Duration dwt = Duration.between(lst,lft);
            //計算結果をLocalTimeに再変換
            LocalTime sumwork = LocalTime.MIDNIGHT.plus(dwt);

            //休憩時間があるときは勤務時間から休憩時間を引く。
            if(sbt != null && !sbt.equals("")) {
                sumwork =sumwork.minusHours(sumBreak.getHour()).minusMinutes(sumBreak.getMinute());
            }
            //計算結果をLocalTimeに再変換後、文字列に変換
            //LocalTime lwt = LocalTime.MIDNIGHT.plus(dwt);
            String swt = DateTimeFormatter.ofPattern("HH:mm:ss").format(sumwork);
            //勤務時間
            r.setWorking_hours(Time.valueOf(swt));

            } catch (ParseException e) {
                e.printStackTrace();
            }


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
