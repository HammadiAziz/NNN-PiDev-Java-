package tn.esprit.controllers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import tn.esprit.entities.Livraison;

import java.io.IOException;

public class PDFGenerator {
    public static void generatePDF(Livraison livraison) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

            // Ajouter le logo
            contentStream.drawImage(PDImageXObject.createFromFile("C:\\Users\\abous\\IdeaProjects\\nour\\pi\\src\\main\\resources\\images\\logo.png", document),
                    50, 750, 100, 50);

            int yPosition = 700; // Initial vertical position

            // Nom de la société
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 14);
            contentStream.newLineAtOffset(200, 775);
            contentStream.showText("NNN");
            contentStream.endText();

            // Dessiner le tableau
            contentStream.drawLine(50, 720, 550, 720); // Ligne horizontale supérieure
            contentStream.drawLine(50, 720, 50, 620); // Ligne verticale gauche
            contentStream.drawLine(550, 720, 550, 620); // Ligne verticale droite
            contentStream.drawLine(50, 620, 550, 620); // Ligne horizontale inférieure

            // Contenu du tableau
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.newLineAtOffset(60, 700); // Position pour la première ligne
            contentStream.showText("Livraison ID: " + livraison.getId());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Nom: " + livraison.getNomC());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Prenom: " + livraison.getPrenomC());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Adresse: " + livraison.getAdress());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Email: " + livraison.getEmail());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Numéro de téléphone: " + livraison.getPhoneNumber());
            contentStream.endText();

            contentStream.close();

            document.save("Livraison_" + livraison.getId() + ".pdf");
            System.out.println("PDF file created: Livraison_" + livraison.getId() + ".pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
