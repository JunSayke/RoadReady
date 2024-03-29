package com.example.roadready.fragments.common;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.roadready.R;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.general.SessionManager;
import com.example.roadready.classes.model.gson.LoginDataGson;
import com.example.roadready.classes.model.gson.data.BuyerGson;
import com.example.roadready.classes.model.gson.response.ErrorGson;
import com.example.roadready.classes.model.gson.response.ResponseGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.databinding.FragmentHomepageContainerBinding;
import com.example.roadready.databinding.FragmentLoginBinding;
import com.google.gson.Gson;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Fragment extends Fragment {
    private static final String TAG = "Login_Fragment"; // Declare TAG for each class for debugging purposes using Log.d()
    private FragmentLoginBinding binding; // Use View binding to avoid using too much findViewById
    private final RoadReadyServer server = new RoadReadyServer(); // Declare this if HTTP operations are needed
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        navController = Navigation.findNavController(requireActivity(), R.id.openingFragmentContainer);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initActions();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initActions() {
        binding.lgnBtnLogin.setOnClickListener(v -> {
            processLogin();
        });

        binding.lgnTextSignup.setOnClickListener(v -> {
            navController.navigate(R.id.action_login_Fragment_to_signUpAs_Fragment2);
        });

        binding.lgnBtnGoogleLogin.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Login with google is not yet available", Toast.LENGTH_SHORT).show();
        });

        binding.lgnTextForgetPassword.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Forgot password is not yet available", Toast.LENGTH_SHORT).show();
        });
    }

    private void processLogin() {
        String email = String.valueOf(binding.lgnInptEmail.getText());
        String password = String.valueOf(binding.lgnInptPassword.getText());
        showProgressBar();

        server.getRetrofitService().login(email, password)
                .enqueue(loginCallback);
    }

    private final Callback<SuccessGson<LoginDataGson>> loginCallback = new Callback<SuccessGson<LoginDataGson>>() {
        @Override
        public void onResponse(@NonNull Call<SuccessGson<LoginDataGson>> call, @NonNull Response<SuccessGson<LoginDataGson>> response) {
            ResponseGson body = null;
            try {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    body = response.body();
                    Log.d(TAG, String.valueOf(body));
                    LoginDataGson data = (LoginDataGson) ((SuccessGson<?>) body).getData();
                    BuyerGson user = data.getUserGson();
                    new SessionManager(requireContext()).startSession(user);
                    navController.navigate(R.id.action_login_Fragment_to_homePageContainer_Fragment);
                } else {
                    assert response.errorBody() != null;
                    body = new Gson().fromJson(response.errorBody().string(), ErrorGson.class);
                    Log.e(TAG, String.valueOf(body));
                }
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            } finally {
                if (body != null) {
                    Toast.makeText(requireContext(), String.valueOf(body.getMessage()), Toast.LENGTH_SHORT).show();
                }
                hideProgressBar();
            }
        }

        @Override
        public void onFailure(@NonNull Call<SuccessGson<LoginDataGson>> call, @NonNull Throwable t) {
            Log.e(TAG, "Login Failed!" + t.getMessage());
            Toast.makeText(requireContext(), "Network Error!", Toast.LENGTH_SHORT).show();
            hideProgressBar();
        }
    };

    private void showProgressBar() {
        binding.lgnBtnLogin.setEnabled(false);
        requireActivity().findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.lgnBtnLogin.setEnabled(true);
        requireActivity().findViewById(R.id.progressBar).setVisibility(View.GONE);
    }
}