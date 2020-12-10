package com.testsandroid.mitwittertest.data;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.testsandroid.mitwittertest.commons.MyAPP;
import com.testsandroid.mitwittertest.model.request.RequestPostTweet;
import com.testsandroid.mitwittertest.model.response.Tweet;
import com.testsandroid.mitwittertest.retrofit.AuthTwitterClient;
import com.testsandroid.mitwittertest.retrofit.AuthTwitterService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TweetRepository {
    AuthTwitterClient authTwitterClient;
    AuthTwitterService authTwitterService;
    MutableLiveData<List<Tweet>> allTweetsData;


    public TweetRepository() {
        authTwitterClient = AuthTwitterClient.getInstance();
        authTwitterService = authTwitterClient.getAuthTwitterService();
        allTweetsData = getAllTeets();
    }

    public MutableLiveData<List<Tweet>> getAllTeets() {

        if (allTweetsData == null) {
            allTweetsData = new MutableLiveData<>();
        }
        Call<List<Tweet>> call = authTwitterService.getAllTweets();

        call.enqueue(new Callback<List<Tweet>>() {

            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                if (response.isSuccessful()) {
                    allTweetsData.setValue(response.body());
                    Toast.makeText(MyAPP.getContext(), "Entramos!!", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(MyAPP.getContext(), "Algo ha ido mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Tweet>> call, Throwable t) {
                Toast.makeText(MyAPP.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

        return allTweetsData;

    }

    public void postTweet(String message) {
        RequestPostTweet requestPostTweet = new RequestPostTweet(message);
        Call<Tweet> call = authTwitterService.postTweet(requestPostTweet);
        call.enqueue(new Callback<Tweet>() {
            @Override
            public void onResponse(Call<Tweet> call, Response<Tweet> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MyAPP.getContext(), "Post Logrado", Toast.LENGTH_SHORT).show();
                    //clonar lista
                    List<Tweet> clonedList = new ArrayList<>();
                    clonedList.add(response.body());

                    for (int i = 0; i < allTweetsData.getValue().size(); i++) {
                        clonedList.add(new Tweet(allTweetsData.getValue().get(i)));
                    }
                    //primer lugar al ultimo tweet que generamos y nos llega del server

                    allTweetsData.setValue(clonedList);

                } else {
                    Toast.makeText(MyAPP.getContext(), "Algo Ocurrio, intente nuevamente", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Tweet> call, Throwable t) {
                Toast.makeText(MyAPP.getContext(), "Error de conexión!!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
