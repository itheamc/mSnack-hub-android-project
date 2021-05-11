package com.itheamc.msnackshub.handlers;

import android.os.Handler;

import androidx.viewpager2.widget.ViewPager2;

import com.itheamc.msnackshub.models.Slider;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SliderHandler {
    private static SliderHandler instance;
    private int currentPage = 0;


    // Constructor
    private SliderHandler() {
    }

    // Instance
    public static SliderHandler getInstance() {
        if (instance == null) {
            instance = new SliderHandler();
        }

        return instance;
    }


    // Function to start auto slider
    public void startAutoSlider(ViewPager2 viewPager, List<Slider> sliders) {
        Handler handler = new Handler();
        Runnable runnable = () -> {
            currentPage = currentPage % sliders.size();
            viewPager.setCurrentItem(currentPage, true);
            currentPage++;
        };

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 1000, 2500);
    }

}
