package com.my.time.accounting.database.managers;

import com.my.time.accounting.entity.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.my.time.accounting.database.SQLConstance.*;

/**
 * Class that contains instructions of processing all database entities
 *
 * @author Vadym Aldyk
 * @version 1.0
 */
public class Utils {
    private static final Logger logger = LogManager.getLogger(Utils.class);

    /**
     * private constructor
     * @see Utils#Utils()
     */
    private Utils(){}

    /**
     * Method that realise auto close method
     * @param autoCloseable - java.lang interface
     */
    public static void close(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception ex) {
                logger.error("Cannot close " + autoCloseable, ex);
                throw new IllegalStateException("Cannot close " + autoCloseable, ex);
            }
        }
    }

    public static String escapeForLike(String param) {
        return param.replace("!", "!!").replace("%", "!%").replace("_", "!_").replace("[", "![");
    }

    /**
     * Instruction of mapping administrator
     * @param rs - resultSet
     * @return administrator
     * @throws SQLException- possible exception
     */
    public static Administrator mapAdministrator(ResultSet rs) throws SQLException {
        Administrator administrator = new Administrator();
        administrator.setAdminId(rs.getLong(ADMINISTRATOR_ID));
        administrator.setName(rs.getString(NAME));
        administrator.setLastName(rs.getString(LAST_NAME));
        administrator.setPassword(rs.getString(PASSWORD));
        administrator.setEmail(rs.getString(EMAIL));
        administrator.setCompany(rs.getString(COMPANY));
        administrator.setPhoneNumber(rs.getString(PHONE_NUMBER));
        administrator.setCreateTime(rs.getTimestamp(CREATE_TIME));
        return administrator;
    }

    /**
     * Instruction of mapping activity
     * @param rs - resultSet
     * @return activity
     * @throws SQLException- possible exception
     */
    public static Activity mapActivity(ResultSet rs) throws SQLException {
        Activity activity = new Activity();
        activity.setActivityId(rs.getLong(ACTIVITY_ID));
        activity.setName(rs.getString(NAME));
        activity.setAbout(rs.getString(ABOUT));
        activity.setAdministratorId(rs.getLong(ADMINISTRATOR_ID));
        return activity;
    }

    /**
     * Instruction of mapping request
     * @param rs - resultSet
     * @return request
     * @throws SQLException- possible exception
     */
    public static Request mapRequest(ResultSet rs) throws SQLException {
        Request request = new Request();
        request.setRequestId(rs.getLong("request_id"));
        request.setAbout(rs.getString(ABOUT));
        request.setActivityId(rs.getLong("activity_type_id"));
        request.setAdministratorId(rs.getLong(ADMINISTRATOR_ID));
        request.setUserId(rs.getLong("user_id"));
        return request;
    }

    /**
     * Instruction of mapping task
     * @param rs - resultSet
     * @return task
     * @throws SQLException- possible exception
     */
    public static Task mapTask(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setTaskId(rs.getLong("task_id"));
        task.setName(rs.getString(NAME));
        task.setDeadline(rs.getDate("deadline"));
        task.setAbout(rs.getString(ABOUT));
        task.setWastedTime(rs.getTime("wasted_time"));
        task.setCreateTime(rs.getTimestamp(CREATE_TIME));
        task.setActivityType(rs.getLong("activity_type_id"));
        task.setAdministratorId(rs.getLong(ADMINISTRATOR_ID));
        return task;
    }

    /**
     * Instruction of mapping team
     * @param rs - resultSet
     * @return team
     * @throws SQLException- possible exception
     */
    public static Team mapTeam(ResultSet rs) throws SQLException {
        Team team = new Team();
        team.setTeamId(rs.getLong(TEAM_ID));
        team.setName(rs.getString(NAME));
        team.setCompany(rs.getString(COMPANY));
        team.setDescription(rs.getString("description"));
        team.setAdministratorId(rs.getLong(ADMINISTRATOR_ID));
        return team;
    }

    /**
     * Instruction of mapping user
     * @param rs - resultSet
     * @return user
     * @throws SQLException- possible exception
     */
    public static User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getLong("user_id"));
        user.setName(rs.getString(NAME));
        user.setLastName(rs.getString(LAST_NAME));
        user.setPassword(rs.getString(PASSWORD));
        user.setEmail(rs.getString(EMAIL));
        user.setPhoneNumber(rs.getString(PHONE_NUMBER));
        user.setCompany(rs.getString(COMPANY));
        user.setCreateTime(rs.getTimestamp(CREATE_TIME));
        user.setTeamId(rs.getLong(TEAM_ID));
        user.setAdministratorId(rs.getLong(ADMINISTRATOR_ID));
        return user;
    }
}
