package com.itheamc.msnackshub.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.itheamc.msnackshub.databinding.ActivitySplashBinding;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private ActivitySplashBinding splashBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(splashBinding.getRoot());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(TAG, "onCreate: " + String.format(Locale.ENGLISH, "Id: %s, Name: %s, Email: %s, Phone: %s, Image: %s",
                    user.getUid(),
                    user.getDisplayName(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    String.valueOf(user.getPhotoUrl())));
        }


        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, PreliminaryActivity.class));
            finish();
        }, 2000);
    }
}