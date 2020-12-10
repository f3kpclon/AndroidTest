package com.testsandroid.mitwittertest.retrofit;

import com.testsandroid.mitwittertest.commons.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MiTwitterClient {


    private static MiTwitterClient instance = null;
    private MiTwitterService miTwitterService;
    private Retrofit retrofit;

    public MiTwitterClient() {
        retrofit = new Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        miTwitterService = retrofit.create(MiTwitterService.class);
    }

    /**
     * patron singleton, se crea solo una ves, devuelve la instancia que ya existe
     */

    public static MiTwitterClient getInstance(){
        if (instance == null){
            instance = new MiTwitterClient();
        }
        return instance;
    }

    public MiTwitterService getMiTwitterService(){
        return miTwitterService;
    }



}
