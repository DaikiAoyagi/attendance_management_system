package controllers.Attendances;

import java.io.IOException;
import java.sql.Date;

import javax.persistence.EntityManager;
//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Attendance;
import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class AttendancesUpdateServlet
 */
@WebServlet("/attendances/update_approval")
public class AttendancesUpdateApprovalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendancesUpdateApprovalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            EntityManager em = DBUtil.createEntityManager();

            Attendance r = em.find(Attendance.class, Integer.parseInt(request.getParameter("id")));

            //ログインユーザーのクラスを取得
            Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

            //承認権限がある場合に実行（管理権限：ログインユーザー>勤怠登録上のユーザー）
            if(r != null && login_employee.getAdmin_flag() > r.getEmployee().getAdmin_flag()) {

                //ステイタスが未承認の場合は承認登録。すでに承認済みの場合は承認解除として未承認に戻す
                String Approver = r.getApprover();
                    System.out.println("登録内容" + Approver);

                if(Approver.equals("未承認")){
                    //承認者（ログインユーザーから氏名を取得）
                    r.setApprover(login_employee.getEmployeeName());
                        System.out.println(login_employee.getEmployeeName());
                    //承認時間を現在の時刻で渡す
                    r.setApproval_date(new Date(System.currentTimeMillis()));
                    request.getSession().setAttribute("flush", "承認しました。");

                }else{
                    r.setApprover("未承認");
                        System.out.println(login_employee.getEmployeeName() + "未承認");
                    r.setApproval_date(Date.valueOf("1000-01-01"));
                        System.out.println(Date.valueOf("1000-01-01"));
                    request.getSession().setAttribute("flush", "承認を解除しました。");
                }

                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();

                request.getSession().removeAttribute("attendance_id");
                request.getSession().removeAttribute("break_id");

            }else{
                em.close();
                request.getSession().setAttribute("flush", "承認権限がありません。");
            }

            response.sendRedirect(request.getContextPath() + "/attendances/index");
        }
    }


