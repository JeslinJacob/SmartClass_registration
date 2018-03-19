package com.example.blackmask.smartclass.Retro;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Blackma$k on 12/03/2018.
 */

public class Regclass {

    @SerializedName("name")
    private String Name;

    @SerializedName("semail")
    private String Email;


    @SerializedName("phone")
    private String Phone;

    @SerializedName("addr")
    private String address;

    @SerializedName("pass")
    private String Password;

    @SerializedName("image")
    private String Image;

    @SerializedName("response")
    private String response;


    public String getResponse() {
        return response;
    }




}
