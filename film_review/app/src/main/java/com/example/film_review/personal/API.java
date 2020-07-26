package com.example.film_review.personal;

import com.example.film_review.personal.bean.Bean;
import com.example.film_review.personal.bean.CollectData;
import com.example.film_review.personal.bean.PostBean;
import com.example.film_review.personal.bean.PostData;
import com.example.film_review.personal.bean.PostLoginBean;
import com.example.film_review.personal.bean.PostToken;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.Call;

public interface API {
    @GET("/api/v1/createuser")
//这是用户主页的get网络请求
    Call<Bean> gethomepage();

    @POST("/api/v1/createuser")
//这是注册的网络请求
    Call<PostBean> getrPostdata(@Body PostData postdata);

    @POST("/api/v1/login")
//这是登录的网络请求
    Call<PostToken> getPost_login(@Body PostLoginBean post_loginBean);

    @GET("/api/v1/people/{user_id}/collection")
    Call<List<CollectData>> getCollection(@Header("token") String token, @Path("user_id") String user_id);
}