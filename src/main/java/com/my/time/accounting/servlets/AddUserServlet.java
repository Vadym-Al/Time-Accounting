package com.my.time.accounting.servlets;

import com.my.time.accounting.database.DBException;
import com.my.time.accounting.database.DBManager;
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

@WebServlet("/add_user")
public class AddUserServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(AddUserServlet.class);
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        HttpSession session = req.getSession();

        long adminId = 0;
        long teamId = 0;
        try {
            adminId = dbManager.searchAdminByEmail((String) session.getAttribute("email")).getAdminId();
            teamId =  dbManager.searchTeamByName(req.getParameter("team_name")).getTeamId();
        } catch (DBException e) {
            logger.error("Can not find id", e);
        }

        User user = User.createUser(req.getParameter("name"),
                req.getParameter("last_name"),
                PasswordCod.encode(req.getParameter("password")),
                req.getParameter("email"),
                req.getParameter("phone_number"),
                req.getParameter("company"),
                teamId, adminId);

        try {
            if (user.equals(dbManager.searchUserByEmail(req.getParameter("email"))) && teamId == 0){
                req.setAttribute("error", true);
                try {
                    getServletContext().getRequestDispatcher("/addUsers.jsp").forward(req,resp);
                } catch (ServletException | IOException e) {
                    logger.error("Error in adding user", e);
                }
            }else{
                dbManager.insertUser(user);

                resp.sendRedirect("show_context?page=Users");
            }
        } catch (DBException | IOException e) {
            logger.error("Error in adding user", e);
        }
    }
}
