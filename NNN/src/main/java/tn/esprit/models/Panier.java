package tn.esprit.models;

public class Panier {
    int id , product_id , user_id ;

    public Panier() {
    }

    public Panier(int id, int product_id) {
        this.id = id;
        this.product_id = product_id;
    }

    public Panier(int id, int product_id, int user_id) {
        this.id = id;
        this.product_id = product_id;
        this.user_id = user_id;
    }


    public Panier(int product_id) {
        this.product_id = product_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
