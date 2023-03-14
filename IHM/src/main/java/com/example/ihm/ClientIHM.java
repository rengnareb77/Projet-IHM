package com.example.ihm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientIHM extends Application {
    
    public static Socket clientSocket;
    @Override
    public void start(Stage stage)  {
        
        String serverHostname = "localhost";
        int port = 2121;
        try {
            // Connexion au socket
            clientSocket = new Socket(serverHostname, port);

            // Lecture des 3 premières lignes du serveur
            
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line;
            do {
                line = in.readLine();
                
            } while (line.charAt(0) != '0' && line.charAt(0) != '2');
            
            FXMLLoader fxmlLoader = new FXMLLoader(
                ClientIHM.class.getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stage.setTitle("Connexion");
            stage.setScene(scene);
            stage.show();
            
            
        } catch (IOException e) {
            System.err.println("Erreur : impossible de se connecter à " + serverHostname);
            System.exit(1);
        }
    }
    
    public static void main(String[] args) {
        launch();
    }
}
