/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flycode.coolstore.utils;

import com.flycode.coolstore.TopicsServlet;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anhay
 */
public class DBUtils {
    
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL="jdbc:mysql://remotemysql.com:3306/Ei4gDiB26n?zeroDateTimeBehavior=convertToNull";

    private static final String USER = "Ei4gDiB26n";
    private static final String PASS = "MKUmJcoYQO";
    
    public static Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);

        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
