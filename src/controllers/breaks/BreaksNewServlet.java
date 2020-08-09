package controllers.breaks;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Break;
/**
 * Servlet implementation class BreaksNewServlet
 */
@WebServlet("/breaks/new")
public class BreaksNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BreaksNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("_token", request.getSession().getId());

        Break b = new Break();

        String strDate = request.getParameter("work_date");
        System.out.println("出勤日"+strDate);
        b.setWork_date(Date.valueOf(strDate));

        b.setBreak_start_time(new Time(System.currentTimeMillis()));


        request.setAttribute("work_date",strDate);
        request.setAttribute("breaks", b);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/breaks/new.jsp");
        rd.forward(request, response);
    }
}
