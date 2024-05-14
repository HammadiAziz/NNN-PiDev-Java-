package tn.esprit.services;

import tn.esprit.entities.Groupe;
import tn.esprit.entities.Publication;
import tn.esprit.interfaces.IServiceFatma;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublicationService implements IServiceFatma<Publication> {

    Connection cnx= MaConnexion.getInstance().getCnx();

    @Override
    public void add(Publication publication) {
        String req= "INSERT INTO `publication`(`group_post_id`, `post_title`, `post_desc`, `date_post`, `image_post`)VALUES ('"+publication.getGroup_post_id()+"','"+publication.getPost_title()+"','"+publication.getPost_desc()+"','" + publication.getDate_post()+"','"+publication.getImage_post()+"')";

        try {
            GroupService gs = new GroupService();
            Groupe groupe= gs.getOne(publication.getGroup_post_id());
            if(groupe!=null) {
                Statement st = cnx.createStatement();
                st.executeUpdate(req);
                System.out.println("publication added successfully");
                groupe.publications.add(publication);
            }
            else
                System.out.println("group not found");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Publication publication) {
        String req="UPDATE `publication` SET `post_title`='"+publication.getPost_title()+"',`post_desc`='"+publication.getPost_desc()+"',`date_post`='"+publication.getDate_post()+"', `image_post`='"+publication.getImage_post()+"', where `id`='"+publication.getId()+"'";
        try {
            Statement st= cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("publication updated successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Publication publication) {
        String req= "DELETE FROM `publication` WHERE `id`=?";
        try {
            GroupService gs = new GroupService();
            Groupe groupe= gs.getOne(publication.getGroup_post_id());
            if(groupe!=null) {
                groupe.events.remove(publication);
            }
            PreparedStatement statement = cnx.prepareStatement(req);
            statement.setInt(1,publication.getId());
            statement.executeUpdate();
            System.out.println("publication deleted successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

    }

    @Override
    public List<Publication> getAll() {
        List<Publication> publications = new ArrayList<>();
        String req= "SELECT * FROM `publication`";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Publication publication = new Publication();
                publication.setId(res.getInt("id"));
                publication.setPost_title(res.getString("post_title"));
                publication.setPost_desc(res.getString("post_desc"));
                publication.setDate_post(res.getDate("date_post").toLocalDate());
                publication.setImage_post(res.getString("image_post"));
                publication.setGroup_post_id(res.getInt("group_post_id"));

                publications.add(publication);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return publications;
    }

    @Override
    public Publication getOne(int id) {
        Publication pb = null;
        String req = "SELECT * FROM `publication` WHERE `id`=?";
        try {
            PreparedStatement statement = cnx.prepareStatement(req);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                pb = new Publication();
                pb.setId(res.getInt("id"));
                pb.setPost_title(res.getString("post_title"));
                pb.setPost_desc(res.getString("post_desc"));
                pb.setImage_post(res.getString("image_post"));
                pb.setDate_post(res.getDate("date_post").toLocalDate());
                pb.setGroup_post_id(res.getInt("group_post_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pb;
    }

    public List<Publication> getPublicationsByGroup(int group_id) {
        List<Publication> publications = new ArrayList<>();
        String req= "SELECT * FROM publication WHERE group_post_id = ?";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, group_id);
            ResultSet res = st.executeQuery(); // Remove the SQL query string argument
            while (res.next()) {
                Publication publication = new Publication();
                publication.setId(res.getInt("id"));
                publication.setPost_title(res.getString("post_title"));
                publication.setPost_desc(res.getString("post_desc"));
                publication.setDate_post(res.getDate("date_post").toLocalDate());
                publication.setImage_post(res.getString("image_post"));
                publication.setGroup_post_id(res.getInt("group_post_id"));
                publications.add(publication);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return publications;
    }


}
