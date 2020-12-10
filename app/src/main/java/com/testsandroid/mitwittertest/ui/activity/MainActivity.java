package com.testsandroid.mitwittertest.ui.activity;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.testsandroid.mitwittertest.model.request.RequestLogin;
import com.testsandroid.mitwittertest.model.response.ResponseAuth;
import com.testsandroid.mitwittertest.R;
import com.testsandroid.mitwittertest.retrofit.MiTwitterClient;
import com.testsandroid.mitwittertest.retrofit.MiTwitterService;
import com.testsandroid.mitwittertest.commons.Constants;
import com.testsandroid.mitwittertest.commons.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin;
    TextView tvGoSignUp;
    EditText email,password;
    MiTwitterService service;
    MiTwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        retrofitInit();
        findViews();
        events();

    }

    private void retrofitInit() {
        client = MiTwitterClient.getInstance();
        service = MiTwitterClient.getInstance().getMiTwitterService();
    }

    private void events() {
        btnLogin.setOnClickListener(this);
        tvGoSignUp.setOnClickListener(this);
    }

    private void findViews() {
        btnLogin = findViewById(R.id.button_login);
        tvGoSignUp = findViewById(R.id.textView_goSignUp);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.button_login:
                goToLogin();
                break;
            case R.id.textView_goSignUp:
                goToSignUp();
                break;
        }
    }

    private void goToLogin() {
        String txtEmail = email.getText().toString();
        String txtPass = password.getText().toString();
        if (txtEmail.isEmpty()){
            email.setError("Debe de ingresar Correo");
        }else if(txtPass.isEmpty()){
            password.setError("Debe de ingresar contraseña");

        }else {
            RequestLogin requestLogin = new RequestLogin(txtEmail,txtPass);

            Call<ResponseAuth> call = service.Login(requestLogin);
            //peticion asincrona
            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Sesión iniciada correctamente", Toast.LENGTH_SHORT).show();

                        SharedPreferencesManager.setSomeStringValue(Constants.PREF_TOKEN, response.body().getToken());
                        SharedPreferencesManager.setSomeBoolean(Constants.PREF_ACTIVE,response.body().getActive());
                        SharedPreferencesManager.setSomeStringValue(Constants.PREF_EMAIL,response.body().getEmail());
                        SharedPreferencesManager.setSomeStringValue(Constants.PREF_USERNAME,response.body().getUsername());
                        SharedPreferencesManager.setSomeStringValue(Constants.PREF_PHOTOURL,response.body().getPhotoUrl());
                        SharedPreferencesManager.setSomeStringValue(Constants.PREF_CREATED,response.body().getCreated());


                        Log.i("TOKEN","TOKEN: " + response.body().getToken());
                        Intent i = new Intent(MainActivity.this,DashboardActivity.class);

                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this, "Algo Ocurrió!! revise sus credenciales", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Problemas de conexión, Intente nuevamente", Toast.LENGTH_SHORT).show();
                }
            });

        }


    }

    private void goToSignUp() {
        Intent i = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(i);
        finish();
    }
}
