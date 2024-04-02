package com.example.roadready.classes.general;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;

import com.example.roadready.activity.MainActivity;
import com.example.roadready.classes.model.gson.CookiesGson;
import com.example.roadready.classes.model.gson.data.BuyerGson;
import com.example.roadready.classes.model.livedata.BuyerGsonViewModel;
import com.example.roadready.classes.model.livedata.BuyerGsonViewModelFactory;
import com.example.roadready.databinding.ActivityMainBinding;

public class MainFacade {
    private static FragmentActivity mainActivity;
    private ActivityMainBinding mainBinding;
    private NavController currentNavController, homepageNavController, mainNavGraphController, homeNavGraphController, applicationNavGraphController, myVehicleNavGraphController, notificationNavGraphController, profileNavGraphController;
    private BuyerGsonViewModelFactory buyerGsonViewModelFactory;
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
        buyerGsonViewModelFactory = new BuyerGsonViewModelFactory(mainActivity.getApplicationContext());

        if (sessionManager.getCookiesGson() != null) {
            server.addCookies(sessionManager.getCookiesGson().getCookies());
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

    public BuyerGsonViewModel getBuyerGsonViewModel(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner, buyerGsonViewModelFactory).get(BuyerGsonViewModel.class);
    }

    // START_OF[Session & Server]
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void startLoginSession(BuyerGson buyerGson, CookiesGson cookiesGson) {
        server.addCookies(cookiesGson.getCookies());
        sessionManager.startSession(buyerGson, cookiesGson);
    }

    public void stopLoginSession() {
        server.removeCookies(sessionManager.getCookiesGson().getCookies());
        sessionManager.stopSession();
    }

    public boolean isLoggedIn() {
        return sessionManager.getUserGson() == null;
    }

    public RoadReadyServer getServer() {
        return server;
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
