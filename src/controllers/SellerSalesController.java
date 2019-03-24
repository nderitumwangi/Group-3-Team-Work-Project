package controllers;

import Connection.Connect;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
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
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class SellerSalesController implements Initializable{

    @FXML
    private Label lblStatus;

    @FXML
    private JFXTextField txtSaleId;

    @FXML
    private JFXTextField txtCarId;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    private JFXDatePicker txtSaleDate;
      @FXML
    private JFXTextField txtSellerId;
    
      @FXML
     TableView tblSales;
      
      
    PreparedStatement preparedStatement;
    Connection connection;
    
   public SellerSalesController(){
        connection = (Connection) Connect.conDB();
   }
   @FXML
    private void HandleEvents(ActionEvent event) {
        //check if not empty
        if (txtSaleId.getText().isEmpty() || txtCarId.getText().isEmpty() || txtSellerId.getText().isEmpty() 
                    || txtSaleDate.getValue().equals(null)) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Enter all details");
        } else {
            addSales();
        }
        
        }
    private void clearFields() {
        txtSaleId.clear();
        txtCustomerId.clear();
        txtSellerId.clear();
        txtCarId.clear();
    }
        private String addSales(){
        try{
           String car= "INSERT INTO sales (sale_id, car_id, customer_id, seller_id, sale_date) VALUES (?,?,?,?,?)" ;
            preparedStatement = (com.mysql.jdbc.PreparedStatement) connection.prepareStatement(car);
           
            preparedStatement.setString(1, txtSaleId.getText());
            preparedStatement.setString(2, txtCarId.getText());
            preparedStatement.setString(3, txtCustomerId.getText());
            preparedStatement.setString(4, txtSellerId.getText());
            preparedStatement.setString(5, txtSaleDate.getValue().toString());
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
    String SQL = "SELECT sale_id, sale_date, car_brand, car_model, color, car_price, seller_name, customer_name, customer_email  from sales, car_details, seller, customers_details "
            + "where customers_details.customer_id = sales.customer_id && seller.seller_id= sales.seller_id && car_details.car_id=sales.car_id";


    //only fetch columns
    private void fetchColumnList() {

        try {
            ResultSet rs = connection.createStatement().executeQuery(SQL);

            //SQL FOR SELECTING ALL OF CARS
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tblSales.getColumns().removeAll(col);
                tblSales.getColumns().addAll(col);

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

            tblSales.setItems(data);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       fetchColumnList();
       fetchRowList();
      //txtSaleDate.
    }
   

}
