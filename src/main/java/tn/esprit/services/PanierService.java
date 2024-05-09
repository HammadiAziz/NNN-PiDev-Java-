package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Commande2;
import tn.esprit.models.Panier;
import tn.esprit.models.Produit2;
import tn.esprit.util.MaConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PanierService implements IService<Panier> {

    private Connection cnx;

    public PanierService() {
        this.cnx = MaConnexion.getInstance().getCnx();
    }

    public void addToPanier(int productId, int userId) {
        String req = "INSERT INTO panier (product_id, user_id) VALUES (?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, productId);
            ps.setInt(2, userId);
            ps.executeUpdate();
            System.out.println("Product added to panier successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding product to panier: " + e.getMessage());
        }
    }


    public List<Produit2> getProductsInPanier(int userId) {
        List<Produit2> products = new ArrayList<>();
        String req = "SELECT p.* FROM produit2 p INNER JOIN panier pa ON p.id = pa.product_id WHERE pa.user_id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Produit2 product = new Produit2();
                product.setId(rs.getInt("id"));
                product.setNomprodui(rs.getString("nomprodui"));
                product.setDesc1(rs.getString("desc1"));
                product.setDesc2(rs.getString("desc2"));
                product.setPrixprodui(rs.getInt("prixprodui"));
                product.setImage(rs.getString("image"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    public void moveProductsToCommande(int userId) {

        List<Produit2> productsInPanier = getProductsInPanier(userId);


        double totalPrice = productsInPanier.stream().mapToDouble(Produit2::getPrixprodui).sum();
        double tva = totalPrice * 0.2;
        double totalPriceWithTVA = totalPrice + tva+5;


        Commande2 commande2 = new Commande2((int) totalPriceWithTVA, userId, produitsToString(productsInPanier));


        Commande2Service commande2Service = new Commande2Service();
        commande2Service.add(commande2);


        for (Produit2 product : productsInPanier) {
            deleteProductFromPanier(product.getId());
        }
    }

    private String produitsToString(List<Produit2> produits) {
        StringBuilder sb = new StringBuilder();
        for (Produit2 produit : produits) {
            sb.append(produit.getNomprodui()).append(", ");
        }
        return sb.toString();
    }
    public void deleteProductFromPanier(int productId) {
        String req = "DELETE FROM panier WHERE product_id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, productId);
            ps.executeUpdate();
            System.out.println("Product deleted from panier successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting product from panier: " + e.getMessage());
        }
    }

    @Override
    public void add(Panier panier) {

    }

    @Override
    public void update(int id, Panier panier) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Panier> getAll() {
        return null;
    }

    @Override
    public Panier getOne(int id) {
        return null;
    }

}
