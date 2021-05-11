package com.itheamc.msnackshub.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.itheamc.msnackshub.databinding.ActivityPreliminaryBinding;

public class PreliminaryActivity extends AppCompatActivity {
    private static final String TAG = "PreliminaryActivity";
    private ActivityPreliminaryBinding preliminaryBinding;
    private NavController navController;
    private NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preliminaryBinding = ActivityPreliminaryBinding.inflate(getLayoutInflater());
        setContentView(preliminaryBinding.getRoot());

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(preliminaryBinding.fragmentContainerView.getId());

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
//            NavigationUI.setupActionBarWithNavController(this, navController);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }
}