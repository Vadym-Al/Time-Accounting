package com.my.time.accounting.database.managers;

import com.my.time.accounting.entity.Administrator;
import com.my.time.accounting.entity.Team;
import com.my.time.accounting.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.time.accounting.database.SQLConstance.*;
import static com.my.time.accounting.database.managers.Utils.*;

public class TeamManager implements Manager<Team, User>{

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

    @Override
    public void insertRaw(Connection connection, Team entity) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_ADD_NEW_TEAM, Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            pstmt.setString(k++, entity.getName());
            pstmt.setString(k++, entity.getCompany());
            pstmt.setString(k++, entity.getDescription());
            pstmt.setLong(k++, entity.getAdministratorId());

            if (pstmt.executeUpdate() > 0) {
                resultSet = pstmt.getGeneratedKeys();
                if (resultSet.next()) {
                    entity.setTeamId(resultSet.getLong(1));
                }
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
    }

    @Override
    public List<Team> getAllActivitiesByRequest(Connection connection, User request) throws SQLException {
        List<Team> teams = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_GET_TEAMS_BY_USER);

            pstmt.setLong(1, request.getUserId());
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

    @Override
    public Team getEntityById(Connection connection, long id) throws SQLException {
        return null;
    }

    @Override
    public Team getEntityByName(Connection connection, String name) throws SQLException {
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

    @Override
    public void deleteEntity(Connection connection, long id) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQL_DELETE_TEAM);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
    }

    @Override
    public String getErrorInfo() {
        return "Team";
    }
}
