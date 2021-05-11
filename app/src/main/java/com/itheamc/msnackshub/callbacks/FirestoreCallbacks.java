package com.itheamc.msnackshub.callbacks;

import androidx.annotation.NonNull;

import com.itheamc.msnackshub.models.User;

public interface FirestoreCallbacks {
    void onUserStoreSuccess();
    void onUserStoreFailure(Exception e);
    void onUserInfoRetrieved(User user);
    void onUserInfoRetrievedError(Exception e);
}
