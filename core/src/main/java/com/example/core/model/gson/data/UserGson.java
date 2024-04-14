package com.example.core.model.gson.data;

import com.example.core.model.gson.GsonData;
import com.google.gson.annotations.SerializedName;

public class UserGson extends GsonData {
    private String id;
    @SerializedName("profileimage")
    private String profileImage;
    @SerializedName("firstname")
    private String firstName;
    @SerializedName("lastname")
    private String lastName;
    @SerializedName("phonenumber")
    private String phoneNumber;
    private String address;
    private String gender;
    @SerializedName("createdat")
    private String createdAt;
    @SerializedName("updatedat")
    private String updatedAt;
    private String role;
    @SerializedName("isapproved")
    private boolean isApproved;
    private String token;
    private String email;
    private DealershipGson dealership;

    public String getId() {
        return id;
    }
    public String getProfileImage() { return profileImage; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getRole() {
        return role;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public DealershipGson getDealership() {
        return dealership;
    }
}
