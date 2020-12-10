package com.testsandroid.mitwittertest.retrofit;


import com.testsandroid.mitwittertest.model.request.RequestPostTweet;
import com.testsandroid.mitwittertest.model.response.Tweet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface AuthTwitterService {


    @GET("tweets/all")
    Call<List<Tweet>> getAllTweets();

    @POST("tweets/create")
    Call<Tweet> postTweet(@Body RequestPostTweet requestPostTweet);

}
