package com.testsandroid.mitwittertest.retrofit;





import com.testsandroid.mitwittertest.model.response.Tweet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface AuthTwitterService {


    @GET("tweets/all")
    Call<List<Tweet>>getAllTweets();

    }
