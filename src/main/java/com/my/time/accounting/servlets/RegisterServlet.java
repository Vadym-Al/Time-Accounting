package com.my.time.accounting.servlets;

import com.my.time.accounting.database.DBException;
import com.my.time.accounting.database.DBManager;
import com.my.time.accounting.entity.Administrator;
import com.my.time.accounting.entity.Team;
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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(RegisterServlet.class);
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        Administrator administrator = Administrator.createAdministrator(req.getParameter("name"),
                req.getParameter("last_name"),
                PasswordCod.encode(req.getParameter("password")),
                req.getParameter("email"),
                req.getParameter("company"),
                req.getParameter("phone_number"));
        try {
            if (administrator.equals(dbManager.searchAdminByEmail(req.getParameter("email")))){
                req.setAttribute("error", true);
                try {
                    getServletContext().getRequestDispatcher("/registration.jsp").forward(req,resp);
                } catch (ServletException | IOException e) {
                    logger.error("Error in register", e);
                }
            }else{
                dbManager.insertAdministrator(administrator);

                Team team = Team.createTeam(req.getParameter("company"),
                        req.getParameter("company"),
                        "My Team", dbManager.searchAdminByEmail(administrator.getEmail()).getAdminId());
                dbManager.insertTeam(team);
                String user = administrator.getName() + " " + administrator.getLastName();

                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                session.setAttribute("isAdmin", true);
                session.setAttribute("email", administrator.getEmail());

                resp.sendRedirect("profile");
            }
        } catch (DBException | IOException e) {
            logger.error("Error in register", e);
            try {
                getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            } catch (ServletException | IOException servletException) {
                logger.error("Can not found error.jsp", servletException);
            }
        }
    }
}
