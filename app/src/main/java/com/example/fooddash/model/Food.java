package com.example.fooddash.model;

import androidx.annotation.NonNull;

import com.example.fooddash.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class Food {

    private String foodName;
    private double foodPrice;
    private double foodRating;
    private String foodType;
    private String deliveryTime;
    private double deliveryCharges;
    private String foodNote;
    private String foodImageUrl;

    public Food() { }

    public Food(String foodName, double foodPrice, double foodRating, String foodType, String deliveryTime, double deliveryCharges, String foodNote, String foodImageUrl) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodRating = foodRating;
        this.foodType = foodType;
        this.deliveryTime = deliveryTime;
        this.deliveryCharges = deliveryCharges;
        this.foodNote = foodNote;
        this.foodImageUrl = foodImageUrl;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public double getFoodRating() {
        return foodRating;
    }

    public void setFoodRating(double foodRating) {
        this.foodRating = foodRating;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public double getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(double deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getFoodNote() {
        return foodNote;
    }

    public void setFoodNote(String foodNote) {
        this.foodNote = foodNote;
    }

    public String getFoodImageUrl() {
        return foodImageUrl;
    }

    public void setFoodImageUrl(String foodImageUrl) {
        this.foodImageUrl = foodImageUrl;
    }

    public void loadItems(){

        Food f1 = new Food("Cheeseburger",5.99, 5,"American","35 min", 3.50, "All american dish", "https://www.sbs.com.au/food/sites/sbs.com.au.food/files/styles/full/public/lotus-burger-lead.jpg?itok=HQnBHNu2&mtime=1590410336");
        Food f2 = new Food("Cheese pizza", 8.99, 4.89, "Italian", "35 min", 3.50, "Famous italian dish", "https://images.contentstack.io/v3/assets/blt068dbc54bf4fc7ed/bltf70df0a3f7b98d04/5db5c829e9effa6ba52972ea/Triple_Cheese_Pizza_v2.jpg");
        Food f3 = new Food("Tonkotsy ramen", 9.99, 4.95, "Asian", "35 min", 3.50, "Amazing asian dish", "https://glebekitchen.com/wp-content/uploads/2017/04/tonkotsuramenfront.jpg");

        List <Food>foodList = new ArrayList();

        foodList.add(f1);
        foodList.add(f2);
        foodList.add(f3);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference food = database.getReference("Food");


        for(Food f : foodList) {
            food.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    food.child(f.getFoodName()).setValue(f);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }



}
