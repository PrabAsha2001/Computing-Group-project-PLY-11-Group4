module com.invento.invento {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.dlsc.formsfx;

    opens com.invento.invento to javafx.fxml;
    exports com.invento.invento;
}