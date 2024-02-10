package com.invento.invento;

import com.invento.invento.javaClass.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserLoginController {

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhoneNo;

    public void LoginAdmin(){
        //Created an object from databaceConnection class and created database connection
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB=connectNow.getConnection();

        String sql="SELECT * FROM admin WHERE contact=? AND password=?";
        try{
            //Executing the sql statement
            PreparedStatement preparedStatement=connectDB.prepareStatement(sql);
            //getting data from text boxes of the fxml form
            preparedStatement.setString(1,txtPhoneNo.getText());
            preparedStatement.setString(2,txtPassword.getText());

            ResultSet rs=preparedStatement.executeQuery();
            Alert alert;


            if(txtPhoneNo.getText().isBlank() || txtPassword.getText().isBlank()){
                //display an alert if username or password is empty
                alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please enter the Phone Number and Password");
            }else{
                if(rs.next()){

                }else{
                    //display an error message is username or password is wrong
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Phone Number or Password");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
