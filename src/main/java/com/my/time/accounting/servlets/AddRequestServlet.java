package com.my.time.accounting.servlets;

import com.my.time.accounting.database.DBException;
import com.my.time.accounting.database.DBManager;
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

@WebServlet("/add_request")
public class AddRequestServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(AddRequestServlet.class);
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();

        User user = null;
        long activityType = 0;
        try {
            user = dbManager.searchUserByEmail((String) session.getAttribute("email"));
            activityType = dbManager.searchActivityByName(req.getParameter("activity_type")).getActivityId();
        } catch (DBException e) {
            logger.error("Can not find id", e);
            try {
                getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            } catch (ServletException | IOException servletException) {
                logger.error("Can not found error.jsp", servletException);
            }
        }
        Request request = Request.createRequest(req.getParameter("about"),
                activityType,
                user.getAdministratorId(),
                user.getUserId());
        try {
            dbManager.insertRequest(request);

            resp.sendRedirect("show_context?page=Requests");
        } catch (IOException | DBException e) {
            logger.error("Error in addRequest", e);
            try {
                getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            } catch (ServletException | IOException servletException) {
                logger.error("Can not found error.jsp", servletException);
            }
        }
    }
}
