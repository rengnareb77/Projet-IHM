module com.example.ihm {
    requires javafx.controls;
    requires javafx.fxml;
    
    
    opens com.example.ihm to javafx.fxml;
    exports com.example.ihm;
}
