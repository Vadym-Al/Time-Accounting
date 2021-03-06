package com.my.time.accounting.logic;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class that hash passwords of database entities
 *
 * @author Vadym Aldyk
 * @version 1.0
 */
public class PasswordCod {
    private static final Logger logger = LogManager.getLogger(PasswordCod.class);
    private static final String ENCODING = "MD5";

    /**
     * private constructor
     * @see PasswordCod#PasswordCod()
     */
    private PasswordCod(){}

    /**
     * Method that code the password use MD5
     * @param password - input password
     * @return code
     */
    public static String encode(String password){
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(ENCODING);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Can not encode password", e);
            throw new IllegalStateException("Can not encode password", e);
        }
        assert digest != null;
        digest.update(password.getBytes());
        byte[] hash = digest.digest();
        StringBuilder str = new StringBuilder();
        for (byte b : hash) {
            str.append(String.format("%02X", b));
        }
        return str.toString();
    }
}
