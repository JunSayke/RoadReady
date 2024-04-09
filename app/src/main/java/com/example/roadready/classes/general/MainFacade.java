package com.example.roadready.classes.general;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.roadready.activity.MainActivity;
import com.example.roadready.classes.model.gson.GsonData;
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.UserDataGson;
import com.example.roadready.classes.model.gson.data.UserGson;
import com.example.roadready.classes.model.livedata.UserGsonViewModel;
import com.example.roadready.classes.model.livedata.UserGsonViewModelFactory;
import com.example.roadready.databinding.ActivityMainBinding;

import java.io.File;

public class MainFacade {
    private static FragmentActivity mainActivity;
    private ActivityMainBinding mainBinding;
    private NavController currentNavController, homepageNavController, mainNavGraphController, homeNavGraphController, applicationNavGraphController, myVehicleNavGraphController, notificationNavGraphController, profileNavGraphController;
    private UserGsonViewModelFactory userGsonViewModelFactory;
    private SessionManager sessionManager;
    private final RoadReadyServer server = new RoadReadyServer();
    private static final MainFacade mainFacade = new MainFacade(null);

    public static synchronized MainFacade getInstance(MainActivity mainActivity) {
        mainFacade.setMainActivity(mainActivity);
        return mainFacade;
    }

    public static synchronized MainFacade getInstance() throws Exception {
        if (mainActivity == null) {
            throw new Exception("Main Activity is not yet instantiated!");
        }
        return mainFacade;
    }

    private MainFacade(@Nullable MainActivity mainActivity) {
        if (mainActivity != null) {
            setMainActivity(mainActivity);
        }
    }

    public FragmentActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(FragmentActivity mainActivity) {
        MainFacade.mainActivity = mainActivity;
        sessionManager = new SessionManager(mainActivity.getApplicationContext());
        userGsonViewModelFactory = new UserGsonViewModelFactory(mainActivity.getApplicationContext());

        if (sessionManager.getCookies() != null) {
            server.addCookies(server.parseCookies(sessionManager.getCookies()));
        }
    }

    public ActivityMainBinding getMainBinding() {
        return mainBinding;
    }

    public void setMainBinding(ActivityMainBinding mainBinding) {
        this.mainBinding = mainBinding;
    }

    // START_OF[Nav Controllers]

    public NavController getCurrentNavController() {
        return currentNavController;
    }

    public void setCurrentNavController(NavController currentNavController) {
        this.currentNavController = currentNavController;
    }

    public NavController getMainNavGraphController() {
        return mainNavGraphController;
    }

    public void setMainNavGraphController(NavController mainNavGraphController) {
        this.mainNavGraphController = mainNavGraphController;
    }

    public NavController getHomepageNavController() {
        return homepageNavController;
    }

    public void setHomepageNavController(NavController homepageNavController) {
        this.homepageNavController = homepageNavController;
    }

    public NavController getHomeNavGraphController() {
        return homeNavGraphController;
    }

    public void setHomeNavGraphController(NavController homeNavGraphController) {
        this.homeNavGraphController = homeNavGraphController;
    }

    public NavController getApplicationNavGraphController() {
        return applicationNavGraphController;
    }

    public void setApplicationNavGraphController(NavController applicationNavGraphController) {
        this.applicationNavGraphController = applicationNavGraphController;
    }

    public NavController getMyVehicleNavGraphController() {
        return myVehicleNavGraphController;
    }

    public void setMyVehicleNavGraphController(NavController myVehicleNavGraphController) {
        this.myVehicleNavGraphController = myVehicleNavGraphController;
    }

    public NavController getNotificationNavGraphController() {
        return notificationNavGraphController;
    }

    public void setNotificationNavGraphController(NavController notificationNavGraphController) {
        this.notificationNavGraphController = notificationNavGraphController;
    }

    public NavController getProfileNavGraphController() {
        return profileNavGraphController;
    }

    public void setProfileNavGraphController(NavController profileNavGraphController) {
        this.profileNavGraphController = profileNavGraphController;
    }

    // END_OF[Nav Controllers]

    public UserGsonViewModel getUserGsonViewModel() {
        return new ViewModelProvider(mainActivity, userGsonViewModelFactory).get(UserGsonViewModel.class);
    }

    // START_OF[Session & Server]

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void startLoginSession(UserGson userGson) {
        server.addCookies(server.getParseCookies());
        sessionManager.startSession(userGson, server.getCookies());
    }

    public void stopLoginSession() {
        server.removeCookies(server.parseCookies(sessionManager.getCookies()));
        sessionManager.stopSession();
    }

    public boolean isLoggedIn() {
        return sessionManager.getUserGson() != null;
    }

    public RoadReadyServer getServer() {
        return server;
    }

    public void login(
            final RoadReadyServer.ResponseListener<UserDataGson> responseListener,
            final String email,
            final String password) {
        server.login(RoadReadyServer.getCallback(responseListener), email, password);
    }

    public void registerBuyer(
            final RoadReadyServer.ResponseListener<GsonData> responseListener,
            final String email,
            final String password,
            final String firstName,
            final String lastName,
            final String phoneNumber,
            final String gender,
            final String address
    ) {
        server.registerBuyer(RoadReadyServer.getCallback(responseListener), email, password, firstName, lastName, phoneNumber, gender, address);
    }

    public void updateBuyerProfile(
            final RoadReadyServer.ResponseListener<UserDataGson> responseListener,
            @Nullable final File profileImage,
            @Nullable final String firstName,
            @Nullable final String lastName,
            @Nullable final String phoneNumber,
            @Nullable final String gender,
            @Nullable final String address
    ) {
        server.updateBuyerProfile(RoadReadyServer.getCallback(responseListener), profileImage, firstName, lastName, phoneNumber, gender, address);
    }

    public void getListings(
            final RoadReadyServer.ResponseListener<ListingsDataGson> responseListener,
            @Nullable final String listingId,
            @Nullable final String dealershipId,
            @Nullable final String modelAndName
    ) {
        server.getListings(RoadReadyServer.getCallback(responseListener), listingId, dealershipId, modelAndName);
    }

    // END_OF[Session & Server]

    // Others

    public void makeToast(Object message, int duration) {
        Toast.makeText(mainActivity.getApplicationContext(), String.valueOf(message), duration).show();
    }

    public void showProgressBar() {
        mainBinding.progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        mainBinding.progressBar.setVisibility(View.GONE);
    }
}
