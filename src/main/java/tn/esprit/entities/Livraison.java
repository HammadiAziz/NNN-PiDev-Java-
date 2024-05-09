package tn.esprit.entities;

public class Livraison {
    //att
    private String NomC ,prenomC ,email , adresse ,typePaiement , state ;
    private int id ;
    private long phoneN ;
    private boolean archived = false;
    private int id_suivi ;
    private int user_id;

    public Livraison(int user_id) {
        this.user_id = user_id;
    }

//cons

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Livraison(String nomC, String prenomC, String email, String adresse, String typePaiement, String state, int id, long phoneN, boolean archived, int id_suivi, int user_id) {
        NomC = nomC;
        this.prenomC = prenomC;
        this.email = email;
        this.adresse = adresse;
        this.typePaiement = typePaiement;
        this.state = state;
        this.id = id;
        this.phoneN = phoneN;
        this.archived = archived;
        this.id_suivi = id_suivi;
        this.user_id = user_id;
    }

    public Livraison() {
    }

    public Livraison(String nomC, String prenomC, String email, String adresse, String typePaiement, String state, int id, long phoneN, boolean archived, int id_suivi) {
        NomC = nomC;
        this.prenomC = prenomC;
        this.email = email;
        this.adresse = adresse;
        this.typePaiement = typePaiement;
        this.state = state;
        this.id = id;
        this.phoneN = phoneN;
        this.archived = archived;
        this.id_suivi = id_suivi;
    }

    public Livraison(String nomC , String prenomC , String typePaiement , String state , String adresse , String email , long phoneN, boolean archived ) {
        this.NomC = nomC;
        this.prenomC = prenomC;
        this.email = email;
        this.state = state;
        this.adresse = adresse ;
        this.typePaiement = typePaiement ;
        this.phoneN = phoneN ;
        this.archived = archived;

    }

    public Livraison(String nomC, String prenomC, String email, String adresse, String typePaiement, String state, int id, long phoneN) {
        this.NomC = nomC;
        this.prenomC = prenomC;
        this.email = email;
        this.adresse = adresse;
        this.typePaiement = typePaiement;
        this.state = state;
        this.id = id;
        this.phoneN = phoneN;

    }
    public Livraison(String nomC, String prenomC, String email, String adresse, String typePaiement, String state, long phoneN) {
        this.NomC = nomC;
        this.prenomC = prenomC;
        this.email = email;
        this.adresse = adresse;
        this.typePaiement = typePaiement;
        this.state = state;
        this.phoneN = phoneN;

    }


    //getters and setters


    public String getNomC() {
        return NomC;
    }

    public void setNomC(String nomC) {
        NomC = nomC;
    }

    public String getPrenomC() {
        return prenomC;
    }

    public void setPrenomC(String prenomC) {
        this.prenomC = prenomC;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adresse;
    }

    public void setAdress(String adress) {
        this.adresse = adress;
    }

    public String getPaiementType() {
        return typePaiement;
    }

    public void setPaiementType(String paiementType) {
        this.typePaiement = paiementType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getArchived() {
        return archived;
    }

    public int getId_suivi() {
        return id_suivi;
    }

    public void setId_suivi(int id_suivi) {
        this.id_suivi = id_suivi;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public long getPhoneNumber() {
        return phoneN;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneN = phoneNumber;
    }
    //display

    @Override
    public String toString() {
        return "Livraison{" +
                "NomC='" + NomC + '\'' +
                ", prenomC='" + prenomC + '\'' +
                ", email='" + email + '\'' +
                ", Adress='" + adresse + '\'' +
                ", PaiementType='" + typePaiement + '\'' +
                ", state='" + state + '\'' +
                ", id=" + id +
                ", PhoneNumber=" + phoneN +
                '}';
    }
}
