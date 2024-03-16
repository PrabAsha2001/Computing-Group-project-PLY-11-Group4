package com.invento.invento;

import javaClass.Customer;
import javaClass.DatabaseConnection;
import javaClass.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class CustomerController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<Customer, String> clmContact;

    @FXML
    private TableColumn<Customer, String> clmName;

    @FXML
    private TableView<Customer> tblCustomer;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSearch;


    private String userEmail;

    private String OldPhone;
    DatabaseConnection connectNow = new DatabaseConnection();

    // Setter method to set the User's email
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void initialize(URL url, ResourceBundle rb) {
        tblCustomer.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                handleTableClick();
            }
        });

    }

    private void handleTableClick() {
        // Get the selected item from the table
        Customer selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedCustomer != null) {
            // Display the selected employee's data in text fields
            displayCustomerDetails(selectedCustomer);
        }
    }

    private void displayCustomerDetails(Customer customer) {
        txtContact.setText(customer.getContact());
        txtName.setText(customer.getName());


        OldPhone=txtContact.getText();
    }

    public void loadData(){
        loadTable();
        clmContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));

    }

    @FXML
    private void loadTable(){
        Connection connectDB = connectNow.getConnection();
        ObservableList<Customer> customers= FXCollections.observableArrayList();
        String sql="select contact,name,email from customer where userEmail=?";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setString(1, userEmail);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                customers.add(new Customer(
                        resultSet.getString("contact"),
                        resultSet.getString("name"),
                        resultSet.getString("email")

                ));
            }

            // Set the items of your TableView to the ObservableList after the loop
            tblCustomer.setItems(customers);

            connectDB.close();
        }catch(Exception e){e.printStackTrace();};

    }
    private void clearTextFields() {
        txtPhoneNumber.clear();
        txtFullName.clear();
        txtPassword.clear();
        txtAddress.clear();
        txtType.clear();
        txtEmail.clear();
            }

}
