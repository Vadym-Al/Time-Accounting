package com.my.time.accounting.servlet;

import com.my.time.accounting.database.DBException;
import com.my.time.accounting.database.DBManager;
import com.my.time.accounting.entity.Administrator;
import com.my.time.accounting.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/redirect")
public class RedirectServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(RedirectServlet.class);
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        try {
            switch (req.getParameter("head")) {
                case "Users":
                    req.setAttribute("teams", dbManager.getAllTeamsForAdmin((String) session.getAttribute("email")));
                    getServletContext().getRequestDispatcher("/addUsers.jsp").forward(req, resp);
                    break;
                case "Tasks":
                    req.setAttribute("activity", dbManager.getAllActivitiesForAdmin((String) session.getAttribute("email")));
                    req.setAttribute("users", dbManager.getAllUsersForAdmin((String) session.getAttribute("email")));
                    getServletContext().getRequestDispatcher("/addTasks.jsp").forward(req, resp);
                    break;
                case "Activities":
                    getServletContext().getRequestDispatcher("/addActivities.jsp").forward(req, resp);
                    break;
                case "Teams":
                    getServletContext().getRequestDispatcher("/addTeams.jsp").forward(req, resp);
                    break;
                case "Requests":
                    User user = dbManager.searchUserByEmail((String) session.getAttribute("email"));
                    Administrator administrator = dbManager.searchAdminById(user.getAdministratorId());
                    req.setAttribute("activity", dbManager.getAllActivitiesForAdmin(administrator.getEmail()));
                    getServletContext().getRequestDispatcher("/addRequests.jsp").forward(req, resp);
                    break;
                default:
                    getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        } catch (ServletException | IOException | DBException e) {
            logger.error("Error in redirect", e);
        }
    }
}
