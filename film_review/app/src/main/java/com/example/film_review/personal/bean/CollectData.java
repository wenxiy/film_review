package com.example.film_review.personal.bean;

public class CollectData {

    /**
     * user_id : 202006
     * name : wenxin
     * user_picture : none
     * review_id : 2
     * title : 卢本伟高光时刻
     * content :   我常说一句话：当年陈刀仔用20块赢到3700万，我卢本伟用200万赢到500万，不是问题。
     * time : 2020-03-06T21:02:42Z
     * tag : 卢本伟
     * picture : 1234567813
     * comment_sum : 3
     * like_sum : 1
     */

    private String user_id;
    private String name;
    private String user_picture;
    private int review_id;
    private String title;
    private String content;
    private String time;
    private String tag;
    private String picture;
    private int comment_sum;
    private int like_sum;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_picture() {
        return user_picture;
    }

    public void setUser_picture(String user_picture) {
        this.user_picture = user_picture;
    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getComment_sum() {
        return comment_sum;
    }

    public void setComment_sum(int comment_sum) {
        this.comment_sum = comment_sum;
    }

    public int getLike_sum() {
        return like_sum;
    }

    public void setLike_sum(int like_sum) {
        this.like_sum = like_sum;
    }
}
