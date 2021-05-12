package com.itheamc.msnackshub.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.itheamc.msnackshub.R;
import com.itheamc.msnackshub.callbacks.FirestoreCallbacks;
import com.itheamc.msnackshub.callbacks.LoginStatusCallback;
import com.itheamc.msnackshub.databinding.FragmentLoginBinding;
import com.itheamc.msnackshub.databinding.VerificationBottomSheetViewBinding;
import com.itheamc.msnackshub.handlers.FirestoreHandler;
import com.itheamc.msnackshub.handlers.LoginHandler;
import com.itheamc.msnackshub.handlers.StorageHandler;
import com.itheamc.msnackshub.models.User;
import com.itheamc.msnackshub.utils.Amcryption;
import com.itheamc.msnackshub.utils.NotifyUtils;
import com.itheamc.msnackshub.utils.InputUtils;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static com.itheamc.msnackshub.utils.Constraints.FACEBOOK_SIGN_IN_REQUEST_CODE;
import static com.itheamc.msnackshub.utils.Constraints.GOOGLE_SIGN_IN_REQUEST_CODE;
import static com.itheamc.msnackshub.utils.Constraints.PHONE_SIGN_IN_REQUEST_CODE;


public class LoginFragment extends Fragment implements View.OnClickListener, LoginStatusCallback, FirestoreCallbacks {
    private static final String TAG = "LoginFragment";
    private FragmentLoginBinding loginBinding;
    private NavController navController;
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String verificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private LoginHandler loginHandler;


    /*
    For Bottom Sheet -- Code Verification Layout
     */
    private LinearLayout linearLayout;
    private BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior;
    private VerificationBottomSheetViewBinding bottomSheetViewBinding;

    private int LOGIN_REQUEST_CODE = 0;
    /*
    Resend timer
     */
    private String _phone_number = "";
    private int time_left = 60;
    private int number_of_resends = 1;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        return loginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

        // Initialize navController
        navController = Navigation.findNavController(view);

        /*
        Initializing LoginHandler Object
         */
        loginHandler = LoginHandler.getInstance(this, mAuth, this);

        // Setting onClickListener on these views
        loginBinding.facebookLoginButton.setOnClickListener(this);
        loginBinding.skipButton.setOnClickListener(this);
        loginBinding.googleLoginButton.setOnClickListener(this);
        loginBinding.signInButton.setOnClickListener(this);

        /*
        Initializing Bottom Sheet and its views
         */
        ConstraintLayout bottomSheetLayout = (ConstraintLayout) loginBinding.bottomSheetCoordinatorLayout.findViewById(R.id.verificationBottomSheetLayout);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheetViewBinding = VerificationBottomSheetViewBinding.bind(bottomSheetLayout);
        bottomSheetViewBinding.verifyOtpButton.setOnClickListener(this);
        bottomSheetViewBinding.resendOtpBtn.setOnClickListener(this);

        /*
          PhoneAuthProvider onVerificationStateChangedCallbacks to handle the
          phone authentication of the user
         */
        mCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        /* This callback will be invoked in two situations:
                            1 - Instant verification. In some cases the phone number can be instantly
                                verified without needing to send or enter a verification code.
                            2 - Auto-retrieval. On some devices Google Play services can automatically
                                detect the incoming verification SMS and perform verification without
                                user action.
                         */
                        NotifyUtils.logDebug(TAG, "phoneLoginHandler() -> onVerificationCompleted:" + phoneAuthCredential.getSmsCode());
                        loginHandler.signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        // This callback is invoked in an invalid request for verification is made,
                        // for instance if the the phone number format is not valid.
                        NotifyUtils.logError(TAG, "phoneLoginHandler() -> onVerificationFailed", e);

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            NotifyUtils.showToast(getContext(), "Invalid Request");
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // The SMS quota for the project has been exceeded
                            NotifyUtils.showToast(getContext(), "Quota exceeded for phone authentication. You can login with google or facebook");
                        }
                        handleLoginFragmentViewsState();
                        // Showing the bottom sheet to enter the verification code
                        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                            handleBottomSheetViewsState();
                        }

                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                        // The SMS verification code has been sent to the provided phone number, we
                        // now need to ask the user to enter the code and then construct a credential
                        // by combining the code with a verification ID.
                        handleLoginFragmentViewsState();
                        NotifyUtils.logDebug(TAG, "phoneLoginHandler() -> onCodeSent:" + verificationId);

                        // Save verification ID and resending token so we can use them later
                        verificationId = s;
                        mResendToken = forceResendingToken;

                        // Showing the bottom sheet to enter the verification code
                        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            InputUtils.requestFocusOnEditText(bottomSheetViewBinding);
                        }
                        handleResendOTPTimer();
                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                        NotifyUtils.logDebug(TAG, s);
                        handleLoginFragmentViewsState();
                    }
                };
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (loginHandler.isUserLoggedIn()) {
            startActivity(new Intent(this.requireActivity(), MainActivity.class));
            this.requireActivity().finish();
        }
    }

    /**
     * -------------------------------------------------------------------
     * Handling click event on views
     * -------------------------------------------------------------------
     */

    @Override
    public void onClick(View v) {
        int _id = v.getId();
        if (_id == loginBinding.skipButton.getId()) {
            startActivity(new Intent(requireActivity(), MainActivity.class));
            requireActivity().finish();

        } else if (_id == loginBinding.facebookLoginButton.getId()) {
            LOGIN_REQUEST_CODE = FACEBOOK_SIGN_IN_REQUEST_CODE;
            handleLoginFragmentViewsState();
            loginHandler.fbLoginHandler(callbackManager);

        } else if (_id == loginBinding.googleLoginButton.getId()) {
            LOGIN_REQUEST_CODE = GOOGLE_SIGN_IN_REQUEST_CODE;
            handleLoginFragmentViewsState();
            loginHandler.googleLoginHandler();

        } else if (_id == loginBinding.signInButton.getId()) {
            String phone = loginBinding.editTextPhone.getText().toString().trim();
            if (InputUtils.isNumberValidated(phone)) {
                LOGIN_REQUEST_CODE = PHONE_SIGN_IN_REQUEST_CODE;
                _phone_number = "+977" + phone;
                handleLoginFragmentViewsState();
                loginHandler.sendVerificationCode(mCallbacks, "+977" + phone);
            } else {
                NotifyUtils.showToast(getContext(), "Please enter a valid number");
            }

        } else if (_id == bottomSheetViewBinding.verifyOtpButton.getId()) {
            String c = InputUtils.formatVerificationCode(bottomSheetViewBinding);
            if (c != null) {
                handleBottomSheetViewsState();
                loginHandler.verifyEnteredOtp(c, verificationId);
            } else {
                NotifyUtils.showToast(getContext(), "Please enter code");
            }
        } else if (_id == bottomSheetViewBinding.resendOtpBtn.getId()) {
            bottomSheetViewBinding.resendOtpBtn.setEnabled(false);
            number_of_resends = number_of_resends + 1;
            loginHandler.resendVerificationCode(mCallbacks, _phone_number, mResendToken);
        } else {
            Log.d(TAG, "onClick: " + "Unhandled view click");
        }
    }


    /**
     * -----------------------------------------------------------------------
     * This is the overrided method to listen the activity result
     * -----------------------------------------------------------------------
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: " + requestCode);
        Log.d(TAG, "onActivityResult: " + LOGIN_REQUEST_CODE);

        // For Facebook Login
        if (LOGIN_REQUEST_CODE == FACEBOOK_SIGN_IN_REQUEST_CODE) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } else if (LOGIN_REQUEST_CODE == GOOGLE_SIGN_IN_REQUEST_CODE) {
            if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE && data != null) {
                GoogleSignIn.getSignedInAccountFromIntent(data)
                        .addOnSuccessListener(signInAccount -> {
                            loginHandler.firebaseAuthWithGoogle(signInAccount.getIdToken());
                            NotifyUtils.logDebug(TAG, signInAccount.getIdToken());
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                NotifyUtils.logError(TAG, "onActivityResult", e);
                                NotifyUtils.showToast(getContext(), "You cancelled the login request");
                                handleLoginFragmentViewsState();
                            }
                        });
            } else {
                NotifyUtils.showToast(getContext(), getString(R.string.unable_to_login_message));
            }
        } else {
            NotifyUtils.showToast(getContext(), getString(R.string.unable_to_login_message));
        }

    }


    /**
     * ----------------------------------------------------------------------
     * Function to navigate to the Main Activity if everything if ok
     * ----------------------------------------------------------------------
     */
    private void startMainActivity() {
        requireActivity().startActivity(new Intent(requireActivity(), MainActivity.class));
        requireActivity().finish();
    }

    /**
     * ----------------------------------------------------------------------
     * Function to handle the Bottom Sheet views inability and disability
     * ----------------------------------------------------------------------
     */
    private void handleBottomSheetViewsState() {
        // Handling progress bar visibility
        if (bottomSheetViewBinding.overlayLayout.getVisibility() == View.VISIBLE) {
            bottomSheetViewBinding.overlayLayout.setVisibility(View.GONE);
        } else {
            bottomSheetViewBinding.overlayLayout.setVisibility(View.VISIBLE);
        }

        // enable or disable the verifyOtpButton
        bottomSheetViewBinding.verifyOtpButton.setEnabled(!bottomSheetViewBinding.verifyOtpButton.isEnabled());

        //Enable or Disable EditTexts
        bottomSheetViewBinding.verificationNumber1.setEnabled(!bottomSheetViewBinding.verificationNumber1.isEnabled());
        bottomSheetViewBinding.verificationNumber2.setEnabled(!bottomSheetViewBinding.verificationNumber2.isEnabled());
        bottomSheetViewBinding.verificationNumber3.setEnabled(!bottomSheetViewBinding.verificationNumber3.isEnabled());
        bottomSheetViewBinding.verificationNumber4.setEnabled(!bottomSheetViewBinding.verificationNumber4.isEnabled());
        bottomSheetViewBinding.verificationNumber5.setEnabled(!bottomSheetViewBinding.verificationNumber5.isEnabled());
        bottomSheetViewBinding.verificationNumber6.setEnabled(!bottomSheetViewBinding.verificationNumber6.isEnabled());

    }

    /**
     * ----------------------------------------------------------------------
     * Function to handle the Login Fragment views inability and disability
     * ----------------------------------------------------------------------
     */
    private void handleLoginFragmentViewsState() {
        // Handling progress bar visibility
        if (LOGIN_REQUEST_CODE != FACEBOOK_SIGN_IN_REQUEST_CODE) {
            if (loginBinding.overlayLayout.getVisibility() == View.VISIBLE) {
                loginBinding.overlayLayout.setVisibility(View.GONE);
            } else {
                loginBinding.overlayLayout.setVisibility(View.VISIBLE);
            }
        }

        //Enable or Disable buttons
        loginBinding.signInButton.setEnabled(!loginBinding.signInButton.isEnabled());
        loginBinding.facebookLoginButton.setEnabled(!loginBinding.facebookLoginButton.isEnabled());
        loginBinding.googleLoginButton.setEnabled(!loginBinding.googleLoginButton.isEnabled());
        loginBinding.editTextPhone.setEnabled(!loginBinding.editTextPhone.isEnabled());
        loginBinding.skipButton.setEnabled(!loginBinding.skipButton.isEnabled());
    }


    /**
     * ----------------------------------------------------------------------
     * Timer function to enable or disable the resend OTP button
     * ----------------------------------------------------------------------
     */
    private void handleResendOTPTimer() {
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                HandlerCompat.createAsync(Looper.getMainLooper()).post(() -> {
                    bottomSheetViewBinding.resendOtpBtn.setText(String.format(Locale.ENGLISH, "wait %d sec", time_left));
                    if (time_left == 0) {
                        time_left = 60 + (60 * number_of_resends) / 2;
                        bottomSheetViewBinding.resendOtpBtn.setEnabled(true);
                        bottomSheetViewBinding.resendOtpBtn.setText(getString(R.string.resend));
                        timer.cancel();
                    }
                    time_left = time_left - 1;
                });
            }
        };

        timer.schedule(timerTask, 0L, 1000L);
    }


    /**
     * ---------------------------------------------------------------------------
     * These are functions Overrided from the LoginStatusCallback
     *
     * @param user -- It the instance of the firebase user after successful login
     *             ---------------------------------------------------------------------------
     */

    @Override
    public void onLoginSuccess(@NonNull FirebaseUser user) {
//        StorageHandler.getInstance(requireActivity()).storeUuId(user.getUid());
        FirestoreHandler.getInstance(this).getUser(user.getUid());
    }

    @Override
    public void onLoginFailure(@NonNull String errorMessage) {
        handleLoginFragmentViewsState();
        NotifyUtils.logDebug(TAG, errorMessage);
        NotifyUtils.showToast(getContext(), errorMessage.split("--")[1]);
        if (LOGIN_REQUEST_CODE == PHONE_SIGN_IN_REQUEST_CODE) {
            handleBottomSheetViewsState();
        }
    }


    /**
     * -------------------------------------------------------------------
     * These are the methods implemented from the FirestoreCallbacks
     * -------------------------------------------------------------------
     */

    @Override
    public void onUserStoreSuccess() {
        startMainActivity();
    }

    @Override
    public void onUserStoreFailure(Exception e) {
        handleLoginFragmentViewsState();
        NotifyUtils.showToast(getContext(), e.getMessage());
        NotifyUtils.logDebug(TAG, "onUserStoreFailure: - " + e.getMessage());
    }

    @Override
    public void onUserInfoRetrieved(User user) {
        if (user != null) {
            NotifyUtils.logDebug(TAG, user.toString());
            startMainActivity();
        } else {
            navController.navigate(R.id.action_loginFragment_to_registerFragment);
        }

    }

    @Override
    public void onUserInfoRetrievedError(Exception e) {
        handleLoginFragmentViewsState();
        NotifyUtils.showToast(getContext(), e.getMessage());
        NotifyUtils.logDebug(TAG, "onUserInfoRetrievedError: - " + e.getMessage());
    }
}