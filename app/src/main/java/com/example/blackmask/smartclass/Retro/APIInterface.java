package com.example.blackmask.smartclass.Retro;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Blackma$k on 13/03/2018.
 */

public interface APIInterface {
    @FormUrlEncoded
    @POST("Sreg.php")
    Call<Regclass> uploadStudentDetails(@Field("name") String name,
                               @Field("semail") String email,
                               @Field("phone") String phone,
                               @Field("addr") String addr,
                               @Field("pass") String pass,
                               @Field("image") String image);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(@Field("semail") String email,
                              @Field("pass") String pass);
}
