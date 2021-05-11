package com.itheamc.msnackshub.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheamc.msnackshub.R;
import com.itheamc.msnackshub.callbacks.FirestoreCallbacks;
import com.itheamc.msnackshub.databinding.FragmentRegisterBinding;
import com.itheamc.msnackshub.models.User;
import com.itheamc.msnackshub.utils.NotifyUtils;


public class RegisterFragment extends Fragment implements FirestoreCallbacks {
    private static final String TAG = "RegisterFragment";
    private FragmentRegisterBinding registerBinding;
    private NavController navController;



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

    }


    /**
     * These are the methods implemented from the FirestoreCallbacks to handle user storing status
     */
    @Override
    public void onUserStoreSuccess() {
        NotifyUtils.logDebug(TAG, "onUserStoreSuccess: --> User stored successfully");
    }

    @Override
    public void onUserStoreFailure(@NonNull Exception e) {
        NotifyUtils.showToast(getContext(), e.getMessage());
    }

    @Override
    public void onUserInfoRetrieved(@NonNull User user) {

    }

    @Override
    public void onUserInfoRetrievedError(@NonNull Exception e) {
        NotifyUtils.showToast(getContext(), e.getMessage());
    }

}