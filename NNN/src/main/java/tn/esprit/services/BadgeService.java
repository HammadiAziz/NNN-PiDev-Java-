package tn.esprit.services;

import tn.esprit.entities.Badge;
import tn.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BadgeService {
    static Connection cnx = DataSource.getInstance().getCnx();

    public List<Badge> getAllBadges() {
        List<Badge> badges = new ArrayList<>();
        try {
            String query = "SELECT * FROM badge";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String nombadge = resultSet.getString("nombadge");
                        int prix = resultSet.getInt("prix");
                        badges.add(new Badge(id, nombadge, prix));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return badges;
    }
    public int getBadgeId(String badgeName) {
        List<Badge> badges = getAllBadges();
        for (Badge badge : badges) {
            if (badge.getNombadge().equalsIgnoreCase(badgeName)) {
                return badge.getId();
            }
        }
        return -1;
    }

    public void displayAllBadges() {
        List<Badge> badges = getAllBadges();
        System.out.println("List of Badges:");
        for (Badge badge : badges) {
            System.out.println("ID: " + badge.getId() + ", Name: " + badge.getNombadge() + ", Price: " + badge.getPrix());
        }
    }

    public int getBadgePrice(String badgeName) {
        List<Badge> badges = getAllBadges();
        for (Badge badge : badges) {
            if (badge.getNombadge().equalsIgnoreCase(badgeName)) {
                return badge.getPrix();
            }
        }
        return -1; // Return -1 if badge not found
    }
}
