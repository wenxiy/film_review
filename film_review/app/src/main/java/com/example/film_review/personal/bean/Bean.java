package com.example.film_review.personal.bean;

public class Bean {

    /**
     * Followers : string
     * Fans : string
     * User_id : string
     * Name : string
     * User_picture : string
     * Attention : true
     */

    private String Followers;
    private String Fans;
    private String User_id;
    private String Name;
    private String User_picture;
    private boolean Attention;

    public String getFollowers() {
        return Followers;
    }

    public void setFollowers(String Followers) {
        this.Followers = Followers;
    }

    public String getFans() {
        return Fans;
    }

    public void setFans(String Fans) {
        this.Fans = Fans;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String User_id) {
        this.User_id = User_id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getUser_picture() {
        return User_picture;
    }

    public void setUser_picture(String User_picture) {
        this.User_picture = User_picture;
    }

    public boolean isAttention() {
        return Attention;
    }

    public void setAttention(boolean Attention) {
        this.Attention = Attention;
    }
}
