package com.example.roadready.classes.retrofit;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFacade {
    private static final String TAG = "RetrofitFacade";
    private static final Set<String> cookies = new HashSet<>();
    private final RetrofitService retrofitService;

    public RetrofitFacade(String baseUrl) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(new CookiesInterceptor())
                .addInterceptor(new RequestInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        retrofitService = retrofit.create(RetrofitService.class);
    }

    public RetrofitService getRetrofitService() {
        return retrofitService;
    }

    private static class CookiesInterceptor implements Interceptor {
        private static final String TAG = "CookiesInterceptor";
        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);

            List<String> cookies = response.headers("Set-Cookie");

            RetrofitFacade.cookies.addAll(cookies);

            Log.d(TAG, RetrofitFacade.cookies.toString());

            return response;
        }
    }

    private static class RequestInterceptor implements Interceptor {
        private static final String TAG = "RequestInterceptor";
        @NonNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder requestBuilder = chain.request().newBuilder();

            for (String cookie : RetrofitFacade.cookies) {
                requestBuilder.addHeader("Cookie", cookie);
            }

            Log.d(TAG, RetrofitFacade.cookies.toString());

            return chain.proceed(requestBuilder.build());
        }
    }
}