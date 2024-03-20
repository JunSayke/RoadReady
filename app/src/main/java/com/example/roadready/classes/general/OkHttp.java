package com.example.roadready.classes.general;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttp {
    private static final String TAG = "NetworkUtils";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS).build();

    public static void addRequest(Context context, String method, String url, Map<String, String> headers, JSONObject data,
                                  Callback callback) {
        Log.d(TAG, "Preparing request . . .");

        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .method(method, (data != null) ? RequestBody.create(data.toString(), JSON) : null);

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        Request request = requestBuilder.build();

        Log.d(TAG, "Pushing request . . .");
        client.newCall(request).enqueue(callback);
        Log.d(TAG, "Executing request . . .");
    }

    public static void post(Context context, String url, JSONObject data, Callback callback) {
        post(context, url, null, data, callback);
    }

    public static void post(Context context, String url, Map<String, String> headers, JSONObject data, Callback callback) {
        addRequest(context, "POST", url, headers, data, callback);
    }

    public static void get(Context context, String url, Callback callback) {
        get(context, url, null, callback);
    }

    public static void get(Context context, String url, Map<String, String> headers, Callback callback) {
        addRequest(context, "GET", url, headers, null, callback);
    }
}
