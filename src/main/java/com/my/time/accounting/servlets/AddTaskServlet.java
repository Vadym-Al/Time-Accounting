package com.my.time.accounting.servlets;

import com.my.time.accounting.database.DBException;
import com.my.time.accounting.database.DBManager;
import com.my.time.accounting.entity.Activity;
import com.my.time.accounting.entity.Task;
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
import java.util.List;

@WebServlet("/add_task")
public class AddTaskServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(AddTaskServlet.class);
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();

        long adminId = 0;
        Activity activity = null;
        try {
            adminId = dbManager.searchAdminByEmail((String) session.getAttribute("email")).getAdminId();
            activity = dbManager.searchActivityByName(req.getParameter("activity_type"));
        } catch (DBException e) {
            logger.error("Can not find id", e);
        }
        Task task = Task.createTask(activity.getName(),
                Date.valueOf(req.getParameter("dead_line")),
                "",
                activity.getActivityId(),
                adminId);
        try {
            String[] list = req.getParameter("users_name").split(",");
            for (String name : list) {
                task.setAbout(name);
                dbManager.insertTaskForUser(task, name.trim());
            }
            req.setAttribute("user", session.getAttribute("user"));
            req.setAttribute("isAdmin", session.getAttribute("isAdmin"));
            req.setAttribute("head", "Tasks");
            req.setAttribute("customers", dbManager.getAllTasksForAdmin((String) session.getAttribute("email")));
            req.setAttribute("isTask", "True");

            getServletContext().getRequestDispatcher("/mainAdmin.jsp").forward(req, resp);
        } catch (DBException | IOException | ServletException e) {
            logger.error("Error in adding user", e);
        }
    }
}
