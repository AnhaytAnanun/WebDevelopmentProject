/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flycode.coolstore.utils;

import com.flycode.coolstore.AmenitiesServlet;
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
    
    public static void resultsToXml(PrintWriter writer, ResultSet results) throws SQLException {
        writer.append("<property>");
        writer.append("<property_id>").append(String.valueOf(results.getInt("PropertyId"))).append("</property_id>");
        writer.append("<address>").append(String.valueOf(results.getString("address"))).append("</address>");
        writer.append("<city>").append(String.valueOf(results.getString("city"))).append("</city>");
        writer.append("<state>").append(String.valueOf(results.getString("state"))).append("</state>");
        writer.append("<zip>").append(String.valueOf(results.getString("zip"))).append("</zip>");
        writer.append("<image>").append(String.valueOf(results.getString("image"))).append("</image>");
        writer.append("<description>").append(String.valueOf(results.getString("description"))).append("</description>");
        writer.append("<rent>").append(String.valueOf(results.getString("rent"))).append("</rent>");
        writer.append("<bedrooms>").append(String.valueOf(results.getString("bedrooms"))).append("</bedrooms>");
        writer.append("<bathrooms>").append(String.valueOf(results.getString("bathrooms"))).append("</bathrooms>");
        writer.append("</property>");
    }
}
