module esprit.nnn {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires com.google.protobuf;
    requires org.apache.pdfbox;
    requires java.desktop;
    requires javafx.swing;


    opens esprit.nnn to javafx.fxml;
    exports esprit.nnn.Test;


    opens esprit.nnn.Test to javafx.fxml;
    opens esprit.nnn.Models to javafx.base;
    exports esprit.nnn.Controllers.Question;
    exports esprit.nnn.Controllers.Quiz.Front;
    opens esprit.nnn.Controllers.Quiz.Front;
    opens esprit.nnn.Controllers.Question;
    exports esprit.nnn.Controllers.Quiz;
    opens esprit.nnn.Controllers.Quiz;
}