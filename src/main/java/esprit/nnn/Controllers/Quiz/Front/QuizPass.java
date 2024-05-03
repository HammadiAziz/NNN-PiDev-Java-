package esprit.nnn.Controllers.Quiz.Front;

import esprit.nnn.Controllers.Question.QuestionCardController;
import esprit.nnn.Models.Questions;
import esprit.nnn.Models.Quiz;
import esprit.nnn.Services.QuestionsService;
import esprit.nnn.Services.QuizService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QuizPass {

    @FXML
    private GridPane grid;


    private final QuizService se = new QuizService();

    private Quiz quizToModify;
    private final QuestionsService us = new QuestionsService();

    private List<QuizPassItem> quizPassItemList;


    private QuestionsService qs = new QuestionsService();

    Set<Questions> questSet;
    List<Questions> questList;


    public void setQuizTopass(Quiz service) {
        this.quizToModify = service;
        // Retrieve questions for the quiz and populate questList
        if (quizToModify != null) {
            questSet = qs.getAll(quizToModify.getId());
            questList = new ArrayList<>(questSet);
            // Populate the grid with questions
            populateGridWithQuestions();
        }

    }


    private void populateGridWithQuestions() {
        // Clear existing grid content
        grid.getChildren().clear();

        int column = 0;
        int row = 1;
        quizPassItemList = new ArrayList<>();
        try {
            for (int i = 0; i < questList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/esprit/nnn/FXML/User/QuizPassitem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                anchorPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, null, new BorderWidths(2))));

                QuizPassItem itemController = fxmlLoader.getController();
                itemController.setData(questList.get(i));

                if (column == 1) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                GridPane.setMargin(anchorPane, new Insets(8));
                quizPassItemList.add(itemController);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void checkAnswers(ActionEvent actionEvent) {
        int score = 0;
        for (QuizPassItem item : quizPassItemList) {
            String userAnswer = item.getUserAnswer();
            if (userAnswer != null) {
                Questions question = item.getQuestion(); // Access the associated Questions object
                if (userAnswer.equals(question.getCorrect())) {
                    score += 1;
                }
            }
        }

        int totalQuestions = quizPassItemList.size();
        Alert alert = new Alert(Alert.AlertType.ERROR);

        if( score > totalQuestions/2 ) {




            alert.setTitle("Congratulations!");
            alert.setHeaderText(null);
            alert.setContentText(" jewebt 3la " + String.valueOf(score) + " / " + String.valueOf(quizPassItemList.size())+ " ya dhkeyyy ");
            alert.showAndWait();
            generateCertificationPDF("aziz", score, totalQuestions);
        }

        else {


            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText(" jewebt 3la " + String.valueOf(score) + " / " + String.valueOf(quizPassItemList.size())+ " yaa bhimmmmm  ");
            alert.showAndWait();

        }
    }


    public void generateCertificationPDF(String recipientName, int score, int totalQuestions) {
        try {
            // Create a new PDF document
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // Create a content stream for writing to the page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Set font and font size
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);

            // Add organization logo
            PDImageXObject logoImage = PDImageXObject.createFromFile("C:\\Users\\azizh\\Desktop\\Java Pidev\\NNN\\src\\main\\resources\\esprit\\nnn\\Img\\logo.png", document);
            contentStream.drawImage(logoImage, 50, 700, 100, 100); // Adjust coordinates and size as needed

            // Add title with blue color
            PDColor blueColor = new PDColor(new float[]{0, 0, 1}, PDDeviceRGB.INSTANCE);
            contentStream.setNonStrokingColor(blueColor);
            contentStream.beginText();
            contentStream.newLineAtOffset(200, 750);
            contentStream.showText("CERTIFICATION OF ACHIEVEMENT");
            contentStream.endText();

            // Add recipient's name with black color
            PDColor blackColor = new PDColor(new float[]{0, 0, 0}, PDDeviceRGB.INSTANCE);
            contentStream.setNonStrokingColor(blackColor);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(200, 720);
            contentStream.showText("This certifies that " + recipientName + " has successfully completed the quiz.");
            contentStream.endText();

            // Add score and total questions with green color
            PDColor greenColor = new PDColor(new float[]{0, 1, 0}, PDDeviceRGB.INSTANCE);
            contentStream.setNonStrokingColor(greenColor);
            contentStream.beginText();
            contentStream.newLineAtOffset(200, 700);
            contentStream.showText("Score: " + score + " / " + totalQuestions);
            contentStream.endText();

            // Add signature line with black color
            contentStream.setNonStrokingColor(blackColor);
            contentStream.beginText();
            contentStream.newLineAtOffset(200, 650);
            contentStream.showText("_____________________________");
            contentStream.endText();

            // Add issuer's signature with black color
            contentStream.beginText();
            contentStream.newLineAtOffset(200, 635);
            contentStream.showText("Issued by: XYZ Organization");
            contentStream.endText();

            // Close the content stream
            contentStream.close();

            // Save the document
            File file = new File("certificationn.pdf");
            document.save(file);
            document.close();

            // Show confirmation message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF generated");
            alert.setHeaderText(null);
            alert.setContentText("The certification PDF has been generated.");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
}
