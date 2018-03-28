package com.example.blackmask.smartclass;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blackmask.smartclass.Retro.APIClient;
import com.example.blackmask.smartclass.Retro.APIInterface;
import com.example.blackmask.smartclass.Retro.LoginResponse;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class S_login extends AppCompatActivity {

    EditText emailtxt,passtxt;
    private Welcome_SessionManager session;
    android.app.AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_login);
        emailtxt=(EditText)findViewById(R.id.input_user);
        passtxt=(EditText)findViewById(R.id.input_password);

        session = new Welcome_SessionManager(getApplicationContext(), "LOGIN PREF");
        dialog = new SpotsDialog(this,R.style.loggingIn);


        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(this, S_mainPage.class);
            startActivity(intent);
            finish();
        }


    }
    public void signp(View v)
    {
        myanimation(v);
        Intent i = new Intent(this, RegistrationPage.class);
        startActivity(i);
    }

    public void signin(View v)
    {
        myanimation(v);
        dialog.show();

        String emailstr,passStr;
        emailstr=emailtxt.getText().toString().trim();
        passStr=passtxt.getText().toString().trim();

        if(emailstr.matches("")||passStr.matches(""))
        {
            Toast.makeText(this, "enter both details", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
        else
        {
            APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
            Call<LoginResponse> call=apiInterface.login(emailstr,passStr);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse loginclass=response.body();
                    System.out.println("$$$$$$$$$$$$$$$$ RESPONSE : " + loginclass.getResponse());

                    if(loginclass.getResponse().matches("success"))
                    {
                        session.setLogin(true,loginclass.getUserid());

                        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                            @Override
                            public void run() {
                                // This method will be executed once the timer is over
                                // Start your app main activity
                                dialog.dismiss();
                                Intent i = new Intent(getApplicationContext(), S_mainPage.class);
                                startActivity(i);
                                finish();
                            }
                        }, 1000);



                    }
                    else {
                        dialog.dismiss();
                        Toast.makeText(S_login.this, "incorrent email or password", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "server page is down", Toast.LENGTH_SHORT).show();

                }
            });
        }



    }

    public void myanimation(View v)
    {
        Animation a = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up);
        v.startAnimation(a);
        Vibrator vibr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibr.vibrate(100);
    }
}
