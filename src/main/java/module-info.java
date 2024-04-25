module NNN {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires jbcrypt;

    opens tn.esprit;
    opens tn.esprit.controllers to javafx.fxml;
    opens tn.esprit.models to javafx.base;

}