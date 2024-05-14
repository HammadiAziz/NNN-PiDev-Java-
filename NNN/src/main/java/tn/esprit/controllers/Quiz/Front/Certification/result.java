package tn.esprit.controllers.Quiz.Front.Certification;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import tn.esprit.entities.Certification;
import tn.esprit.entities.HistoryQuiz;
import tn.esprit.services.CertificationService;
import tn.esprit.services.historyService;

import java.io.File;
import java.io.IOException;

public class result {
    @FXML
    private Button pdfbutton;

    @FXML
    private Label pdptext;

    @FXML
    private Label result;


    @FXML
    private Label text;
    int id;
    int score;
    int quest;
    String quizname;



    private final CertificationService se = new CertificationService();
    private final historyService hs = new historyService();


    public void setdata(int id,int score,int quest,String quiznamen,int quizid ){




this.id = id;
this.score = score;
this.quest = quest;
this.quizname = quizname;
    if (score>quest/2) {
        result.setText("Congratulations!");
        text.setText("Good job! "+ id +"You got " + score + " / " + quest + " in the Quiz : " + quizname + "and you have earned a certifciation " );
        pdptext.setText("You can Download The PDF version by clicking the button below");
        pdfbutton.setDisable(false);

        se.Create(new Certification(quizid,6));
        hs.Create(new HistoryQuiz(quizid,6,1 ));

    }
    else {

        result.setText("Failed");
        text.setText("Sorry "+ id +"You got " + score + " / " + quest + " in the Quiz : " + quizname  );
        pdptext.setText("You can try again later ");

        pdfbutton.setVisible(false);
        hs.Create(new HistoryQuiz(quizid,1,0 ));


    }


}


    @FXML
    void generateCertification(ActionEvent event) {
        generateCertificationPDF("aziz",score,quest);



    }
    @FXML
    void backtoList(ActionEvent event) {

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

        }
    }











}
