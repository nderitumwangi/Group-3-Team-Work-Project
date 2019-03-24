package controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Connection.Connect;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class SellerLoginController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblErrors;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private Button btnSignin;
    @FXML
    private Button btnSalesManagerSignin;

    @FXML 
    private ImageView logoImage;
    @FXML 
    private ImageView sellerImage;
    
    /// -- 
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    public static String nameForHome="";

    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException {

        if (event.getSource() == btnSignin) {
            //login here
            if (logIn().equals("Success")) {
                try {

                    //add you loading or delays - ;-)
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    //stage.setMaximized(true);
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/SellerDashboard.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

            }
        }
        if(event.getSource()==btnSalesManagerSignin){
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Check");
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Server is up : Good to go");
        }


    }

    public SellerLoginController() {
        con = Connect.conDB();

    }

    //we gonna use string to check for status
    private String logIn() {

        String email = txtUsername.getText();
        String password = txtPassword.getText();
        if(txtUsername.getText().isEmpty() ||txtPassword.getText().isEmpty() ){
         lblErrors.setText("Please enter all fields");
        }else{
        if((password.length()>10)||password.length()<6) {
            lblErrors.setText("Password should be between 6 and 10 characters");
        }
        else{
        //query
        String sql = "SELECT * FROM seller Where seller_email = ? and seller_password = ?";

        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            //nameForHome=resultSet.getString("seller_name");
            if (!resultSet.next()) {
                
                lblErrors.setTextFill(Color.TOMATO);
                lblErrors.setText("Enter Correct Email/Password");
                System.err.println("Wrong Logins --///");
                return "Error";

            } else {
                
                lblErrors.setTextFill(Color.GREEN);
                lblErrors.setText("Login Successful..Redirecting..");
                System.out.println("Successfull Login");
                return "Success";
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return "Exception";
        }

    }

      
        }
        return null;
  
    }
}

