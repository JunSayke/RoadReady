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
import com.example.roadready.databinding.ActivityCommonMainBinding;

import java.io.File;
import java.util.Set;

public class MainFacade {
    private static FragmentActivity mainActivity;
    private ActivityCommonMainBinding mainBinding;
    private NavController currentNavController, buyerHomepageNavController, buyerMainNavController,
            buyerHomeNavController, buyerApplicationNavController, buyerMyVehicleNavController,
            commonNotificationNavController, commonProfileNavController, dealershipHomepageNavController,
            dealershipApprovedNavController, dealershipBankNavController, dealershipDocumentsProgressNavController,
            dealershipForApprovalNavController, dealershipLtoNavController, dealershipMyVehicleNavController;
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

        Set<String> PREF_COOKIES = sessionManager.getCookies();
        if (!PREF_COOKIES.isEmpty()) {
            server.addCookies(PREF_COOKIES);
        }
    }

    public ActivityCommonMainBinding getMainBinding() {
        return mainBinding;
    }

    public void setMainBinding(ActivityCommonMainBinding mainBinding) {
        this.mainBinding = mainBinding;
    }

    // START_OF[Nav Controllers]

    public NavController getCurrentNavController() {
        return currentNavController;
    }

    public void setCurrentNavController(NavController currentNavController) {
        this.currentNavController = currentNavController;
    }

    public NavController getBuyerMainNavController() {
        return buyerMainNavController;
    }

    public void setBuyerMainNavController(NavController buyerMainNavController) {
        this.buyerMainNavController = buyerMainNavController;
    }

    public NavController getBuyerHomepageNavController() {
        return buyerHomepageNavController;
    }

    public void setBuyerHomepageNavController(NavController buyerHomepageNavController) {
        this.buyerHomepageNavController = buyerHomepageNavController;
    }

    public NavController getBuyerHomeNavController() {
        return buyerHomeNavController;
    }

    public void setBuyerHomeNavController(NavController buyerHomeNavController) {
        this.buyerHomeNavController = buyerHomeNavController;
    }

    public NavController getBuyerApplicationNavController() {
        return buyerApplicationNavController;
    }

    public void setBuyerApplicationNavController(NavController buyerApplicationNavController) {
        this.buyerApplicationNavController = buyerApplicationNavController;
    }

    public NavController getBuyerMyVehicleNavController() {
        return buyerMyVehicleNavController;
    }

    public void setBuyerMyVehicleNavController(NavController buyerMyVehicleNavController) {
        this.buyerMyVehicleNavController = buyerMyVehicleNavController;
    }

    public NavController getCommonNotificationNavController() {
        return commonNotificationNavController;
    }

    public void setCommonNotificationNavController(NavController commonNotificationNavController) {
        this.commonNotificationNavController = commonNotificationNavController;
    }

    public NavController getCommonProfileNavController() {
        return commonProfileNavController;
    }

    public void setCommonProfileNavController(NavController commonProfileNavController) {
        this.commonProfileNavController = commonProfileNavController;
    }

    public NavController getDealershipHomepageNavController() {
        return dealershipHomepageNavController;
    }

    public void setDealershipHomepageNavController(NavController dealershipHomepageNavController) {
        this.dealershipHomepageNavController = dealershipHomepageNavController;
    }

    public NavController getDealershipApprovedNavController() {
        return dealershipApprovedNavController;
    }

    public void setDealershipApprovedNavController(NavController dealershipApprovedNavController) {
        this.dealershipApprovedNavController = dealershipApprovedNavController;
    }

    public NavController getDealershipBankNavController() {
        return dealershipBankNavController;
    }

    public void setDealershipBankNavController(NavController dealershipBankNavController) {
        this.dealershipBankNavController = dealershipBankNavController;
    }

    public NavController getDealershipDocumentsProgressNavController() {
        return dealershipDocumentsProgressNavController;
    }

    public void setDealershipDocumentsProgressNavController(NavController dealershipDocumentsProgressNavController) {
        this.dealershipDocumentsProgressNavController = dealershipDocumentsProgressNavController;
    }

    public NavController getDealershipForApprovalNavController() {
        return dealershipForApprovalNavController;
    }

    public void setDealershipForApprovalNavController(NavController dealershipForApprovalNavController) {
        this.dealershipForApprovalNavController = dealershipForApprovalNavController;
    }

    public NavController getDealershipLtoNavController() {
        return dealershipLtoNavController;
    }

    public void setDealershipLtoNavController(NavController dealershipLtoNavController) {
        this.dealershipLtoNavController = dealershipLtoNavController;
    }

    public NavController getDealershipMyVehicleNavController() {
        return dealershipMyVehicleNavController;
    }

    public void setDealershipMyVehicleNavController(NavController dealershipMyVehicleNavController) {
        this.dealershipMyVehicleNavController = dealershipMyVehicleNavController;
    }

    // END_OF[Nav Controllers]

    public UserGsonViewModel getUserGsonViewModel() {
        return new ViewModelProvider(mainActivity, userGsonViewModelFactory).get(UserGsonViewModel.class);
    }

    // START_OF[Session & Server]

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public RoadReadyServer getServer() {
        return server;
    }

    public void startLoginSession(UserGson userGson) {
        Set<String> PREF_COOKIES = server.getCookies();
        server.addCookies(PREF_COOKIES);
        sessionManager.startSession(userGson, PREF_COOKIES);
//        Log.d(TAG, server.getCookies() + "\n" + sessionManager.getCookies());
    }

    public void stopLoginSession() {
        server.removeCookies(sessionManager.getCookies());
        sessionManager.stopSession();
//        Log.d(TAG, server.getCookies() + "\n" + sessionManager.getCookies());
    }

    public boolean isLoggedIn() {
        return sessionManager.getUserGson() != null;
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

    public void createListing(
            final RoadReadyServer.ResponseListener<GsonData> responseListener,
            final File listingImage,
            final String modelAndName,
            final String make,
            final String fuelType,
            final String power,
            final String transmission,
            final String engine,
            final String fuelTankCapacity,
            final String seatingCapacity,
            final String price,
            final String dealershipName,
            final String vehicleType
    ) {
        server.createListing(RoadReadyServer.getCallback(responseListener),
                listingImage, modelAndName, make, fuelType,
                power, transmission, engine, fuelTankCapacity,
                seatingCapacity, price, dealershipName, vehicleType);
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
