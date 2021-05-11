package com.itheamc.msnackshub.handlers;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

    /**
     --------------------------------------------------------------------------------------
     */

    // Function to get user info from the cloud firestore
    public void getUser(String userId) {
        firestore.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(Executors.BACKGROUND_EXECUTOR, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot != null) {
                            User user = new User();
                            user = documentSnapshot.toObject(User.class);
                            notifyOnSuccess(user);
                        }

                    }
                })
                .addOnFailureListener(Executors.BACKGROUND_EXECUTOR, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        notifyOnFailure(e);
                    }
                });
    }

    // Function to notify whether getUser() is success or failure
    private void notifyOnSuccess(User user) {
        handler.post(() -> {
            callbacks.onUserInfoRetrieved(user);
        });
    }

    private void notifyOnFailure(Exception e) {
        handler.post(() -> {
            callbacks.onUserInfoRetrievedError(e);
        });
    }

    /**
    --------------------------------------------------------------------------------------
     */

    // Function to store user in the Firestore
    public void storeUser(User user) {
        firestore.collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
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
