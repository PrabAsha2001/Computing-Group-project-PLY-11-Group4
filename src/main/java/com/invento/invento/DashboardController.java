package com.invento.invento;

import javaClass.DatabaseConnection;
import javaClass.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DashboardController {
    public String  userEmail;

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }



    DatabaseConnection connectNow = new DatabaseConnection();

    LocalDate currentDate = LocalDate.now();
    String formattedDate=currentDate.toString();
    java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);
    public void getTodayOrderCount(){

        try {
            Connection connectDB = connectNow.getConnection();
            String sql="SELECT COUNT(*) AS `Order_Count` FROM `orders` WHERE DATE(`date`) = ? AND userEmail=?";

            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setDate(1, sqlDate);
            preparedStatement.setString(2, userEmail);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
