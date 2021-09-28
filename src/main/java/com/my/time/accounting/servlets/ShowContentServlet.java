package com.my.time.accounting.servlets;

import com.my.time.accounting.database.DBException;
import com.my.time.accounting.database.DBManager;
import com.my.time.accounting.entity.Administrator;
import com.my.time.accounting.entity.Request;
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
import java.util.List;

@WebServlet("/show_context")
public class ShowContentServlet extends HttpServlet {
    private static final String CUSTOMERS = "customers";
    private final Logger logger = LogManager.getLogger(ShowContentServlet.class);
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();

        if ((boolean) session.getAttribute("isAdmin")) {
            try {
                switch (req.getParameter("page")) {
                    case "Teams":
                        req.setAttribute(CUSTOMERS, dbManager.getAllTeamsForAdmin((String) session.getAttribute("email")));
                        req.setAttribute("head", "Teams");
                        req.setAttribute("isTeam", true);
                        break;
                    case "Users":
                        req.setAttribute(CUSTOMERS, dbManager.getAllUsersForAdmin((String) session.getAttribute("email")));
                        req.setAttribute("head", "Users");
                        req.setAttribute("isUser", true);
                        break;
                    case "Activities":
                        req.setAttribute(CUSTOMERS, dbManager.getAllActivitiesForAdmin((String) session.getAttribute("email")));
                        req.setAttribute("head", "Activities");
                        req.setAttribute("isActivity", true);
                        break;
                    case "Tasks":
                        req.setAttribute("activity", dbManager.getAllActivitiesForAdmin((String) session.getAttribute("email")));
                        req.setAttribute(CUSTOMERS, dbManager.getAllTasksForAdmin((String) session.getAttribute("email")));
                        req.setAttribute("head", "Tasks");
                        req.setAttribute("isTask", true);
                        break;
                    case "Requests":
                        List<Request> list = dbManager.getAllRequestsForAdmin((String) session.getAttribute("email"));
                        for (Request request : list) {
                            request.setActivityName(dbManager.searchActivityById(request.getActivityId()).getName());
                        }
                        req.setAttribute(CUSTOMERS, list);
                        req.setAttribute("head", "Requests");
                        req.setAttribute("isRequest", true);
                        break;
                    default:
                        getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
                }
            } catch (ServletException | IOException | DBException e) {
                logger.error("Error in show admin info", e);
                try {
                    getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
                } catch (ServletException | IOException servletException) {
                    logger.error("Can not found error.jsp", servletException);
                }
            }
        } else {
            try {
                switch (req.getParameter("page")) {
                    case "Teams":
                        req.setAttribute(CUSTOMERS, dbManager.getAllTeamsForUser((String) session.getAttribute("email")));
                        req.setAttribute("head", "Teams");
                        req.setAttribute("isTeam", true);
                        break;
                    case "Tasks":
                        req.setAttribute(CUSTOMERS, dbManager.getAllTasksForUser((String) session.getAttribute("email")));
                        req.setAttribute("head", "Tasks");
                        req.setAttribute("isTask", true);
                        break;
                    case "Activities":
                        User user = dbManager.searchUserByEmail((String) session.getAttribute("email"));
                        Administrator administrator = dbManager.searchAdminById(user.getAdministratorId());
                        req.setAttribute(CUSTOMERS, dbManager.getAllActivitiesForAdmin(administrator.getEmail()));
                        req.setAttribute("head", "Activities");
                        req.setAttribute("isActivity", true);
                        break;
                    case "Requests":
                        List<Request> list = dbManager.getAllRequestsForUser((String) session.getAttribute("email"));
                        for (Request request : list) {
                            request.setActivityName(dbManager.searchActivityById(request.getActivityId()).getName());
                        }
                        req.setAttribute(CUSTOMERS, list);
                        req.setAttribute("head", "Requests");
                        req.setAttribute("isRequest", true);
                        break;
                    default:
                        getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
                }
            } catch (ServletException | IOException | DBException e) {
                logger.error("Error in show user info", e);
                try {
                    getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
                } catch (ServletException | IOException servletException) {
                    logger.error("Can not found error.jsp", servletException);
                }
            }
        }
        req.setAttribute("user", session.getAttribute("user"));
        req.setAttribute("isAdmin", session.getAttribute("isAdmin"));

        try {
            if ((boolean) session.getAttribute("isAdmin")) {
                getServletContext().getRequestDispatcher("/mainAdmin.jsp").forward(req, resp);
            } else {
                getServletContext().getRequestDispatcher("/mainUser.jsp").forward(req, resp);
            }
        } catch (ServletException | IOException e) {
            logger.error("Error in show", e);
            try {
                getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            } catch (ServletException | IOException servletException) {
                logger.error("Can not found error.jsp", servletException);
            }
        }
    }
}
