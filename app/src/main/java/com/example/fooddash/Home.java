package com.example.fooddash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fooddash.adapter.MenuAdapter;
import com.example.fooddash.adapter.PopularAdapter;
import com.example.fooddash.adapter.RecommendedAdapter;
import com.example.fooddash.model.Menu;
import com.example.fooddash.model.Popular;
import com.example.fooddash.model.Recommended;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    RecyclerView popularRecyclerView, recommendedRecyclerView, menuRecycleView;

    PopularAdapter popularAdapter;
    RecommendedAdapter recommendedAdapter;
    MenuAdapter menuAdapter;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // List<FoodData> foodDataList = null;
        List<Popular> popularFood = new ArrayList<>();
        popularFood.add(new Popular("Steak","$20","5","35 min","3.50","Extra Spicy"));
        getPopularData(popularFood);
        List <Recommended> recommended = new ArrayList<>();
        recommended.add(new Recommended("Steak","$20","5","35 min","3.50","Extra Spicy"));
        getRecommendedData(recommended);
        List <Menu> menus = new ArrayList<>();
        menus.add(new Menu("Steak","$20","5","35 min","3.50","Extra Spicy"));
        getMenu(menus);


//        getMenu(menus);
//        getPopularData(foodDataList.get(0).getPopular());
//
//        getRecommendedData(foodDataList.get(0).getRecommended());

        signUpButton = (Button)findViewById(R.id.signUp);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });
    }

    public void openNewActivity(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private void  getMenu(List <Menu> menuList){

        menuRecycleView = findViewById(R.id.menu_recycler);
        menuAdapter = new MenuAdapter(this, menuList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        menuRecycleView.setLayoutManager(layoutManager);
        menuRecycleView.setAdapter(menuAdapter);
        menuAdapter.notifyDataSetChanged();

    }
    private void  getPopularData(List<Popular> popularList){

        popularRecyclerView = findViewById(R.id.popular_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        popularAdapter = new PopularAdapter(this, popularList);
        popularRecyclerView.setLayoutManager(layoutManager);
        popularRecyclerView.setAdapter(popularAdapter);

    }

    private void  getRecommendedData(List<Recommended> recommendedList){

        recommendedRecyclerView = findViewById(R.id.recommended_recycler);
        recommendedAdapter = new RecommendedAdapter(this, recommendedList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recommendedRecyclerView.setLayoutManager(layoutManager);
        recommendedRecyclerView.setAdapter(recommendedAdapter);

    }

}