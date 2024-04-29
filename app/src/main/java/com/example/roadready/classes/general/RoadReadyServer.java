package com.example.roadready.classes.general;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.roadready.classes.model.gson.ApplicationDataGson;
import com.example.roadready.classes.model.gson.ApplicationsDataGson;
import com.example.roadready.classes.model.gson.DealershipsDataGson;
import com.example.roadready.classes.model.gson.GsonData;
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.ModeOfPaymentDataGson;
import com.example.roadready.classes.model.gson.NotificationsDataGson;
import com.example.roadready.classes.model.gson.UserDataGson;
import com.example.roadready.classes.model.gson.data.GoogleAuthGson;
import com.example.roadready.classes.model.gson.response.ErrorGson;
import com.example.roadready.classes.model.gson.response.ResponseGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.classes.retrofit.RetrofitFacade;
import com.google.gson.Gson;

import java.io.File;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoadReadyServer extends RetrofitFacade {
    private static final String TAG = "RoadReadyServer";

    public RoadReadyServer() {
        super("https://road-ready-black.vercel.app");
    }

    public void login(
            final Callback<SuccessGson<UserDataGson>> callback,
            final String email,
            final String password
    ) {
        getRetrofitService().login(email, password).enqueue(callback);
    }

    public void getDealerships(
            final Callback<SuccessGson<DealershipsDataGson>> callback,
            @Nullable final String dealershipId,
            @Nullable final String dealershipName
    ) {
        Map<String, String> filters = new HashMap<>();
        if (dealershipId != null)
            filters.put("dealership_id", dealershipId);
        if (dealershipName != null)
            filters.put("dealership_name", dealershipName);
        getRetrofitService().getDealerships(filters).enqueue(callback);
    }

    public void applyForListing(
            final Callback<SuccessGson<ApplicationDataGson>> callback,
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
        MultipartBody.Part validIdImagePart = null;
        MultipartBody.Part signatureImagePart = null;
        MultipartBody.Part coMakerValidIdImagePart = null;
        MultipartBody.Part coMakerSignatureImagePart = null;
        MultipartBody.Part bankCertificateImagePart = null;

        if (validIdImage != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(URLConnection.guessContentTypeFromName(validIdImage.getName())), validIdImage);
            validIdImagePart = MultipartBody.Part.createFormData("image", validIdImage.getName(), requestBody);
        }

        if (signatureImage != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(URLConnection.guessContentTypeFromName(signatureImage.getName())), signatureImage);
            signatureImagePart = MultipartBody.Part.createFormData("image", signatureImage.getName(), requestBody);
        }

        if (coMakerValidIdImage != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(URLConnection.guessContentTypeFromName(coMakerValidIdImage.getName())), coMakerValidIdImage);
            coMakerValidIdImagePart = MultipartBody.Part.createFormData("image", coMakerValidIdImage.getName(), requestBody);
        }

        if (coMakerSignatureImage != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(URLConnection.guessContentTypeFromName(coMakerSignatureImage.getName())), coMakerSignatureImage);
            coMakerSignatureImagePart = MultipartBody.Part.createFormData("image", coMakerSignatureImage.getName(), requestBody);
        }

        if (bankCertificateImage != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(URLConnection.guessContentTypeFromName(bankCertificateImage.getName())), bankCertificateImage);
            bankCertificateImagePart = MultipartBody.Part.createFormData("image", bankCertificateImage.getName(), requestBody);
        }

        Map<String, RequestBody> fields = new HashMap<>();
        fields.put("modeofpayment", RequestBody.create(MediaType.parse("text/plain"), modeOfPayment));
        fields.put("listingId", RequestBody.create(MediaType.parse("text/plain"), listingId));
        fields.put("firstName", RequestBody.create(MediaType.parse("text/plain"), firstName));
        fields.put("lastName", RequestBody.create(MediaType.parse("text/plain"), lastName));
        fields.put("address", RequestBody.create(MediaType.parse("text/plain"), address));
        fields.put("phoneNumber", RequestBody.create(MediaType.parse("text/plain"), phoneNumber));

        if (cashModeOfPayment != null)
            fields.put("cashmodeofpayment", RequestBody.create(MediaType.parse("text/plain"), cashModeOfPayment));

        if (coMakerFirstName != null)
            fields.put("coMakerFirstName", RequestBody.create(MediaType.parse("text/plain"), coMakerFirstName));

        if (coMakerLastName != null)
            fields.put("coMakerLastName", RequestBody.create(MediaType.parse("text/plain"), coMakerLastName));

        if (coMakerAddress != null)
            fields.put("coMakerAddress", RequestBody.create(MediaType.parse("text/plain"), coMakerAddress));

        if (coMakerPhoneNumber != null)
            fields.put("coMakerPhoneNumber", RequestBody.create(MediaType.parse("text/plain"), coMakerPhoneNumber));

        getRetrofitService().applyForListing(validIdImagePart, signatureImagePart, coMakerValidIdImagePart, coMakerSignatureImagePart, bankCertificateImagePart, fields).enqueue(callback);
    }

    public void getBuyerApplications(
            final Callback<SuccessGson<ApplicationsDataGson>> callback
    ) {
        getRetrofitService().getBuyerApplications().enqueue(callback);
    }

    public void getDealershipApplications(
            final Callback<SuccessGson<ApplicationsDataGson>> callback
    ) {
        getRetrofitService().getDealershipApplications().enqueue(callback);
    }

    public void getListings(
            final Callback<SuccessGson<ListingsDataGson>> callback,
            @Nullable final String listingId,
            @Nullable final String dealershipId,
            @Nullable final String modelAndName
    ) {
        Map<String, String> filters = new HashMap<>();
        if (listingId != null)
            filters.put("listing_id", listingId);
        if (dealershipId != null)
            filters.put("dealership_id", dealershipId);
        if (modelAndName != null)
            filters.put("model_and_name", modelAndName);

        Log.d(TAG, filters.toString());
        getRetrofitService().getListings(filters).enqueue(callback);
    }

    public void createListing(
            final Callback<SuccessGson<ListingsDataGson>> callback,
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
        RequestBody requestBody = RequestBody.create(MediaType.parse(URLConnection.guessContentTypeFromName(listingImage.getName())), listingImage);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("listingImage", listingImage.getName(), requestBody);

        Map<String, RequestBody> fields = new HashMap<>();
        fields.put("modelAndName", RequestBody.create(MediaType.parse("text/plain"), modelAndName));
        fields.put("make", RequestBody.create(MediaType.parse("text/plain"), make));
        fields.put("fuelType", RequestBody.create(MediaType.parse("text/plain"), fuelType));
        fields.put("power", RequestBody.create(MediaType.parse("text/plain"), power));
        fields.put("transmission", RequestBody.create(MediaType.parse("text/plain"), transmission));
        fields.put("engine", RequestBody.create(MediaType.parse("text/plain"), engine));
        fields.put("fuelTankCapacity", RequestBody.create(MediaType.parse("text/plain"), fuelTankCapacity));
        fields.put("seatingCapacity", RequestBody.create(MediaType.parse("text/plain"), seatingCapacity));
        fields.put("price", RequestBody.create(MediaType.parse("text/plain"), price));
        fields.put("dealershipName", RequestBody.create(MediaType.parse("text/plain"), dealershipName));
        fields.put("vehicleType", RequestBody.create(MediaType.parse("text/plain"),vehicleType));

        getRetrofitService().createListing(imagePart, fields).enqueue(callback);
    }

    public void deleteListing(
            final Callback<SuccessGson<GsonData>> callback,
            final String listingId
    ) {
        getRetrofitService().deleteListing(listingId).enqueue(callback);
    }

    public void updateListing(
            final Callback<SuccessGson<ListingsDataGson>> callback,
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

        MultipartBody.Part imagePart = null;
        if (listingImage != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(URLConnection.guessContentTypeFromName(listingImage.getName())), listingImage);
            imagePart = MultipartBody.Part.createFormData("image", listingImage.getName(), requestBody);
        }

        Map<String, RequestBody> fields = new HashMap<>();

        if (modelAndName != null)
            fields.put("modelAndName", RequestBody.create(MediaType.parse("text/plain"), modelAndName));

        if (make != null)
            fields.put("make", RequestBody.create(MediaType.parse("text/plain"), make));

        if (fuelType != null)
            fields.put("fuelType", RequestBody.create(MediaType.parse("text/plain"), fuelType));

        if (power != null)
            fields.put("power", RequestBody.create(MediaType.parse("text/plain"), power));

        if (transmission != null)
            fields.put("transmission", RequestBody.create(MediaType.parse("text/plain"), transmission));

        if (engine != null)
            fields.put("engine", RequestBody.create(MediaType.parse("text/plain"), engine));

        if (fuelTankCapacity != null)
            fields.put("fuelTankCapacity", RequestBody.create(MediaType.parse("text/plain"), fuelTankCapacity));

        if (seatingCapacity != null)
            fields.put("seatingCapacity", RequestBody.create(MediaType.parse("text/plain"), seatingCapacity));

        if (price != null)
            fields.put("price", RequestBody.create(MediaType.parse("text/plain"), price));

        getRetrofitService().createListing(imagePart, fields).enqueue(callback);
    }

    public void updateBuyerProfile(
            final Callback<SuccessGson<UserDataGson>> callback,
            @Nullable final File profileImage,
            @Nullable final String firstName,
            @Nullable final String lastName,
            @Nullable final String phoneNumber,
            @Nullable final String gender,
            @Nullable final String address
    ) {
        MultipartBody.Part imagePart = null;
        if (profileImage != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(URLConnection.guessContentTypeFromName(profileImage.getName())), profileImage);
            imagePart = MultipartBody.Part.createFormData("profileImage", profileImage.getName(), requestBody);
        }

        Map<String, RequestBody> fields = new HashMap<>();
        if (firstName != null)
            fields.put("firstName", RequestBody.create(MediaType.parse("text/plain"), firstName));
        if (lastName != null)
            fields.put("lastName", RequestBody.create(MediaType.parse("text/plain"), lastName));
        if (phoneNumber != null)
            fields.put("phoneNumber", RequestBody.create(MediaType.parse("text/plain"), phoneNumber));
        if (gender != null)
            fields.put("gender", RequestBody.create(MediaType.parse("text/plain"), gender));
        if (address != null)
            fields.put("address", RequestBody.create(MediaType.parse("text/plain"), address));

        getRetrofitService().updateUserProfile(imagePart, fields).enqueue(callback);
    }

    public void updateDealershipProfile(
            final Callback<SuccessGson<UserDataGson>> callback,
            @Nullable final File profileImage,
            @Nullable final String firstName,
            @Nullable final String lastName,
            @Nullable final String phoneNumber,
            @Nullable final String gender,
            @Nullable final String address
    ) {
        MultipartBody.Part imagePart = null;
        if (profileImage != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(URLConnection.guessContentTypeFromName(profileImage.getName())), profileImage);
            imagePart = MultipartBody.Part.createFormData("profileImage", profileImage.getName(), requestBody);
        }

        Map<String, RequestBody> fields = new HashMap<>();
        if (firstName != null)
            fields.put("firstName", RequestBody.create(MediaType.parse("text/plain"), firstName));
        if (lastName != null)
            fields.put("lastName", RequestBody.create(MediaType.parse("text/plain"), lastName));
        if (phoneNumber != null)
            fields.put("phoneNumber", RequestBody.create(MediaType.parse("text/plain"), phoneNumber));
        if (gender != null)
            fields.put("gender", RequestBody.create(MediaType.parse("text/plain"), gender));
        if (address != null)
            fields.put("address", RequestBody.create(MediaType.parse("text/plain"), address));

        getRetrofitService().updateUserProfile(imagePart, fields).enqueue(callback);
    }

    public void getUserProfile(
            final Callback<SuccessGson<UserDataGson>> callback,
            final String userId
    ) {
        getRetrofitService().getUserProfile(userId).enqueue(callback);
    }

    public void registerBuyer(
            final Callback<SuccessGson<GsonData>> callback,
            final String email,
            final String password,
            final String firstName,
            final String lastName,
            final String phoneNumber,
            final String gender,
            final String address
    ) {
        Map<String, String> fields = new HashMap<>();
        fields.put("email", email);
        fields.put("password", password);
        fields.put("firstName", firstName);
        fields.put("lastName", lastName);
        fields.put("phoneNumber", phoneNumber);
        fields.put("gender", gender);
        fields.put("address", address);
        getRetrofitService().buyerRegister(fields).enqueue(callback);
    }

    public void registerDealership(
            final Callback<SuccessGson<GsonData>> callback,
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
        MultipartBody.Part imagePart = null;
        if (dealershipImage != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(URLConnection.guessContentTypeFromName(dealershipImage.getName())), dealershipImage);
            imagePart = MultipartBody.Part.createFormData("dealershipImage", dealershipImage.getName(), requestBody);
        }

        Map<String, RequestBody> fields = new HashMap<>();
        fields.put("email", RequestBody.create(MediaType.parse("text/plain"), email));
        fields.put("password", RequestBody.create(MediaType.parse("text/plain"), password));
        fields.put("firstName", RequestBody.create(MediaType.parse("text/plain"), firstName));
        fields.put("lastName", RequestBody.create(MediaType.parse("text/plain"), lastName));
        fields.put("phoneNumber", RequestBody.create(MediaType.parse("text/plain"), phoneNumber));
        fields.put("gender", RequestBody.create(MediaType.parse("text/plain"), gender));
        fields.put("dealershipName", RequestBody.create(MediaType.parse("text/plain"), dealershipName));
        fields.put("establishmentAddress", RequestBody.create(MediaType.parse("text/plain"), establishmentAddress));
        fields.put("latitude", RequestBody.create(MediaType.parse("text/plain"), latitude));
        fields.put("longitude", RequestBody.create(MediaType.parse("text/plain"), longitude));
        fields.put("modeOfPayments", RequestBody.create(MediaType.parse("text/plain"), modeOfPayments));

        getRetrofitService().dealershipRegister(imagePart, fields).enqueue(callback);
    }

    public void getGoogleAuthLink(
            final Callback<SuccessGson<GoogleAuthGson>> callback
    ) {
        getRetrofitService().getGoogleAuth().enqueue(callback);
    }

    public void requestOTP(
            final Callback<SuccessGson<GsonData>> callback
    ) {
        getRetrofitService().requestOTP().enqueue(callback);
    }

    public void verifyBuyerOTP(
            final Callback<SuccessGson<UserDataGson>> callback,
            final String code
    ) {
        getRetrofitService().verifyBuyerOTP(code).enqueue(callback);
    }

    public void getModeOfPayments(
            final Callback<SuccessGson<ModeOfPaymentDataGson>> callback,
            final String dealershipId
    ) {
        getRetrofitService().getModeOfPayments(dealershipId).enqueue(callback);
    }

    public void getNotifications(
            final Callback<SuccessGson<NotificationsDataGson>> callback
    ) {
        getRetrofitService().getNotification().enqueue(callback);
    }

    public void deleteNotification(
            final Callback<SuccessGson<GsonData>> callback,
            final String notificationId
    ) {
        getRetrofitService().deleteNotification(notificationId).enqueue(callback);
    }

    public void addCookies(Set<String> cookies) {
        RetrofitFacade.cookies.addAll(cookies);
    }

    public void removeCookies(Set<String> cookies) {
        RetrofitFacade.cookies.removeAll(cookies);
    }

    public Set<String> getCookies() {
        return cookies;
    }

    public interface ResponseListener<T extends GsonData> {
        void onSuccess(T data);

        void onFailure(String message);
    }

    public static <T1 extends GsonData> Callback<SuccessGson<T1>> getCallback(ResponseListener<T1> responseListener) {
        return new Callback<SuccessGson<T1>>() {
            @Override
            public void onResponse(@NonNull Call<SuccessGson<T1>> call, @NonNull Response<SuccessGson<T1>> response) {
                ResponseGson body = null;
                try {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        body = response.body();

                        @SuppressWarnings("unchecked")
                        T1 data = (T1) ((SuccessGson<?>) body).getData();
                        responseListener.onSuccess(data);
                    } else {
                        assert response.errorBody() != null;
                        body = new Gson().fromJson(response.errorBody().string(), ErrorGson.class);
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
    };
}


