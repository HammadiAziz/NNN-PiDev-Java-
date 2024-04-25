package esprit.nnn.Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {


        FXMLLoader loader=new FXMLLoader(getClass().getResource("/esprit/nnn/FXML/Admin/QuizHome.fxml"));



            Parent root=loader.load();
            Scene scene=new Scene(root);
            primaryStage.setTitle("Quiz");
            primaryStage.setScene(scene);
            primaryStage.show();

    }

}

