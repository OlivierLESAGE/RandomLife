package fr.lesage.olivier.info503;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.Delayed;

/**
 * Activité qui affiche le logo de l'application au démarrage.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, this.getResources().getInteger(R.integer.splash_time));




    }
}
