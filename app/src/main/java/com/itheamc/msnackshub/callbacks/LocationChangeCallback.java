package com.itheamc.msnackshub.callbacks;

import android.location.Location;

public interface LocationChangeCallback {
    void onLocationChanged(Location location);
    void onLocationError(String message);
}
