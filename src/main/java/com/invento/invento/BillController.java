package com.invento.invento;

import javaClass.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Formattable;
import java.util.List;

public class BillController {


    @FXML
    private Button btnAddCustomer;

    @FXML
    private Button btnAddItem;

    @FXML
    private Button btnAddNewCustomer;

    @FXML
    private Button btnPurchase;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSearchCustomer;

    @FXML
    private Button btnSearchItem;

    @FXML
    private TableColumn<String, Bill> clmBillProduct;

    @FXML
    private TableColumn<Integer, Bill> clmBillQuantity;

    @FXML
    private Label lblTotal;

    @FXML
    private TableView<Bill> tblBill;

    @FXML
    private TextField txtBuyQuantity;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtProductPrice;

    @FXML
    private TextField txtSearchCustomer;

    @FXML
    private TextField txtSearchProduct;
    private String userEmail;
    private String CusContact;
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    private List<Product> selectedProducts = new ArrayList<>();


    DatabaseConnection connectNow = new DatabaseConnection();

    private Float Total = 0.0f;
    Float subTotal;
    @FXML
    private void handleAddItem(ActionEvent event) {
        String searchTerm = txtSearchProduct.getText().trim();
        if (!searchTerm.isEmpty()) {
            Product product = getProductDetailsBySearch(searchTerm);
            if (product != null) {
                Float price=Float.parseFloat(String.valueOf(txtProductPrice.getText()));
                Float qty=Float.parseFloat(String.valueOf(txtBuyQuantity.getText()));
                Total=Total+(price+qty);
                selectedProducts.add(product);
                lblTotal.setText(Total.toString()   );


            }
        }
    }
    private Product getProductDetailsBySearch(String searchTerm) {
        Connection connectDB = connectNow.getConnection();
        String sql = "SELECT * FROM product WHERE userEmail=? AND (code LIKE ?) LIMIT 1";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setString(1, userEmail);
            preparedStatement.setString(2, searchTerm);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String proCode=resultSet.getString("code");
                String proName = resultSet.getString("productName");
                float proPrice = resultSet.getFloat("price");
                String proCategory=resultSet.getString("category");
                String proDescription=resultSet.getString("description");
                int proQty =Integer.parseInt(txtBuyQuantity.getText());



                // Return a Product object with retrieved details
                return new Product(proCode,proName,proPrice,proCategory,proDescription,proQty);
            } else {
                // Handle the case where no product is found (return null or throw an exception)
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception according to your needs (return null or throw an exception)
            return null;
        } finally {
            try {
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception according to your needs

            }
        }
    }

    public String cusContact="";
    public Date date;
    public Time time;
    @FXML
    private void handlePurchase(ActionEvent event) {
        // Assuming you have a table named "orders" with columns like orderId, customerContact, productName, price, quantity, etc.

        CusContact=txtSearchCustomer.getText();

        if (CusContact != null && !selectedProducts.isEmpty()) {
            Connection connectDB = connectNow.getConnection();


            LocalTime currentTime = LocalTime.now();
            String formattedTime=currentTime.toString();
            LocalDate currentDate = LocalDate.now();
            String formattedDate=currentDate.toString();

            java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);
            java.sql.Time sqlTime = java.sql.Time.valueOf(currentTime);

            String insertOrderSQL = "INSERT INTO orders (cusContact,proCode,date,time,qty,userEmail) VALUES (?,?,?,?,?,?)";

            cusContact=CusContact;
            date=sqlDate;
            time=sqlTime;
            try {
                PreparedStatement orderStatement = connectDB.prepareStatement(insertOrderSQL);

                for (Product product : selectedProducts) {
                    orderStatement.setString(1, txtSearchCustomer.getText());
                    orderStatement.setString(2, product.getCode());
                    orderStatement.setDate(3, sqlDate);
                    orderStatement.setTime(4, sqlTime);
                    orderStatement.setInt(5, product.getQty());
                    orderStatement.setString(6,userEmail);

                    orderStatement.executeUpdate();
                }

                connectDB.close();
                // Clear the selected products list after purchase.
                selectedProducts.clear();
                // Optionally, update UI or perform other actions after purchase.

            } catch (Exception e) {
                e.printStackTrace();
                // Handle the exception according to your needs.
            }
        }

        loadData();
    }





    @FXML
    private void handleCustomerSearch(ActionEvent event) {
        String searchTerm = txtSearchCustomer.getText().trim();
        if (!searchTerm.isEmpty()) {
            String customerName = displayCustomerDetailsBySearch(searchTerm);
            txtCustomerName.setText(customerName);
        }
    }


    public String displayCustomerDetailsBySearch(String searchTerm) {
        Connection connectDB = connectNow.getConnection();
        String sql = "SELECT name FROM customer WHERE userEmail=? AND (contact LIKE ?) LIMIT 1";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setString(1, userEmail);
            preparedStatement.setString(2, searchTerm);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String cusName = resultSet.getString("name");
                connectDB.close();
                return cusName;
            } else {
                connectDB.close();
                return null; // or an empty string, depending on your requirements
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // or handle the exception according to your needs
        }
    }

    @FXML
    private void AddCustomerForPurchase(ActionEvent event){

        CusContact=txtSearchCustomer.getText();
    }

    @FXML
    private void handleProductSearch(ActionEvent event) {
        String searchTerm = txtSearchProduct.getText().trim();
        if (!searchTerm.isEmpty()) {
            displayProductDetailsBySearch(searchTerm);
        }
    }


    public void displayProductDetailsBySearch(String searchTerm) {

        Connection connectDB = connectNow.getConnection();
        String sql = "SELECT * FROM product WHERE userEmail=? AND (code LIKE ?) LIMIT 1";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setString(1, userEmail);
            preparedStatement.setString(2, searchTerm);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String proName = resultSet.getString("productName");
                float proPrice = resultSet.getFloat("price");
                int proQty = resultSet.getInt("qty");
                txtProductName.setText(proName);
                txtProductPrice.setText(String.valueOf(proPrice));
                connectDB.close();

            } else {
                connectDB.close();
                txtProductName.setText("hi");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void loadData(){
        loadTable();
        clmBillProduct.setCellValueFactory(new PropertyValueFactory<>("productName"));
        clmBillQuantity.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));

    }

    @FXML
    private void loadTable(){
        Connection connectDB = connectNow.getConnection();
        ObservableList<Bill> bill= FXCollections.observableArrayList();
        String sql="SELECT p.productName AS `Product_Name`, o.qty AS `Quantity` FROM `orders` o JOIN product p ON o.proCode = p.code WHERE o.date = ? AND o.time = ? AND o.cusContact = ? AND o.userEmail=?;\n";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setDate(1, date);
            preparedStatement.setTime(2, time);
            preparedStatement.setString(3,cusContact)
            ;preparedStatement.setString(4,userEmail);


            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bill.add(new Bill(
                        resultSet.getString("product_name"),
                        resultSet.getInt("Quantity")


                ));
            }

            // Set the items of your TableView to the ObservableList after the loop
            tblBill.setItems(bill);

            connectDB.close();
        }catch(Exception e){e.printStackTrace();};

    }
    public void clearData(){
        txtProductPrice.setText("");
    }

}

