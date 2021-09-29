package com.my.time.accounting.database;

/**
 * Class constants for SQL database
 *
 * @author Vadym Aldyk
 * @version 1.0
 */
public class SQLConstance {
    private SQLConstance(){}

    public static final String NAME = "name";
    public static final String LAST_NAME = "last_name";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String COMPANY = "company";
    public static final String ABOUT = "about";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String CREATE_TIME = "create_time";
    public static final String ADMINISTRATOR_ID = "administrator_id";
    public static final String ACTIVITY_ID = "activity_id";
    public static final String TEAM_ID = "team_id";
    public static final String ERROR = "error";

    //--------------------------------------------------------

    public static final String SQL_FIND_ADMINISTRATOR_BY_EMAIL = "SELECT * FROM administrator WHERE email LIKE ? ESCAPE '!'";
    public static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM user WHERE email LIKE ? ESCAPE '!'";
    public static final String SQL_FIND_TEAM_BY_NAME = "SELECT * FROM team WHERE name LIKE ? ESCAPE '!'";
    public static final String SQL_FIND_ACTIVITY_BY_NAME = "SELECT * FROM activity_type WHERE name LIKE ? ESCAPE '!'";
    public static final String SQL_FIND_ACTIVITY_BY_ID = "SELECT * FROM activity_type WHERE activity_id LIKE ?";
    public static final String SQL_FIND_USER_BY_NAME = "SELECT * FROM user WHERE name LIKE ?";
    public static final String SQL_FIND_USER_BY_TEAM_ID = "SELECT * FROM user WHERE team_id LIKE ?";
    public static final String SQL_FIND_USER_BY_ID = "SELECT * FROM user WHERE user_id LIKE ?";
    public static final String SQL_FIND_ADMINISTRATOR_BY_ID = "SELECT * FROM administrator WHERE administrator_id LIKE ?";
    public static final String SQL_FIND_TASK_BY_NAME = "SELECT * FROM task WHERE name LIKE ? ESCAPE '!'";

    //--------------------------------------------------------

    public static final String SQL_GET_ACTIVITIES_BY_ADMIN = "SELECT * FROM activity_type WHERE administrator_id LIKE ?";
    public static final String SQL_GET_REQUESTS_BY_ADMIN = "SELECT * FROM request WHERE administrator_id LIKE ?";
    public static final String SQL_GET_TEAMS_BY_ADMIN = "SELECT * FROM team WHERE administrator_id LIKE ?";
    public static final String SQL_GET_USERS_BY_ADMIN = "SELECT * FROM user WHERE administrator_id LIKE ?";
    public static final String SQL_GET_TASKS_BY_ADMIN = "SELECT * FROM task WHERE administrator_id LIKE ?";
    public static final String SQL_GET_TASKS_BY_ID = "SELECT * FROM task WHERE task_id LIKE ?";
    public static final String SQL_GET_USER_HAS_TASK_BY_USER = "SELECT * FROM user_has_task WHERE user_id LIKE ?";
    public static final String SQL_GET_REQUEST_BY_ID = "SELECT * FROM request WHERE request_id LIKE ?";

    public static final String SQL_GET_TEAMS_BY_USER = "SELECT * FROM team WHERE team_id LIKE ?";
    public static final String SQL_GET_REQUESTS_BY_USER = "SELECT * FROM request WHERE user_id LIKE ?";

    //--------------------------------------------------------

    public static final String SQL_ADD_NEW_ADMINISTRATOR = "INSERT INTO administrator (name, last_name, password, email, phone_number, company) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SQL_ADD_NEW_USER = "INSERT INTO user (name, last_name, password, email, phone_number, company, team_id, administrator_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_ADD_TASK_TO_USER = "INSERT INTO user_has_task (user_id, task_id) VALUES (?, ?)";
    public static final String SQL_ADD_NEW_TASK = "INSERT INTO task (name, deadline, about, activity_type_id, administrator_id) VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_ADD_NEW_TEAM = "INSERT INTO team (name, company, description, administrator_id) VALUES (?, ?, ?, ?)";
    public static final String SQL_ADD_NEW_REQUEST = "INSERT INTO request (about, activity_type_id, administrator_id, user_id) VALUES (?, ?, ?, ?)";
    public static final String SQL_ADD_NEW_ACTIVITY_TYPE = "INSERT INTO activity_type (name, about, administrator_id) VALUES (?, ?, ?)";

    //--------------------------------------------------------

    public static final String SQL_DELETE_TEAM = "DELETE FROM team WHERE team_id=?";
    public static final String SQL_DELETE_ACTIVITY = "DELETE FROM activity_type WHERE activity_id=?";
    public static final String SQL_DELETE_REQUEST = "DELETE FROM request WHERE request_id=?";
    public static final String SQL_DELETE_TASK = "DELETE FROM task WHERE task_id=?";
    public static final String SQL_DELETE_USER_HAS_TASK_USER = "DELETE FROM user_has_task WHERE user_id=?";
    public static final String SQL_DELETE_USER_HAS_TASK_TASK = "DELETE FROM user_has_task WHERE task_id=?";
    public static final String SQL_DELETE_USER = "DELETE FROM user WHERE user_id=?";
    public static final String SQL_DELETE_REQUEST_FOR_USER = "DELETE FROM request WHERE user_id=?";

    //--------------------------------------------------------

    public static final String SQL_UPDATE_TASK = "UPDATE task SET wasted_time=? WHERE task_id=?";
    public static final String SQL_GET_ALL_TASKS_BY_ADMIN_SORT = "SELECT * FROM task WHERE administrator_id LIKE ? ORDER BY wasted_time DESC";
}
