package com.my.time.accounting.servlet;

import com.my.time.accounting.database.DBException;
import com.my.time.accounting.database.DBManager;
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

@WebServlet("/add_task")
public class AddTaskServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(AddTaskServlet.class);
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();

        long adminId = 0;
        long activityType = 0;
        try {
            adminId = dbManager.searchAdminByEmail((String) session.getAttribute("email")).getAdminId();
            activityType = dbManager.searchActivityByName(req.getParameter("activity_type")).getActivityId();
        } catch (DBException e) {
            logger.error("Can not find id", e);
        }
        Task task = Task.createTask(req.getParameter("name"),
                Date.valueOf(req.getParameter("dead_line")),
                req.getParameter("about"),
                activityType,
                adminId);
        try {
            /// to do !!
            if (activityType == 0){
                req.setAttribute("error", true);
                try {
                    getServletContext().getRequestDispatcher("/addTasks.jsp").forward(req,resp);
                } catch (ServletException | IOException e) {
                    logger.error("Error in adding user", e);
                }
            }else{
                dbManager.insertTaskForUser(task, req.getParameter("users_name"));

                req.setAttribute("user", session.getAttribute("user"));
                req.setAttribute("isAdmin", session.getAttribute("isAdmin"));
                req.setAttribute("head", "Tasks");

                getServletContext().getRequestDispatcher("/main.jsp").forward(req,resp);
            }
        } catch (DBException | IOException | ServletException e) {
            logger.error("Error in adding user", e);
        }
    }
}
