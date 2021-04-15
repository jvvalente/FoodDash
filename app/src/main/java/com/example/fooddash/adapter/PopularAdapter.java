package com.example.fooddash.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.fooddash.model.Popular;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {

    private Context context;
    private List<Popular> popularList;

    public PopularAdapter(Context context, List<Popular> popularList) {
        this.context = context;
        this.popularList = popularList;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.popular, parent, false);

        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, final int position) {

        //Used to open and set image
        try {
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //Gets URL and sets bitmap
                URL newurl = new URL(popularList.get(position).getImageUrl());
                Bitmap imagePic = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                holder.popularImage.setImageBitmap(imagePic);

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Sets info on the correct data bindings
        holder.popularName.setText(popularList.get(position).getName());
        holder.popularPrice.setText(popularList.get(position).getPrice());
        holder.popularRating.setText(popularList.get(position).getRating());

        //Sends back the correct data
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FoodDetails.class);
                i.putExtra("name", popularList.get(position).getName());
                i.putExtra("price", popularList.get(position).getPrice());
                i.putExtra("rating", popularList.get(position).getRating());
                i.putExtra("description", popularList.get(position).getNote());
                i.putExtra("image", popularList.get(position).getImageUrl());

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return popularList.size();
    }

    public  static class PopularViewHolder extends RecyclerView.ViewHolder{

        ImageView popularImage;
        TextView popularName,popularRating,popularPrice;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);

            popularName = itemView.findViewById(R.id.popular_name);
            popularImage = itemView.findViewById(R.id.popular_img);
            popularRating = itemView.findViewById(R.id.popular_rating);
            popularPrice = itemView.findViewById(R.id.popular_price);

        }
    }
}
