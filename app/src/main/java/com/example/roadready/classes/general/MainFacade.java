package com.example.roadready.classes.general;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.roadready.activity.MainActivity;
import com.example.roadready.classes.model.gson.ApplicationDataGson;
import com.example.roadready.classes.model.gson.ApplicationsDataGson;
import com.example.roadready.classes.model.gson.DealershipsDataGson;
import com.example.roadready.classes.model.gson.GsonData;
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.ModeOfPaymentDataGson;
import com.example.roadready.classes.model.gson.NotificationsDataGson;
import com.example.roadready.classes.model.gson.UserDataGson;
import com.example.roadready.classes.model.gson.data.GoogleAuthGson;
import com.example.roadready.classes.model.gson.data.UserGson;
import com.example.roadready.classes.model.livedata.UserGsonViewModel;
import com.example.roadready.classes.model.livedata.UserGsonViewModelFactory;
import com.example.roadready.databinding.ActivityCommonMainBinding;

import java.io.File;
import java.util.Set;

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

    public void login(
            final RoadReadyServer.ResponseListener<UserDataGson> responseListener,
            final String email,
            final String password) {
        server.login(RoadReadyServer.getCallback(responseListener), email, password);
    }

    public void getDealerships(
            final RoadReadyServer.ResponseListener<DealershipsDataGson> responseListener,
            @Nullable final String dealershipId,
            @Nullable final String dealershipName
    ) {
        server.getDealerships(RoadReadyServer.getCallback(responseListener), dealershipId, dealershipName);
    }

    public void applyCashForListing(
            final RoadReadyServer.ResponseListener<ApplicationDataGson> responseListener,
            final String listingId,
            final String firstName,
            final String lastName,
            final String address,
            final String phoneNumber,
            final File validIdImage,
            final File signatureImage,
            final String cashModeOfPayment
    ) {
        applyForListing(
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

    public void applyInHouseFinanceListing(
            final RoadReadyServer.ResponseListener<ApplicationDataGson> responseListener,
            final String listingId,
            final String firstName,
            final String lastName,
            final String address,
            final String phoneNumber,
            final File validIdImage,
            final File signatureImage,
            final @Nullable String coMakerFirstName,
            final @Nullable String coMakerLastName,
            final @Nullable String coMakerAddress,
            final @Nullable String coMakerPhoneNumber,
            final @Nullable File coMakerValidIdImage,
            final @Nullable File coMakerSignatureImage
    ) {
        applyForListing(
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

    public void applyBankLoanDealershipBankChoiceListing(
            final RoadReadyServer.ResponseListener<ApplicationDataGson> responseListener,
            final String listingId,
            final String firstName,
            final String lastName,
            final String address,
            final String phoneNumber,
            final File validIdImage,
            final File signatureImage,
            final File bankCertificateImage
    ) {
        applyForListing(
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
                bankCertificateImage
        );
    }

    public void applyBankLoanBuyerBankChoiceListing(
            final RoadReadyServer.ResponseListener<ApplicationDataGson> responseListener,
            final String listingId,
            final String firstName,
            final String lastName,
            final String address,
            final String phoneNumber,
            final File validIdImage,
            final File signatureImage,
            final File bankCertificateImage
    ) {
        applyForListing(
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

    public void applyForListing(
            final RoadReadyServer.ResponseListener<ApplicationDataGson> responseListener,
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
        server.applyForListing(
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
    public void updateApplication(
            final RoadReadyServer.ResponseListener<ApplicationDataGson> responseListener,
            final String applicationType,
            final String applicationId,
            final int progress
    ) {
        server.updateApplication(RoadReadyServer.getCallback(responseListener), applicationType, applicationId, progress);
    }

    // Logged user must be a buyer
    public void getBuyerApplications(
            final RoadReadyServer.ResponseListener<ApplicationsDataGson> responseListener
    ) {
        server.getBuyerApplications(RoadReadyServer.getCallback(responseListener));
    }

    // Logged user must be a dealership
    public void getDealershipApplications(
            final RoadReadyServer.ResponseListener<ApplicationsDataGson> responseListener
    ) {
        server.getDealershipApplications(RoadReadyServer.getCallback(responseListener));
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

    public void registerDealership(
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
        server.registerDealership(RoadReadyServer.getCallback(responseListener), dealershipImage, email, password, firstName, lastName, phoneNumber, gender, dealershipName, establishmentAddress, latitude, longitude, modeOfPayments);
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

    public void updateDealershipProfile(
            final RoadReadyServer.ResponseListener<UserDataGson> responseListener,
            @Nullable final File profileImage,
            @Nullable final String firstName,
            @Nullable final String lastName,
            @Nullable final String phoneNumber,
            @Nullable final String gender,
            @Nullable final String address
    ) {
        server.updateDealershipProfile(RoadReadyServer.getCallback(responseListener), profileImage, firstName, lastName, phoneNumber, gender, address);
    }

    public void getUserProfile(
            final RoadReadyServer.ResponseListener<UserDataGson> responseListener,
            final String userId
    ) {
        server.getUserProfile(RoadReadyServer.getCallback(responseListener), userId);
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
        server.createListing(RoadReadyServer.getCallback(responseListener),
                listingImage, modelAndName, make, fuelType,
                power, transmission, engine, fuelTankCapacity,
                seatingCapacity, price, dealershipName, vehicleType);
    }

    public void deleteListing(
            final RoadReadyServer.ResponseListener<GsonData> responseListener,
            final String listingId
    ) {
        server.deleteListing(RoadReadyServer.getCallback(responseListener), listingId);
    }

    public void updateListing(
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
        server.updateListing(RoadReadyServer.getCallback(responseListener),
                listingImage, modelAndName, make, fuelType,
                power, transmission, engine, fuelTankCapacity,
                seatingCapacity, price);
    }

    public void getGoogleAuthLink(
            final RoadReadyServer.ResponseListener<GoogleAuthGson> responseListener
    ) {
        server.getGoogleAuthLink(RoadReadyServer.getCallback(responseListener));
    }

    public void requestOTP(
            final RoadReadyServer.ResponseListener<GsonData> responseListener
    ) {
        server.requestOTP(RoadReadyServer.getCallback(responseListener));
    }

    public void verifyBuyerOTP(
            final RoadReadyServer.ResponseListener<UserDataGson> responseListener,
            final String code
    ) {
        server.verifyBuyerOTP(RoadReadyServer.getCallback(responseListener), code);
    }

    public void getModeOfPayments(
            final RoadReadyServer.ResponseListener<ModeOfPaymentDataGson> responseListener,
            final String dealershipId
    ) {
        server.getModeOfPayments(RoadReadyServer.getCallback(responseListener), dealershipId);
    }

    // Automatically get the logged in user notification.
    public void getNotification(
            final RoadReadyServer.ResponseListener<NotificationsDataGson> responseListener
    ) {
        server.getNotifications(RoadReadyServer.getCallback(responseListener));
    }

    public void deleteNotification(
            final RoadReadyServer.ResponseListener<GsonData> responseListener,
            final String notificationId
    ) {
        server.deleteNotification(RoadReadyServer.getCallback(responseListener), notificationId);
    }

    // END_OF[Session & Server]

    // Others
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
