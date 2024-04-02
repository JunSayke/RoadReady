package com.example.roadready.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private MainFacade mainFacade;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainFacade = MainFacade.getInstance(this);
        mainFacade.setMainBinding(binding);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragmentContainer);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        mainFacade.setMainNavGraphController(navController);
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
}
