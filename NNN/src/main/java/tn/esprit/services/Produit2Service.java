package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Produit2;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Produit2Service implements IService<Produit2>{
    //att
    Connection cnx = MaConnexion.getInstance().getCnx();
    //actions
    @Override
    public void add(Produit2 produit2) {
        String req ="INSERT INTO `produit2`( `nomprodui`, `prixprodui`,`image`, `desc1`, `desc2`) VALUES ('"+produit2.getNomprodui()+"',"+produit2.getPrixprodui()+",'"+produit2.getImage()+"','"+produit2.getDesc1()+"','"+produit2.getDesc2()+"')";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Product added successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int id, Produit2 produit2) {
        String req = "UPDATE produit2 SET nomprodui=?,image=?,prixprodui=?,desc1=?,desc2=? WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, produit2.getNomprodui());
            ps.setString(2, produit2.getImage());
            ps.setInt(3, produit2.getPrixprodui());
            ps.setString(4, produit2.getDesc1());
            ps.setString(5, produit2.getDesc2());
            ps.setInt(6,id);

            ps.executeUpdate();
            System.out.println("product successfully modified\n !");
        } catch (SQLException e) {
            System.out.println("Error while editing product\n : " + e.getMessage());
        }

    }


    @Override
    public void delete(int id) {
        String req = "DELETE FROM produit2 WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            int rowCount = ps.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Product deleted successfully!");
            } else {
                System.out.println("No products found with this ID\n.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting product\n : " + e.getMessage());
        }


    }


        @Override
    public List<Produit2> getAll() {
        List<Produit2> produit2s = new ArrayList<>();

        String req ="SELECT * FROM Produit2";

        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while(res.next()){
                Produit2 produit2=new Produit2();
                produit2.setId(res.getInt("id"));
                produit2.setNomprodui(res.getString(3));
                produit2.setPrixprodui(res.getInt(4));
                produit2.setImage(res.getString(5));
                produit2.setDesc1(res.getString(6));
                produit2.setDesc2(res.getString(7));


                produit2s.add(produit2);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return produit2s;
    }

    @Override
    public Produit2 getOne(int id) {

        Produit2 produit2 = null ;
        String req = " SELECT * FROM produit2 WHERE id=? ";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String nomprodui=rs.getString(3);
                int prixprodui=rs.getInt(4);
                String image=rs.getString(5);
                String desc1=rs.getString(6);
                String desc2=rs.getString(7);


                produit2=new Produit2(id,nomprodui,prixprodui,image,desc1,desc2);
            }

        } catch (Exception e) {
            System.out.println("Error retrieving product\n : " + e.getMessage());
        }
        return produit2;
    }



}
