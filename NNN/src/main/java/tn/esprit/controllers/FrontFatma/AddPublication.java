package tn.esprit.controllers.FrontFatma;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.controllers.AfficherLivUser;
import tn.esprit.controllers.AfficherShop;
import tn.esprit.controllers.Quiz.Front.QuizList;
import tn.esprit.controllers.UserControllers.ProfileController;
import tn.esprit.controllers.home;
import tn.esprit.entities.Groupe;
import tn.esprit.entities.Publication;
import tn.esprit.entities.User;
import tn.esprit.services.GroupService;
import tn.esprit.services.PublicationService;
import tn.esprit.services.SessionManager;
import tn.esprit.services.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddPublication {

    @FXML
    private Button Re;

    @FXML
    private Text descControl;

    @FXML
    private TextArea descPostTA;

    @FXML
    private ComboBox<String> groupeCB;

    @FXML
    private Text imageControl;

    @FXML
    private TextField imageUrlTF;

    @FXML
    private Text nameControl;

    @FXML
    private TextField namePostTF;

    private Scene previousScene;

    GroupService gs = new GroupService();
    PublicationService ps = new PublicationService();

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

    public void initialize() {
        List<Groupe> groupes = gs.getAll();
        List<String>names=new ArrayList<>();
        for(Groupe g : groupes) {
            names.add(g.getName_group());
        }
        groupeCB.setItems(FXCollections.observableList(names));
        int userId = (int) SessionManager.getSession("userId");
        fetchUserData();
    }

    @FXML
    void addPublication(ActionEvent event) {
        String title = namePostTF.getText().trim();
        String desc = descPostTA.getText().trim();
        String imageUrl = imageUrlTF.getText().trim();
        String selectedGroup = groupeCB.getSelectionModel().getSelectedItem();
        LocalDate date = LocalDate.now();
        if (title.isEmpty() || desc == null || selectedGroup == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de saisie !");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.show();
        }
         else {
            int groupId = gs.FindByName(selectedGroup).getId();
            //add
            ps.add(new Publication(title, desc, date, imageUrl, groupId));
            // Efface les champs après l'ajout
            clearFields();
        }


    }

    private void clearFields() {
        namePostTF.clear();
        descPostTA.clear();
        imageUrlTF.clear();
        groupeCB.getSelectionModel().clearSelection();
    }

    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }


    @FXML
    void returnButton(ActionEvent event) {
        // Navigate back to the previous scene
        if (previousScene != null) {
            Scene currentScene = ((Node) event.getSource()).getScene();
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(previousScene);
        }
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
