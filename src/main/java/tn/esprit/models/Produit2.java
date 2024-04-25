package tn.esprit.models;

public class Produit2 {

    //Att
    private String nomprodui,image,desc1,desc2;
    private int id,prixprodui;

    //constructor
    public Produit2() {
    }
    public Produit2(String nomprodui, int prixprodui, String image, String desc1, String desc2) {
        this.nomprodui = nomprodui;
        this.prixprodui = prixprodui;
        this.image = image;
        this.desc1 = desc1;
        this.desc2 = desc2;
    }
    public Produit2(int id, String nomprodui, int prixprodui, String image, String desc1, String desc2) {
        this.id = id;
        this.nomprodui = nomprodui;
        this.prixprodui = prixprodui;
        this.image = image;
        this.desc1 = desc1;
        this.desc2 = desc2;
    }
    //Getters and setters


    public String getNomprodui() {
        return nomprodui;
    }

    public void setNomprodui(String nomprodui) {
        this.nomprodui = nomprodui;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrixprodui() {
        return prixprodui;
    }

    public void setPrixprodui(int prixprodui) {
        this.prixprodui = prixprodui;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }
    //Display

    @Override
    public String toString() {
        return "Produit2{" +
                " id=" + id +
                ",nomprodui='" + nomprodui + '\'' +
                ", image='" + image + '\'' +
                ", desc1='" + desc1 + '\'' +
                ", desc2='" + desc2 + '\'' +
                ", prixprodui=" + prixprodui +
                '}';
    }
}
