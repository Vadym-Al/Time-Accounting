package com.my.time.accounting.servlet;

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
                        req.setAttribute(CUSTOMERS, dbManager.getAllTasksForAdmin((String) session.getAttribute("email")));
                        req.setAttribute("head", "Tasks");
                        req.setAttribute("isTask", true);
                        break;
                    case "Requests":
                        req.setAttribute(CUSTOMERS, dbManager.getAllRequestsForAdmin((String) session.getAttribute("email")));
                        req.setAttribute("head", "Requests");
                        req.setAttribute("isRequest", true);
                        break;
                    default:
                        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
                }
            } catch (ServletException | IOException | DBException e) {
                logger.error("Error in show admin info", e);
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
                    case "Requests":
                        req.setAttribute(CUSTOMERS, dbManager.getAllRequestsForUser((String) session.getAttribute("email")));
                        req.setAttribute("head", "Requests");
                        req.setAttribute("isRequest", true);
                        break;
                    default:
                        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
                }
            } catch (ServletException | IOException | DBException e) {
                logger.error("Error in show user info", e);
            }
        }
        req.setAttribute("user", session.getAttribute("user"));
        req.setAttribute("isAdmin", session.getAttribute("isAdmin"));

        try {
            if((boolean) session.getAttribute("isAdmin")){
                getServletContext().getRequestDispatcher("/mainAdmin.jsp").forward(req, resp);
            }else {
                getServletContext().getRequestDispatcher("/mainUser.jsp").forward(req, resp);
            }
        } catch (ServletException | IOException e) {
            logger.error("Error in show", e);
        }
    }
}
