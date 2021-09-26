package com.my.time.accounting.servlets;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(LogOutServlet.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.invalidate();

        try {
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("Error in logout", e);
        }
    }
}
