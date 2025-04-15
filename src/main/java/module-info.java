module org.example.ovapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.ovapp to javafx.fxml;
    exports org.example.ovapp;
}