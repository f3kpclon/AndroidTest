package com.testsandroid.mitwittertest.ui.fragment.tweetList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.testsandroid.mitwittertest.R;
import com.testsandroid.mitwittertest.data.TweetViewModel;
import com.testsandroid.mitwittertest.model.response.Tweet;
import com.testsandroid.mitwittertest.ui.fragment.TweetDialog.TweetDialogFragment;
import com.testsandroid.mitwittertest.ui.interfaces.MyListener;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class TweetsListFragment extends Fragment implements MyListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    RecyclerView recyclerView;
    MyTweetsRecyclerViewAdapter adapter;
    Context context;
    List<Tweet> tweetList;
    TweetViewModel tweetViewModel;
    ProgressBar progressBar;



    public TweetsListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets_list_list, container, false);

        tweetViewModel = new ViewModelProvider(this).get(TweetViewModel.class);

        FloatingActionButton floatButton = view.findViewById(R.id.fab2);
        progressBar = view.findViewById(R.id.loading);
        recyclerView = view.findViewById(R.id.list);

        // Set the adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        adapter = new MyTweetsRecyclerViewAdapter(getActivity(), tweetList);
        recyclerView.setAdapter(adapter);
        loadDataAdapter();

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TweetDialogFragment tweetDialogFragment = new TweetDialogFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                tweetDialogFragment.setMyListener(TweetsListFragment.this);
                tweetDialogFragment.show(fragmentManager,"TweetDialogFragment");



                }
        });


        return view;
    }


    private void loadDataAdapter() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tweetViewModel.getListLiveData().observe(getActivity(), tweets -> {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            tweetList = tweets;
            adapter.setData(tweetList);


        });

    }


    @Override
    public void saveTweetListener() {
        loadDataAdapter();
    }
}