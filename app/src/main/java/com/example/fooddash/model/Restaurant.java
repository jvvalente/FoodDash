package com.example.fooddash.model;

public class Restaurant {

    String restaurantName;
    String restaurantLogoUrl;
    String restaurantType;
    String restaurantOpenTime;
    String restaurantCloseTime;
    String restaurantAddress;
    Double restaurantLatitude;
    Double restaurantLongitude;

    public Restaurant(String restaurantName, String restaurantLogoUrl, String restaurantType, String restaurantOpenTime, String restaurantCloseTime, String restaurantAddress, Double restaurantLatitude, Double restaurantLongitude) {
        this.restaurantName = restaurantName;
        this.restaurantLogoUrl = restaurantLogoUrl;
        this.restaurantType = restaurantType;
        this.restaurantOpenTime = restaurantOpenTime;
        this.restaurantCloseTime = restaurantCloseTime;
        this.restaurantAddress = restaurantAddress;
        this.restaurantLatitude = restaurantLatitude;
        this.restaurantLongitude = restaurantLongitude;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantLogoUrl() {
        return restaurantLogoUrl;
    }

    public void setRestaurantLogoUrl(String restaurantLogoUrl) {
        this.restaurantLogoUrl = restaurantLogoUrl;
    }

    public String getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(String restaurantType) {
        this.restaurantType = restaurantType;
    }

    public String getRestaurantOpenTime() {
        return restaurantOpenTime;
    }

    public void setRestaurantOpenTime(String restaurantOpenTime) {
        this.restaurantOpenTime = restaurantOpenTime;
    }

    public String getRestaurantCloseTime() {
        return restaurantCloseTime;
    }

    public void setRestaurantCloseTime(String restaurantCloseTime) {
        this.restaurantCloseTime = restaurantCloseTime;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public Double getRestaurantLatitude() {
        return restaurantLatitude;
    }

    public void setRestaurantLatitude(Double restaurantLatitude) {
        this.restaurantLatitude = restaurantLatitude;
    }

    public Double getRestaurantLongitude() {
        return restaurantLongitude;
    }

    public void setRestaurantLongitude(Double restaurantLongitude) {
        this.restaurantLongitude = restaurantLongitude;
    }

}
