package com.itheamc.msnackshub.handlers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.itheamc.msnackshub.callbacks.LocationChangeCallback;

import java.util.concurrent.ExecutorService;

public class LocationHandler {
    private static final String TAG = "LocationHandler";
    private static LocationHandler instance;
    private final Context context;
    private final FusedLocationProviderClient fusedLocationClient;
    private final ExecutorService executorService;
    private final LocationChangeCallback changeCallback;

    // Constructor
    private LocationHandler(Context context, LocationChangeCallback locationChangeCallback, ExecutorService executorService) {
        this.context = context;
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        this.changeCallback = locationChangeCallback;
        this.executorService = executorService;
    }

    // Instance
    public static LocationHandler getInstance(Context context, LocationChangeCallback locationChangeCallback, ExecutorService executorService) {
        if (instance == null) {
            instance = new LocationHandler(context, locationChangeCallback, executorService);
        }

        return instance;
    }

    // Getting Current Location
    // Method -1
    @SuppressLint("MissingPermission")
    public void getLastLocation() {
        executorService.execute(() -> {
            Log.d(TAG, "getCurrentLocation: This method is called");
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                notifyLocationChanged(location);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            notifyLocationError(e.getMessage());
                        }
                    });
        });

    }

    /**
     * Creating function to request network update incase
     * getCurrentLocation() function unable to get user location
     */
    // Method -2
    @SuppressLint("MissingPermission")
    public void requestUserLocation() {
        executorService.execute(() -> {
            LocationRequest locationRequest = LocationRequest.create()
                    .setWaitForAccurateLocation(true)
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(2000)
                    .setFastestInterval(1000)
                    .setNumUpdates(1);

            // Location Callback
            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    Location gotLocation = locationResult.getLastLocation();
                    notifyLocationChanged(gotLocation);
                }
            };

            // Finally requesting to get user location
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        });
    }


    // Function to get location
    // Method -3
    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {
        executorService.execute(() -> {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    notifyLocationChanged(location);
                }

                @Override
                public void onProviderDisabled(@NonNull String provider) {
                    notifyLocationError("Unable to get location");
                }

            };


            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 20F, locationListener, Looper.getMainLooper());
        });
    }


    // Function to notify Location Changed
    private void notifyLocationChanged(Location location) {
        new Handler().post(() -> {
            changeCallback.onLocationChanged(location);
        });
    }

    // Function to notify Location error
    private void notifyLocationError(String message) {
        new Handler().post(() -> {
            changeCallback.onLocationError(message);
        });
    }


    // Creating method to calculate distance between two places
    public double calcDistance(double latitude1, double longitude1, double latitude2,
                               double longitude2) {
        double distance = 0;
        double latDistance = 0;
        double longDistance = 0;
        final double earthRadius = 6371;    // 6371 is for KM, Use 3956 for Miles

        // Now calculating distance between two points using Haversine formula
        latDistance = Math.toRadians(latitude2) - Math.toRadians(latitude1);
        longDistance = Math.toRadians(longitude2) - Math.toRadians(longitude1);

        // let declare a double variable tempCalc
        double tempCalc = Math.pow(Math.sin(latDistance / 2), 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                * Math.pow(Math.sin(longDistance / 2), 2);

        // Now Calculating final distance
        distance = 2 * earthRadius * Math.asin(Math.sqrt(tempCalc));

        // Finally Returning distance
        return distance;
    }


}
