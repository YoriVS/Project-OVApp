module org.example.ovapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires com.opencsv;


    opens org.example.ovapp to javafx.fxml;
    exports org.example.ovapp;
    exports org.example.ovapp.controller;
    opens org.example.ovapp.controller to javafx.fxml;
    exports org.example.ovapp.handler;
    opens org.example.ovapp.handler to javafx.fxml;
}