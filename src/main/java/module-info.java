module org.example.ovapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.ovapp to javafx.fxml;
    exports org.example.ovapp;
    exports org.example.ovapp.node;
    opens org.example.ovapp.node to javafx.fxml;
    exports org.example.ovapp.controller;
    opens org.example.ovapp.controller to javafx.fxml;
}