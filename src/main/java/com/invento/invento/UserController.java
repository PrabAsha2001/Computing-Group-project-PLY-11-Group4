package com.invento.invento;

import javaClass.DatabaseConnection;
import javaClass.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class UserController implements Initializable {

    private String userEmail;
    private String OldPhone;

    // Setter method to set the User's email
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public void initialize(URL url, ResourceBundle rb) {
        tblUser.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                handleTableClick();
            }
        });

    }


    DatabaseConnection connectNow = new DatabaseConnection();


    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private TableView<Employee> tblUser;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtType;

    @FXML
    private TableColumn<Employee, String> clmAddress;

    @FXML
    private TableColumn<Employee, String> clmEmail;

    @FXML
    private TableColumn<Employee, String> clmName;

    @FXML
    private TableColumn<Employee, String> clmPassword;

    @FXML
    private TableColumn<Employee, String> clmPhone;

    @FXML
    private TableColumn<Employee, String> clmType;

    private void handleTableClick() {
        // Get the selected item from the table
        Employee selectedEmployee = tblUser.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedEmployee != null) {
            // Display the selected employee's data in text fields
            displayEmployeeDetails(selectedEmployee);
        }
    }


    private void displayEmployeeDetails(Employee employee) {
        txtPhoneNumber.setText(employee.getContact());
        txtFullName.setText(employee.getName());
        txtPassword.setText(employee.getPassword());
        txtAddress.setText(employee.getAddress());
        txtType.setText(employee.getType());
        txtEmail.setText(employee.getEmail());

        OldPhone=txtPhoneNumber.getText();
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String searchTerm = txtSearch.getText().trim();
        if (!searchTerm.isEmpty()) {
            displayEmployeeDetailsBySearch(searchTerm);
        }
    }
        public void displayEmployeeDetailsBySearch(String searchTerm) {
            Connection connectDB = connectNow.getConnection();
            String sql = "SELECT contact, name, password, address, type, email FROM employee WHERE userEmail=? AND (contact LIKE ?) LIMIT 1";
            try {
                PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
                preparedStatement.setString(1, userEmail);
                preparedStatement.setString(2,  searchTerm );
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Employee resultEmployee = new Employee(
                            resultSet.getString("contact"),
                            resultSet.getString("name"),
                            resultSet.getString("password"),
                            resultSet.getString("address"),
                            resultSet.getString("type"),
                            resultSet.getString("email")
                    );
                    displayEmployeeDetails(resultEmployee);
                    OldPhone=txtPhoneNumber.getText();
                } else {
                    // Handle the case when no matching records are found
                    clearTextFields();
                }
                connectDB.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    private void clearTextFields() {
        txtPhoneNumber.clear();
        txtFullName.clear();
        txtPassword.clear();
        txtAddress.clear();
        txtType.clear();
        txtEmail.clear();
    }



    public void loadData(){

        loadTable();


        clmPhone.setCellValueFactory(new PropertyValueFactory<>("contact"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clmType.setCellValueFactory(new PropertyValueFactory<>("type"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    @FXML
    private void loadTable(){
        Connection connectDB = connectNow.getConnection();
        ObservableList<Employee> employees=FXCollections.observableArrayList();
        String sql="select contact,name,password,address,type,email from employee where userEmail=?";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setString(1, userEmail);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                employees.add(new Employee(
                        resultSet.getString("contact"),
                        resultSet.getString("name"),
                        resultSet.getString("password"),
                        resultSet.getString("address"),
                        resultSet.getString("type"),
                        resultSet.getString("email")
                ));
            }

            // Set the items of your TableView to the ObservableList after the loop
            tblUser.setItems(employees);

            connectDB.close();
        }catch(Exception e){e.printStackTrace();};

    }


    @FXML private void Add(ActionEvent event){
        Connection connectDB = connectNow.getConnection();
        String sql="insert into employee(contact,name,password,address,type,email,userEmail) values(?,?,?,?,?,?,?)";
        try{
            PreparedStatement preparedStatement=connectDB.prepareStatement(sql);
            preparedStatement.setString(1,txtPhoneNumber.getText());
            preparedStatement.setString(2,txtFullName.getText());
            preparedStatement.setString(3,txtPassword.getText());
            preparedStatement.setString(4,txtAddress.getText());
            preparedStatement.setString(5,txtType.getText());
            preparedStatement.setString(6,txtEmail.getText());
            preparedStatement.setString(7,userEmail);
            preparedStatement.executeUpdate();

            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Employee Registration");
            alert.setHeaderText("Employee Registration");
            alert.setContentText("New Employee Added Successfully!");

            alert.showAndWait();

            clearTextFields();

            loadData();
            connectDB.close();

        }catch (Exception e){e.printStackTrace();}


    }
     @FXML private void Edit(ActionEvent event){

         Connection connectDB = connectNow.getConnection();
         String sql="update employee set contact=?,name=?,password=?,address=?,type=?,email=? where contact=? AND userEmail=?";
         try{
             PreparedStatement preparedStatement=connectDB.prepareStatement(sql);
             preparedStatement.setString(1,txtPhoneNumber.getText());
             preparedStatement.setString(2,txtFullName.getText());
             preparedStatement.setString(3,txtPassword.getText());
             preparedStatement.setString(4,txtAddress.getText());
             preparedStatement.setString(5,txtType.getText());
             preparedStatement.setString(6,txtEmail.getText());
             preparedStatement.setString(7,OldPhone);
             preparedStatement.setString(8,userEmail);
             preparedStatement.executeUpdate();

             Alert alert=new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Employee Updation");
             alert.setHeaderText("Employee Updation");
             alert.setContentText("Employee Details updated successfully !");

             alert.showAndWait();

             clearTextFields();

             loadData();
             connectDB.close();

         }catch (Exception e){
             Alert alert=new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Employee Updation");
             alert.setHeaderText("Employee Updation");
             alert.setContentText("To change the contact informat");
             e.printStackTrace();
         }


     }
    @FXML private void Delete(ActionEvent event) {

        Connection connectDB = connectNow.getConnection();
        String sql = "delete from employee where contact=? AND userEmail=?";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setString(1, txtPhoneNumber.getText());
            preparedStatement.setString(2, userEmail);
            preparedStatement.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Employee Updation");
            alert.setHeaderText("Employee Updation");
            alert.setContentText("Employee Details updated successfully !");

            alert.showAndWait();

            clearTextFields();

            loadData();
            connectDB.close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Employee Updation");
            alert.setHeaderText("Employee Updation");
            alert.setContentText("To change the contact informat");
            e.printStackTrace();
        }


    }



}
