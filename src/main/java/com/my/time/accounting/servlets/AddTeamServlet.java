package com.my.time.accounting.servlets;

import com.my.time.accounting.database.DBException;
import com.my.time.accounting.database.DBManager;
import com.my.time.accounting.entity.Team;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/add_team")
public class AddTeamServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(AddTeamServlet.class);
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        long id = 0;
        try {
            id = dbManager.searchAdminByEmail((String) session.getAttribute("email")).getAdminId();
        } catch (DBException e) {
            logger.error("Can not find id", e);
        }
        Team team = Team.createTeam(req.getParameter("name"),
                req.getParameter("company"),
                req.getParameter("about"), id);
        try {
            if (team.equals(dbManager.searchTeamByName(req.getParameter("name")))) {
                req.setAttribute("error", true);
                try {
                    getServletContext().getRequestDispatcher("/addTeams.jsp").forward(req, resp);
                } catch (ServletException | IOException e) {
                    logger.error("Error adding team", e);
                }
            } else {
                dbManager.insertTeam(team);

                resp.sendRedirect("show_context?page=Teams");
            }
        } catch (IOException | DBException e) {
            logger.error("Error when add team", e);
        }
    }
}
