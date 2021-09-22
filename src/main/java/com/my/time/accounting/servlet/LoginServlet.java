package com.my.time.accounting.servlet;

import static com.my.time.accounting.database.SQLConstance.*;
import com.my.time.accounting.database.DBException;
import com.my.time.accounting.database.DBManager;
import com.my.time.accounting.entity.Administrator;
import com.my.time.accounting.entity.User;
import com.my.time.accounting.logic.AsList;
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
    private static final String PROFILE_PAGE = "/profile.jsp";
    private final Logger logger = LogManager.getLogger(LoginServlet.class);
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String password = PasswordCod.encode(req.getParameter("password"));
        HttpSession session = req.getSession();

        try {
            if (dbManager.searchUserByEmail(req.getParameter(SQL_EMAIL)).getEmail() != null) {
                User user = dbManager.searchUserByEmail(req.getParameter(SQL_EMAIL));
                if (user.getPassword().equals(password)) {
                    String customer = user.getName() + " " + user.getLastName();
                    req.setAttribute("user", customer);
                    req.setAttribute("isAdmin", false);
                    req.setAttribute("customers", AsList.userAsList(user));
                    session.setAttribute("user", customer);
                    session.setAttribute("isAdmin", false);
                    session.setAttribute("email", user.getEmail());

                    getServletContext().getRequestDispatcher(PROFILE_PAGE).forward(req, resp);
                } else {
                    req.setAttribute(SQL_ERROR, true);
                    getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
                }
            } else if (dbManager.searchAdminByEmail(req.getParameter(SQL_EMAIL)).getEmail() != null) {
                Administrator administrator = dbManager.searchAdminByEmail(req.getParameter(SQL_EMAIL));
                if (administrator.getPassword().equals(password)) {
                    String user = administrator.getName() + " " + administrator.getLastName();
                    req.setAttribute("user", user);
                    req.setAttribute("isAdmin", true);
                    req.setAttribute("customers", AsList.adminAsList(administrator));
                    session.setAttribute("user", user);
                    session.setAttribute("isAdmin", true);
                    session.setAttribute("email", administrator.getEmail());

                    getServletContext().getRequestDispatcher(PROFILE_PAGE).forward(req, resp);
                } else {
                    req.setAttribute(SQL_ERROR, true);
                    getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
                }
            } else {
                req.setAttribute(SQL_ERROR, true);
                getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
            }
        } catch (DBException | ServletException | IOException e) {
            logger.error("Error in login servlet", e);
        }
    }
}
