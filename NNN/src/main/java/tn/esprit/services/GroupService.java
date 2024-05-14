package tn.esprit.services;

import tn.esprit.entities.Groupe;
import tn.esprit.interfaces.IServiceFatma;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupService implements IServiceFatma<Groupe> {

    Connection cnx= MaConnexion.getInstance().getCnx();


    @Override
    public void add(Groupe groupe) {
        String req= "INSERT INTO `groupe`( `name_group`, `desc_group`, `group_image`) VALUES ('"+groupe.getName_group()+"','"+groupe.getDesc_group()+"','"+groupe.getGroup_image()+"')";
        GroupService grp= new GroupService();
        Groupe existing=grp.FindByName(groupe.getName_group());
        if(existing != null){
            System.out.println("already exist");
            return;
        }

        try {
            Statement st= cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Groupe added successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }




    @Override
    public void update(Groupe groupe) {
        String req="UPDATE `groupe` SET `name_group`='"+groupe.getName_group()+"',`desc_group`='"+groupe.getDesc_group()+"',`group_image`='"+groupe.getGroup_image()+"' where `id`='"+groupe.getId()+"'";
        try {
            Statement st= cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Groupe updated successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void delete(Groupe groupe) {
        String req= "DELETE FROM `groupe` WHERE `id`=?";
        try {
            PreparedStatement statement = cnx.prepareStatement(req);
            statement.setInt(1,groupe.getId());
            statement.executeUpdate();
            System.out.println("Groupe deleted successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public List<Groupe> getAll() {
        List<Groupe> groupes = new ArrayList<>();
        String req= "SELECT * FROM groupe ";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Groupe grp = new Groupe();
                grp.setId(res.getInt("id"));
                grp.setName_group(res.getString("name_group"));
                grp.setDesc_group(res.getString("desc_group"));
                grp.setGroup_image(res.getString("group_image"));

                groupes.add(grp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groupes;
    }

    @Override
    public Groupe getOne(int id) {
        Groupe grp = null;
        String req = "SELECT * FROM `groupe` WHERE `id`=?";
        try {
            PreparedStatement statement = cnx.prepareStatement(req);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                grp = new Groupe();
                grp.setId(res.getInt("id"));
                grp.setName_group(res.getString("name_group"));
                grp.setDesc_group(res.getString("desc_group"));
                grp.setGroup_image(res.getString("group_image"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return grp;
    }

    public Groupe FindByName(String name_group) {
        Groupe grp = null;
        String req = "SELECT * FROM `groupe` WHERE `name_group`=?";
        try {
            PreparedStatement statement = cnx.prepareStatement(req);
            statement.setString(1, name_group); // Utilisez setString pour un paramètre de type String
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                grp = new Groupe();
                grp.setId(res.getInt("id"));
                grp.setName_group(res.getString("name_group"));
                grp.setDesc_group(res.getString("desc_group"));
                grp.setGroup_image(res.getString("group_image"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return grp;
    }
}
