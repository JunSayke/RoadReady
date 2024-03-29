package com.example.roadready.fragments.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.roadready.R;
import com.example.roadready.activity.GoogleMaps_Activity;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.response.ErrorGson;
import com.example.roadready.classes.model.gson.response.ResponseGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.databinding.FragmentSignUpBinding;
import com.google.gson.Gson;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp_Fragment extends Fragment {
    private static final String TAG = "SignUp_Fragment"; // Declare TAG for each class for debugging purposes using Log.d()
    private FragmentSignUpBinding binding; // Use View binding to avoid using too much findViewById
    private ActivityResultLauncher<Intent> mapResultLauncher;
    private final RoadReadyServer server = new RoadReadyServer(); // Declare this if HTTP operations are needed
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        navController = Navigation.findNavController(requireActivity(), R.id.openingFragmentContainer);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initMapResultLauncher();
        initActions();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void initActions() {
        binding.sgnupBtnSubmit.setOnClickListener(v -> {
            processRegistration();
        });

        binding.sgnupTextLogin.setOnClickListener(v -> {
            navController.navigate(R.id.action_signUp_Fragment_to_login_Fragment);
        });

        binding.sgnupBtnOpenMaps.setOnClickListener(v -> {
            startGoogleMaps();
        });
      
        binding.sgnupBtnGoogleLogin.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Login with google is not yet available", Toast.LENGTH_SHORT).show();
        });
    }

    private void startGoogleMaps() {
        Intent intent = new Intent(requireContext(), GoogleMaps_Activity.class);
        mapResultLauncher.launch(intent);
    }

    private void initMapResultLauncher() {
        mapResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    double latitude = data.getDoubleExtra("latitude", 0);
                    double longitude = data.getDoubleExtra("longitude", 0);

                    String LongLatText = longitude + ", " + latitude;
                    binding.sgnupInptCoordinates.setText(LongLatText);
                }
            }
        });
    }

    private void processRegistration() {
        showProgressBar();

        String email = String.valueOf(binding.sgnupInptEmail.getText());
        String password = String.valueOf(binding.sgnupInptPassword.getText());
        String firstName = String.valueOf(binding.sgnupInptFname.getText());
        String lastName = String.valueOf(binding.sgnupInptLname.getText());
        String phoneNumber = String.valueOf(binding.sgnupInptPhoneNumber.getText());
        String gender = String.valueOf(binding.getRoot().findViewById(binding.sgnupRgSexOptions.getCheckedRadioButtonId()).getContentDescription());
        String address = String.valueOf(binding.sgnupInptAddress.getText());

        server.getRetrofitService().register(email, password, firstName, lastName, phoneNumber, gender, address)
                .enqueue(registerCallback);
    }

    private final Callback< SuccessGson<Void> > registerCallback = new Callback< SuccessGson<Void> >() {
        @Override
        public void onResponse(@NonNull Call<SuccessGson<Void>> call, @NonNull Response<SuccessGson<Void>> response) {
            ResponseGson body = null;
            try {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    body = response.body();
                    Log.d(TAG, String.valueOf(body));
                } else {
                    assert response.errorBody() != null;
                    body = new Gson().fromJson(response.errorBody().string(), ErrorGson.class);
                    Log.e(TAG, String.valueOf(body));
                }
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            } finally {
                if (body != null) {
                    Toast.makeText(getContext(), String.valueOf(body.getMessage()), Toast.LENGTH_SHORT).show();
                }
                hideProgressBar();
            }
        }

        @Override
        public void onFailure(@NonNull Call<SuccessGson<Void>> call, @NonNull Throwable t) {
            Log.e(TAG, "Registration Failed!" + t.getMessage());
            Toast.makeText(getContext(), "Network Error!", Toast.LENGTH_SHORT).show();
            hideProgressBar();
        }
    };

    private void showProgressBar() {
        binding.sgnupBtnSubmit.setEnabled(false);
        requireActivity().findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.sgnupBtnSubmit.setEnabled(true);
        requireActivity().findViewById(R.id.progressBar).setVisibility(View.GONE);
    }
}
