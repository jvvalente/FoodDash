package com.example.fooddash.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddash.FoodDetails;
import com.example.fooddash.R;
import com.example.fooddash.model.Recommended;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder> {

    private Context context;
    private List<Recommended> recommendedList;

    Bitmap imagePic;

    public RecommendedAdapter(Context context, List<Recommended> recommendedList) {
        this.context = context;
        this.recommendedList = recommendedList;
    }

    @NonNull
    @Override
    public RecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommended, parent, false);
        return new RecommendedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedViewHolder holder, final int position) {

        //Used to open and set image
        try {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            URL newurl = new URL(recommendedList.get(position).getImageUrl());
            imagePic = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            holder.recommendedImage.setImageBitmap(imagePic);

        }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Sets data to correct binding
        holder.recommendedName.setText(recommendedList.get(position).getName());
        holder.recommendedRating.setText(recommendedList.get(position).getRating());
        holder.recommendedCharges.setText(recommendedList.get(position).getDeliveryCharges());
        holder.recommendedDeliveryTime.setText(recommendedList.get(position).getDeliveryTime());
        holder.recommendedPrice.setText(recommendedList.get(position).getPrice());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FoodDetails.class);
                //Sends correct data back
                i.putExtra("name", recommendedList.get(position).getName());
                i.putExtra("price", recommendedList.get(position).getPrice());
                i.putExtra("rating", recommendedList.get(position).getRating());
                i.putExtra("description", recommendedList.get(position).getNote());
                i.putExtra("image", recommendedList.get(position).getImageUrl());

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recommendedList.size();
    }

    public static class RecommendedViewHolder extends RecyclerView.ViewHolder{

        ImageView recommendedImage;
        TextView recommendedName, recommendedRating, recommendedDeliveryTime, recommendedCharges, recommendedPrice;

        public RecommendedViewHolder(@NonNull View itemView) {
            super(itemView);

            recommendedImage = itemView.findViewById(R.id.recommended_image);
            recommendedName = itemView.findViewById(R.id.recommended_name);
            recommendedRating = itemView.findViewById(R.id.recommended_rating);
            recommendedDeliveryTime = itemView.findViewById(R.id.recommended_delivery_time);
            recommendedCharges = itemView.findViewById(R.id.delivery_type);
            recommendedCharges = itemView.findViewById(R.id.delivery_type);
            recommendedPrice = itemView.findViewById(R.id.recommended_price);

        }
    }


}
