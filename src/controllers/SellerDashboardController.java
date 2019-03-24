package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class SellerDashboardController implements Initializable {

    @FXML
    private JFXButton btnDashboard, btnCars, btnSales, btnFeedback, btnSignout, btnExit;

    @FXML
    private Pane pnDashboard, pnCars,  pnFeedback, pnSales;
    @FXML 
    private Label usernameLB;
    @FXML 
    private AnchorPane root;
       
  @FXML
  private void handleButtonAction(ActionEvent actionEvent) throws IOException{
      if(actionEvent.getSource() == btnDashboard ){
          pnDashboard.toFront();
          }
       if(actionEvent.getSource() == btnCars){ 
           Parent fxml =FXMLLoader.load(getClass().getResource("/fxml/Cars.fxml"));
            pnCars.getChildren().removeAll();
          pnCars.getChildren().setAll(fxml);
          pnCars.setStyle("-fx-background-color: #1620A1");
          pnCars.toFront();
          
          }
          if(actionEvent.getSource() == btnSales){ 
           Parent fxml =FXMLLoader.load(getClass().getResource("/fxml/SellerSales.fxml"));
           pnSales.getChildren().removeAll();
          pnSales.getChildren().setAll(fxml);
          pnSales.setStyle("-fx-background-color: #1620A1");
          pnSales.toFront();
          
          
          }
          if(actionEvent.getSource() == btnFeedback){ 
           Parent fxml =FXMLLoader.load(getClass().getResource("/fxml/SellerFeedback.fxml"));
           pnFeedback.getChildren().removeAll();
          pnFeedback.getChildren().setAll(fxml);
          pnFeedback.setStyle("-fx-background-color: WHITE");
          pnFeedback.toFront();
          
          }
          if(actionEvent.getSource() == btnSignout){ 
           //Parent fxml =FXMLLoader.load(getClass().getResource("/fxml/Cars.fxml"));
            //pnCars.getChildren().removeAll();
          //pnCars.getChildren().setAll(fxml);
          pnSales.setStyle("-fx-background-color: #blue");
          pnSales.toFront();
          
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
            Parent root  = FXMLLoader.load(getClass().getResource("/fxml/SellerLogin.fxml"));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            //usernameLB.setText(SellerLoginController.nameForHome);
    }
}