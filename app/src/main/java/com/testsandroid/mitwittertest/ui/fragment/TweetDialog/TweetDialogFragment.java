package com.testsandroid.mitwittertest.ui.fragment.TweetDialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.testsandroid.mitwittertest.R;
import com.testsandroid.mitwittertest.commons.Constants;
import com.testsandroid.mitwittertest.commons.SharedPreferencesManager;
import com.testsandroid.mitwittertest.data.TweetViewModel;
import com.testsandroid.mitwittertest.model.response.Tweet;

import java.util.List;

public class TweetDialogFragment extends DialogFragment implements View.OnClickListener {

    ImageView ivCLose, ivAvatar;
    Button button;
    EditText edMessage;
    List<Tweet> tweetList;

    //Dialog newTweet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        getDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.new_tweet_dialog, container, false);

        ivCLose = view.findViewById(R.id.imageViewClose);
        ivAvatar = view.findViewById(R.id.imageViewAvtar);
        button = view.findViewById(R.id.buttonPOST);
        edMessage = view.findViewById(R.id.editTextTextMultiLine);

        ivCLose.setOnClickListener(this);
        button.setOnClickListener(this);
        //setting profileImage
        String photoUser = SharedPreferencesManager.getSomeStringValue(Constants.PREF_PHOTOURL);

        if(!photoUser.isEmpty()){
            Glide.with(getActivity())
                    .load(Constants.BASE_URL_FILE + photoUser)
                    .into(ivAvatar);
        }



        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String mensaje = edMessage.getText().toString();

        switch (id) {
            case R.id.imageViewClose:
                if (!mensaje.isEmpty()){
                    showDialogConfim();
                }else {
                    getDialog().dismiss();
                }

                break;
            case R.id.buttonPOST:
                if (mensaje.isEmpty()) {
                    Toast.makeText(getActivity(), "Debe de escribir un mensaje para postear", Toast.LENGTH_SHORT).show();
                } else {
                    TweetViewModel tweetViewModel = new ViewModelProvider(this).get(TweetViewModel.class);
                    tweetViewModel.postTweets(mensaje);
                    getDialog().dismiss();
                }

                break;
        }

    }

    private void showDialogConfim() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("¿Desea eliminar el POST? el mensaje se eliminará!!")
                .setTitle("Cancelar POST");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getDialog().dismiss();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
