package com.example.core.model.gson.data;

import com.example.core.model.gson.GsonData;
import com.google.gson.annotations.SerializedName;

public class VehicleGson extends GsonData {
    private String id;
    @SerializedName("modelandname")
    private String modelAndName;
    @SerializedName("make")
    private String brand;
    @SerializedName("fueltype")
    private String fuelType;
    private String power;
    private String transmission;
    private String engine;
    @SerializedName("fueltankcapacity")
    private String fuelTankCapacity;
    @SerializedName("seatingcapacity")
    private String seatingCapacity;
    private int price;
    @SerializedName("vehicletype")
    private String vehicleType;
    private String image;
    @SerializedName("dealership")
    private DealershipGson dealershipGson;
    @SerializedName("createdat")
    private String createdAt;
    @SerializedName("updatedat")
    private String updatedAt;
    @SerializedName("isavailable")
    private boolean isAvailable;

    public String getId() {
        return id;
    }

    public String getModelAndName() {
        return modelAndName;
    }

    public String getBrand() {
        return brand;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getPower() {
        return power;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getEngine() {
        return engine;
    }

    public String getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    public String getSeatingCapacity() {
        return seatingCapacity;
    }

    public int getPrice() {
        return price;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getImage() {
        return image;
    }

    public DealershipGson getDealershipGson() {
        return dealershipGson;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}