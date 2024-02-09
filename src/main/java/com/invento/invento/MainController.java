package com.invento.invento;

import com.invento.invento.javaClass.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
        validateLogin();

        if(txtEmail.getText().isBlank()==false && txtPassword.getText().isBlank()==false){

        }else{
            lblMessage.setText("Please enter the username and password!");
        }
    }

    public void validateLogin(){

        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB=connectNow.getConnection();

        String verifyLogin="SELECT count(1) FROM user WHERE email='"+txtEmail.getText()+"' AND password='"+txtPassword.getText()+"' ";

        try{
            Statement statement=connectDB.createStatement();
            ResultSet rs=statement.executeQuery(verifyLogin);

            while (rs.next()){
                if (rs.getInt(1)==1) {
                    lblMessage.setText("Welcome");

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

}