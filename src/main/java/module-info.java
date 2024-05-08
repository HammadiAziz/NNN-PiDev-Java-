module esprit.nnn {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.protobuf;
    requires java.desktop;
    requires org.json;


    opens esprit.nnn to javafx.fxml;
    exports esprit.nnn;
    exports esprit.nnn.test;
    opens esprit.nnn.controllers.Front;
    opens esprit.nnn.controllers.Back;
    exports esprit.nnn.controllers.Back;
    exports esprit.nnn.controllers.Front;
    exports esprit.nnn.models;
    opens esprit.nnn.models;
}