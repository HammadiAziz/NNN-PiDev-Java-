package tn.esprit.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Publication {
    private int id;
    private String post_title;
    private String post_desc;
    private LocalDate date_post;
    private String image_post;
    private int group_post_id;
    public List<Like> likes;


    private boolean likedByCurrentUser;


    public boolean isLikedByCurrentUser() {
        return likedByCurrentUser;
    }

    public void updateLikeState(boolean liked) {
        likedByCurrentUser = liked;
    }





    public Publication(String post_title, String post_desc, LocalDate date_post, String image_post, int group_post_id) {
        this.post_title = post_title;
        this.post_desc = post_desc;
        this.date_post = date_post;
        this.image_post = image_post;
        this.group_post_id = group_post_id;
        this.likes = new ArrayList<>();


    }

    public Publication() {
        this.likes = new ArrayList<>();


    }

    @Override
    public String toString() {
        return "Publication{" +
                "id=" + id +
                ", post_title='" + post_title + '\'' +
                ", post_desc='" + post_desc + '\'' +
                ", date_post=" + date_post +
                ", image_post='" + image_post + '\'' +
                ", group_post_id=" + group_post_id +
                ", likes=" + likes +
                ", likedByCurrentUser=" + likedByCurrentUser +
                '}';
    }

    public String getPost_desc() {
        return post_desc;
    }

    public void setPost_desc(String post_desc) {
        this.post_desc = post_desc;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate_post() {
        return date_post;
    }

    public void setDate_post(LocalDate date_post) {
        this.date_post = date_post;
    }

    public String getImage_post() {
        return image_post;
    }

    public void setImage_post(String image_post) {
        this.image_post = image_post;
    }

    public int getGroup_post_id() {
        return group_post_id;
    }

    public void setGroup_post_id(int group_post_id) {
        this.group_post_id = group_post_id;
    }
}
