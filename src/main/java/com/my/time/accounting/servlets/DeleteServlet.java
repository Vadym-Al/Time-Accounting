package com.my.time.accounting.servlets;

import com.my.time.accounting.database.DBException;
import com.my.time.accounting.database.DBManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(DeleteServlet.class);
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            switch (req.getParameter("type")){
                case "Team":
                    dbManager.deleteTeam(Integer.parseInt(req.getParameter("id")));
                    break;
                case "Activity":
                    dbManager.deleteActivity(Integer.parseInt(req.getParameter("id")));
                    break;
                case "Task":
                    dbManager.deleteTask(Integer.parseInt(req.getParameter("id")));
                    break;
                case "User":
                    dbManager.deleteUser(Integer.parseInt(req.getParameter("id")));
                    break;
                case "Request":
                    dbManager.deleteRequest(Integer.parseInt(req.getParameter("id")));
                    break;
                default:
                    getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            }
        }catch (DBException | ServletException | IOException e) {
            logger.error("Error in delete Servlet", e);
            try {
                getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            } catch (ServletException | IOException servletException) {
                logger.error("Can not found error.jsp", servletException);
            }
        }
        try {
            resp.sendRedirect("show_context?page="+req.getParameter("type"));
        } catch (IOException e) {
            logger.error("Error when add activity", e);
            try {
                getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            } catch (ServletException | IOException servletException) {
                logger.error("Can not found error.jsp", servletException);
            }
        }
    }
}
