package com.example.film_review.firstpage;

import com.example.film_review.firstpage.attention.AttentionItemData;
import com.example.film_review.firstpage.found.groundsData;
import com.example.film_review.firstpage.review_content.ReviewContext;
import com.example.film_review.firstpage.review_content.SingleComment;
import com.example.film_review.firstpage.review_content.SingleCommentData;
import com.example.film_review.firstpage.review_content.UserData;
import com.example.film_review.firstpage.search.SearchText;
import com.example.film_review.firstpage.search.searchData;


import java.util.EmptyStackException;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitAPI {
    @GET("/api/v1/grounds")
    Call<List<groundsData>> getData();
    @GET("api/v1/grounds/{user_id}")
    Call<List<AttentionItemData>> getAttention(@Header("token")String token, @Path("user_id") String user_id);
    @GET("api/v1/reviews/{review_id}")
    Call<ReviewContext> getReview(@Header("token")String token, @Path("review_id") String review_id);
    @POST("api/v1//reviews/{review_id}/comment")
    Call<SingleCommentData> SentComment(@Header("token")String token, @Path("review_id")String revire_id, @Body SingleComment singleComment);
    @PUT("api/v1/reviews/{review_id}")
    Call<EmptyStackException> Liking(@Header("token")String token, @Path("review_id") String review_id);
    @PATCH("api/v1/reviews/{review_id}")
    Call<EmptyStackException> Collecting(@Header("token")String token, @Path("review_id") String review_id);
    @PUT("api/v1/review/comments/{comment_id}")
    Call<EmptyStackException> CommentLiking(@Header("token")String token,@Path("comment_id")String comment_id);
    @POST("api/v1/searcher/results")
    Call<List<searchData>> search(@Body SearchText searchText);
    @GET("api/v1/people/{user_id}")
    Call<UserData.UserInfoBean> AttentionData(@Header("token")String token, @Path("user_id") String user_id);
    @PATCH("api/v1/people/{user_id}")
    Call<EmptyStackException> takeAttention(@Header("token")String token, @Path("user_id") String user_id);
}
