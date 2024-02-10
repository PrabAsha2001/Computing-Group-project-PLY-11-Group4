package com.invento.invento;

import com.invento.invento.javaClass.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainController {

    @FXML
   private Button btnLogin;

    @FXML
    private Button btnClose;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label lblMessage;

    public void closeButtonOnAction(ActionEvent event){
        Stage stage=(Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void loginButtonOnAction(ActionEvent event){


        if(txtEmail.getText().isBlank() || txtPassword.getText().isBlank()){
            lblMessage.setText("Please enter the username and password!");

        }else{
            validateLogin();
        }
    }

    public void validateLogin(){

        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB=connectNow.getConnection();

        String sql="SELECT count(1) FROM user WHERE email='"+txtEmail.getText()+"' AND password='"+txtPassword.getText()+"' ";

        try{
            Statement statement=connectDB.createStatement();
            ResultSet rs=statement.executeQuery(sql);

            while (rs.next()){
                if (rs.getInt(1)==1) {
                    lblMessage.setText("Welcome");

                    btnLogin.getScene().getWindow().hide();
                    Parent root= FXMLLoader.load(getClass().getResource("UserLogin.fxml"));
                    Stage stage=new Stage();
                    Scene scene=new Scene(root);

                    stage.setScene(scene);
                    stage.show();

                }

            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }


    }

}