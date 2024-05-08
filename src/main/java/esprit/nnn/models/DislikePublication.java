package esprit.nnn.models;

public class DislikePublication {
    private int id;
    private boolean stateDislike;
    public int id_publication;
    public int id_user;
    public DislikePublication() {
    }

    public DislikePublication(int id_publication, boolean stateDislike, int id_user, int id) {
        this.id_publication = id_publication;
        this.stateDislike = stateDislike;
        this.id_user = id_user;
        this.id = id;
    }

    public DislikePublication(int id_publication) {
        this.id_publication = id_publication;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStateDislike() {
        return stateDislike;
    }

    public void setStateDislike(boolean stateDislike) {
        this.stateDislike = stateDislike;
    }

    public int getId_publication() {
        return id_publication;
    }

    public void setId_publication(int id_publication) {
        this.id_publication = id_publication;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}
