package com.example.film_review.personal.bean;

public class PostBean {
    @Override
    public String toString() {
        return  user_id ;
    }

    /**
     * user_id : string
     */

    private String user_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
