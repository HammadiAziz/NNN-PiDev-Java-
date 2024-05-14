package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Commande2;
import tn.esprit.models.Produit2;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public  class Commande2Service implements IService<Commande2> {

    //att
    Connection cnx = MaConnexion.getInstance().getCnx();
    //actions
    @Override
    public void add(Commande2 commande2) {
        String req = "INSERT INTO commande2 (prixtotale, user_id, produits) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, commande2.getPrixtotale());
            ps.setInt(2, commande2.getUser_id());
            ps.setString(3, commande2.getProduits());
            int rowCount = ps.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Command added successfully!");
            } else {
                System.out.println("Failed to add command.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding command: " + e.getMessage());
        }
    }

    public List<Commande2> getOrderedProductsForUser(int userId) {
        List<Commande2> orderedProducts = new ArrayList<>();

        String req = "SELECT * FROM commande2 WHERE user_id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Commande2 commande2 = new Commande2();
                commande2.setId(rs.getInt("id"));
                commande2.setPrixtotale(rs.getInt("prixtotale"));
                commande2.setUser_id(rs.getInt("user_id"));
                commande2.setProduits(rs.getString("produits"));

                orderedProducts.add(commande2);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving ordered products: " + e.getMessage());
        }
        return orderedProducts;
    }
    @Override
    public void update(int id, Commande2 commande2) {
    }
    @Override
    public void delete(int id) {
        String req = "DELETE FROM commande2 WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            int rowCount = ps.executeUpdate();
            if (rowCount > 0) {
                System.out.println("order deleted successfully!");
            } else {
                System.out.println("No orders found with this ID\n.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting order\n : " + e.getMessage());
        }

    }

    @Override
    public List<Commande2> getAll() {
        List<Commande2> Commande2s = new ArrayList<>();

        String req ="SELECT * FROM Commande2";

        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while(res.next()){
                Commande2 commande2=new Commande2();
                commande2.setId(res.getInt("id"));
                commande2.setPrixtotale(res.getInt(2));
                commande2.setUser_id(res.getInt(3));
                commande2.setProduits(res.getString(4));

                Commande2s.add(commande2);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Commande2s;
    }

    @Override
    public Commande2 getOne(int id) {

        Commande2 commande2 = null ;
        String req = " SELECT * FROM commande2 WHERE id=? ";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int prixtotale=rs.getInt(2);
                int user_id=rs.getInt(3);
                String produits=rs.getString(4);
                commande2=new Commande2(id,prixtotale,user_id,produits);
            }

        } catch (Exception e) {
            System.out.println("Error retrieving order\n : " + e.getMessage());
        }
        return commande2;
    }
}
