module com.graphics{
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    opens com.graphics to javafx.fxml;
    exports com.graphics;
}