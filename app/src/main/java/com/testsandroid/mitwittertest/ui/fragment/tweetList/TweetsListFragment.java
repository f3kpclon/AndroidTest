package com.testsandroid.mitwittertest.ui.fragment.tweetList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.testsandroid.mitwittertest.R;
import com.testsandroid.mitwittertest.data.TweetViewModel;
import com.testsandroid.mitwittertest.model.response.Tweet;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class TweetsListFragment extends Fragment {

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

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TweetsListFragment newInstance(int columnCount) {
        TweetsListFragment fragment = new TweetsListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweetViewModel = new ViewModelProvider(this).get(TweetViewModel.class);


        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets_list_list, container, false);
        //progressBar = view.findViewById(R.id.progressBarTest);
        //progressBar.setVisibility(View.VISIBLE);
        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyTweetsRecyclerViewAdapter(
                    getActivity(),
                    tweetList
            );
            recyclerView.setAdapter(adapter);


            loadDataAdapter();







        }
        return view;
    }


    private void loadDataAdapter() {

        tweetViewModel.getListLiveData().observe(getActivity(), new Observer<List<Tweet>>() {

            @Override
            public void onChanged(@Nullable List<Tweet> tweets) {
                tweetList = tweets;
                adapter.setData(tweetList);


            }


        });




    }
}