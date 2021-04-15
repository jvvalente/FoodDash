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
import com.example.fooddash.model.Menu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.AllMenuViewHolder> {

    Context context;
    List<Menu> menuList;

    Bitmap imagePic;

    public MenuAdapter(Context context, List<Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public AllMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu, parent, false);

        return new AllMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllMenuViewHolder holder, final int position) {

        //Used to open and set image
        try {
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //opens URL and sets bitmap
                URL newurl = new URL(menuList.get(position).getImageUrl());
                imagePic = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                holder.menuImage.setImageBitmap(imagePic);

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Sets correct data to correct binding
        holder.menuName.setText(menuList.get(position).getName());
        holder.menuPrice.setText(menuList.get(position).getPrice());
        holder.menuTime.setText(menuList.get(position).getDeliveryTime());
        holder.menuRating.setText(menuList.get(position).getRating());
        holder.menuCharges.setText(menuList.get(position).getDeliveryCharges());
        holder.menuDetail.setText(menuList.get(position).getNote());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Sends correct data back
                Intent i = new Intent(context, FoodDetails.class);
                i.putExtra("name", menuList.get(position).getName());
                i.putExtra("price", menuList.get(position).getPrice());
                i.putExtra("rating", menuList.get(position).getRating());
                i.putExtra("description", menuList.get(position).getNote());
                i.putExtra("image", menuList.get(position).getImageUrl());

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class AllMenuViewHolder extends RecyclerView.ViewHolder{

        TextView menuName, menuDetail, menuRating, menuTime, menuCharges, menuPrice;
        ImageView menuImage;

        public AllMenuViewHolder(@NonNull View itemView) {
            super(itemView);

            menuName = itemView.findViewById(R.id.menu_name);
            menuDetail = itemView.findViewById(R.id.menu_note);
            menuCharges = itemView.findViewById(R.id.menu_delivery_charge);
            menuRating = itemView.findViewById(R.id.menu_rating);
            menuTime = itemView.findViewById(R.id.menu_deliverytime);
            menuPrice = itemView.findViewById(R.id.menu_price);
            menuImage = itemView.findViewById(R.id.menu_image);
        }
    }

}

