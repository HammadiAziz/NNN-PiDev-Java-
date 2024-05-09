package tn.esprit.util;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.Parent;


public class MainFX extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/AddRec.fxml")) ;


		try {
			Parent root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setTitle("Ajouter reclamation");
			primaryStage.setScene(scene);
			primaryStage.show();

		}catch(IOException e ){
			throw new RuntimeException(e);
			}

	}
}
