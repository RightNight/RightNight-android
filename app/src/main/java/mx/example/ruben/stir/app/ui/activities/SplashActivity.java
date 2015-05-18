package mx.example.ruben.stir.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;

import mx.example.ruben.stir.R;

public class SplashActivity extends Activity {

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_splash);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    SharedPreferences sharedPref = getSharedPreferences("fb_user_prefs", MODE_PRIVATE);
                    boolean isLogin = sharedPref.getBoolean("is_login", false);

                    if (isLogin){
                        i = new Intent("mx.example.ruben.stir.MAINACTIVITY");
                    } else {
                        i = new Intent("mx.example.ruben.stir.FACEACTIVITY");
                    }
                    startActivity(i);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}