package esprit.nnn.Controllers.Quiz;

import esprit.nnn.Models.Quiz;
import esprit.nnn.Services.QuizService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class QuizListcontroller implements Initializable {


    @FXML
    private TableColumn<Quiz, String> Desc;

    @FXML
    private TableColumn<Quiz, String> Name;

    @FXML
    private TableView<Quiz> QuizTable;

    @FXML
    private TableColumn<Quiz, Integer> points;

    @FXML
    private TableColumn<Quiz, String> type;

    @FXML
    private GridPane grid;

    private final QuizService us = new QuizService();

    private QuizService se = new QuizService();
    Set<Quiz> serviceSet = se.getAll();
    List<Quiz> serviceList = new ArrayList<>(serviceSet);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < serviceList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/esprit/nnn/FXML/Admin/QuizCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                anchorPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, null, new BorderWidths(2))));


                QuizcardController itemController = fxmlLoader.getController();
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

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }








