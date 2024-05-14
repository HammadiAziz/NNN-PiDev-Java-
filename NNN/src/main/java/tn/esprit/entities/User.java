package tn.esprit.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    int id,phone;
    String email,password,nom,prenom,block_reason,photo;
    String status="active";
    LocalDate birthday;
    List<String> roles = new ArrayList<>();
    int id_budge , wallet ;
    String reset_token ;
    int verification_code;
    String rolesJson;

    public User(){}
    public User(int id, int phone, String email, String password, String nom, String prenom, String block_reason, String photo, String status, LocalDate birthday, List<String> roles) {
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.block_reason = block_reason;
        this.photo = photo;
        this.status = status;
        this.birthday = birthday;
        this.roles = roles;
    }


    public User(int id, int phone, String email, String password, String nom, String prenom, String block_reason, String photo, String status, LocalDate birthday, List<String> roles, int id_budge, int wallet, String reset_token) {
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.block_reason = block_reason;
        this.photo = photo;
        this.status = status;
        this.birthday = birthday;
        this.roles = roles;
        this.id_budge = id_budge;
        this.wallet = wallet;
        this.reset_token = reset_token;
    }

    public int getVerification_code() {
        return verification_code;
    }

    public User(int id, int phone, String email, String password, String nom, String prenom, String block_reason, String photo, String status, LocalDate birthday, List<String> roles, int id_budge, int wallet, String reset_token, int verification_code) {
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.block_reason = block_reason;
        this.photo = photo;
        this.status = status;
        this.birthday = birthday;
        this.roles = roles;
        this.id_budge = id_budge;
        this.wallet = wallet;
        this.reset_token = reset_token;
        this.verification_code = verification_code;
    }

    public User(int phone, String email, String password, String nom, String prenom, String block_reason, String photo, String status, LocalDate birthday, List<String> roles, int id_budge, int wallet, String reset_token, int verification_code) {
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.block_reason = block_reason;
        this.photo = photo;
        this.status = status;
        this.birthday = birthday;
        this.roles = roles;
        this.id_budge = id_budge;
        this.wallet = wallet;
        this.reset_token = reset_token;
        this.verification_code = verification_code;
    }

    public void setVerification_code(int verification_code) {
        this.verification_code = verification_code;
    }

    public User(String nom, String prenom) {

        this.nom = nom;
        this.prenom = prenom;
    }

    public User(int id, int phone, String email, String password, String nom, String prenom, String block_reason, String photo, String status, LocalDate birthday, List<String> roles, int id_budge, int wallet) {
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.block_reason = block_reason;
        this.photo = photo;
        this.status = status;
        this.birthday = birthday;
        this.roles = roles;
        this.id_budge = id_budge;
        this.wallet = wallet;
    }

    public User(int phone, String email, String password, String nom, String prenom, String block_reason, String photo, String status, LocalDate birthday, List<String> roles, int id_budge, int wallet) {
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.block_reason = block_reason;
        this.photo = photo;
        this.status = status;
        this.birthday = birthday;
        this.roles = roles;
        this.id_budge = id_budge;
        this.wallet = wallet;
    }


    public String getRolesJson() {
        return rolesJson;
    }

    public void setRolesJson(String rolesJson) {
        this.rolesJson = rolesJson;
    }
    public String getReset_token() {
        return reset_token;
    }

    public void setReset_token(String reset_token) {
        this.reset_token = reset_token;
    }

    public int getId_budge() {
        return id_budge;
    }

    public void setId_budge(int id_budge) {
        this.id_budge = id_budge;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getBlock_reason() {
        return block_reason;
    }

    public void setBlock_reason(String block_reason) {
        this.block_reason = block_reason;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
