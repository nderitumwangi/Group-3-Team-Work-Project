package controllers;

import Connection.Connect;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class SellerFeedbackController implements Initializable{

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtCustomerContact;

    @FXML
    private JFXTextField txtSellerId;

    @FXML
    private JFXTextArea txtFeedbackDescription;

    @FXML
    private JFXDatePicker txtDate;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private TableView tblFeedback;
    @FXML
    private Label lblStatus;
    PreparedStatement preparedStatement;
    Connection connection;
    
   public SellerFeedbackController(){
        connection = (Connection) Connect.conDB();
   }
   @FXML
    private void HandleEvents(ActionEvent event) {
        //check if not empty
        if (txtCustomerName.getText().isEmpty() || 
                txtCustomerContact.getText().isEmpty() || txtSellerId.getText().isEmpty() 
                 || txtFeedbackDescription.getText().isEmpty()
                    || txtDate.getValue().equals(null)) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Enter all details");
        } else {
            addFeedback();
        }
        
        }
    private void clearFields() {
        txtSellerId.clear();
        txtCustomerName.clear();
        txtCustomerContact.clear();
        txtSellerId.clear();
        txtFeedbackDescription.clear();
    }
        private String addFeedback(){
        try{
           String car= "INSERT INTO feedback (feedback_description, seller_id,customer_name,customer_contact,   date) VALUES (?,?,?,?,?)" ;
            preparedStatement = (com.mysql.jdbc.PreparedStatement) connection.prepareStatement(car);
           
            preparedStatement.setString(1, txtFeedbackDescription.getText());
            preparedStatement.setString(2, txtSellerId.getText());
            preparedStatement.setString(3, txtCustomerName.getText());
            preparedStatement.setString(4, txtCustomerContact.getText());
            preparedStatement.setString(5, txtDate.getValue().toString());
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
    String SQL = "SELECT * from feedback";

    //only fetch columns
    private void fetchColumnList() {

        try {
            ResultSet rs = connection.createStatement().executeQuery(SQL);

            //SQL FOR SELECTING ALL OF FEEDBACKS
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tblFeedback.getColumns().removeAll(col);
                tblFeedback.getColumns().addAll(col);

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

            tblFeedback.setItems(data);
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
