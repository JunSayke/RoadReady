package com.example.roadready.classes.retrofit;

import androidx.annotation.Nullable;


import com.example.roadready.classes.model.gson.DealershipsDataGson;
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.LoginDataGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitService {
    @FormUrlEncoded
    @POST("user/login")
    Call<SuccessGson<LoginDataGson>> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("buyer/register")
    Call<SuccessGson<Void>> register(
            @Field("email") String email,
            @Field("password") String password,
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("phoneNumber") String phoneNumber,
            @Field("gender") String gender,
            @Field("address") String address
    );

    @FormUrlEncoded
    @PUT("user/profile")
    Call< SuccessGson<Void> > updateProfile(
            @Field("firstName") @Nullable String firstName,
            @Field("lastName") @Nullable String lastName,
            @Field("phoneNumber") @Nullable String phoneNumber,
            @Field("gender") @Nullable String gender,
            @Field("address") @Nullable String address
    );

    @GET("dealership")
    Call< SuccessGson<DealershipsDataGson> > getDealerships(
            @Query("dealershipId") @Nullable String dealershipId,
            @Query("dealershipName") @Nullable String dealershipName
    );

    @GET("listing")
    Call< SuccessGson<ListingsDataGson> > getListings(
            @Query("listingId") @Nullable String listingId,
            @Query("dealershipId") @Nullable String dealershipId,
            @Query("dealershipAgentId") @Nullable String dealershipAgentId
    );
}