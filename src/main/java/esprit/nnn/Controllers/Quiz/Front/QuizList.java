package esprit.nnn.Controllers.Quiz.Front;

import esprit.nnn.Controllers.Quiz.QuizcardController;
import esprit.nnn.Models.Quiz;
import esprit.nnn.Services.QuizService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class QuizList implements Initializable {


    @FXML
    private GridPane rec;
    @FXML
    private GridPane grid;

    private final QuizService us = new QuizService();

    private QuizService se = new QuizService();
    Set<Quiz> serviceSet = se.getAll();
    List<Quiz> serviceList = new ArrayList<>(serviceSet);



    Set<Quiz> recmnd = se.getRecommendedQuizzesForUser(1);
    List<Quiz> recmndlist = new ArrayList<>(recmnd);




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < serviceList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/esprit/nnn/FXML/User/QuizItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                anchorPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID,
                        new CornerRadii(10), // Set the corner radii to make corners rounded
                        new BorderWidths(1))));


                Quizitem itemController = fxmlLoader.getController();
                itemController.setData(serviceList.get(i));

                if (column == 1) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
               grid.setPrefWidth(700);  // Adjust these values as needed
                grid.setPrefHeight(100);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(8));
            }


            for (int i = 0; i < recmndlist.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/esprit/nnn/FXML/User/QuizRecemn.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                anchorPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID,
                        new CornerRadii(10), // Set the corner radii to make corners rounded
                        new BorderWidths(1))));

                anchorPane.setPrefWidth(180);  // Adjust these values as needed
                anchorPane.setPrefHeight(100);
                QuizRecm itemController = fxmlLoader.getController();
                itemController.setdata(recmndlist.get(i));

                if (column == 1) {
                    column = 0;
                    row++;
                }

                rec.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                rec.setMinWidth(Region.USE_COMPUTED_SIZE);
                rec.setPrefWidth(Region.USE_COMPUTED_SIZE);
                rec.setMaxWidth(Region.USE_PREF_SIZE);
                rec.setPrefWidth(150);  // Adjust these values as needed
                rec.setPrefHeight(100);

                //set grid height
                rec.setMinHeight(Region.USE_COMPUTED_SIZE);
                rec.setPrefHeight(Region.USE_COMPUTED_SIZE);
                rec.setMaxHeight(Region.USE_PREF_SIZE);


                GridPane.setMargin(anchorPane, new Insets(4));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
