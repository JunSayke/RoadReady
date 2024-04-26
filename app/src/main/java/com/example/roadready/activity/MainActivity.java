package com.example.roadready.activity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.model.gson.data.UserGson;
import com.example.roadready.databinding.ActivityCommonMainBinding;
import com.google.gson.Gson;

import java.util.Base64;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private ActivityCommonMainBinding binding;
    private MainFacade mainFacade;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommonMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainFacade = MainFacade.getInstance(this);
        mainFacade.setMainBinding(binding);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragmentContainer);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        mainFacade.setCommonMainNavController(navController);
        mainFacade.setCurrentNavController(navController);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavController currentNavController = mainFacade.getCurrentNavController();

                if (currentNavController != null && !currentNavController.popBackStack()) {
                    mainFacade.makeToast("Moving backward", Toast.LENGTH_SHORT);
                    finish();
                }
            }
        };

        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Uri uri = getIntent().getData();
        if (uri != null) {
            if (Objects.equals(uri.getHost(), "road-ready-black.vercel.app")) {
                String apiAccessToken = uri.getQueryParameter("api_access_token");

                if (apiAccessToken != null) {
                    DecodedJWT jwt = JWT.decode(apiAccessToken);

                    String payload = jwt.getClaims().toString();
                    UserGson userGson = new Gson().fromJson(payload, UserGson.class);
                    mainFacade.startLoginSession(userGson);
                    mainFacade.getUserGsonViewModel().setUserGsonLiveData(userGson);
                }
            }
        }
    }
}
