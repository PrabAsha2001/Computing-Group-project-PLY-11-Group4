package com.invento.invento;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class AdminDashboardController {

    private String userEmail;

    // Setter method to set the User's email
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @FXML
    private Button btnBill;

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnInventory;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnSupplier;

    @FXML
    private Button btnUser;

    @FXML
    private AnchorPane pnlMain;

    @FXML
    private void handleWindow(ActionEvent event){
        if(event.getSource()==btnDashboard){

            loadDashboard();
            
        } else if (event.getSource()==btnUser) {
            
        } else if (event.getSource()==btnInventory) {

        }else if(event.getSource()==btnCustomer){
            loadCustomer();

        }else if(event.getSource()==btnSupplier){

        }else if(event.getSource()==btnOrders){

        }else if(event.getSource()==btnBill){

        }
    }

    private void loadCustomer() {
        try {
            // Load the customer FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customer.fxml"));
            AnchorPane customerPane = loader.load();

            // Set the customer FXML content into the anchor pane
            pnlMain.getChildren().setAll(customerPane);

            CustomerController customerController = loader.getController();
            customerController.setUserEmail(userEmail);

        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    private void loadDashboard() {
        try {
            // Load the customer FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PanelDashboard.fxml"));
            AnchorPane pnlDashbpardPane = loader.load();

            // Set the customer FXML content into the anchor pane
            pnlMain.getChildren().setAll(pnlDashbpardPane);


        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }


}
