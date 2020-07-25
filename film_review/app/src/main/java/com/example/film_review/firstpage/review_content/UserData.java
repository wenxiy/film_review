package com.example.film_review.firstpage.review_content;

public class UserData {
    /**
     * userInfo : {"followers":"0","fans":"2","user_id":"202006","user_picture":"","name":"wenxin","attention":true}
     */

    private UserInfoBean userInfo;

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean {
        /**
         * followers : 0
         * fans : 2
         * user_id : 202006
         * user_picture :
         * name : wenxin
         * attention : true
         */

        private String followers;
        private String fans;
        private String user_id;
        private String user_picture;
        private String name;
        private boolean attention;

        public String getFollowers() {
            return followers;
        }

        public void setFollowers(String followers) {
            this.followers = followers;
        }

        public String getFans() {
            return fans;
        }

        public void setFans(String fans) {
            this.fans = fans;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_picture() {
            return user_picture;
        }

        public void setUser_picture(String user_picture) {
            this.user_picture = user_picture;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isAttention() {
            return attention;
        }

        public void setAttention(boolean attention) {
            this.attention = attention;
        }
    }
}
