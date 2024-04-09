package com.example.roadready.classes.retrofit;

import androidx.annotation.Nullable;

import com.example.roadready.classes.model.gson.DealershipsDataGson;
import com.example.roadready.classes.model.gson.GsonData;
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.UserDataGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RetrofitService {
    @FormUrlEncoded
    @POST("user/login")
    Call<SuccessGson<UserDataGson>> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("buyer/register")
    Call<SuccessGson<GsonData>> buyerRegister(
            @FieldMap Map<String, String> fields
    );

    @Multipart
    @PUT("user/profile")
    Call<SuccessGson<UserDataGson>> updateBuyerProfile(
            @Part @Nullable MultipartBody.Part profileImage,
            @PartMap Map<String, RequestBody> fields
    );

    @GET("dealership")
    Call<SuccessGson<DealershipsDataGson>> getDealerships(
            @QueryMap Map<String, String> filters
    );

    @GET("listing")
    Call<SuccessGson<ListingsDataGson>> getListings(
            @QueryMap Map<String, String> filters
    );


    @Multipart
    @POST("manager/listing")
        // TODO:
    Call<SuccessGson<GsonData>> createListing(
            @Part @Nullable MultipartBody.Part listingImage,
            @PartMap Map<String, RequestBody> fields
    );

}