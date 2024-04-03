package com.example.roadready.classes.general;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.roadready.activity.MainActivity;
import com.example.roadready.classes.model.gson.GsonData;
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.UserDataGson;
import com.example.roadready.classes.model.gson.data.BuyerGson;
import com.example.roadready.classes.model.gson.response.ErrorGson;
import com.example.roadready.classes.model.gson.response.ResponseGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.classes.model.livedata.BuyerGsonViewModel;
import com.example.roadready.classes.model.livedata.BuyerGsonViewModelFactory;
import com.example.roadready.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public BuyerGsonViewModel getBuyerGsonViewModel() {
        return new ViewModelProvider(mainActivity, buyerGsonViewModelFactory).get(BuyerGsonViewModel.class);
    }

    // START_OF[Interfaces & Inner Classes]

    public interface ResponseListener<T extends GsonData> {
        void onSuccess(T data);

        void onFailure(String message);
    }

    public static class CallbackTemplate<T1 extends GsonData> {
        private String TAG = "CallbackTemplate";

        public void setTAG(String TAG) {
            this.TAG = TAG;
        }

        <T2 extends GsonData> Callback<SuccessGson<T1>> generate(ResponseListener<T2> responseListener) {
            return new Callback<SuccessGson<T1>>() {
                @Override
                public void onResponse(@NonNull Call<SuccessGson<T1>> call, @NonNull Response<SuccessGson<T1>> response) {
                    ResponseGson body = null;
                    try {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            body = response.body();
                            Log.d(TAG, String.valueOf(body));

                            @SuppressWarnings("unchecked")
                            T2 data = (T2) ((SuccessGson<?>) body).getData();
                            responseListener.onSuccess(data);
                        } else {
                            assert response.errorBody() != null;
                            body = new Gson().fromJson(response.errorBody().string(), ErrorGson.class);
                            Log.e(TAG, String.valueOf(body));
                        }
                    } catch (Exception e) {
                        Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                    } finally {
                        String message = "Null response!";
                        if (body != null) {
                            message = body.getMessage();
                        }
                        responseListener.onFailure(message);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SuccessGson<T1>> call, @NonNull Throwable t) {
                    Log.e(TAG, "Network Error!" + t.getMessage());
                    responseListener.onFailure(t.getMessage());
                }
            };
        }
    }

    // END_OF[Interfaces & Inner Classes]

    // START_OF[Session & Server]

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void startLoginSession(BuyerGson buyerGson) {
        server.addCookies(server.getParseCookies());
        sessionManager.startSession(buyerGson, server.getCookies());
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
            String email,
            String password,
            ResponseListener<?> responseListener) {
        CallbackTemplate<UserDataGson> callbackTemplate = new CallbackTemplate<>();
        server.getRetrofitService().login(email, password).enqueue(callbackTemplate.generate(responseListener));
    }

    public void updateProfile(
            String firstName,
            String lastName,
            String phoneNumber,
            String gender,
            String address,
            ResponseListener<?> responseListener) {
        CallbackTemplate<UserDataGson> callbackTemplate = new CallbackTemplate<>();
        server.getRetrofitService().updateProfile(
                        firstName, lastName, phoneNumber, gender, address)
                .enqueue(callbackTemplate.generate(responseListener));
    }

    public void getListings(
            String listingId,
            String dealershipId,
            String dealershipAgentId,
            String modelAndName,
            ResponseListener<?> responseListener
    ) {
        CallbackTemplate<ListingsDataGson> callbackTemplate = new CallbackTemplate<>();
        server.getRetrofitService().getListings(
                        listingId, dealershipId, dealershipAgentId, modelAndName)
                .enqueue(callbackTemplate.generate(responseListener));
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
