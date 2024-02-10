package com.invento.invento;

import com.invento.invento.javaClass.DatabaseConnection;
import com.invento.invento.javaClass.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserLoginController {


    private String userEmail;

    // Setter method to set the User's email
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhoneNo;

    @FXML
    private Label lblMessage;

    public void AdminloginButtonOnAction(ActionEvent event){

        if(txtPhoneNo.getText().isEmpty() || txtPassword.getText().isEmpty()){
            lblMessage.setText("Please enter Phone Number and Password!");


        }else{
            LoginAdmin();
        }

    }
    public void LoginAdmin(){
        //Created an object from databaceConnection class and created database connection
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB=connectNow.getConnection();



        String type;

        String sql="SELECT * FROM employee WHERE contact=? AND password=? AND userEmail=? ";
        try{
            //Executing the sql statement
            PreparedStatement preparedStatement=connectDB.prepareStatement(sql);
            //getting data from text boxes of the fxml form
            preparedStatement.setString(1,txtPhoneNo.getText());
            preparedStatement.setString(2,txtPassword.getText());
            preparedStatement.setString(3,userEmail);

            try(ResultSet rs=preparedStatement.executeQuery()){


                if(rs.next()){
                    type=rs.getString("type");

                    if(type.equals("admin")){
                        btnLogin.getScene().getWindow().hide();
                        Parent root= FXMLLoader.load(getClass().getResource("AdminDashboard.fxml"));
                        Stage stage=new Stage();
                        Scene scene=new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }else{
                        btnLogin.getScene().getWindow().hide();
                        Parent root= FXMLLoader.load(getClass().getResource("UserDashboard.fxml"));
                        Stage stage=new Stage();
                        Scene scene=new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }


                }else{
                    lblMessage.setText(userEmail);
                }


            }





        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
