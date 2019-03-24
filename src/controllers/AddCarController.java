package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import Connection.Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class AddCarController implements Initializable{

    @FXML
    private TextField txtCarBrand;

    @FXML
    private TextField txtModelName;

    @FXML
    private TextField txtModelYear;

    @FXML
    private TextField txtSeatingCapacity;

    @FXML
    private TextField txtMileage;

    @FXML
    private DatePicker txtDateEntry;

    @FXML
    private TextField txtColor;

    @FXML
    private ComboBox<String> txtTransmission;

    @FXML
    private ComboBox<String> txtFuelType;

    @FXML
    private ComboBox<String> txtWheelType;
    
    @FXML
    private TextField txtPrice;

    @FXML
    private Label lblStatus;
    
    PreparedStatement preparedStatement;
    Connection connection;
    
    @FXML
    private Button btnSave;
    
    public AddCarController(){
        connection = (Connection) Connect.conDB();
                
        }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtFuelType.getItems().addAll("Diesel", "Petrol", "Gas");
        txtTransmission.getItems().addAll("Manual", "Automatic");
        txtWheelType.getItems().addAll("2 by 2", "4 by 4");
    }

    @FXML
    private void HandleEvents(ActionEvent event) {
        //check if not empty
        if (txtCarBrand.getText().isEmpty() || txtModelName.getText().isEmpty() 
                || txtModelYear.getText().isEmpty() || txtSeatingCapacity.getText().isEmpty()
                || txtMileage.getText().isEmpty() ||txtColor.getText().isEmpty()||txtPrice.getText().isEmpty()
              
                || txtDateEntry.getValue().equals(null)) {
            lblStatus.setTextFill(Color.WHITE);
            lblStatus.setText("Enter all details");
        } else {
            addCar();
        }
        }
        private void clearFields() {
        txtCarBrand.clear();
        txtModelName.clear();
        txtModelYear.clear();
        txtSeatingCapacity.clear();
        txtMileage.clear();
        txtColor.clear();
        txtPrice.clear();
    }

    private String addCar(){
        try{
           String car= "INSERT INTO car_details (car_brand, car_model, year_of_model, "
                   + "seating_capacity, mileage, color, transmission_type, wheel_type,  fuel_type, car_price) VALUES (?,?,?,?,?,?,?,?,?,?)" ;
            preparedStatement = (com.mysql.jdbc.PreparedStatement) connection.prepareStatement(car);
           
            preparedStatement.setString(1, txtCarBrand.getText());
            preparedStatement.setString(2, txtModelName.getText());
            preparedStatement.setString(3, txtModelYear.getText());
            preparedStatement.setString(4, txtSeatingCapacity.getText());
            preparedStatement.setString(5, txtMileage.getText());
            preparedStatement.setString(6, txtColor.getText());
            preparedStatement.setString(7, txtTransmission.getValue());
            preparedStatement.setString(8, txtWheelType.getValue());
            preparedStatement.setString(9, txtFuelType.getValue());
            preparedStatement.setString(9, txtPrice.getText());
            
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
    
    
    
}
