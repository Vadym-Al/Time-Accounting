package com.my.time.accounting.database;

/**
 * Class exception for database methods
 *
 * @author Vadym Aldyk
 * @version 1.0
 */
public class DBException extends Exception{
    public DBException() {
        super();
    }

    public DBException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBException(String message) {
        super(message);
    }

    public DBException(Throwable cause) {
        super(cause);
    }
}
