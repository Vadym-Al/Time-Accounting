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
import java.sql.Time;

@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(EditServlet.class);
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            dbManager.updateTask(Integer.parseInt(req.getParameter("id")), Time.valueOf(req.getParameter("wastedTime")+":00"));

            resp.sendRedirect("show_context?page=Tasks");
        } catch (DBException | IOException e) {
            logger.error("Error in edit", e);
            try {
                getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            } catch (ServletException | IOException servletException) {
                logger.error("Can not found error.jsp", servletException);
            }
        }
    }
}
