package com.example.roadready.classes.general;

import android.service.autofill.UserData;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
//        super("https://road-ready-black.vercel.app");
        super("http://10.0.2.2:6969");
    }

    public Call<SuccessGson<UserDataGson>> login(
            final Callback<SuccessGson<UserDataGson>> callback,
            final String email,
            final String password
    ) {
        Call<SuccessGson<UserDataGson>> call = getRetrofitService().login(email, password);
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<DealershipsDataGson>> getDealerships(
            final Callback<SuccessGson<DealershipsDataGson>> callback,
            @Nullable final String dealershipId,
            @Nullable final String dealershipName
    ) {
        Map<String, String> filters = new HashMap<>();
        if (dealershipId != null)
            filters.put("dealership_id", dealershipId);
        if (dealershipName != null)
            filters.put("dealership_name", dealershipName);
        Call<SuccessGson<DealershipsDataGson>> call = getRetrofitService().getDealerships(filters);
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<ApplicationsDataGson>> applyForListing(
            final Callback<SuccessGson<ApplicationsDataGson>> callback,
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
            validIdImagePart = MultipartBody.Part.createFormData("validId", validIdImage.getName(), requestBody);
        }

        if (signatureImage != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(URLConnection.guessContentTypeFromName(signatureImage.getName())), signatureImage);
            signatureImagePart = MultipartBody.Part.createFormData("signature", signatureImage.getName(), requestBody);
        }

        if (coMakerValidIdImage != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(URLConnection.guessContentTypeFromName(coMakerValidIdImage.getName())), coMakerValidIdImage);
            coMakerValidIdImagePart = MultipartBody.Part.createFormData("coMakerValidId", coMakerValidIdImage.getName(), requestBody);
        }

        if (coMakerSignatureImage != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(URLConnection.guessContentTypeFromName(coMakerSignatureImage.getName())), coMakerSignatureImage);
            coMakerSignatureImagePart = MultipartBody.Part.createFormData("coMakerSignature", coMakerSignatureImage.getName(), requestBody);
        }

        if (bankCertificateImage != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(URLConnection.guessContentTypeFromName(bankCertificateImage.getName())), bankCertificateImage);
            bankCertificateImagePart = MultipartBody.Part.createFormData("bankLoanCertificate", bankCertificateImage.getName(), requestBody);
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

        Call<SuccessGson<ApplicationsDataGson>> call = getRetrofitService().applyForListing(validIdImagePart, signatureImagePart, coMakerValidIdImagePart, coMakerSignatureImagePart, bankCertificateImagePart, fields);
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<ApplicationsDataGson>> updateApplication(
            final Callback<SuccessGson<ApplicationsDataGson>> callback,
            final String applicationType,
            final String applicationId,
            final int progress
    ) {
        Call<SuccessGson<ApplicationsDataGson>> call = getRetrofitService().updateApplication(applicationType, applicationId, progress);
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<ApplicationsDataGson>> getBuyerApplications(
            final Callback<SuccessGson<ApplicationsDataGson>> callback
    ) {
        Call<SuccessGson<ApplicationsDataGson>> call = getRetrofitService().getBuyerApplications();
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<ApplicationsDataGson>> getDealershipApplications(
            final Callback<SuccessGson<ApplicationsDataGson>> callback
    ) {
        Call<SuccessGson<ApplicationsDataGson>> call = getRetrofitService().getDealershipApplications();
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<ListingsDataGson>> getListings(
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

        Call<SuccessGson<ListingsDataGson>> call = getRetrofitService().getListings(filters);
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<ListingsDataGson>> createListing(
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

        Call<SuccessGson<ListingsDataGson>> call = getRetrofitService().createListing(imagePart, fields);
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<GsonData>> deleteListing(
            final Callback<SuccessGson<GsonData>> callback,
            final String listingId
    ) {
        Call<SuccessGson<GsonData>> call = getRetrofitService().deleteListing(listingId);
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<ListingsDataGson>> updateListing(
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

        Call<SuccessGson<ListingsDataGson>> call = getRetrofitService().updateListing(imagePart, fields);
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<UserDataGson>> updateBuyerProfile(
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

        Call<SuccessGson<UserDataGson>> call = getRetrofitService().updateUserProfile(imagePart, fields);
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<UserDataGson>> updateDealershipProfile(
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

        Call<SuccessGson<UserDataGson>> call = getRetrofitService().updateUserProfile(imagePart, fields);
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<UserDataGson>> getUserProfile(
            final Callback<SuccessGson<UserDataGson>> callback,
            final String userId
    ) {
        Call<SuccessGson<UserDataGson>> call = getRetrofitService().getUserProfile(userId);
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<GsonData>> registerBuyer(
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
        Call<SuccessGson<GsonData>> call = getRetrofitService().buyerRegister(fields);
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<GsonData>> registerDealership(
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

        Call<SuccessGson<GsonData>> call = getRetrofitService().dealershipRegister(imagePart, fields);
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<GoogleAuthGson>> getGoogleAuthLink(
            final Callback<SuccessGson<GoogleAuthGson>> callback
    ) {
        Call<SuccessGson<GoogleAuthGson>> call = getRetrofitService().getGoogleAuth();
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<GsonData>> requestOTP(
            final Callback<SuccessGson<GsonData>> callback
    ) {
        Call<SuccessGson<GsonData>> call = getRetrofitService().requestOTP();
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<UserDataGson>> verifyBuyerOTP(
            final Callback<SuccessGson<UserDataGson>> callback,
            final String code
    ) {
        Call<SuccessGson<UserDataGson>> call = getRetrofitService().verifyBuyerOTP(code);
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<ModeOfPaymentDataGson>> getModeOfPayments(
            final Callback<SuccessGson<ModeOfPaymentDataGson>> callback,
            final String dealershipId
    ) {
        Call<SuccessGson<ModeOfPaymentDataGson>> call = getRetrofitService().getModeOfPayments(dealershipId);
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<NotificationsDataGson>> getNotifications(
            final Callback<SuccessGson<NotificationsDataGson>> callback
    ) {
        Call<SuccessGson<NotificationsDataGson>> call = getRetrofitService().getNotification();
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<GsonData>> deleteNotification(
            final Callback<SuccessGson<GsonData>> callback,
            final String notificationId
    ) {
        Call<SuccessGson<GsonData>> call = getRetrofitService().deleteNotification(notificationId);
        call.enqueue(callback);
        return call;
    }

    public Call<SuccessGson<UserDataGson>> registerAgent(
            final Callback<SuccessGson<UserDataGson>> callback,
            final String email,
            final String password,
            final String firstName,
            final String lastName,
            final String phoneNumber,
            final String address,
            final String gender,
            final String agentType,
            @Nullable final String bank,
            @Nullable final String bankAddress) {

        Map<String, String> fields = new HashMap<>();

        fields.put("email", email);
        fields.put("password", password);
        fields.put("firstName", firstName);
        fields.put("lastName", lastName);
        fields.put("phoneNumber", phoneNumber);
        fields.put("address", address);
        fields.put("gender",  gender);
        fields.put("agentType", agentType);

        if (bank != null)
            fields.put("bank", bank);

        if (bankAddress != null)
            fields.put("bankAddress", bankAddress);

        Call<SuccessGson<UserDataGson>> call = getRetrofitService().registerAgent(fields);
        call.enqueue(callback);
        return call;
    };

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
        void onSuccess(SuccessGson<T> response);

        void onFailure(int code, String message);
    }

    public static <T extends GsonData> Callback<SuccessGson<T>> getCallback(ResponseListener<T> responseListener) {
        return new Callback<SuccessGson<T>>() {
            @Override
            public void onResponse(@NonNull Call<SuccessGson<T>> call, @NonNull Response<SuccessGson<T>> response) {
                ResponseGson body = null;
                try {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        body = response.body();

                        @SuppressWarnings("unchecked")
                        SuccessGson<T> successGson = (SuccessGson<T>) body;
                        responseListener.onSuccess(successGson);
                    } else {
                        assert response.errorBody() != null;
                        body = new Gson().fromJson(response.errorBody().string(), ErrorGson.class);
                        throw new RequestFailedException(body.getMessage());
                    }
                } catch (RequestFailedException e) {
                    responseListener.onFailure(response.code(), e.getMessage());
                } catch (Exception e) {
                    onFailure(call, e);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessGson<T>> call, @NonNull Throwable t) {
                Log.e(TAG, "Network Error!" + t.getMessage());
                responseListener.onFailure(-1, t.getMessage());
            }
        };
    };

    static class RequestFailedException extends Exception {
        public RequestFailedException(String message) {
            super(message);
        }
    }
}



