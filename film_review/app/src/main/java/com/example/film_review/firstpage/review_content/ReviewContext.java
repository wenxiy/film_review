package com.example.film_review.firstpage.review_content;

import java.util.List;

public class ReviewContext {

    /**
     * comment : [{"User_id":"202006","Name":"wenxin","User_picture":"none","Comment_id":2,"Content":"hello world","Time":"2020-03-26T20:18:43Z","Like_sum":0,"Comment_like":false}]
     * review_collection : false
     * review_like : false
     */

    private boolean review_collection;
    private boolean review_like;
    private List<CommentBean> comment;

    public boolean isReview_collection() {
        return review_collection;
    }

    public void setReview_collection(boolean review_collection) {
        this.review_collection = review_collection;
    }

    public boolean isReview_like() {
        return review_like;
    }

    public void setReview_like(boolean review_like) {
        this.review_like = review_like;
    }

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    public static class CommentBean {
        /**
         * User_id : 202006
         * Name : wenxin
         * User_picture : none
         * Comment_id : 2
         * Content : hello world
         * Time : 2020-03-26T20:18:43Z
         * Like_sum : 0
         * Comment_like : false
         */

        private String User_id;
        private String Name;
        private String User_picture;
        private int Comment_id;
        private String Content;
        private String Time;
        private int Like_sum;
        private boolean Comment_like;

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

        public int getComment_id() {
            return Comment_id;
        }

        public void setComment_id(int Comment_id) {
            this.Comment_id = Comment_id;
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

        public int getLike_sum() {
            return Like_sum;
        }

        public void setLike_sum(int Like_sum) {
            this.Like_sum = Like_sum;
        }

        public boolean isComment_like() {
            return Comment_like;
        }

        public void setComment_like(boolean Comment_like) {
            this.Comment_like = Comment_like;
        }
    }
}
