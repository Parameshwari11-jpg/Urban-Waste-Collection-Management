package com.waste.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    public static Connection getDBConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@//Paramesh:1521/xe";
            String user = "system";
            String password = "system123";
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
    }
}
