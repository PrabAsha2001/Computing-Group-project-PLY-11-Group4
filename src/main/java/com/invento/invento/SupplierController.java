package com.invento.invento;

import javaClass.DatabaseConnection;
import javaClass.Employee;
import javaClass.Supplier;
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


public class SupplierController implements Initializable {

    private String userEmail;
    private String OldPhone;

    DatabaseConnection connectNow = new DatabaseConnection();

    // Setter method to set the User's email
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void initialize(URL url, ResourceBundle rb) {
        tblSupplier.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Single click
                handleTableClick();
            }
        });


    }

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private TableView<Supplier> tblSupplier;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFullName;


    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableColumn<Supplier, String> clmAddress;

    @FXML
    private TableColumn<Supplier, String> clmEmail;

    @FXML
    private TableColumn<Supplier, String> clmName;

    @FXML
    private TableColumn<Supplier, String> clmPhone;


    private void handleTableClick() {
        // Get the selected item from the table
        Supplier selectedSupplier = tblSupplier.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedSupplier != null) {
            // Display the selected employee's data in text fields
            displaySupplierDetails(selectedSupplier);
        }
    }
    private void displaySupplierDetails(Supplier supplier) {
        txtPhoneNumber.setText(supplier.getContact());
        txtFullName.setText(supplier.getName());
        txtAddress.setText(supplier.getAddress());
        txtEmail.setText(supplier.getEmail());

        OldPhone=txtPhoneNumber.getText();
    }
    @FXML
    private void handleSearch(ActionEvent event) {
        String searchTerm = txtSearch.getText().trim();
        if (!searchTerm.isEmpty()) {
            displaySupplierDetailsBySearch(searchTerm);
        }
    }

    public void displaySupplierDetailsBySearch(String searchTerm) {
        Connection connectDB = connectNow.getConnection();
        String sql = "SELECT contact, name, address, email FROM supplier WHERE userEmail=? AND (contact LIKE ?) LIMIT 1";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setString(1, userEmail);
            preparedStatement.setString(2,  searchTerm );
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Supplier resultSupplier = new Supplier(
                        resultSet.getString("contact"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("email")
                );
                displaySupplierDetails(resultSupplier);
            } else {
                // Handle the case when no matching records are found
                clearTextFields();
            }
            connectDB.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData(){

        loadTable();

        clmPhone.setCellValueFactory(new PropertyValueFactory<>("contact"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    private void loadTable(){
        Connection connectDB = connectNow.getConnection();
        ObservableList<Supplier> suppliers=FXCollections.observableArrayList();
        String sql="select contact,name,address,email from supplier where userEmail=?";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setString(1, userEmail);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                suppliers.add(new Supplier(
                        resultSet.getString("contact"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("email")

                ));
            }

            // Set the items of your TableView to the ObservableList after the loop
            tblSupplier.setItems(suppliers);

            connectDB.close();
        }catch(Exception e){e.printStackTrace();};




    }
    private void clearTextFields() {
        txtPhoneNumber.clear();
        txtFullName.clear();

        txtAddress.clear();

        txtEmail.clear();
    }



}
