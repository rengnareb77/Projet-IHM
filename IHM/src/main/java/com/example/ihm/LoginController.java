package com.example.ihm;

import client.CommandeSender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private boolean userOk = false;
    private boolean pwOk = false;
    private Socket clientSocket;
    
    @FXML
    private TextField userLogin;
    
    @FXML
    private TextField userPassword;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private void login(ActionEvent event) {
        
        String login = this.userLogin.getText();
        String password = this.userPassword.getText();
        
        // Vérification des champs
        if (login.isEmpty() || password.isEmpty()) {
            showDialog("Login ou mot de passe vide");
            return;
        }
    
        try {
            String response;
            BufferedReader br = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            PrintWriter pw = new PrintWriter(this.clientSocket.getOutputStream(),true);
            if (!userOk){
                pw.println("user " + login);
                response = CommandeSender.sendCommande(br, "user " + login);
                if (response.contains("2 ")){
                    showDialog("Login incorrect");
                    return;
                }
                userOk = true;
            }
            if (!pwOk){
                pw.println("pass " + password);
                response = CommandeSender.sendCommande(br, "pass " + password);
    
                if (response.contains("2 ")){
                    showDialog("Mot de passe incorrect");
                    return;
                }
                pwOk = true;
            }
    
            FXMLLoader loader = new FXMLLoader(getClass().getResource("client-view.fxml"));
            Parent root = loader.load();
    
            // Créer une nouvelle scène
            Scene scene = new Scene(root);
            Stage stage = new Stage();
    
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
    
            // Associer la scène à la fenêtre
            stage.setScene(scene);
    
            // Afficher la fenêtre
            stage.show();
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    private void showDialog(String message){
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(this.loginButton.getScene().getWindow());
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(javafx.geometry.Pos.CENTER);
        dialogVbox.getChildren().add(new Text(message));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.clientSocket = ClientIHM.clientSocket;
    }
}
