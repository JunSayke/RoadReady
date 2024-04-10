package com.example.roadready.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;

import com.example.roadready.databinding.ActivityCommonGoogleMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class GoogleMaps_Activity extends AppCompatActivity implements OnMapReadyCallback {
    private final String TAG = "GoogleMaps_Activity";
    private ActivityCommonGoogleMapsBinding binding;
    private GoogleMap myMap;
    private final int FINE_PERMISSION_CODE = 1;
    private LatLng currentLatLng;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommonGoogleMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initLocationProviderClient();
        getLastLocation();
        initOnClicks();
    }

    private void initLocationProviderClient() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void initOnClicks() {
        binding.gmSearchMap.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                handleSearchQuery();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        binding.gmBtnBack.setOnClickListener(v -> {
            finish();
        });

        binding.gmBtnSetLocation.setOnClickListener(v -> {
            submitLatLng();
        });
    }

    private void handleSearchQuery() {
        String location = binding.gmSearchMap.getQuery().toString();
        List<Address> addressList = null;

        if (!location.isEmpty()) {
            Geocoder geocoder = new Geocoder(GoogleMaps_Activity.this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                callToast("Location not found!");
                throw new RuntimeException(e);
            }
        }

        assert addressList != null;
        Address address = addressList.get(0);
        currentLatLng = new LatLng(address.getLatitude(), address.getLongitude());

        setCameraToCurrentLatLng(location);
    }

    private void submitLatLng() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("latitude", currentLatLng.latitude);
        returnIntent.putExtra("longitude", currentLatLng.longitude);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if(location != null) {
                currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                openGoogleMaps(GoogleMaps_Activity.this);
            }
        });
    }

    private void openGoogleMaps(GoogleMaps_Activity googleMapsActivity) {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(binding.viewGoogleMaps.getId());

        assert mapFragment != null;
        mapFragment.getMapAsync(googleMapsActivity);
    }

    private void setCameraToCurrentLatLng(String title) {
        myMap.addMarker(new MarkerOptions().position(currentLatLng).title(title));
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 10));
    }

    private void callToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
        setCameraToCurrentLatLng("Current Location");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}