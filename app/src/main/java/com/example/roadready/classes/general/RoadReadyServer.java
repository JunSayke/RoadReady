package com.example.roadready.classes.general;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.roadready.classes.model.gson.DealershipsDataGson;
import com.example.roadready.classes.model.gson.GsonData;
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.UserDataGson;
import com.example.roadready.classes.model.gson.response.ErrorGson;
import com.example.roadready.classes.model.gson.response.ResponseGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.classes.retrofit.RetrofitFacade;
import com.google.gson.Gson;

import java.io.File;
import java.net.HttpCookie;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
            filters.put("dealershipId", dealershipId);
        if (dealershipName != null)
            filters.put("dealershipName", dealershipName);
        getRetrofitService().getDealerships(filters).enqueue(callback);
    }

    public void getListings(
            final Callback<SuccessGson<ListingsDataGson>> callback,
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
        getRetrofitService().getListings(filters).enqueue(callback);
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

        getRetrofitService().updateBuyerProfile(imagePart, fields).enqueue(callback);
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

    public void addCookies(Set<String> cookies) {
        RetrofitFacade.cookies.addAll(cookies);
    }

    public void removeCookies(Set<String> cookies) {
        RetrofitFacade.cookies.removeAll(cookies);
    }

    public Set<String> parseCookies(String cookies) {
        Log.d(TAG, cookies);
        List<HttpCookie> parseCookies = HttpCookie.parse(cookies);
        Set<String> output = new HashSet<>();
        for (HttpCookie cookie : parseCookies) {
            output.add(cookie.toString());
        }
        Log.d(TAG, output.toString());
        return output;
    }

    public String getCookies() {
        String cookies = RetrofitFacade.cookies.toString();
        return cookies.substring(1, cookies.length() - 1);
    }

    public Set<String> getParseCookies() {
        return parseCookies(getCookies());
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
                        Log.d(TAG, String.valueOf(body));

                        @SuppressWarnings("unchecked")
                        T1 data = (T1) ((SuccessGson<?>) body).getData();
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
    };
}


