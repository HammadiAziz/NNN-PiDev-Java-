package tn.esprit.services;
import tn.esprit.entities.Livraison;
import tn.esprit.entities.SuiviLiv;
import tn.esprit.util.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LivraisonService implements IService<Livraison> {

    static Connection cnx = Data.getInstance().getCnx();

    @Override
    public void ajouter(Livraison livraison) {
        String selectReq = "SELECT COUNT(*) FROM `livraison` WHERE nom_c = ? AND prenom_c = ? AND email = ? AND adresse = ? AND type_paiement = ? AND state = ? AND phone_n = ?";
        String insertReq = "INSERT INTO `livraison` (nom_c, prenom_c, email, adresse, type_paiement, state, phone_n) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement selectSt = cnx.prepareStatement(selectReq);
            selectSt.setString(1, livraison.getNomC());
            selectSt.setString(2, livraison.getPrenomC());
            selectSt.setString(3, livraison.getEmail());
            selectSt.setString(4, livraison.getAdress());
            selectSt.setString(5, livraison.getPaiementType());
            selectSt.setString(6, livraison.getState());
            selectSt.setLong(7, livraison.getPhoneNumber());

            ResultSet rs = selectSt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            selectSt.close();

            if (count == 0) {
                PreparedStatement insertSt = cnx.prepareStatement(insertReq);
                insertSt.setString(1, livraison.getNomC());
                insertSt.setString(2, livraison.getPrenomC());
                insertSt.setString(3, livraison.getEmail());
                insertSt.setString(4, livraison.getAdress());
                insertSt.setString(5, livraison.getPaiementType());
                insertSt.setString(6, livraison.getState());
                insertSt.setLong(7, livraison.getPhoneNumber());

                insertSt.executeUpdate();
                insertSt.close();

                System.out.println("Livraison ajoutée avec succès");
            } else {
                System.out.println("Une livraison avec des attributs similaires existe déjà");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifier( Livraison livraison) {
        String req = "UPDATE livraison SET nom_c=?,prenom_c=?,email=?,adresse=?,type_paiement=?,state=?,phone_n=? WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, livraison.getNomC());
            ps.setString(2, livraison.getPrenomC());
            ps.setString(3, livraison.getEmail());
            ps.setString(4, livraison.getAdress());
            ps.setString(5, livraison.getPaiementType());
            ps.setString(6, livraison.getState());
            ps.setLong(7, livraison.getPhoneNumber());
            ps.setLong(8, livraison.getId());
            ps.executeUpdate();
            System.out.println("Livraison modifiée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de la livraison : " + e.getMessage());
        }


    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `livraison` WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            int rowCount = ps.executeUpdate();
            if (rowCount > 0) {
                System.out.println("livraison supprimée avec succès !");
                Livraison livraison = getOneByID(id);
                livraison.setArchived(true);
            } else {
                System.out.println("Aucune livraison trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la livraison : " + e.getMessage());
        }


    }

    @Override
    public Set<Livraison> getAll() {
        return Set.of();
    }

    public static List<Livraison> getAllLiv() {
        List<Livraison>livraisons=new ArrayList<>();
        String req ="SELECT * FROM `livraison`";
        try{
            Statement st = cnx.createStatement();
            ResultSet res= st.executeQuery(req);
            while (res.next()){

                Livraison livraison = new Livraison();
                if (livraison.getArchived() == false) {
                    livraison.setId(res.getInt("id"));
                    livraison.setNomC(res.getString("nom_c"));
                    livraison.setPrenomC(res.getString("prenom_c"));
                    livraison.setEmail(res.getString("email"));
                    livraison.setAdress(res.getString("adresse"));
                    livraison.setPaiementType(res.getString("type_paiement"));
                    livraison.setState(res.getString("state"));
                    livraison.setPhoneNumber(res.getLong("phone_n"));
                    livraisons.add(livraison);
                }
            }

        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture de livraison : " + e.getMessage());
        }


        return livraisons;
    }

    @Override
    public Livraison getOneByID(int id) {
        Livraison livraison = null ;
        String req = " SELECT * FROM livraison WHERE id=? ";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String NomC=rs.getString(5);
                String PrenomC=rs.getString(6);
                String email=rs.getString(7);
                String Adress=rs.getString(8);
                String PaimentType=rs.getString(9);
                String statE=rs.getString(10);
                long Phone=rs.getLong(11);
                boolean archived= rs.getBoolean(12);
                livraison=new Livraison(NomC ,PrenomC,email,Adress,PaimentType,statE,Phone,archived);
            }

        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération de la livraison : " + e.getMessage());
        }
        return livraison;
    }

    @Override
    public List<Livraison> getAllLivraisonsByUserId(int userId) {
        List<Livraison> livraisons = new ArrayList<>();
        String req = "SELECT * FROM `livraison` WHERE id_client_id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, userId);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                Livraison livraison = new Livraison();
                if (!livraison.getArchived()) {
                    livraison.setId(res.getInt("id"));
                    livraison.setNomC(res.getString("nom_c"));
                    livraison.setPrenomC(res.getString("prenom_c"));
                    livraison.setEmail(res.getString("email"));
                    livraison.setAdress(res.getString("adresse"));
                    livraison.setPaiementType(res.getString("type_paiement"));
                    livraison.setState(res.getString("state"));
                    livraison.setPhoneNumber(res.getLong("phone_n"));
                    livraisons.add(livraison);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la lecture de livraison : " + e.getMessage());
        }
        return livraisons;
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
