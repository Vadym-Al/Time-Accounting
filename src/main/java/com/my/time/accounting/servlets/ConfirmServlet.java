package com.my.time.accounting.servlets;

import com.my.time.accounting.database.DBException;
import com.my.time.accounting.database.DBManager;
import com.my.time.accounting.entity.Request;
import com.my.time.accounting.entity.Task;
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
import java.sql.Date;

@WebServlet("/confirm")
public class ConfirmServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(ConfirmServlet.class);
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        Request request = null;

        try {
            request = dbManager.searchRequestById(Integer.parseInt(req.getParameter("id")));
        } catch (DBException e) {
            logger.error("Can not find request", e);
        }
        try {
            Task task = Task.createTask(dbManager.searchActivityById(request.getActivityId()).getName(),
                    Date.valueOf(req.getParameter("deadline")),
                    "",
                    request.getActivityId(),
                    request.getAdministratorId());

            dbManager.insertTaskForUser(task, dbManager.searchUserById(request.getUserId()).getName());
            dbManager.deleteRequest(request.getRequestId());
        } catch (DBException e) {
            logger.error("Can not create new task", e);
        }

        req.setAttribute("user", session.getAttribute("user"));
        req.setAttribute("isAdmin", session.getAttribute("isAdmin"));
        req.setAttribute("head", "Requests");
        req.setAttribute("isRequest", true);

        try {
            req.setAttribute("customers", dbManager.getAllRequestsForAdmin((String) session.getAttribute("email")));
            getServletContext().getRequestDispatcher("/mainAdmin.jsp").forward(req, resp);
        } catch (ServletException | IOException | DBException e) {
            logger.error("Error in confirm", e);
        }

    }
}
