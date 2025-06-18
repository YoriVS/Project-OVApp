module org.example.ovapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires com.opencsv;
    requires java.sql;
    requires jaxb.api;


    opens org.example.ovapp to javafx.fxml;
    exports org.example.ovapp;
    exports org.example.ovapp.controller;
    opens org.example.ovapp.controller to javafx.fxml;
    exports org.example.ovapp.handler;
    opens org.example.ovapp.handler to javafx.fxml;
    exports org.example.ovapp.traject;
    opens org.example.ovapp.traject to javafx.fxml;
    exports org.example.ovapp.controller.TrajectOption;
    opens org.example.ovapp.controller.TrajectOption to javafx.fxml;
    exports org.example.ovapp.traject.train;
    opens org.example.ovapp.traject.train to javafx.fxml;
    exports org.example.ovapp.nsApi.train;
    opens org.example.ovapp.nsApi.train to javafx.fxml;
    exports org.example.ovapp.traject.bus;
    opens org.example.ovapp.traject.bus to javafx.fxml;
    exports org.example.ovapp.user;
    opens org.example.ovapp.user to javafx.fxml;
}