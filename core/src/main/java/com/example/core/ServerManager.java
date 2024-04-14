package com.example.core;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.core.model.gson.DealershipsDataGson;
import com.example.core.model.gson.GsonData;
import com.example.core.model.gson.ListingsDataGson;
import com.example.core.model.gson.UserDataGson;
import com.example.core.model.gson.response.ErrorGson;
import com.example.core.model.gson.response.SuccessGson;
import com.example.core.retrofit.RetrofitFacade;
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

public class ServerManager extends RetrofitFacade {
    private static final String TAG = "RoadReadyServer";

    public ServerManager() {
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
            final ResponseListener<DealershipsDataGson> responseListener,
            @Nullable final String dealershipId,
            @Nullable final String dealershipName
    ) {
        Map<String, String> filters = new HashMap<>();
        if (dealershipId != null)
            filters.put("dealershipId", dealershipId);
        if (dealershipName != null)
            filters.put("dealershipName", dealershipName);
        getRetrofitService().getDealerships(filters).enqueue(getCallback(responseListener));
    }

    public void getListings(
            final ResponseListener<ListingsDataGson> responseListener,
            @Nullable final String listingId,
            @Nullable final String dealershipId,
            @Nullable final String modelAndName
    ) {
        Map<String, String> filters = new HashMap<>();
        if (listingId != null)
            filters.put("listingId", listingId);
        if (dealershipId != null)
            filters.put("dealershipId", dealershipId);
        if (modelAndName != null)
            filters.put("modelAndName", modelAndName);

        Log.d(TAG, filters.toString());
        getRetrofitService().getListings(filters).enqueue(getCallback(responseListener));
    }

    public void createListing(
            final ResponseListener<GsonData> responseListener,
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
        fields.put("vehicleType", RequestBody.create(MediaType.parse("text/plain"), vehicleType));

        getRetrofitService().createListing(imagePart, fields).enqueue(getCallback(responseListener));
    }

    public void updateBuyerProfile(
            final ResponseListener<UserDataGson> responseListener,
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

        getRetrofitService().updateBuyerProfile(imagePart, fields).enqueue(getCallback(responseListener));
    }

    public void registerBuyer(
            final ResponseListener<GsonData> responseListener,
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
        getRetrofitService().buyerRegister(fields).enqueue(getCallback(responseListener));
    }

    public void getUserProfile(
            ResponseListener<UserDataGson> responseListener,
            final String userId
    ) {
        getRetrofitService().getUserProfile(userId).enqueue(getCallback(responseListener));
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
                ErrorGson body = null;
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
    }

    ;
}