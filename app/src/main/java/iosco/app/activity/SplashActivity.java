package iosco.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import iosco.app.R;

/**
 * Created by Victor Casas on 17/05/2015.
 */
public class SplashActivity extends AppCompatActivity {

    private long splashDelay = 2000;//3000

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(userRegistred()) {
                    Intent mainIntent = new Intent().setClass(SplashActivity.this, MainActivityNew.class);
                    startActivity(mainIntent);
                }else{
                    Intent mainIntent = new Intent().setClass(SplashActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                }
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, splashDelay);
    }

    private boolean userRegistred(){
        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(saved_values.getString("access_token","").equals(""))
            return false;
        else
            return true;
    }
}
