package com.itheamc.msnackshub.handlers;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.util.Executors;
import com.itheamc.msnackshub.callbacks.FirestoreCallbacks;
import com.itheamc.msnackshub.models.User;

import java.util.concurrent.ExecutorService;

public class FirestoreHandler {
    private FirebaseFirestore firestore;
    private static FirestoreHandler instance;
    private Handler handler;
    private final FirestoreCallbacks callbacks;

    // Constructor
    public FirestoreHandler(@NonNull FirestoreCallbacks firestoreCallbacks) {
        this.callbacks = firestoreCallbacks;
        firestore = FirebaseFirestore.getInstance();
        handler = HandlerCompat.createAsync(Looper.getMainLooper());
    }

    // Getter for instance
    public static FirestoreHandler getInstance(@NonNull FirestoreCallbacks firestoreCallbacks) {
        if (instance == null) {
            instance = new FirestoreHandler(firestoreCallbacks);
        }
        
        return instance;
    }


    // Function to store user in the Firestore
    public void storeUser(@NonNull User user) {
        firestore.collection("users")
                .document("uid")
                .set(user)
                .addOnSuccessListener(Executors.BACKGROUND_EXECUTOR, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        notifyUserStored();
                    }
                })
                .addOnFailureListener(Executors.BACKGROUND_EXECUTOR, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        notifyUserFailure(e);
                    }
                });
    }

    // Function to Notify that user is stored successfully
    private void notifyUserStored() {
        handler.post(callbacks::onUserStoreSuccess);
    }

    // Function to Notify that user is unable to store
    private void notifyUserFailure(@NonNull Exception e) {
        handler.post(() -> {
            callbacks.onUserStoreFailure(e);
        });
    }
}
