package tn.esprit.controllers.FrontFatma;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.controllers.AfficherLivUser;
import tn.esprit.controllers.AfficherShop;
import tn.esprit.controllers.Quiz.Front.QuizList;
import tn.esprit.controllers.UserControllers.ProfileController;
import tn.esprit.controllers.home;
import tn.esprit.entities.Event;
import tn.esprit.entities.Groupe;
import tn.esprit.entities.User;
import tn.esprit.services.EventService;
import tn.esprit.services.GroupService;
import tn.esprit.services.SessionManager;
import tn.esprit.services.UserService;

import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddEvent {

    @FXML
    private DatePicker debutDateDP;

    @FXML
    private TextArea descEventTA;

    @FXML
    private DatePicker endDateDP;

    @FXML
    private TextField nameEventTF;

    @FXML
    private ComboBox<String> groupCB;



    private final GroupService gs = new GroupService();
    private final EventService es = new EventService();
    private Stage stage;
    @FXML
    private Button profilename;

    final UserService pf = new UserService();
    private void fetchUserData() {
        // Retrieve user data from UserService
        User user = pf.fetchPlayerData();
        if (user != null) {
            // Populate the UI with user data

            profilename.setText(user.getNom());

        } else {
            // Handle case where user data is not found

            profilename.setText("N/A");

        }
    }

    @FXML
    void backToAllEvents(ActionEvent event) {
        try {
            // Charger le fichier FXML de la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontFatma/ShowAllEvents.fxml"));
            Parent root = loader.load();            // Obtenir le contrôleur de la deuxième page
            ShowAllEvents controller = loader.getController();
            // Obtenir la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();
            // Remplacer la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }


    @FXML
    public void getDebutDate(ActionEvent event) {

    }

    @FXML
    public void getEndDate(ActionEvent event) {


    }
    @FXML
    public void initialize() {
        List<Groupe> groupes = gs.getAll();
        List<String>names=new ArrayList<>();
        for(Groupe g : groupes) {
            names.add(g.getName_group());
        }
        groupCB.setItems(FXCollections.observableList(names));
        int userId = (int) SessionManager.getSession("userId");
        fetchUserData();
    }


    @FXML
    public void addEvent(ActionEvent event) {
        String eventName = nameEventTF.getText();
        LocalDate debutDate = debutDateDP.getValue();
        LocalDate endDate = endDateDP.getValue();
        String selectedGroup = groupCB.getSelectionModel().getSelectedItem();

        if (eventName.isEmpty() || debutDate == null || endDate == null || selectedGroup == null) {
            showAlert("Erreur de saisie !", "Veuillez remplir tous les champs.", Alert.AlertType.ERROR);
        } else if (debutDate.isAfter(endDate)) {
            showAlert("Erreur de dates !", "La date de début ne peut pas être après la date de fin.", Alert.AlertType.ERROR);
        } else if (debutDate.isBefore(LocalDate.now())) {
            showAlert("Erreur de date de début !", "La date de début doit être aujourd'hui ou une date ultérieure.", Alert.AlertType.ERROR);
        } else {
            int groupId = gs.FindByName(selectedGroup).getId();
            //add
            es.add(new Event(groupId, eventName, descEventTA.getText(), debutDate, endDate));
            // Efface les champs après l'ajout
            clearFields();
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Erreur");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.show();
    }

    // Méthode pour effacer les champs après l'ajout
    private void clearFields() {
        nameEventTF.clear();
        descEventTA.clear();
        debutDateDP.setValue(null);
        endDateDP.setValue(null);
        groupCB.getSelectionModel().clearSelection();
    }



    @FXML
    void goToEvents(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontFatma/ShowAllEvents.fxml"));
            Parent root = loader.load();
            ShowAllEvents controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    @FXML
    public void goToQuiz(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Quiz/User/QuizList.fxml"));
            Parent root = loader.load();
            QuizList controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void goToGroups(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontFatma/ShowAllGroupes.fxml"));
            Parent root = loader.load();
            ShowAllGroupes controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void goToMarket(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherShop.fxml"));
            Parent root = loader.load();
            AfficherShop controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    @FXML
    void goToLivraison(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherLivUser.fxml"));
            Parent root = loader.load();
            AfficherLivUser controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    @FXML
    void gotoHome(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            Parent root = loader.load();
            home controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void gotoprofile(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateUserGUI.fxml"));
            Parent root = loader.load();
            ProfileController controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

}
