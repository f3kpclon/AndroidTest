package com.testsandroid.mitwittertest.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.testsandroid.mitwittertest.R;
import com.testsandroid.mitwittertest.commons.Constants;
import com.testsandroid.mitwittertest.commons.SharedPreferencesManager;

public class DashboardActivity extends AppCompatActivity {
    ImageView ivAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        ivAvatar = findViewById(R.id.imageViewUser);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_likes, R.id.navigation_profile)
                .build();
        getSupportActionBar().hide();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);



        String photoUSer = SharedPreferencesManager.getSomeStringValue(Constants.PREF_PHOTOURL);
        if(!photoUSer.isEmpty()){
            Glide.with(this)
                    .load(Constants.BASE_URL_FILE + photoUSer)
                    .into(ivAvatar);
        }



    }

}