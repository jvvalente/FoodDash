package com.example.fooddash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


import com.example.fooddash.adapter.PopularAdapter;
import com.example.fooddash.adapter.RecommendedAdapter;
import com.example.fooddash.model.Menu;
import com.example.fooddash.adapter.MenuAdapter;
import com.example.fooddash.model.Popular;
import com.example.fooddash.model.Recommended;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);

    }

}