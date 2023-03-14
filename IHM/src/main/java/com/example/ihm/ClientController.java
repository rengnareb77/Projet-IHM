package com.example.ihm;

import client.CommandeSender;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    public Socket clientSocket;
    
    private BufferedReader br;
    private PrintWriter pw;
    @FXML
    private TextArea responseTextArea;
    @FXML
    public TreeView<String> serveurTreeView;
    @FXML
    private TreeView<String> clientTreeView;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientSocket = ClientIHM.clientSocket;
        responseTextArea.setText("> Connexion réussie !");
        
        try{
            br = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            pw = new PrintWriter(this.clientSocket.getOutputStream(),true);
    
            // On initialise l'arbre du serveur
            TreeItem<String> root = new TreeItem<>("Serveur");
            root.setExpanded(true);
            serveurTreeView.setRoot(initServeur(root));
            
            // On initialise l'arbre du client
            root = new TreeItem<>("Client");
            root.setExpanded(true);
            clientTreeView.setRoot(initClient(root, System.getProperty("user.dir")));
            
        } catch (IOException e){
            e.printStackTrace();
        }
        
    }
    private TreeItem<String> initServeur(TreeItem<String> root) throws IOException{
        pw.println("ls");
        String response = CommandeSender.sendCommande(br, "ls");
        if (response.contains("vide")){
            root.getChildren().add(new TreeItem<>("Vide..."));
            return root;
        }

        String[] files = response.split("\n");
        for (String file : files){
        
            TreeItem<String> item = new TreeItem<>(file.substring(2));
            // Si c'est un dossier
            if (file.endsWith("/")){
                String command = "cd " + file.substring(2);
                pw.println(command);
                response = CommandeSender.sendCommande(br, command);
                initServeur(item);
                command = "cd ..";
                pw.println(command);
                response = CommandeSender.sendCommande(br, command);
            }
            root.getChildren().add(item);
        }
        return root;
    }
    
    private TreeItem<String> initClient(TreeItem<String> root, String path) {
        
        File f = new File(path);
        if (!f.isDirectory()){
            root.getChildren().add(new TreeItem<>("Vide..."));
            return root;
        }
        File[] files = f.listFiles();
        for (File file : files){
            TreeItem<String> item = new TreeItem<>(file.getName());
            if (file.isDirectory()){
                initClient(item, file.getAbsolutePath());
            }
            root.getChildren().add(item);
        }
        
        return root;
    }
}
