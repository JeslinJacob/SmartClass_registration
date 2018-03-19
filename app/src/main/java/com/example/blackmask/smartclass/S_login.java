package com.example.blackmask.smartclass;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class S_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_login);
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
        Intent i = new Intent(this, S_mainPage.class);
        startActivity(i);

    }

    public void myanimation(View v)
    {
        Animation a = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up);
        v.startAnimation(a);
        Vibrator vibr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibr.vibrate(100);
    }
}
