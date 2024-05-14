package tn.esprit.entities;

import java.time.LocalDate;
import java.util.Date;

public class Event {
    private int id;
    private String nom;
    private String desc_event;
    private LocalDate date_d;
    private LocalDate date_f;
    private int groupe_id;
    private int user_id;

    public Event() {

    }

    public Event(int groupe_id, String desc_event, String nom,  LocalDate date_d, LocalDate date_f, int user_id) {

        this.desc_event = desc_event;
        this.nom = nom;
        this.groupe_id = groupe_id;
        this.date_d = date_d;
        this.date_f = date_f;
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Event(int groupe_id, String nom, String desc_event, LocalDate date_d, LocalDate date_f) {
        this.nom = nom;
        this.date_d = date_d;
        this.desc_event = desc_event;
        this.date_f = date_f;
        this.groupe_id = groupe_id;
    }

    public Event(int id, String nom, LocalDate date_d) {
        this.id = id;
        this.nom = nom;
        this.date_d = date_d;
    }

    public Event(String nom, String desc_event, LocalDate date_d, LocalDate date_f) {
        this.nom = nom;
        this.desc_event = desc_event;
        this.date_d = date_d;
        this.date_f = date_f;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDesc_event() {
        return desc_event;
    }

    public void setDesc_event(String desc_event) {
        this.desc_event = desc_event;
    }

    public LocalDate getDate_d() {
        return date_d;
    }

    public void setDate_d(LocalDate date_d) {
        this.date_d = date_d;
    }

    public LocalDate getDate_f() {
        return date_f;
    }

    public void setDate_f(LocalDate date_f) {
        this.date_f = date_f;
    }

    public int getGroupe_id() {
        return groupe_id;
    }

    public void setGroupe_id(int groupe_id) {
        this.groupe_id = groupe_id;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", desc_event='" + desc_event + '\'' +
                ", date_d=" + date_d +
                ", date_f=" + date_f +
                ", groupe_id=" + groupe_id +
                '}';
    }
}
