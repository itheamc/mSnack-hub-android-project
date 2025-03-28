package com.itheamc.msnackshub.callbacks;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;

public interface LoginStatusCallback {
    void onLoginSuccess(@NonNull FirebaseUser user);
    void onLoginFailure(@NonNull String errorMessage);
}
