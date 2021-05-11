package com.itheamc.msnackshub.callbacks;

import androidx.annotation.NonNull;

public interface FirestoreCallbacks {
    void onUserStoreSuccess();
    void onUserStoreFailure(@NonNull Exception e);
}
