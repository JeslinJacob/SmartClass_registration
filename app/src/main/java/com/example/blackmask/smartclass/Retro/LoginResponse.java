package com.example.blackmask.smartclass.Retro;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Blackma$k on 18/03/2018.
 */

public class LoginResponse {

    @SerializedName("semail")
    private String Email;

    @SerializedName("pass")
    private String Pass;

    @SerializedName("response")
    private String response;

    @SerializedName("userid")
    private String userid;

    public String getResponse() {
        return response;
    }
    public String getUserid(){
        return userid;
    }
}
