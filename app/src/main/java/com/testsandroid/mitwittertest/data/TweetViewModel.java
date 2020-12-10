package com.testsandroid.mitwittertest.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.testsandroid.mitwittertest.model.response.Tweet;

import java.util.List;

public class TweetViewModel extends AndroidViewModel {

    private TweetRepository tweetRepository;
    private LiveData<List<Tweet>> listLiveData;

    public TweetViewModel(@NonNull Application application) {
        super(application);
        tweetRepository = new TweetRepository();
        listLiveData = tweetRepository.getAllTeets();
    }


    public LiveData<List<Tweet>> getListLiveData() {
        return listLiveData;
    }
    public void postTweets(String message){
        tweetRepository.postTweet(message);
    }
}
