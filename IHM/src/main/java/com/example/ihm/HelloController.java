package com.example.ihm;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private TreeView<String> treeView;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Créer un arbre de fichiers à partir du chemin du projet (user.dir)
        TreeItem<String> root = new TreeItem<>(System.getProperty("user.dir"));
        root.setExpanded(true);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        
        
        
    
    }
}
