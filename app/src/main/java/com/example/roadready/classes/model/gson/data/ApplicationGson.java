package com.example.roadready.classes.model.gson.data;

import com.example.roadready.classes.model.gson.GsonData;
import com.google.gson.annotations.SerializedName;

public class ApplicationGson extends GsonData {
    private String id;
    @SerializedName("buyer")
    private String buyerId;
    @SerializedName("listing")
    private String listingId;
    @SerializedName("agent")
    private String agentId;
    private String status;
    private int progress;
    @SerializedName("applicationType")
    private String applicationType;
    @SerializedName("firstname")
    private String firstName;
    @SerializedName("lastname")
    private String lastName;
    private String address;
    @SerializedName("phonenumber")
    private String phoneNumber;
    @SerializedName("validid")
    private String validIdUrl;
    @SerializedName("signature")
    private String signatureUrl;
    @SerializedName("comakerfirstname")
    private String coMakerFirstName;
    @SerializedName("comakerlasttname")
    private String coMakerLastName;
    @SerializedName("comakeraddress")
    private String coMakerAddress;
    @SerializedName("comakerphonenumber")
    private String coMakerPhoneNumber;
    @SerializedName("comakervalidid")
    private String coMakerValidIdUrl;
    @SerializedName("comakersignature")
    private String coMakerSignatureUrl;
    @SerializedName("createdat")
    private String createdAt;
    @SerializedName("updatedat")
    private String updatedAt;
    @SerializedName("applicationpdf")
    private String applicationPdfUrl;

    public String getId() {
        return id;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public String getListingId() {
        return listingId;
    }

    public String getAgentId() {
        return agentId;
    }

    public String getStatus() {
        return status;
    }

    public int getProgress() {
        return progress;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getValidIdUrl() {
        return validIdUrl;
    }

    public String getSignatureUrl() {
        return signatureUrl;
    }

    public String getCoMakerFirstName() {
        return coMakerFirstName;
    }

    public String getCoMakerLastName() {
        return coMakerLastName;
    }

    public String getCoMakerAddress() {
        return coMakerAddress;
    }

    public String getCoMakerPhoneNumber() {
        return coMakerPhoneNumber;
    }

    public String getCoMakerValidIdUrl() {
        return coMakerValidIdUrl;
    }

    public String getCoMakerSignatureUrl() {
        return coMakerSignatureUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getApplicationPdfUrl() {
        return applicationPdfUrl;
    }
}
