package com.testsandroid.mitwittertest.ui.fragment.tweetList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.testsandroid.mitwittertest.R;
import com.testsandroid.mitwittertest.commons.Constants;
import com.testsandroid.mitwittertest.commons.SharedPreferencesManager;
import com.testsandroid.mitwittertest.model.response.Like;
import com.testsandroid.mitwittertest.model.response.Tweet;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Tweet}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTweetsRecyclerViewAdapter extends RecyclerView.Adapter<MyTweetsRecyclerViewAdapter.ViewHolder> {

    private  List<Tweet> mValues;
    private Context ctx;
    String username;

    public MyTweetsRecyclerViewAdapter(Context context, List<Tweet>items) {

        mValues = items;
        ctx = context;
        username = SharedPreferencesManager.getSomeStringValue(Constants.PREF_USERNAME);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tweets_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if(mValues != null){
        holder.mItem = mValues.get(position);

        holder.tvUsername.setText(holder.mItem.getUser().getUsername());
        holder.tvMessage.setText(holder.mItem.getMensaje());
        holder.tvNumLikes.setText(String.valueOf(holder.mItem.getLikes().size()));

        String photo = holder.mItem.getUser().getPhotoUrl();

        if(!photo.equals("")){
            Glide.with(ctx)
                    .load("https://www.minitwitter.com/apiv1/uploads/photos/"
                    + photo)
                    .into(holder.ivAvatar);
        }

        for (Like like: holder.mItem.getLikes()
             ) {
            if(like.getUsername().equals(username)){
                Glide.with(ctx)
                        .load(R.drawable.ic_like_pink)
                        .into(holder.ivLike);
                holder.tvNumLikes.setTextColor(ctx.getResources().getColor(R.color.pink));
                holder.tvNumLikes.setTypeface(null, Typeface.BOLD);
                break;
            }

        }
        }


    }
    public void setData (List<Tweet> tweetList){
        this.mValues = tweetList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        if (mValues != null){
        return  mValues.size();
        }else{
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView ivAvatar;
        public  final ImageView ivLike;
        public final TextView tvUsername;
        public final TextView tvMessage;
        public final TextView tvNumLikes;
        public Tweet mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvMessage =  view.findViewById(R.id.textViewMessage);
            tvNumLikes =  view.findViewById(R.id.textViewLikes);
            tvUsername =  view.findViewById(R.id.textViewUsername);
            ivAvatar = view.findViewById(R.id.imageViewAvatar);
            ivLike =  view.findViewById(R.id.imageViewLikes);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvUsername.getText() + "'";
        }

    }
}