package com.itheamc.msnackshub.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.itheamc.msnackshub.adapters.HomeAdapter;
import com.itheamc.msnackshub.callbacks.LocationChangeCallback;
import com.itheamc.msnackshub.databinding.FragmentHomeBinding;
import com.itheamc.msnackshub.models.Category;
import com.itheamc.msnackshub.models.HomeItem;
import com.itheamc.msnackshub.models.Notice;
import com.itheamc.msnackshub.models.Product;
import com.itheamc.msnackshub.models.Slider;
import com.itheamc.msnackshub.handlers.LocationHandler;
import com.itheamc.msnackshub.utils.NotifyUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.itheamc.msnackshub.utils.Constraints.ITEM_TYPE_CATEGORY;
import static com.itheamc.msnackshub.utils.Constraints.ITEM_TYPE_NEW_PRODUCTS;
import static com.itheamc.msnackshub.utils.Constraints.ITEM_TYPE_POPULAR_PRODUCTS;
import static com.itheamc.msnackshub.utils.Constraints.ITEM_TYPE_RECOMMENDED_PRODUCTS;
import static com.itheamc.msnackshub.utils.Constraints.LOCATION_PERMISSION_REQUEST_CODE;
import static com.itheamc.msnackshub.utils.Constraints.VIEW_TYPE_ITEMS;
import static com.itheamc.msnackshub.utils.Constraints.VIEW_TYPE_NOTICE;
import static com.itheamc.msnackshub.utils.Constraints.VIEW_TYPE_SLIDERS;


public class HomeFragment extends Fragment implements LocationChangeCallback, View.OnClickListener {
    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding homeBinding;
    private HomeAdapter homeAdapter;
    private ExecutorService executorService;
    private FirebaseAuth mAuth;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return homeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeAdapter = new HomeAdapter();
        homeBinding.homeRecyclerView.setAdapter(homeAdapter);
        homeAdapter.submitList(generateHomeItems());
        executorService = Executors.newFixedThreadPool(4);

        mAuth = FirebaseAuth.getInstance();

        if (!isPermissionGranted()) {
            homeBinding.overlayLayout.setVisibility(View.VISIBLE);
            if (mAuth.getCurrentUser() != null) {
                homeBinding.setName(mAuth.getCurrentUser().getDisplayName().split(" ")[0]);
            }
        } else {
            LocationHandler.getInstance(requireContext(), this, executorService).getCurrentLocation();
        }

        homeBinding.allowPermissionButton.setOnClickListener(this);
        homeBinding.closeButton.setOnClickListener(this);

    }


    /**
     * Overriding method to handle on Click listener on views
     * -------------------------------------------------------------
     */
    @Override
    public void onClick(View v) {
        int _id = v.getId();
        if (_id == homeBinding.allowPermissionButton.getId()) {
            if (!isPermissionGranted()) {
                requestPermission();
            } else {
                NotifyUtils.showToast(getContext(), "Permission already granted");
            }

        } else if (_id == homeBinding.closeButton.getId()) {
            if (isPermissionGranted()) {
                homeBinding.overlayLayout.setVisibility(View.GONE);
            } else {
                requireActivity().finish();
            }

        } else {
            NotifyUtils.logDebug(TAG, "Unspecified button click");
        }
    }



    // Function to create dummy list
    private List<HomeItem> generateHomeItems() {
        List<HomeItem> homeItemList = new ArrayList<>();
        List<Slider> sliders = new ArrayList<>();
        List<Notice> notices = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        List<Product> newProducts = new ArrayList<>();
        List<Product> popularProducts = new ArrayList<>();
        List<Product> suggestedProducts = new ArrayList<>();



        // Adding items
        for (int i = 1; i < 11; i++) {
            sliders.add(new Slider(i, "Slider - " + i, "This is slider" + i, "no Url1"));
        }
        notices.add(new Notice(1, "Notice-1", "This is notice", "no Url1"));
        categories.add(new Category("thisisid1", "Category-1", 200));
        categories.add(new Category("thisisid2", "Category-2", 300));

        for (int i = 1; i < 11; i++) {
            newProducts.add(new Product(
                    "thisisproduct"+i,
                    "New Product-"+i,
                    "Product-" + i + " Description",
                    "sellerid"+i,
                    "image",
                    500.00,
                    1000,
                    500,
                    true,
                    new ArrayList<>(),
                    "2021-10-10"
            ));
        }

        for (int i = 1; i < 11; i++) {
            popularProducts.add(new Product(
                    "thisispopularproduct"+i,
                    "Popular Product-"+i,
                    "Product-" + i + " Description",
                    "sellerid"+i,
                    "image",
                    500.00,
                    1000,
                    500,
                    true,
                    new ArrayList<>(),
                    "2021-10-10"
            ));
        }

        for (int i = 1; i < 11; i++) {
            suggestedProducts.add(new Product(
                    "thisisrecommendedproduct"+i,
                    "Recommended Product-"+i,
                    "Product-" + i + " Description",
                    "sellerid"+i,
                    "image",
                    500.00,
                    1000,
                    500,
                    true,
                    new ArrayList<>(),
                    "2021-10-10"
            ));
        }




        homeItemList.add(new HomeItem(1, VIEW_TYPE_SLIDERS, 1, sliders, "Sliders"));
        homeItemList.add(new HomeItem(2, VIEW_TYPE_NOTICE, 1, notices, "Notice"));
        homeItemList.add(new HomeItem(3, VIEW_TYPE_ITEMS, ITEM_TYPE_CATEGORY, categories, "Category"));
        homeItemList.add(new HomeItem(3, VIEW_TYPE_ITEMS, ITEM_TYPE_NEW_PRODUCTS, newProducts, "New Products"));
        homeItemList.add(new HomeItem(3, VIEW_TYPE_ITEMS, ITEM_TYPE_POPULAR_PRODUCTS, popularProducts, "Popular Products"));
        homeItemList.add(new HomeItem(3, VIEW_TYPE_ITEMS, ITEM_TYPE_RECOMMENDED_PRODUCTS, suggestedProducts, "Recommended Products"));


        return homeItemList;
    }

    /**
     * These are the functions that will handle the permissions
     *
     */
    // Function to check whether permission is granted or not
    private boolean isPermissionGranted() {
        return ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    // FUnction to request permission
    private void requestPermission() {
        if (getActivity() == null) {
            return;
        }
        ActivityCompat.requestPermissions(
                getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d(TAG, "onRequestPermissionsResult: " + Arrays.toString(grantResults));
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (isAllGranted(grantResults)) {
                // All permission is granted
                Log.d(TAG, "onRequestPermissionsResult: All Permission granted");
                LocationHandler.getInstance(getContext(), this, executorService).getCurrentLocation();
            } else {
                Log.d(TAG, "onRequestPermissionsResult: Denied");
            }
        }
    }

    // Checking whether all permission is granted or not after requesting
    private boolean isAllGranted(int[] grantResults) {
        boolean isGranted = false;
        for (int i: grantResults) {
            if (i == PackageManager.PERMISSION_DENIED) {
                isGranted = false;
                break;
            } else {
                isGranted = true;
            }
        }

        return isGranted;
    }


    // method implemented from the LocationChangeCallback interface
    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged: " + "Latitude is - " + location.getLatitude() + "   Longitude is - " + location.getLongitude());
    }

    @Override
    public void onLocationError(String message) {
        Log.d(TAG, "onLocationError: " + message);
    }
}