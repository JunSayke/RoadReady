package com.example.roadready.classes.retrofit;

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
import retrofit2.http.HTTP;
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
    @POST("manager/register")
    Call<SuccessGson<GsonData>> dealershipRegister(
            @Part @Nullable MultipartBody.Part dealershipImage,
            @PartMap Map<String, RequestBody> fields
    );

    @Multipart
    @PUT("user/profile")
    Call<SuccessGson<UserDataGson>> updateUserProfile(
            @Part @Nullable MultipartBody.Part profileImage,
            @PartMap Map<String, RequestBody> fields
    );

    @GET("user/profile")
    Call<SuccessGson<UserDataGson>> getUserProfile(
            @Query("user_id") String userId
    );

    @GET("dealerships")
    Call<SuccessGson<DealershipsDataGson>> getDealerships(
            @QueryMap Map<String, String> filters
    );

    @Multipart
    @POST("buyer/listings/apply")
    Call<SuccessGson<ApplicationDataGson>> applyForListing(
            @Part MultipartBody.Part validIdImage,
            @Part MultipartBody.Part signatureImage,
            @Part @Nullable MultipartBody.Part coMakerValidIdImage,
            @Part @Nullable MultipartBody.Part coMakerSignatureImage,
            @Part @Nullable MultipartBody.Part backCertificateImage,
            @PartMap Map<String, RequestBody> fields
    );

    @FormUrlEncoded
    @PUT("agent/application")
    Call<SuccessGson<ApplicationDataGson>> updateApplication(
            @Field("applicationType") String applicationType,
            @Field("applicationId") String applicationId,
            @Field("progress") int progress
    );

    @GET("buyer/applications")
    Call<SuccessGson<ApplicationsDataGson>> getBuyerApplications();

    @GET("dealership/applications")
    Call<SuccessGson<ApplicationsDataGson>> getDealershipApplications();

    @GET("dealership/listings")
    Call<SuccessGson<ListingsDataGson>> getListings(
            @QueryMap Map<String, String> filters
    );

    @Multipart
    @POST("manager/listings")
    Call<SuccessGson<ListingsDataGson>> createListing(
            @Part @Nullable MultipartBody.Part listingImage,
            @PartMap Map<String, RequestBody> fields
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "manager/listings", hasBody = true)
    Call<SuccessGson<GsonData>> deleteListing(
            @Field("listingId") String listingId
    );

    @Multipart
    @PUT("manager/listings")
    Call<SuccessGson<ListingsDataGson>> updateListing(
            @Part @Nullable MultipartBody.Part listingImage,
            @PartMap Map<String, RequestBody> fields
    );

    @GET("auth/google")
    Call<SuccessGson<GoogleAuthGson>> getGoogleAuth();

    @GET("user/otp")
    Call<SuccessGson<GsonData>> requestOTP();

    @FormUrlEncoded
    @POST("buyer/verify")
    Call<SuccessGson<UserDataGson>> verifyBuyerOTP(
            @Field("code") String code
    );

    @GET("dealership/modeofpayments")
    Call<SuccessGson<ModeOfPaymentDataGson>> getModeOfPayments(
        @Query("dealership_id") String dealershipId
    );

    @GET("user/notifications")
    Call<SuccessGson<NotificationsDataGson>> getNotification();

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "user/notifications", hasBody = true)
    Call<SuccessGson<GsonData>> deleteNotification(
            @Field("notification_id") String notificationId
    );
}