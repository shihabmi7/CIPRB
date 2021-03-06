package com.ciprb.injury;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 1600;
    private static final String AUDIO_FILE_NAME = "labbaiyk_allah_first";
    // public static String AUDIO_FILE_NAME = "welcome";

    SplashActivity splashActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent loginActivity = new Intent(splashActivity,
                        HomeActivity.class);
                startActivity(loginActivity);
                SplashActivity.this.finish();
                // SHIHAB ANIMATION
                //overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        }, SPLASH_TIME);
    }
}
