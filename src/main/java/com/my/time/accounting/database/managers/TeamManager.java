package com.my.time.accounting.database.managers;

import com.my.time.accounting.entity.Administrator;
import com.my.time.accounting.entity.Team;
import com.my.time.accounting.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.time.accounting.database.SQLConstance.*;
import static com.my.time.accounting.database.managers.Utils.*;

/**
 * Class that contains instructions of processing information about activities in data base
 *
 * @author Vadym Aldyk
 * @version 1.0
 */
public class TeamManager {
    /**
     * private constructor
     * @see TeamManager#TeamManager()
     */
    private TeamManager() {}

    /**
     * Instruction of inserting team to database
     * @param connection - connection with database
     * @param team - data base entity
     * @throws SQLException - possible exception
     */
    public static void insertTeam(Connection connection, Team team) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_ADD_NEW_TEAM, Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            pstmt.setString(k++, team.getName());
            pstmt.setString(k++, team.getCompany());
            pstmt.setString(k++, team.getDescription());
            pstmt.setLong(k++, team.getAdministratorId());

            if (pstmt.executeUpdate() > 0) {
                resultSet = pstmt.getGeneratedKeys();
                if (resultSet.next()) {
                    team.setTeamId(resultSet.getLong(1));
                }
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
    }

    /**
     * Instruction of getting all teams from database for user
     * @param connection - connection with database
     * @param user - person that take data
     * @return list of teams for user
     * @throws SQLException
     */
    public static List<Team> getAllTeamsForUser(Connection connection, User user) throws SQLException {
        List<Team> teams = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_GET_TEAMS_BY_USER);

            pstmt.setLong(1, user.getUserId());
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                teams.add(mapTeam(resultSet));
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return teams;
    }

    /**
     * Instruction of getting all teams from database for administrator
     * @param connection - connection with database
     * @param administrator - person that take data
     * @return list of teams for administrator
     * @throws SQLException
     */
    public static List<Team> getAllTeamsForAdmin(Connection connection, Administrator administrator) throws SQLException {
        List<Team> teams = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_GET_TEAMS_BY_ADMIN);

            pstmt.setLong(1, administrator.getAdminId());

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                teams.add(mapTeam(resultSet));
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return teams;
    }

    /**
     * Instruction of searching team by his iname
     * @param connection - connection with database
     * @param name - team id
     * @return team from database
     * @throws SQLException - possible exception
     */
    public static Team searchTeamByName(Connection connection, String name) throws SQLException {
        Team team = new Team();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_FIND_TEAM_BY_NAME);

            pstmt.setString(1, "%" + escapeForLike(name) + "%");
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                team = mapTeam(resultSet);
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return team;
    }

    /**
     * Instruction of deleting team from database
     * @param connection - connection with database
     * @param id - team id
     * @throws SQLException - possible exception
     */
    public static void deleteTeam(Connection connection, long id) throws SQLException{
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQL_DELETE_TEAM);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
    }
}
