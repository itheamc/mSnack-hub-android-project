package com.itheamc.msnackshub.handlers;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.itheamc.msnackshub.R;
import com.itheamc.msnackshub.callbacks.LoginStatusCallback;
import com.itheamc.msnackshub.ui.LoginFragment;
import com.itheamc.msnackshub.ui.MainActivity;
import com.itheamc.msnackshub.utils.NotifyUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.itheamc.msnackshub.utils.Constraints.GOOGLE_SIGN_IN_REQUEST_CODE;

public class LoginHandler {
    private static final String TAG = "LoginHandler";
    private static LoginHandler instance;
    private final FirebaseAuth mAuth;
    private final LoginManager loginManager;
    private final Fragment fragment;
    private final LoginStatusCallback statusCallback;
    private final ExecutorService executorService;
    private final Handler handler;


    // Constructor
    protected LoginHandler(@NonNull Fragment fragment, FirebaseAuth mAuth, LoginStatusCallback loginStatusCallback) {
        this.fragment = fragment;
        this.mAuth = mAuth;
        this.statusCallback = loginStatusCallback;
        this.loginManager = LoginManager.getInstance();
        this.executorService = Executors.newFixedThreadPool(4);
        this.handler = HandlerCompat.createAsync(Looper.getMainLooper());
    }

    // Function to create and return the instance of the LoginHandler Object
    public static LoginHandler getInstance(@NonNull Fragment fragment, FirebaseAuth mAuth, LoginStatusCallback loginStatusCallback) {
        if (instance == null) {
            instance = new LoginHandler(fragment, mAuth, loginStatusCallback);
        }

        return instance;
    }

    /**
     * ____________________________CHECK USER LOGIN STATUS_______________________
     * This function will checked whether user already logged in or not
     */
    public boolean isUserLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }


    /**
     * _____________________FACEBOOK LOGIN HANDLING FUNCTIONS________________________
     * These are functions that will handle the facebook login
     */

    // Function to handle facebook login
    public void fbLoginHandler(@NonNull CallbackManager callbackManager) {
        loginManager.logInWithReadPermissions(fragment, Arrays.asList("email", "public_profile"));
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                NotifyUtils.logDebug(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                notifyLoginFailure("fbLoginHandler - onError --You cancelled the login");
            }

            @Override
            public void onError(FacebookException error) {
                notifyLoginFailure("fbLoginHandler - onError --" + error.getMessage());
            }
        });
    }

    // Handle Facebook Access Token
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(executorService, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            NotifyUtils.logDebug(TAG, "handleFacebookAccessToken() -> onComplete - signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            notifyLoginSuccess(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            notifyLoginFailure("handleFacebookAccessToken() -> signInWithCredential:failure --" + task.getException().getMessage());

                        }

                    }
                });
    }


    /**
     * ___________________GOOGLE SIGN IN HANDLER_________________________________
     * These are the functions that will handle the user login
     * using the google
     */

    public void googleLoginHandler() {
        if (fragment.getActivity() == null || fragment.getContext() == null) {
            return;
        }
        GoogleSignInOptions signInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(fragment.getActivity().getResources().getString(R.string.web_client_id))
                .build();

        // Build a GoogleSignInClient with the options specified by gso.

        GoogleSignInClient signInClient = GoogleSignIn.getClient(fragment.getContext(), signInOptions);
        Intent signInIntent = signInClient.getSignInIntent();
        fragment.startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE);
    }

    public void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(executorService, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            NotifyUtils.logDebug(TAG, "firebaseAuthWithGoogle() -> signInWithCredential:onComplete");
                            FirebaseUser user = task.getResult().getUser();
                            notifyLoginSuccess(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            notifyLoginFailure("firebaseAuthWithGoogle() -> signInWithCredential:failure --" + task.getException().getMessage());

                        }
                    }
                });
    }


    /**
     * _________________________PHONE NUMBER LOGIN FUNCTIONS_____________________________
     * These are the functions that will handle the user registration through
     * phone authentication
     */

    public void sendVerificationCode(@NonNull PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks, String phoneNumber) {
        if (fragment.getActivity() == null) {
            return;
        }
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)               // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(fragment.getActivity())      // Activity (for callback binding)
                        .setCallbacks(mCallbacks)                 // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    // Function to resend phone code
    public void resendVerificationCode(@NonNull PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks, String phoneNumber, PhoneAuthProvider.ForceResendingToken resendToken) {
        if (fragment.getActivity() == null) {
            return;
        }
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(fragment.getActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .setForceResendingToken(resendToken)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }


    // Function to verify phone if user entered code with him/herself
    public void verifyEnteredOtp(String enteredCode, String verificationId) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, enteredCode);
        signInWithPhoneAuthCredential(credential);
    }

    // Function to verify credential with firebase auth
    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(executorService, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            NotifyUtils.logDebug(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            notifyLoginSuccess(user);
                        } else {
                            notifyLoginFailure("signInWithCredential:failure --" + task.getException().getMessage());
                        }
                    }
                });
    }


    /**
     * _____________________________________SIGN OUT HANDLER_______________________
     * This function will handle the sign out
     */
    // Function to handle sign out
    private void signOutNow() {
        mAuth.signOut();
    }


    /**
     * Function to notify login success
     */
    private void notifyLoginSuccess(@NonNull FirebaseUser user) {
        handler.post(() -> {
            statusCallback.onLoginSuccess(user);
        });
    }

    /**
     * Function to notify login failure
     */
    private void notifyLoginFailure(@NonNull String error_message) {
        handler.post(() -> {
            statusCallback.onLoginFailure(error_message);
        });
    }


}
