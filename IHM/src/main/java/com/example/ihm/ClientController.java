package com.example.ihm;

import client.CommandeSender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    
    @FXML
    private TextField mkdirTextField;
    
    @FXML
    private Button rmdirButton;
    
    @FXML
    private Button mkdirButton;
    
    @FXML
    private Button getButton;
    
    @FXML
    private Button storButton;
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientSocket = ClientIHM.clientSocket;
        responseTextArea.setText("> Connexion réussie !");
        
        clientTreeView.setCellFactory(tree -> {
            TreeCell<String> cell = new TreeCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item);
                    }
                }
            };
            cell.setOnMouseClicked(event -> {
                
                String content = cell.getItem();
                String userDir = System.getProperty("user.dir");
    
                // Cas où on clique sur un dossier parent
                if (cell.getTreeItem().equals(clientTreeView.getRoot())){
                    System.setProperty("user.dir", new File(userDir).getParent());
                    clientTreeView.getRoot().getChildren().clear();
                    initClient(clientTreeView.getRoot(), System.getProperty("user.dir"));
                    return;
                }
                File f = new File(userDir+ "/" + content);
                
                // Cas où on clique sur un dossier
                if (f.isDirectory()){
                    System.setProperty("user.dir", userDir + "/" + cell.getItem());
                    clientTreeView.getRoot().getChildren().clear();
                    initClient(clientTreeView.getRoot(), System.getProperty("user.dir"));
                }
                
            });
            return cell;
        });
        
        try{
            br = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            pw = new PrintWriter(this.clientSocket.getOutputStream(),true);
    
            // On initialise l'arbre du serveur
            TreeItem<String> root = new TreeItem<>("Serveur");
            root.setExpanded(true);
            serveurTreeView.setRoot(initServeur(root));
            String resp = CommandeSender.sendCommande(pw,br, "cd ..");
            
            // On initialise l'arbre du client
            root = new TreeItem<>("Client");
            root.setExpanded(true);
            clientTreeView.setRoot(initClient(root, System.getProperty("user.dir")));
            
        } catch (IOException e){
            e.printStackTrace();
        }
        
    }
    private TreeItem<String> initServeur(TreeItem<String> root) {
        try{
            String response = CommandeSender.sendCommande(pw,br, "ls");
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
                    response = CommandeSender.sendCommande(pw,br, command);
                    initServeur(item);
                    command = "cd ..";
                    response = CommandeSender.sendCommande(pw,br, command);
                }
                root.getChildren().add(item);
            }
        } catch (IOException e){
            e.printStackTrace();
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
    
    @FXML
    public void rmdir(MouseEvent mouseEvent) {
        TreeItem<String> item = serveurTreeView.getSelectionModel().getSelectedItem();
        String itemValue = item.getValue();
        
        
        try {
            String response = CommandeSender.sendCommande(pw,br, "rmdir " + itemValue);
            responseTextArea.appendText("\n> " + response);
            if (response.startsWith("0")){
                serveurTreeView.getRoot().getChildren().clear();
                initServeur(serveurTreeView.getRoot());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void mkdir(MouseEvent mouseEvent) {
        String dirName = mkdirTextField.getText();
        if (dirName.isBlank()){
            responseTextArea.appendText("\n> Veuillez entrer un nom de dossier");
            return;
        }
        try {
            String response = CommandeSender.sendCommande(pw,br, "mkdir " + dirName);
            if (response.startsWith("0")){
                serveurTreeView.getRoot().getChildren().clear();
                initServeur(serveurTreeView.getRoot());
            }
            responseTextArea.appendText("\n> " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    @FXML
    public void stor(MouseEvent mouseEvent) {
        TreeItem<String> item = clientTreeView.getSelectionModel().getSelectedItem();
        String itemValue = item.getValue();
        if (itemValue.endsWith("/")){
            return;
        }
        try{
            String response = CommandeSender.sendCommande(pw,br, "stor " + itemValue);
            if (response.startsWith("0")){
                serveurTreeView.getRoot().getChildren().clear();
                initServeur(serveurTreeView.getRoot());
            }
            responseTextArea.appendText("\n> " + response);
        }catch (IOException e){
            e.printStackTrace();
        }
    
    }
    @FXML
    public void get(MouseEvent mouseEvent) {
        TreeItem<String> item = serveurTreeView.getSelectionModel().getSelectedItem();
        
        String itemValue = item.getValue();
        if (itemValue.equals("Vide...")){
            return;
        }
        
        try{
            String response = CommandeSender.sendCommande(pw,br, "get " + itemValue);
            
            if (response.startsWith("0")){
                clientTreeView.getRoot().getChildren().clear();
                initClient(clientTreeView.getRoot(), System.getProperty("user.dir"));
            }
            responseTextArea.appendText("\n> " + response);
            
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public void quit(ActionEvent actionEvent) {
        System.exit(0);
    }
    
  
}
