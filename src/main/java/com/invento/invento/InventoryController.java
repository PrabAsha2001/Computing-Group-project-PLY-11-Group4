package com.invento.invento;

import javaClass.DatabaseConnection;
import javaClass.Employee;
import javaClass.Product;
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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    @FXML
    private Button btnAddNew;

    @FXML
    private Button btnAddToInventory;

    @FXML
    private TableColumn<Product, String> clmCategory;

    @FXML
    private TableColumn<Product, String> clmCode;

    @FXML
    private TableColumn<Product, String> clmDescription;

    @FXML
    private TableColumn<Product, String> clmName;

    @FXML
    private TableColumn<Product, Float> clmPrice;

    @FXML
    private TableColumn<Product, Integer> clmQty;

    @FXML
    private TableView<Product> tblInventory;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtNewItemCode;

    @FXML
    private TextField txtOldItemCode;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSuppContact;

    @FXML
    private TextField txtTotal;

    @FXML
    private TextField txtCodeEdit;

    @FXML
    private TextField txtProductNameEdit;

    @FXML
    private TextField txtPriceEdit;

    @FXML
    private TextField txtCategoryEdit;

    @FXML
    private TextField txtDescriptionEdit;

    @FXML
    private TextField txtQtyEdit;

    @FXML
    private TextField txtSearch;

    private String OldCode;

    public void initialize(URL url, ResourceBundle rb) {
        tblInventory.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                handleTableClick();
            }
        });
    }

    private String userEmail;

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    DatabaseConnection connectNow = new DatabaseConnection();

    @FXML
    private void handleSearch(ActionEvent event) {
        String searchTerm = txtSearch.getText().trim();
        if (!searchTerm.isEmpty()) {
            displayProductDetailsBySearch(searchTerm);
        }
    }

    private void handleTableClick() {
        // Get the selected item from the table
        Product selectedProduct = tblInventory.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedProduct != null) {
            // Display the selected employee's data in text fields
            displayProductDetails(selectedProduct);
        }
    }

    private void displayProductDetails(Product product) {
        txtCodeEdit.setText(product.getCode());
        txtProductNameEdit.setText(product.getProductName());
        txtPriceEdit.setText(String.valueOf(product.getPrice()));
        txtCategoryEdit.setText(product.getCategory());
        txtDescriptionEdit.setText(product.getDescription());
        txtQtyEdit.setText(String.valueOf(product.getQty()));

        OldCode = txtCodeEdit.getText();
    }

    public void displayProductDetailsBySearch(String searchTerm) {
        Connection connectDB = connectNow.getConnection();
        String sql = "SELECT code, productName, price, category, description, qty FROM product WHERE userEmail=? AND (code LIKE ?) LIMIT 1";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setString(1, userEmail);
            preparedStatement.setString(2, searchTerm);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Product resultProduct = new Product(
                        resultSet.getString("code"),
                        resultSet.getString("productName"),
                        resultSet.getFloat("price"),
                        resultSet.getString("category"),
                        resultSet.getString("description"),
                        resultSet.getInt("qty")
                );
                displayProductDetails(resultProduct);

            } else {
                // Handle the case when no matching records are found
                //clearTextFields();
            }

            connectDB.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Add(ActionEvent event) {
        Connection connectDB = connectNow.getConnection();
        String sql = "insert into product(code,productName,price,category,description,userEmail,qty) values(?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setString(1, txtNewItemCode.getText());
            preparedStatement.setString(2, txtItemName.getText());
            preparedStatement.setFloat(3, Float.parseFloat(txtPrice.getText()));
            preparedStatement.setString(4, txtCategory.getText());
            preparedStatement.setString(5, txtDescription.getText());
            preparedStatement.setString(6, userEmail);
            preparedStatement.setInt(7, 0);
            preparedStatement.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Employee Registration");
            alert.setHeaderText("Employee Registration");
            alert.setContentText("New Employee Added Successfully!");

            alert.showAndWait();

            //clearTextFields();

            //loadData();
            connectDB.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void AddToInventory(ActionEvent event) {

        Date date = Date.valueOf(LocalDate.now());

        Connection connectDB = connectNow.getConnection();


        String sql = "insert into supplier_product(suppContact,itemCode,qty,date,userEmail,price) values(?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setString(1, txtSuppContact.getText());
            preparedStatement.setString(2, txtOldItemCode.getText());
            preparedStatement.setInt(3, Integer.parseInt(txtQty.getText()));
            preparedStatement.setDate(4, date);
            preparedStatement.setString(5, userEmail);
            preparedStatement.setFloat(6, Float.parseFloat(txtTotal.getText()));
            preparedStatement.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Employee Registration");
            alert.setHeaderText("Employee Registration");
            alert.setContentText("New Employee Added Successfully!");

            alert.showAndWait();

            //clearTextFields();

            //loadData();
            connectDB.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void loadData01() {

        loadTable01();

        clmCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        clmPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        clmCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        clmQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        clmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

    }

    private void loadTable01() {
        Connection connectDB = connectNow.getConnection();
        ObservableList<Product> products = FXCollections.observableArrayList();
        String sql = "select code,productName,price,category,qty,description from product where userEmail=?";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setString(1, userEmail);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                products.add(new Product(
                        resultSet.getString("code"),
                        resultSet.getString("ProductName"),
                        resultSet.getFloat("price"),
                        resultSet.getString("category"),
                        resultSet.getString("description"),
                        resultSet.getInt("qty")


                ));
            }

            // Set the items of your TableView to the ObservableList after the loop
            tblInventory.setItems(products);

            connectDB.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;

    }

    @FXML
    private void Delete(ActionEvent event) {

        Connection connectDB = connectNow.getConnection();
        String sql = "delete from product where code=? AND userEmail=?";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setString(1, txtCodeEdit.getText());
            preparedStatement.setString(2, userEmail);
            preparedStatement.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Product");
            alert.setHeaderText("Delete Product");
            alert.setContentText("Product Details deleted successfully !");

            alert.showAndWait();

            //clearTextFields();

            loadData01();
            connectDB.close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Employee Updation");
            alert.setHeaderText("Employee Updation");
            alert.setContentText("To change the contact informat");
            e.printStackTrace();
        }


    }

    @FXML
    private void Edit(ActionEvent event) {

        Connection connectDB = connectNow.getConnection();
        String sql = "update product set code=?,productName=?,price=?,category=?,description=?,qty=? where code=? AND userEmail=?";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setString(1, txtCodeEdit.getText());
            preparedStatement.setString(2, txtProductNameEdit.getText());
            preparedStatement.setFloat(3, Float.parseFloat(txtPriceEdit.getText()));
            preparedStatement.setString(4, txtCategoryEdit.getText());
            preparedStatement.setString(5, txtDescriptionEdit.getText());
            preparedStatement.setInt(6, Integer.parseInt(txtQtyEdit.getText()));
            preparedStatement.setString(7, OldCode);
            preparedStatement.setString(8, userEmail);
            preparedStatement.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product Updation");
            alert.setHeaderText("Product Updation");
            alert.setContentText("Product Details updated successfully !");

            alert.showAndWait();

            //clearTextFields();

            loadData01();
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