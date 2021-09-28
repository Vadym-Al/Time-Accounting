package com.my.time.accounting.servlets;

import static com.my.time.accounting.database.SQLConstance.*;
import com.my.time.accounting.database.DBException;
import com.my.time.accounting.database.DBManager;
import com.my.time.accounting.entity.Administrator;
import com.my.time.accounting.entity.User;
import com.my.time.accounting.logic.PasswordCod;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String LOGIN_PAGE = "/login.jsp";
    private final Logger logger = LogManager.getLogger(LoginServlet.class);
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String password = PasswordCod.encode(req.getParameter("password"));
        HttpSession session = req.getSession();

        try {
            if (dbManager.searchUserByEmail(req.getParameter(EMAIL)).getEmail() != null) {
                User user = dbManager.searchUserByEmail(req.getParameter(EMAIL));
                if (user.getPassword().equals(password)) {
                    String customer = user.getName() + " " + user.getLastName();
                    session.setAttribute("user", customer);
                    session.setAttribute("isAdmin", false);
                    session.setAttribute("email", user.getEmail());

                    resp.sendRedirect("profile");
                } else {
                    req.setAttribute(ERROR, true);
                    getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
                }
            } else if (dbManager.searchAdminByEmail(req.getParameter(EMAIL)).getEmail() != null) {
                Administrator administrator = dbManager.searchAdminByEmail(req.getParameter(EMAIL));
                if (administrator.getPassword().equals(password)) {
                    String user = administrator.getName() + " " + administrator.getLastName();
                    session.setAttribute("user", user);
                    session.setAttribute("isAdmin", true);
                    session.setAttribute("email", administrator.getEmail());

                    resp.sendRedirect("profile");
                } else {
                    req.setAttribute(ERROR, true);
                    getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
                }
            } else {
                req.setAttribute(ERROR, true);
                getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
            }
        } catch (DBException | ServletException | IOException e) {
            logger.error("Error in login servlet", e);
            try {
                getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            } catch (ServletException | IOException servletException) {
                logger.error("Can not found error.jsp", servletException);
            }
        }
    }
}
