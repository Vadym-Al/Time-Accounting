package com.my.time.accounting.database.managers;

import com.my.time.accounting.entity.Task;

import java.sql.*;

import static com.my.time.accounting.database.managers.Utils.*;
import static com.my.time.accounting.database.SQLConstance.SQL_ADD_NEW_TASK;

public class TaskManager {
    private TaskManager(){}

    public static void insertTask(Connection connection, Task task) throws  SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_ADD_NEW_TASK, Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            pstmt.setString(k++, task.getName());
            pstmt.setDate(k++, task.getDeadline());
            pstmt.setString(k++, task.getAbout());
            pstmt.setLong(k++, task.getActivityType());
            pstmt.setLong(k++, task.getAdministratorId());

            if (pstmt.executeUpdate() > 0) {
                resultSet = pstmt.getGeneratedKeys();
                if (resultSet.next()) {
                    task.setTaskId(resultSet.getLong(1));
                }
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
    }
}
