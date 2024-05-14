package tn.esprit.entities;

public class Like {

    private int id;
    private int id_publication;
    private boolean stateLike;
    private int id_user;

    public Like() {

    }

    public Like(int id_publication) {
        this.id_publication = id_publication;
    }

    public Like(boolean stateLike, int id_publication, int id_user) {
        this.stateLike = stateLike;
        this.id_publication = id_publication;
        this.id_user = id_user;
    }

    public Like(int id_publication, boolean stateLike, int id_user, int id) {
        this.id_publication = id_publication;
        this.stateLike = stateLike;
        this.id_user = id_user;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_publication() {
        return id_publication;
    }

    public void setId_publication(int id_publication) {
        this.id_publication = id_publication;
    }

    public boolean isStateLike() {
        return stateLike;
    }

    public void setStateLike(boolean stateLike) {
        this.stateLike = stateLike;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}
