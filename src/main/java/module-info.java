module esprit.nnn {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires com.google.protobuf;


    opens esprit.nnn to javafx.fxml;
    exports esprit.nnn.Test;


    opens esprit.nnn.Test to javafx.fxml;
    opens esprit.nnn.Models to javafx.base;
    exports esprit.nnn.Controllers.Question;
    opens esprit.nnn.Controllers.Question;
    exports esprit.nnn.Controllers.Quiz;
    opens esprit.nnn.Controllers.Quiz;
}