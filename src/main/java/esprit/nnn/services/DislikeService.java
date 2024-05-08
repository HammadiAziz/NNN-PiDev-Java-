package esprit.nnn.services;

import esprit.nnn.interfaces.Iservice;
import esprit.nnn.models.DislikePublication;
import esprit.nnn.models.Publication;
import esprit.nnn.util.MaConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DislikeService implements Iservice<DislikePublication> {

    Connection cnx= MaConnexion.getInstance().getCnx();

    @Override
    public void add(DislikePublication dislikePublication) {
        String req= "INSERT INTO `dislikepublication`(`id_publication`)VALUES ('"+dislikePublication.getId_publication()+"')";

        try {
            PublicationService gs = new PublicationService();
            Publication publication= gs.getOne(dislikePublication.getId_publication());
            if(publication!=null) {
                Statement st = cnx.createStatement();
                st.executeUpdate(req);
                System.out.println("dislike added successfully");
                publication.dislikes.add(dislikePublication);
            }
            else
                System.out.println("publication not found");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(DislikePublication dislikePublication) {

    }

    @Override
    public void delete(DislikePublication dislikePublication) {
        String req= "DELETE FROM `likepublication` WHERE `id`=?";
        try {
            PublicationService gs = new PublicationService();
            Publication publication= gs.getOne(dislikePublication.getId_publication());
            if(publication!=null) {
                publication.dislikes.remove(dislikePublication);
            }
            PreparedStatement statement = cnx.prepareStatement(req);
            statement.setInt(1,publication.getId());
            statement.executeUpdate();
            System.out.println("event deleted successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }


    }

    @Override
    public List<DislikePublication> getAll() {
        return List.of();
    }

    @Override
    public DislikePublication getOne(int id) {
        return null;
    }
}
