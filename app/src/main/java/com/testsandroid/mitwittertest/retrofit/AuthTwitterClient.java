package com.testsandroid.mitwittertest.retrofit;

import com.testsandroid.mitwittertest.commons.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthTwitterClient {


    private static AuthTwitterClient instance = null;
    private AuthTwitterService miTwitterService;
    private Retrofit retrofit;

    public AuthTwitterClient() {
        //incluir cabecera en las peticiones con Auth

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new AuthInterceptor());
        OkHttpClient client = okHttpClientBuilder.build();

        retrofit = new Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        miTwitterService = retrofit.create(AuthTwitterService.class);
    }

    /**
     * patron singleton, se crea solo una ves, devuelve la instancia que ya existe
     */

    public static AuthTwitterClient getInstance(){
        if (instance == null){
            instance = new AuthTwitterClient();
        }
        return instance;
    }

    public AuthTwitterService getAuthTwitterService(){
        return miTwitterService;
    }



}
