package com.alhikmah.ciprb;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class SplashActivity extends FragmentActivity {

    private static final int SPLASH_TIME = 1600;
    private static final String AUDIO_FILE_NAME = "labbaiyk_allah_first";
    // public static String AUDIO_FILE_NAME = "welcome";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);

        int resID = getResources().getIdentifier(AUDIO_FILE_NAME, "raw",
                getPackageName());

		/*// initialize play and release sound
        MediaPlayer mediaPlayer = MediaPlayer.create(this, resID);
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// Release after played the file
				mp.release();

			}

		});
		mediaPlayer.start();*/

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent loginActivity = new Intent(SplashActivity.this,
                        HouseHoldInformationActivity.class);
                startActivity(loginActivity);
                SplashActivity.this.finish();
                // SHIHAB ANIMATION
                //overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        }, SPLASH_TIME);

    }

}
