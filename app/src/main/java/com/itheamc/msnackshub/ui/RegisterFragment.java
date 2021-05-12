package com.itheamc.msnackshub.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.itheamc.msnackshub.R;
import com.itheamc.msnackshub.callbacks.FirestoreCallbacks;
import com.itheamc.msnackshub.databinding.FragmentRegisterBinding;
import com.itheamc.msnackshub.handlers.FirestoreHandler;
import com.itheamc.msnackshub.handlers.StorageHandler;
import com.itheamc.msnackshub.models.User;
import com.itheamc.msnackshub.utils.Amcryption;
import com.itheamc.msnackshub.utils.InputUtils;
import com.itheamc.msnackshub.utils.NotifyUtils;

import java.util.regex.Pattern;

import static com.itheamc.msnackshub.utils.Constraints.FACEBOOK_SIGN_IN_REQUEST_CODE;


public class RegisterFragment extends Fragment implements FirestoreCallbacks, View.OnClickListener {
    private static final String TAG = "RegisterFragment";
    private FragmentRegisterBinding registerBinding;
    private NavController navController;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        registerBinding = FragmentRegisterBinding.inflate(inflater, container, false);
        return registerBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        mAuth = FirebaseAuth.getInstance();

        // Implementing onClickListener on the button
        registerBinding.continueButton.setOnClickListener(this);

        // Getting Current firebase user
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            registerBinding.editTextName.setText(firebaseUser.getDisplayName());
            //Checking Phone
            if (firebaseUser.getPhoneNumber() != null && !firebaseUser.getPhoneNumber().isEmpty()) {
                registerBinding.editTextPhone.setText(firebaseUser.getPhoneNumber());
                registerBinding.editTextPhone.setEnabled(false);
            }

            //Checking Email
            if (firebaseUser.getEmail() != null && !firebaseUser.getEmail().isEmpty()) {
                registerBinding.editTextEmail.setText(firebaseUser.getEmail());
                registerBinding.editTextEmail.setEnabled(false);
            }
        }

    }

    /**
     * Method Overrided from the onClick Listener
     */
    @Override
    public void onClick(View v) {
        int _id = v.getId();
        if (_id == registerBinding.continueButton.getId()) {
            storeUserInfo();
        }
    }


    /**
     * Function to store the user info to the Firestore
     */
    private void storeUserInfo() {
        String name = registerBinding.editTextName.getText().toString().trim();
        String phone = registerBinding.editTextPhone.getText().toString().trim();
        String email = registerBinding.editTextEmail.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email)) {
            NotifyUtils.showToast(getContext(), "Please enter all the details");
            return;
        }

        if (!InputUtils.isNumberValidated(phone.substring(4))) {
            registerBinding.editTextPhone.setError("Please enter valid number");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerBinding.editTextEmail.setError("Please enter valid email");
            return;
        }

        /*
         * Will be stored on the firebase Firestore */
        if (firebaseUser != null) {
            User newUser = new User(
                    firebaseUser.getUid(),
                    Amcryption.getEncoder().encode(name),
                    Amcryption.getEncoder().encode(phone),
                    Amcryption.getEncoder().encode(email),
                    String.valueOf(firebaseUser.getPhotoUrl()),
                    0.00,
                    0.00
            );
            handleRegisterFragmentViewsState();
            NotifyUtils.logDebug(TAG, "onUserInfoRetrieved: " + newUser.toString());
            StorageHandler.getInstance(requireActivity()).storeUser(newUser);
            FirestoreHandler.getInstance(this).storeUser(newUser);
        }
    }


    /**
     * ----------------------------------------------------------------------
     * Function to handle the Login Fragment views inability and disability
     * ----------------------------------------------------------------------
     */
    private void handleRegisterFragmentViewsState() {
        // Handling progress bar visibility
        if (registerBinding.overlayLayout.getVisibility() == View.VISIBLE) {
            registerBinding.overlayLayout.setVisibility(View.GONE);
        } else {
            registerBinding.overlayLayout.setVisibility(View.VISIBLE);
        }

        //Enable or Disable buttons
        registerBinding.continueButton.setEnabled(!registerBinding.continueButton.isEnabled());

        // Enable or disable EditTexts
        registerBinding.editTextName.setEnabled(!registerBinding.editTextName.isEnabled());
        registerBinding.editTextPhone.setEnabled(!registerBinding.editTextPhone.isEnabled());
        registerBinding.editTextEmail.setEnabled(!registerBinding.editTextEmail.isEnabled());
    }


    /**
     * These are the methods implemented from the FirestoreCallbacks to handle user storing status
     */
    @Override
    public void onUserStoreSuccess() {
        NotifyUtils.logDebug(TAG, "onUserStoreSuccess: --> User stored successfully");
        handleRegisterFragmentViewsState();
        startMainActivity();
    }

    @Override
    public void onUserStoreFailure(@NonNull Exception e) {
        handleRegisterFragmentViewsState();
        NotifyUtils.showToast(getContext(), e.getMessage());
    }

    @Override
    public void onUserInfoRetrieved(@NonNull User user) {
        handleRegisterFragmentViewsState();

    }

    @Override
    public void onUserInfoRetrievedError(@NonNull Exception e) {
        handleRegisterFragmentViewsState();
        NotifyUtils.showToast(getContext(), e.getMessage());
    }

    /**
     * Function to start main activity if everything is fine
     */
    private void startMainActivity() {
        requireActivity().startActivity(new Intent(requireActivity(), MainActivity.class));
        requireActivity().finish();
    }


}