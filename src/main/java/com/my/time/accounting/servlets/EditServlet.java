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
import java.sql.Time;

@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(EditServlet.class);
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();

        try {
            dbManager.updateTask(Integer.parseInt(req.getParameter("id")), Time.valueOf(req.getParameter("wastedTime")+":00"));
            req.setAttribute("customers", dbManager.getAllTasksForUser((String) session.getAttribute("email")));
            req.setAttribute("user", session.getAttribute("user"));
            req.setAttribute("isAdmin", session.getAttribute("isAdmin"));
            req.setAttribute("head", "Tasks");
            req.setAttribute("isTask", true);

            getServletContext().getRequestDispatcher("/mainUser.jsp").forward(req, resp);
        } catch (DBException | ServletException | IOException e) {
            logger.error("Error in edit", e);
        }
    }
}
