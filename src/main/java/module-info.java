module org.example.ovapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires com.opencsv;
    requires java.sql;


    opens org.example.ovapp to javafx.fxml;
    exports org.example.ovapp;
    exports org.example.ovapp.controller;
    opens org.example.ovapp.controller to javafx.fxml;
    exports org.example.ovapp.handler;
    opens org.example.ovapp.handler to javafx.fxml;
    exports org.example.ovapp.traject;
    opens org.example.ovapp.traject to javafx.fxml;
}