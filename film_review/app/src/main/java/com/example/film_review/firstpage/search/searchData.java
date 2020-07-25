package com.example.film_review.firstpage.search;

public class searchData {
    /**
     * User_id : string
     * Name : string
     * User_picture : string
     * Review_id : 0
     * Title : string
     * Content : string
     * Time : string
     * Tag : string
     * Picture : string
     * Comment_sum : 0
     * Like_sum : 0
     */

    private String User_id;
    private String Name;
    private String User_picture;
    private int Review_id;
    private String Title;
    private String Content;
    private String Time;
    private String Tag;
    private String Picture;
    private int Comment_sum;
    private int Like_sum;

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

    public int getReview_id() {
        return Review_id;
    }

    public void setReview_id(int Review_id) {
        this.Review_id = Review_id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String Tag) {
        this.Tag = Tag;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String Picture) {
        this.Picture = Picture;
    }

    public int getComment_sum() {
        return Comment_sum;
    }

    public void setComment_sum(int Comment_sum) {
        this.Comment_sum = Comment_sum;
    }

    public int getLike_sum() {
        return Like_sum;
    }

    public void setLike_sum(int Like_sum) {
        this.Like_sum = Like_sum;
    }
}
