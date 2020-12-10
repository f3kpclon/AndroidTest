package com.testsandroid.mitwittertest.data;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.testsandroid.mitwittertest.commons.MyAPP;
import com.testsandroid.mitwittertest.model.response.Tweet;
import com.testsandroid.mitwittertest.retrofit.AuthTwitterClient;
import com.testsandroid.mitwittertest.retrofit.AuthTwitterService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TweetRepository {
    AuthTwitterClient authTwitterClient;
    AuthTwitterService authTwitterService;
    LiveData<List<Tweet>> allTweetsData;



    public TweetRepository() {
        authTwitterClient = AuthTwitterClient.getInstance();
        authTwitterService = authTwitterClient.getAuthTwitterService();
        allTweetsData = getAllTeets();
    }
    public LiveData<List<Tweet>> getAllTeets(){
        final MutableLiveData<List<Tweet>> data = new MutableLiveData<>();
        Call<List<Tweet>> call = authTwitterService.getAllTweets();

        call.enqueue(new Callback<List<Tweet>>() {

            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                if(response.isSuccessful()){
                   data.setValue(response.body());
                    Toast.makeText(MyAPP.getContext(), "Entramos!!", Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(MyAPP.getContext(), "Algo ha ido mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Tweet>> call, Throwable t) {
                Toast.makeText(MyAPP.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

        return data;

    }


}
