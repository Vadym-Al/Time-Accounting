package com.my.time.accounting.servlets;

import com.my.time.accounting.database.DBException;
import com.my.time.accounting.database.DBManager;
import com.my.time.accounting.entity.Activity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/add_activity")
public class AddActivityServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(AddActivityServlet.class);
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();

        long id = 0;
        try {
            id = dbManager.searchAdminByEmail((String) session.getAttribute("email")).getAdminId();
        } catch (DBException e) {
            logger.error("Can not find id", e);
        }

        Activity activity = Activity.createActivity(req.getParameter("name"),
                req.getParameter("about"), id);

        try {
            if (activity.equals(dbManager.searchActivityByName(req.getParameter("name")))) {
                req.setAttribute("error", true);
                try {
                    getServletContext().getRequestDispatcher("/addActivities.jsp").forward(req, resp);
                } catch (ServletException | IOException e) {
                    logger.error("Error in adding team", e);
                }
            } else {
                dbManager.insertActivity(activity);

                req.setAttribute("user", session.getAttribute("user"));
                req.setAttribute("isAdmin", session.getAttribute("isAdmin"));
                req.setAttribute("head", "Activities");
                req.setAttribute("customers", dbManager.getAllActivitiesForAdmin((String) session.getAttribute("email")));
                req.setAttribute("isActivity", "True");

                getServletContext().getRequestDispatcher("/mainAdmin.jsp").forward(req, resp);
            }
        } catch (ServletException | IOException | DBException e) {
            logger.error("Error when add activity", e);
        }
    }
}
