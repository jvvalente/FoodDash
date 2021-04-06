package com.example.fooddash.model;

public class Restaurant {

    String restaurantName;
    String restaurantType;
    String restaurantOpenTime;
    String restaurantCloseTime;
    Double restaurantLatitude;
    Double restaurantLongitude;

    public Restaurant(String restaurantName, String restaurantType, String restaurantOpenTime, String restaurantCloseTime, Double restaurantLatitude, Double restaurantLongitude) {
        this.restaurantName = restaurantName;
        this.restaurantType = restaurantType;
        this.restaurantOpenTime = restaurantOpenTime;
        this.restaurantCloseTime = restaurantCloseTime;
        this.restaurantLatitude = restaurantLatitude;
        this.restaurantLongitude = restaurantLongitude;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
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
