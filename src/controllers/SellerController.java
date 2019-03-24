package controllers;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import Connection.Connect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class SellerController implements Initializable{

    @FXML
    private TextField sellerName;

    @FXML
    private TextField sellerEmail;

    @FXML
    private TextField sellerUsername;

    @FXML
    private TextField sellerPassword;

    @FXML
    private TextField sellerNationalID;

    @FXML
    private TextField sellerPhoneNumber;
    
    @FXML
     private Label lblStatus;
        
    @FXML
    TableView tblSellers;
    
    PreparedStatement preparedStatement;
    Connection connection;

    public SellerController(){
        connection = (Connection) Connect.conDB();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fetchColumnList();
       fetchRowList();
     
    }
    
    @FXML
    public void HandleEvents(ActionEvent event){
               if (sellerName.getText().isEmpty() || sellerEmail.getText().isEmpty() 
                || sellerUsername.getText().isEmpty() || sellerPassword.getText().isEmpty()
                || sellerNationalID.getText().isEmpty() ||sellerPhoneNumber.getText().isEmpty()
                    ) {
            lblStatus.setTextFill(Color.WHITE);
            lblStatus.setText("Enter all details");
        } else {
            addSeller();
        } 
    }
     private void clearFields() {
       sellerName.clear();
       sellerEmail.clear();
       sellerPhoneNumber.clear();
       sellerUsername.clear();
       sellerPassword.clear();
       sellerNationalID.clear();
    }
     private void Cancel(ActionEvent event){
         clearFields();
     }
        private String addSeller(){
        try{
           String seller = "INSERT INTO seller(seller_name, seller_email, seller_phone_number, seller_national_id, seller_username, seller_password) VALUES (?,?,?,?,?,?)" ;
           
            preparedStatement = (com.mysql.jdbc.PreparedStatement) connection.prepareStatement(seller);
           
            preparedStatement.setString(1, sellerName.getText());
            preparedStatement.setString(2, sellerEmail.getText());
            preparedStatement.setString(3, sellerPhoneNumber.getText());
            preparedStatement.setString(4, sellerNationalID.getText());
            preparedStatement.setString(5, sellerUsername.getText());
            preparedStatement.setString(6, sellerPassword.getText());
            preparedStatement.executeUpdate();
            lblStatus.setTextFill(Color.WHITE);
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
    String SQL = "SELECT * from seller";

    //only fetch columns
    private void fetchColumnList() {

        try {
            ResultSet rs = connection.createStatement().executeQuery(SQL);

            //SQL FOR SELECTING ALL OF SELLERS
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tblSellers.getColumns().removeAll(col);
                tblSellers.getColumns().addAll(col);

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

            tblSellers.setItems(data);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
        
        
}
