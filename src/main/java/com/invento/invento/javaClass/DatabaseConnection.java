package com.invento.invento.javaClass;

import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
        String databaseName = "invento";
        String databaseUser = "root";
        String databasePass = "";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink=DriverManager.getConnection(url,databaseUser,databasePass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }

}
