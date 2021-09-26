package com.my.time.accounting.servlets;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet("/sort")
public class SortServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(SortServlet.class);
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        List<Task> list = null;
        System.out.println(req.getParameter("sort_param"));
        try {
            list = dbManager.getAllTasksForAdmin((String) session.getAttribute("email"));
        } catch (DBException e) {
            e.printStackTrace();
        }
        System.out.println(list);
        switch (req.getParameter("sort_param")) {
            case "Name":
                assert list != null;
                Collections.sort(list, Comparator.comparing(Task::getName));
                break;
            case "Activity type":
                assert list != null;
                Collections.sort(list, Comparator.comparing(Task::getActivityType));
                break;
            case "By user":
                assert list != null;
                Collections.sort(list, Comparator.comparing(Task::getDeadline));
                break;
        }
        System.out.println(list);
        try {
            req.setAttribute("user", session.getAttribute("user"));
            req.setAttribute("isAdmin", session.getAttribute("isAdmin"));
            req.setAttribute("customers", list);
            req.setAttribute("head", "Tasks");
            req.setAttribute("isTask", true);

            getServletContext().getRequestDispatcher("/mainAdmin.jsp").forward(req, resp);
        } catch (IOException | ServletException e) {
            logger.error("Error in sort", e);
        }

    }
}