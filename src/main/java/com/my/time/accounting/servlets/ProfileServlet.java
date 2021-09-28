package com.my.time.accounting.servlets;

import com.my.time.accounting.database.DBException;
import com.my.time.accounting.database.DBManager;
import com.my.time.accounting.logic.AsList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(ProfileServlet.class);
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        HttpSession session = req.getSession();
        if ((boolean) session.getAttribute("isAdmin")){
            req.setAttribute("user", session.getAttribute("user"));
            req.setAttribute("isAdmin", true);
            try {
                req.setAttribute("customers", AsList.adminAsList(dbManager.searchAdminByEmail((String) session.getAttribute("email"))));
            } catch (DBException e) {
                logger.error("Error in profile", e);
            }
        }else{
            req.setAttribute("user", session.getAttribute("user"));
            req.setAttribute("isAdmin", false);
            try {
                req.setAttribute("customers", AsList.userAsList(dbManager.searchUserByEmail((String) session.getAttribute("email"))));
            } catch (DBException e) {
                logger.error("Error in profile", e);
            }
        }
        try {
            getServletContext().getRequestDispatcher("/profile.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("Error in profile", e);
            try {
                getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            } catch (ServletException | IOException servletException) {
                logger.error("Can not found error.jsp", servletException);
            }
        }
    }
}
