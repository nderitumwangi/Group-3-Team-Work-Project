package controllers;

import Connection.Connect;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class CustomersController implements Initializable{

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtCustomerEmail;

    @FXML
    private TextField txtCustomerNo;

    @FXML
    private TextField txtCustomer_Id;

    @FXML
    private Label lblStatus;
    
    @FXML
     TableView tblCustomers;
    
    PreparedStatement preparedStatement;
    Connection connection;
    
   public CustomersController(){
        connection = (Connection) Connect.conDB();
   }
    @FXML
    private void HandleEvents(ActionEvent event) {
        //check if not empty
        if (txtCustomerName.getText().isEmpty() || 
                txtCustomerEmail.getText().isEmpty() || txtCustomerNo.getText().isEmpty() 
                 || txtCustomer_Id.getText().isEmpty()
                    ) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Enter all details");
        } else {
            addCustomer();
        }
        
        }
    private void clearFields() {
        txtCustomerName.clear();
        txtCustomerEmail.clear();
        txtCustomer_Id.clear();
        txtCustomerNo.clear();
        
    }
        private String addCustomer(){
        try{
           String car= "INSERT INTO customers_details (customer_name,customer_email,customer_no, customer_national_id) VALUES (?,?,?,?)" ;
            preparedStatement = (com.mysql.jdbc.PreparedStatement) connection.prepareStatement(car);
           
            preparedStatement.setString(1, txtCustomerName.getText());
            preparedStatement.setString(2, txtCustomerEmail.getText());
            preparedStatement.setString(3, txtCustomerNo.getText());
            preparedStatement.setString(4, txtCustomer_Id.getText());
           
            preparedStatement.executeUpdate();
            lblStatus.setTextFill(Color.BLACK);
            lblStatus.setText("Added Successfully");
            
            clearFields();
            return "Success";
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText(ex.getMessage());
            return "Exception";
        }
        
    }
          private ObservableList<ObservableList> data;
    String SQL = "SELECT * from customers_details";

    //only fetch columns
    private void fetchColumnList() {

        try {
            ResultSet rs = connection.createStatement().executeQuery(SQL);

            //SQL FOR SELECTING ALL OF CUSTOMERS
            
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tblCustomers.getColumns().removeAll(col);
                tblCustomers.getColumns().addAll(col);

                System.out.println("Column [" + i + "] ");

            }

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }
    }

    //fetches rows and data from the list
    private void fetchRowList() {
        data = FXCollections.observableArrayList();
        ResultSet rs;
        try {
            rs = connection.createStatement().executeQuery(SQL);

            while (rs.next()) {
                //Iterate Row
                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }

            tblCustomers.setItems(data);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fetchColumnList();
        fetchRowList();
    }

}
