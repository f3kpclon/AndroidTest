package com.testsandroid.mitwittertest.retrofit;



import com.testsandroid.mitwittertest.model.request.RequestLogin;
import com.testsandroid.mitwittertest.model.request.RequestSignUp;
import com.testsandroid.mitwittertest.model.response.ResponseAuth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MiTwitterService {

    @Headers({"Content-Type:application/json"})
    @POST("auth/login")
    Call<ResponseAuth>Login(@Body RequestLogin requestLogin);

    @POST("auth/signup")
    Call<ResponseAuth>signUp(@Body RequestSignUp requestSignUp);

    }
