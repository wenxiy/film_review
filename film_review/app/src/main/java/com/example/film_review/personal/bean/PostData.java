package com.example.film_review.personal.bean;
//注册的发送请求数据类
public class PostData {

    /**
     * name : string
     * password : string
     */

    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
