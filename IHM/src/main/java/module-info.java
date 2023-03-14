module com.example.ihm {
    requires javafx.controls;
    requires javafx.fxml;
    requires Client;
    
    
    opens com.example.ihm to javafx.fxml;
    exports com.example.ihm;
}
