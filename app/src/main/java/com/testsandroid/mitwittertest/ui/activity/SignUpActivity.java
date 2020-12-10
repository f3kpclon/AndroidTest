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

import com.testsandroid.mitwittertest.model.request.RequestSignUp;
import com.testsandroid.mitwittertest.model.response.ResponseAuth;
import com.testsandroid.mitwittertest.R;
import com.testsandroid.mitwittertest.retrofit.MiTwitterClient;
import com.testsandroid.mitwittertest.retrofit.MiTwitterService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSignUp;
    TextView tvGoLogin;
    EditText eUserName, eTEmail, ePassword;
    MiTwitterService service;
    MiTwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();
        retroFitInit();
        findViews();
        events();


    }

    private void retroFitInit() {
        client = MiTwitterClient.getInstance();
        service = MiTwitterClient.getInstance().getMiTwitterService();
    }

    private void events() {
        btnSignUp.setOnClickListener(this);
        tvGoLogin.setOnClickListener(this);
    }

    private void findViews() {
        btnSignUp = findViewById(R.id.button_SignUp);
        tvGoLogin = findViewById(R.id.textView_goSignLogin);
        eUserName = findViewById(R.id.editTextUser);
        eTEmail = findViewById(R.id.editTextTextEmailAddress);
        ePassword = findViewById(R.id.editTextTextPassword);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.button_SignUp:
                goToSignUp();
                break;
            case R.id.textView_goSignLogin:
                goToLogin();
                break;
        }
    }

    private void goToSignUp() {

        String txtETMail = eTEmail.getText().toString();
        String txtUser = eUserName.getText().toString();
        String txtEpass = ePassword.getText().toString();


        if (txtETMail.isEmpty()){
            eTEmail.setError("Debe de ingresar Correo");
        }else if(txtEpass.isEmpty()){
            ePassword.setError("Debe de ingresar contraseña");

        }else if(txtUser.isEmpty()){
            eUserName.setError("Debe de ingresar Username");
        }else {
            String code = "UDEMYANDROID";
            RequestSignUp requestSignUp = new RequestSignUp(txtUser, txtETMail, txtEpass, code);
            Call<ResponseAuth> call = service.signUp(requestSignUp);

            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    Log.i("RESPONSE","Response: " + response.errorBody());
                    if (response.isSuccessful()){
                        Intent i = new Intent(SignUpActivity.this, DashboardActivity.class);
                        Toast.makeText(SignUpActivity.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                        finish();
                    }else {
                        Toast.makeText(SignUpActivity.this, "Algo a ocurrido mal, revise sus datos", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this, "Problemas de conexión, Intente nuevamente", Toast.LENGTH_SHORT).show();


                }
            });
        }
    }

    private void goToLogin() {
        Intent i = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

}
