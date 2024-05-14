package tn.esprit.controllers.UserControllers;

import tn.esprit.services.BadgeService;
import tn.esprit.services.SessionManager;
import tn.esprit.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class BadgeController {
    @FXML
    private Text bronzePrice;
    @FXML
    private Text GoldPrice;
    @FXML
    private Text silverPrice;
    private UserService userService;
    private BadgeService badgeService;
    @FXML
    public void initialize() {
        userService = new UserService();
        badgeService = new BadgeService();
        displayBadgePrices();
    }

    private void displayBadgePrices() {

        badgeService.displayAllBadges();

        int bronzeBadgePrice = badgeService.getBadgePrice("Bronze");
        int goldBadgePrice = badgeService.getBadgePrice("Gold");
        int silverBadgePrice = badgeService.getBadgePrice("Silver");

        bronzePrice.setText(String.valueOf(bronzeBadgePrice));
        GoldPrice.setText(String.valueOf(goldBadgePrice));
        silverPrice.setText(String.valueOf(silverBadgePrice));
    }

    @FXML
    public void purchaseBronze(ActionEvent actionEvent) {
        int userId = (int) SessionManager.getSession("userId");
        boolean purchased = userService.purchaseBadge(userId, "Bronze", badgeService);
        if (purchased) {
            // Handle successful purchase
        } else {
            // Handle failed purchase
        }
    }


    @FXML
    public void purchaseGold(ActionEvent actionEvent) {
        int userId = (int) SessionManager.getSession("userId");
        boolean purchased = userService.purchaseBadge(userId, "Gold",badgeService);
        if (purchased) {

        } else {

        }
    }

    @FXML
    public void purchaseSilver(ActionEvent actionEvent) {
        int userId = (int) SessionManager.getSession("userId");
        boolean purchased = userService.purchaseBadge(userId, "Silver",badgeService);
        if (purchased) {

        } else {

        }
    }

}
