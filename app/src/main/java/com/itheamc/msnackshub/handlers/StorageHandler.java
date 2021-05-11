package com.itheamc.msnackshub.handlers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.itheamc.msnackshub.models.User;

import static com.itheamc.msnackshub.utils.Constraints.SHARED_PREFERENCE_NAME;
import static com.itheamc.msnackshub.utils.Constraints.USER_EMAIL;
import static com.itheamc.msnackshub.utils.Constraints.USER_ID;
import static com.itheamc.msnackshub.utils.Constraints.USER_IMAGE;
import static com.itheamc.msnackshub.utils.Constraints.USER_LAT;
import static com.itheamc.msnackshub.utils.Constraints.USER_LONG;
import static com.itheamc.msnackshub.utils.Constraints.USER_NAME;
import static com.itheamc.msnackshub.utils.Constraints.USER_PHONE;

public class StorageHandler {
    private static StorageHandler instance;
    private final Activity activity;
    private final SharedPreferences sharedPreferences;

    // Constructor
    private StorageHandler(@NonNull Activity activity) {
        this.activity = activity;
        this.sharedPreferences = activity.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    // Getter for instance of this class
    public static StorageHandler getInstance(@NonNull Activity activity) {
        if (instance == null) {
            instance = new StorageHandler(activity);
        }

        return instance;
    }

    // Function to store user info using shared preference
    public void storeUser(@NonNull User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, user.get_id());
        editor.putString(USER_NAME, user.get_name());
        editor.putString(USER_PHONE, user.get_phone());
        editor.putString(USER_EMAIL, user.get_email());
        editor.putString(USER_IMAGE, user.get_image());
        editor.putString(USER_LAT, String.valueOf(user.get_lat()));
        editor.putString(USER_LONG, String.valueOf(user.get_long()));
        editor.apply();
    }

    // FUnction to get user
    public User getUser() {
        String _id = sharedPreferences.getString(USER_ID, null);
        String _name = sharedPreferences.getString(USER_NAME, null);
        String _phone = sharedPreferences.getString(USER_PHONE, null);
        String _email = sharedPreferences.getString(USER_EMAIL, null);
        String _image = sharedPreferences.getString(USER_IMAGE, null);
        String _lat = sharedPreferences.getString(USER_LAT, "0.00");
        String _long = sharedPreferences.getString(USER_LONG, "0.00");

        return new User(
                _id,
                _name,
                _phone,
                _email,
                _image,
                Double.valueOf(_lat),
                Double.valueOf(_long)
        );
    }

}
