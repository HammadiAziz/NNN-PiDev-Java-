package tn.esprit.services;

import tn.esprit.entities.Like;
import tn.esprit.interfaces.IServiceFatma;
import tn.esprit.util.MaConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LikeService implements IServiceFatma<Like> {

    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void add(Like like) {
        String req = "INSERT INTO `like`(`id_publication`) VALUES (?)";

        try {
            PreparedStatement statement = cnx.prepareStatement(req);
            statement.setInt(1, like.getId_publication());
            statement.executeUpdate();
            System.out.println("Like added successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Like like) {
        // No need for update in this case
    }

    @Override
    public void delete(Like like) {
        String req = "DELETE FROM `like` WHERE `id`=?";
        try {
            PreparedStatement statement = cnx.prepareStatement(req);
            statement.setInt(1, like.getId());
            statement.executeUpdate();
            System.out.println("Like deleted successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Like> getAll() {
        List<Like> likes = new ArrayList<>();
        String req = "SELECT * FROM `like`";
        try {
            PreparedStatement statement = cnx.prepareStatement(req);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                Like like = new Like();
                like.setId(res.getInt("id"));
                like.setId_publication(res.getInt("id_publication"));
                likes.add(like);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return likes;
    }

    @Override
    public Like getOne(int id) {
        Like like = null;
        String req = "SELECT * FROM `like` WHERE `id`=?";
        try {
            PreparedStatement statement = cnx.prepareStatement(req);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                like = new Like();
                like.setId(res.getInt("id"));
                like.setId_publication(res.getInt("id_publication"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return like;
    }

    public Like getOneByPublicationId(int publicationId) {
        Like like = null;
        String req = "SELECT * FROM `like` WHERE `id_publication`=?";
        try {
            PreparedStatement statement = cnx.prepareStatement(req);
            statement.setInt(1, publicationId);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                like = new Like();
                like.setId(res.getInt("id"));
                like.setId_publication(res.getInt("id_publication"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return like;
    }

    public void deleteByPublicationId(int publicationId) {
        String req = "DELETE FROM `like` WHERE `id_publication`=?";
        try {
            PreparedStatement statement = cnx.prepareStatement(req);
            statement.setInt(1, publicationId);
            statement.executeUpdate();
            System.out.println("Like deleted successfully for publication with ID: " + publicationId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
