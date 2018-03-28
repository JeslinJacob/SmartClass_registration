package com.example.blackmask.smartclass.Retro;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Blackma$k on 12/03/2018.
 */


public class APIClient extends Application {
    public static final String BASE_URL = "http://192.168.1.5/SmartStudent/";

    private static Retrofit retrofit = null;



    @Override

    public void onCreate() {

        super.onCreate();



    }



    public static Retrofit getClient() {

        if (retrofit==null) {

            retrofit = new Retrofit.Builder()

                    .baseUrl(BASE_URL)

                    .addConverterFactory(GsonConverterFactory.create())

                    .build();

        }

        return retrofit;

    }
}
