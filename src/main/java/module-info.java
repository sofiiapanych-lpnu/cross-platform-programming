module com.example.lab2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.jfree.jfreechart;
    requires java.desktop;

    opens com.example.lab2.model to com.google.gson;

    opens com.example.lab2.service to com.google.gson;
    opens com.example.lab2.view to javafx.graphics;
}