package tn.esprit.entities;

import java.util.ArrayList;
import java.util.List;

public class Groupe {
    private int id;
    private String name_group;
    private String desc_group;
    private String group_image;


    public List<Event> events;
    public List<Publication> publications;

    @Override
    public String toString() {
        return "Groupe{" +
                "id=" + id +
                ", name_group='" + name_group + '\'' +
                ", desc_group='" + desc_group + '\'' +
                ", group_image='" + group_image + '\'' +
                ", events=" + events +
                '}';
    }

    public Groupe() {
        this.events = new ArrayList<>();
        this.publications = new ArrayList<>();
    }

    public Groupe(String name_group, String desc_group, String group_image) {
        this.name_group = name_group;
        this.desc_group = desc_group;
        this.group_image = group_image;
        this.events = new ArrayList<>();
        this.publications = new ArrayList<>();

    }

    public Groupe(int id, String name_group, String desc_group, String group_image) {
        this.id = id;
        this.name_group = name_group;
        this.desc_group = desc_group;
        this.group_image = group_image;
        this.events = new ArrayList<>();
        this.publications = new ArrayList<>();
    }

    public Groupe(String group_image, String desc_group, String name_group, List<Event> events) {
        this.events = events;
        this.group_image = group_image;
        this.desc_group = desc_group;
        this.name_group = name_group;
        this.publications = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroup_image() {
        return group_image;
    }

    public void setGroup_image(String group_image) {
        this.group_image = group_image;
    }

    public String getDesc_group() {
        return desc_group;
    }

    public void setDesc_group(String desc_group) {
        this.desc_group = desc_group;
    }

    public String getName_group() {
        return name_group;
    }

    public void setName_group(String name_group) {
        this.name_group = name_group;
    }
}
