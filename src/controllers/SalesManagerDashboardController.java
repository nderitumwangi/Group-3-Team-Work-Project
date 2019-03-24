package controllers;

import java.io.IOException;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class SalesManagerDashboardController {

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnCars;

    
    @FXML
    private Button btnAddCar;

    @FXML
    private Button btnSellers;

    @FXML
    private Button btnSales;

    @FXML
    private Button btnFeedbacks;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnSignout;

    @FXML
    private Button btnExit;

    @FXML
    private AnchorPane root;

    @FXML
    private Pane pnOverview;
    @FXML
    private Pane pnCars;
    @FXML
    private Pane pnCustomers;
    @FXML
    private Pane pnFeedback;
    @FXML
    private Pane pnSellers;
   @FXML
   private Pane pnDashboard;
 @FXML
 private Pane pnSales;
  @FXML
  private Pane pnAddCar;
    
   
   
  @FXML
  private void handleButtonAction(ActionEvent actionEvent) throws IOException{
      if(actionEvent.getSource() == btnDashboard ){
          pnOverview.toFront();
          }
       if(actionEvent.getSource() == btnCars){ 
           Parent fxml =FXMLLoader.load(getClass().getResource("/fxml/Cars.fxml"));
            pnCars.getChildren().removeAll();
          pnCars.getChildren().setAll(fxml);
          pnCars.setStyle("-fx-background-color: #1620A1");
          pnCars.toFront();
          
          }
        if(actionEvent.getSource() == btnSellers ){
           Parent fxml =FXMLLoader.load(getClass().getResource("/fxml/Seller.fxml"));
          pnSellers.getChildren().removeAll();
          pnSellers.getChildren().setAll(fxml);
          pnSellers.setStyle("-fx-background-color: #cccccc");
          pnSellers.toFront();
          System.out.println("Hello seller"); 
          }
         if(actionEvent.getSource() == btnSales ){
           Parent fxml =FXMLLoader.load(getClass().getResource("/fxml/Sales.fxml"));
          pnSales.getChildren().removeAll();
          pnSales.getChildren().setAll(fxml);
          pnSales.setStyle("-fx-background-color: #cccccc");
          pnSales.toFront();
          System.out.println("Hello seller");
          }
         
         if(actionEvent.getSource() == btnAddCar ){
          Parent fxml =FXMLLoader.load(getClass().getResource("/fxml/AddCar.fxml"));
          pnAddCar.getChildren().removeAll();
          pnAddCar.getChildren().setAll(fxml);
          pnAddCar.setStyle("-fx-background-color: #cccccc");
          pnAddCar.toFront();
          System.out.println("Hello seller");
          }
         if(actionEvent.getSource() == btnFeedbacks ){
          Parent fxml =FXMLLoader.load(getClass().getResource("/fxml/Feedback.fxml"));
          pnFeedback.getChildren().removeAll();
          pnFeedback.getChildren().setAll(fxml);
          pnFeedback.setStyle("-fx-background-color: #cccccc");
          pnFeedback.toFront();
          System.out.println("Hello feedback");
          }
         if(actionEvent.getSource() == btnCustomers ){
          Parent fxml =FXMLLoader.load(getClass().getResource("/fxml/Customer.fxml"));
          pnCustomers.getChildren().removeAll();
          pnCustomers.getChildren().setAll(fxml);
          pnCustomers.setStyle("-fx-background-color: #cccccc");
          pnCustomers.toFront();
          System.out.println("Hello Customers");
          }
           if(actionEvent.getSource() == btnSignout ){
          
          System.out.println("Signing Out");
          }
  }
  @FXML
  public void handleExitButton(ActionEvent event){
   Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
   alert.setTitle("Confirm Exit");
   String s = "Are you sure you want to exit ?";
   alert.setContentText(s);
   Optional<ButtonType>result   = alert.showAndWait();
   if((result.isPresent()) && (result.get() == ButtonType.OK)){
       System.exit(0);
   }

  }
  @FXML
  public void handleSignoutButton(ActionEvent event) throws IOException{
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
   alert.setTitle("Confirm Signout");
   String s = "Are you sure you want to signout ?";
   alert.setContentText(s);
   Optional<ButtonType>result   = alert.showAndWait();
   if((result.isPresent()) && (result.get() == ButtonType.OK)){
            root.getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            Parent root  = FXMLLoader.load(getClass().getResource("/fxml/SalesManagerLogin.fxml"));
            Scene scene = new Scene (root);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(scene);
            primaryStage.show();

            DoubleProperty opacity = root.opacityProperty();
            Timeline fadeIn = new Timeline (

                    new KeyFrame (Duration.ZERO, new KeyValue (opacity, 0.0)),
                    new KeyFrame (new Duration (4000), new KeyValue(opacity, 2.0))
            );
              fadeIn.play();  
   }     
        
  }
}

 