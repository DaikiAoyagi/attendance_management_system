package controllers.breaks;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Break;
import utils.DBUtil;

/**
 * Servlet implementation class BreaksDestroyServlet
 */
@WebServlet("/breaks/destroy")
public class BreaksDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BreaksDestroyServlet() {
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

            em.getTransaction().begin();
            em.remove(b);
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "削除が完了しました。");

            request.getSession().removeAttribute("break_id");

            response.sendRedirect(request.getContextPath() + "/breaks/index");
            }
    }

}