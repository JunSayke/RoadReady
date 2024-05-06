package com.example.roadready.classes.general;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.roadready.activity.MainActivity;
import com.example.roadready.classes.model.gson.ApplicationsDataGson;
import com.example.roadready.classes.model.gson.DealershipsDataGson;
import com.example.roadready.classes.model.gson.GsonData;
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.ModeOfPaymentDataGson;
import com.example.roadready.classes.model.gson.NotificationsDataGson;
import com.example.roadready.classes.model.gson.UserDataGson;
import com.example.roadready.classes.model.gson.data.GoogleAuthGson;
import com.example.roadready.classes.model.gson.data.UserGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.classes.model.livedata.UserGsonViewModel;
import com.example.roadready.classes.model.livedata.UserGsonViewModelFactory;
import com.example.roadready.classes.util.LocationTool;
import com.example.roadready.databinding.ActivityCommonMainBinding;

import java.io.File;
import java.util.Set;

import retrofit2.Call;

public class MainFacade {
    private static final MainFacade mainFacade = new MainFacade(null);
    private static FragmentActivity mainActivity;
    private final RoadReadyServer server = new RoadReadyServer();
    private ActivityCommonMainBinding mainBinding;
    private NavController currentNavController, buyerHomepageNavController, commonMainNavController,
            buyerHomeNavController, buyerApplicationNavController, buyerMyVehicleNavController,
            commonNotificationNavController, commonProfileNavController, dealershipHomepageNavController,
            dealershipApprovedNavController, dealershipBankNavController, dealershipDocumentsProgressNavController,
            dealershipForApprovalNavController, dealershipLtoNavController, dealershipMyVehicleNavController,
            dealershipProfileNavController;
    private UserGsonViewModelFactory userGsonViewModelFactory;
    private SessionManager sessionManager;

    private MainFacade(@Nullable MainActivity mainActivity) {
        if (mainActivity != null) {
            setMainActivity(mainActivity);
        }
    }

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

    public NavController getCommonMainNavController() {
        return commonMainNavController;
    }

    public void setCommonMainNavController(NavController commonMainNavController) {
        this.commonMainNavController = commonMainNavController;
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

    public NavController getDealershipProfileNavController() {
        return dealershipProfileNavController;
    }

    public void setDealershipProfileNavController(NavController dealershipProfileNavController) {
        this.dealershipProfileNavController = dealershipProfileNavController;
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
    }

    public void stopLoginSession() {
        server.removeCookies(sessionManager.getCookies());
        sessionManager.stopSession();
    }

    public boolean isLoggedIn() {
        return sessionManager.getUserGson() != null;
    }

    public Call<SuccessGson<UserDataGson>> login(
            final RoadReadyServer.ResponseListener<UserDataGson> responseListener,
            final String email,
            final String password) {
        return server.login(RoadReadyServer.getCallback(responseListener), email, password);
    }

    public Call<SuccessGson<DealershipsDataGson>> getDealerships(
            final RoadReadyServer.ResponseListener<DealershipsDataGson> responseListener,
            @Nullable final String dealershipId,
            @Nullable final String dealershipName
    ) {
        return server.getDealerships(RoadReadyServer.getCallback(responseListener), dealershipId, dealershipName);
    }

    public Call<SuccessGson<ApplicationsDataGson>> applyCashForListing(
            final RoadReadyServer.ResponseListener<ApplicationsDataGson> responseListener,
            final String listingId,
            final String firstName,
            final String lastName,
            final String address,
            final String phoneNumber,
            final File validIdImage,
            final File signatureImage,
            final String cashModeOfPayment
    ) {
        return applyForListing(
                responseListener,
                "cash",
                listingId,
                firstName,
                lastName,
                address,
                phoneNumber,
                validIdImage,
                signatureImage,
                cashModeOfPayment,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public Call<SuccessGson<ApplicationsDataGson>> applyInHouseFinanceListing(
            final RoadReadyServer.ResponseListener<ApplicationsDataGson> responseListener,
            final String listingId,
            final String firstName,
            final String lastName,
            final String address,
            final String phoneNumber,
            final File validIdImage,
            final File signatureImage,
            final String coMakerFirstName,
            final String coMakerLastName,
            final String coMakerAddress,
            final String coMakerPhoneNumber,
            final File coMakerValidIdImage,
            final File coMakerSignatureImage
    ) {
        return applyForListing(
                responseListener,
                "inhouseFinance",
                listingId,
                firstName,
                lastName,
                address,
                phoneNumber,
                validIdImage,
                signatureImage,
                null,
                coMakerFirstName,
                coMakerLastName,
                coMakerAddress,
                coMakerPhoneNumber,
                coMakerValidIdImage,
                coMakerSignatureImage,
                null
        );
    }

    public Call<SuccessGson<ApplicationsDataGson>> applyBankLoanDealershipBankChoiceListing(
            final RoadReadyServer.ResponseListener<ApplicationsDataGson> responseListener,
            final String listingId,
            final String firstName,
            final String lastName,
            final String address,
            final String phoneNumber,
            final File validIdImage,
            final File signatureImage
    ) {
        return applyForListing(
                responseListener,
                "bankLoan(dealershipBankChoice)",
                listingId,
                firstName,
                lastName,
                address,
                phoneNumber,
                validIdImage,
                signatureImage,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public Call<SuccessGson<ApplicationsDataGson>> applyBankLoanBuyerBankChoiceListing(
            final RoadReadyServer.ResponseListener<ApplicationsDataGson> responseListener,
            final String listingId,
            final String firstName,
            final String lastName,
            final String address,
            final String phoneNumber,
            final File validIdImage,
            final File signatureImage,
            final File bankCertificateImage
    ) {
        return applyForListing(
                responseListener,
                "bankLoan(buyerBankChoice)",
                listingId,
                firstName,
                lastName,
                address,
                phoneNumber,
                validIdImage,
                signatureImage,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                bankCertificateImage
        );
    }

    public Call<SuccessGson<ApplicationsDataGson>> applyForListing(
            final RoadReadyServer.ResponseListener<ApplicationsDataGson> responseListener,
            final String modeOfPayment,
            final String listingId,
            final String firstName,
            final String lastName,
            final String address,
            final String phoneNumber,
            final File validIdImage,
            final File signatureImage,
            final @Nullable String cashModeOfPayment,
            final @Nullable String coMakerFirstName,
            final @Nullable String coMakerLastName,
            final @Nullable String coMakerAddress,
            final @Nullable String coMakerPhoneNumber,
            final @Nullable File coMakerValidIdImage,
            final @Nullable File coMakerSignatureImage,
            final @Nullable File bankCertificateImage
    ) {
        return server.applyForListing(
                RoadReadyServer.getCallback(responseListener),
                modeOfPayment,
                listingId,
                firstName,
                lastName,
                address,
                phoneNumber,
                validIdImage,
                signatureImage,
                cashModeOfPayment,
                coMakerFirstName,
                coMakerLastName,
                coMakerAddress,
                coMakerPhoneNumber,
                coMakerValidIdImage,
                coMakerSignatureImage,
                bankCertificateImage
        );
    }

    // Logged user must be of role "dealershipAgent" based on the web (JAKE) idk anymore.
    public Call<SuccessGson<ApplicationsDataGson>> updateApplication(
            final RoadReadyServer.ResponseListener<ApplicationsDataGson> responseListener,
            final String applicationType,
            final String applicationId,
            final int progress
    ) {
        return server.updateApplication(RoadReadyServer.getCallback(responseListener), applicationType, applicationId, progress);
    }

    // Logged user must be a buyer
    public Call<SuccessGson<ApplicationsDataGson>> getBuyerApplications(
            final RoadReadyServer.ResponseListener<ApplicationsDataGson> responseListener
    ) {
        return server.getBuyerApplications(RoadReadyServer.getCallback(responseListener));
    }

    // Logged user must be a dealership
    public Call<SuccessGson<ApplicationsDataGson>> getDealershipApplications(
            final RoadReadyServer.ResponseListener<ApplicationsDataGson> responseListener
    ) {
        return server.getDealershipApplications(RoadReadyServer.getCallback(responseListener));
    }

    public Call<SuccessGson<GsonData>> registerBuyer(
            final RoadReadyServer.ResponseListener<GsonData> responseListener,
            final String email,
            final String password,
            final String firstName,
            final String lastName,
            final String phoneNumber,
            final String gender,
            final String address
    ) {
        return server.registerBuyer(RoadReadyServer.getCallback(responseListener), email, password, firstName, lastName, phoneNumber, gender, address);
    }

    public Call<SuccessGson<GsonData>> registerDealership(
            final RoadReadyServer.ResponseListener<GsonData> responseListener,
            @Nullable final File dealershipImage,
            final String email,
            final String password,
            final String firstName,
            final String lastName,
            final String phoneNumber,
            final String gender,
            final String dealershipName,
            final String establishmentAddress,
            final String latitude,
            final String longitude,
            final String modeOfPayments
    ) {
        return server.registerDealership(RoadReadyServer.getCallback(responseListener), dealershipImage, email, password, firstName, lastName, phoneNumber, gender, dealershipName, establishmentAddress, latitude, longitude, modeOfPayments);
    }

    public Call<SuccessGson<UserDataGson>> updateBuyerProfile(
            final RoadReadyServer.ResponseListener<UserDataGson> responseListener,
            @Nullable final File profileImage,
            @Nullable final String firstName,
            @Nullable final String lastName,
            @Nullable final String phoneNumber,
            @Nullable final String gender,
            @Nullable final String address
    ) {
        return server.updateBuyerProfile(RoadReadyServer.getCallback(responseListener), profileImage, firstName, lastName, phoneNumber, gender, address);
    }

    public Call<SuccessGson<UserDataGson>> updateDealershipProfile(
            final RoadReadyServer.ResponseListener<UserDataGson> responseListener,
            @Nullable final File profileImage,
            @Nullable final String firstName,
            @Nullable final String lastName,
            @Nullable final String phoneNumber,
            @Nullable final String gender,
            @Nullable final String address
    ) {
        return server.updateDealershipProfile(RoadReadyServer.getCallback(responseListener), profileImage, firstName, lastName, phoneNumber, gender, address);
    }

    public Call<SuccessGson<UserDataGson>> getUserProfile(
            final RoadReadyServer.ResponseListener<UserDataGson> responseListener,
            final String userId
    ) {
        return server.getUserProfile(RoadReadyServer.getCallback(responseListener), userId);
    }

    public Call<SuccessGson<ListingsDataGson>> getListings(
            final RoadReadyServer.ResponseListener<ListingsDataGson> responseListener,
            @Nullable final String listingId,
            @Nullable final String dealershipId,
            @Nullable final String modelAndName
    ) {
        return server.getListings(RoadReadyServer.getCallback(responseListener), listingId, dealershipId, modelAndName);
    }

    public Call<SuccessGson<ListingsDataGson>> createListing(
            final RoadReadyServer.ResponseListener<ListingsDataGson> responseListener,
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
        return server.createListing(RoadReadyServer.getCallback(responseListener),
                listingImage, modelAndName, make, fuelType,
                power, transmission, engine, fuelTankCapacity,
                seatingCapacity, price, dealershipName, vehicleType);
    }

    public Call<SuccessGson<GsonData>> deleteListing(
            final RoadReadyServer.ResponseListener<GsonData> responseListener,
            final String listingId
    ) {
        return server.deleteListing(RoadReadyServer.getCallback(responseListener), listingId);
    }

    public Call<SuccessGson<ListingsDataGson>> updateListing(
            final RoadReadyServer.ResponseListener<ListingsDataGson> responseListener,
            @Nullable final File listingImage,
            @Nullable final String modelAndName,
            @Nullable final String make,
            @Nullable final String fuelType,
            @Nullable final String power,
            @Nullable final String transmission,
            @Nullable final String engine,
            @Nullable final String fuelTankCapacity,
            @Nullable final String seatingCapacity,
            @Nullable final String price
    ) {
        return server.updateListing(RoadReadyServer.getCallback(responseListener),
                listingImage, modelAndName, make, fuelType,
                power, transmission, engine, fuelTankCapacity,
                seatingCapacity, price);
    }

    public Call<SuccessGson<GoogleAuthGson>> getGoogleAuthLink(
            final RoadReadyServer.ResponseListener<GoogleAuthGson> responseListener
    ) {
        return server.getGoogleAuthLink(RoadReadyServer.getCallback(responseListener));
    }

    public Call<SuccessGson<GsonData>> requestOTP(
            final RoadReadyServer.ResponseListener<GsonData> responseListener
    ) {
        return server.requestOTP(RoadReadyServer.getCallback(responseListener));
    }

    public Call<SuccessGson<UserDataGson>> verifyBuyerOTP(
            final RoadReadyServer.ResponseListener<UserDataGson> responseListener,
            final String code
    ) {
        return server.verifyBuyerOTP(RoadReadyServer.getCallback(responseListener), code);
    }

    public Call<SuccessGson<ModeOfPaymentDataGson>> getModeOfPayments(
            final RoadReadyServer.ResponseListener<ModeOfPaymentDataGson> responseListener,
            final String dealershipId
    ) {
        return server.getModeOfPayments(RoadReadyServer.getCallback(responseListener), dealershipId);
    }

    // Automatically get the logged in user notification.
    public Call<SuccessGson<NotificationsDataGson>> getNotification(
            final RoadReadyServer.ResponseListener<NotificationsDataGson> responseListener
    ) {
        return server.getNotifications(RoadReadyServer.getCallback(responseListener));
    }

    public Call<SuccessGson<GsonData>> deleteNotification(
            final RoadReadyServer.ResponseListener<GsonData> responseListener,
            final String notificationId
    ) {
        return server.deleteNotification(RoadReadyServer.getCallback(responseListener), notificationId);
    }

    // END_OF[Session & Server]

    // START_OF[Location]

    public void fetchLocation(LocationTool.LocationCallback callback) {
        LocationTool.fetchLocation(mainActivity, callback);
    }

    // END_OF[Location]

    // Others
    private void startProcessTimer(long millisInFuture, long countDownInterval, LinearLayout container) {
        container.setEnabled(false);
        new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                container.setEnabled(true);
            }
        }.start();
    }

    public void restrictButton(Button btn) {
        btn.setEnabled(false);
        btn.setAlpha(0.2F);
    }

    public void restrictImageButton(ImageButton btn) {
        btn.setEnabled(false);
        btn.setAlpha(0.2F);
    }

    public void makeToast(Object message, int duration) {
        Toast.makeText(mainActivity.getApplicationContext(), String.valueOf(message), duration).show();
    }

    public void showUnverifiedDialog() {
        mainBinding.unverifiedLayout.setVisibility(View.VISIBLE);
    }

    public void hideUnverifiedDialog() {
        mainBinding.unverifiedLayout.setVisibility(View.GONE);
    }

    public void showProgressBar() {
        mainBinding.progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        mainBinding.progressBar.setVisibility(View.GONE);
    }

    public void showBackDrop() {
        mainBinding.backDrop.setVisibility(View.VISIBLE);
    }

    public void hideBackDrop() {
        mainBinding.backDrop.setVisibility(View.GONE);
    }
}
