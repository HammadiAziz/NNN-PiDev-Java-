package tn.esprit.services;

import tn.esprit.entities.Livraison;
import tn.esprit.entities.SuiviLiv;
import tn.esprit.services.IService;
import tn.esprit.util.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SuiviService implements IService<SuiviLiv> {
    ///att
    Connection cnx = Data.getInstance().getCnx();
    ///actions
    @Override
    public void ajouter(SuiviLiv suiviLiv) {
        String selectReq = "SELECT COUNT(*) FROM `suivi_livraison` WHERE date_comm = ? AND localisatione= ? AND livree  = ?";
        String insertReq = "INSERT INTO `suivi_livraison` (`date_comm`,  `livree`,`localisatione`) VALUES (?, ?, ?)";

        try {
            PreparedStatement selectSt = cnx.prepareStatement(selectReq);
            selectSt.setString(1, suiviLiv.getDateCommande());
            selectSt.setBoolean(2, suiviLiv.isLivree());
            selectSt.setString(3, suiviLiv.getLocation());

            ResultSet rs = selectSt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            selectSt.close();

            if (count == 0) {
                PreparedStatement insertSt = cnx.prepareStatement(insertReq);
                insertSt.setString(1, suiviLiv.getDateCommande());
                insertSt.setBoolean(2, suiviLiv.isLivree());
                insertSt.setString(3, suiviLiv.getLocation());

                insertSt.executeUpdate();
                insertSt.close();

                System.out.println("SuiviLiv ajouté avec succès");
            } else {
                System.out.println("Un SuiviLiv similaire existe déjà");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout' de suivi : " + e.getMessage());        }
    }

    @Override
    public void modifier(  SuiviLiv suiviLiv) {
        String req = "UPDATE suivi_livraison SET date_comm=?,localisatione=?,livree=? WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, suiviLiv.getDateCommande());
            ps.setString(2, suiviLiv.getLocation());
            ps.setBoolean(3, suiviLiv.isLivree());

            ps.executeUpdate();
            System.out.println("suiviLiv modifiée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de la suiviLiv : " + e.getMessage());
        }


    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `suivi_livraison` WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            int rowCount = ps.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Suiv livraison supprimée avec succès !");
            } else {
                System.out.println("Aucune Suivi livraison trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la Suivi livraison : " + e.getMessage());
        }

    }

    @Override
    public Set<SuiviLiv> getAll() {
        return Set.of();
    }


    public List<SuiviLiv> getAllLiv() throws SQLException {

        List<SuiviLiv>suiviLivList=new ArrayList<>();
        String req ="SELECT * FROM `suivi_livraison` ";

            Statement st = cnx.createStatement();
            ResultSet res= st.executeQuery(req);
            while (res.next()){
                SuiviLiv sui = new SuiviLiv();
                sui.setId(res.getInt("id"));
                sui.setDateCommande(res.getString(5));
                sui.setLocation(res.getString(6));
                sui.setLivree(res.getBoolean(7));
                suiviLivList.add(sui);
            }

        return suiviLivList;
    }

    @Override
    public SuiviLiv getOneByID(int id) {
        SuiviLiv sui = null ;
        String req = " SELECT * FROM suivi_livraison WHERE id=? ";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String dateCommande=rs.getString(5);
                String location=rs.getString(6);
                Boolean livree=rs.getBoolean(7);

                sui=new SuiviLiv(dateCommande ,livree,location);
            }

        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération de la sui: " + e.getMessage());
        }
        return sui;
    }

    @Override
    public List<Livraison> getAllLivraisonsByUserId(int userId) {
        return null;
    }
    @Override
    public SuiviLiv getSuiviLivByLivraisonId(int livraisonId) {
        SuiviLiv suiviLiv = null;
        String query = "SELECT * FROM suivi_livraison WHERE id_livraison_id = ?";
        try (
                PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, livraisonId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    suiviLiv = new SuiviLiv();
                    suiviLiv.setId(resultSet.getInt("id"));
                    suiviLiv.setDateCommande(resultSet.getString("date_comm"));
                    suiviLiv.setLivree(resultSet.getBoolean("livree"));
                    suiviLiv.setLocation(resultSet.getString("localisatione"));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suiviLiv;
    }
}
