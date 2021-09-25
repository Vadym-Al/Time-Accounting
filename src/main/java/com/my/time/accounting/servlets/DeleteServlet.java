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
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    private static final String CUSTOMERS = "customers";
    private final Logger logger = LogManager.getLogger(DeleteServlet.class);
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        try {
            switch (req.getParameter("type")){
                case "Team":
                    dbManager.deleteTeam(Integer.parseInt(req.getParameter("id")));
                    req.setAttribute("isTeam", true);
                    req.setAttribute(CUSTOMERS, dbManager.getAllTeamsForAdmin((String) session.getAttribute("email")));
                    break;
                case "Activity":
                    dbManager.deleteActivity(Integer.parseInt(req.getParameter("id")));
                    req.setAttribute("isActivity", true);
                    req.setAttribute(CUSTOMERS, dbManager.getAllActivitiesForAdmin((String) session.getAttribute("email")));
                    break;
                case "Task":
                    dbManager.deleteTask(Integer.parseInt(req.getParameter("id")));
                    req.setAttribute("isTask", true);
                    req.setAttribute(CUSTOMERS, dbManager.getAllTasksForAdmin((String) session.getAttribute("email")));
                    break;
                case "User":
                    dbManager.deleteUser(Integer.parseInt(req.getParameter("id")));
                    req.setAttribute("isUser", true);
                    req.setAttribute(CUSTOMERS, dbManager.getAllUsersForAdmin((String) session.getAttribute("email")));
                    break;
                case "Request":
                    dbManager.deleteRequest(Integer.parseInt(req.getParameter("id")));
                    req.setAttribute("isRequest", true);
                    req.setAttribute(CUSTOMERS, dbManager.getAllRequestsForUser((String) session.getAttribute("email")));
                    getServletContext().getRequestDispatcher("/mainUser.jsp").forward(req, resp);
                    break;
                default:
                    getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        }catch (DBException | ServletException | IOException e) {
            logger.error("Error in delete Servlet", e);
        }
        try {
            req.setAttribute("user", session.getAttribute("user"));
            req.setAttribute("isAdmin", session.getAttribute("isAdmin"));

            getServletContext().getRequestDispatcher("/mainAdmin.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("Error when add activity", e);
        }
    }
}
