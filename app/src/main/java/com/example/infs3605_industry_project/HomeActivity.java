package com.example.infs3605_industry_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.AlertDialog;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    //shared preference to save and share user email to each pages
    SharedPreferences sharedPreferences;
    private static final String SP_EMAIL = "mypref";

    //initialise fields
    private TextView test_textview;
    private RecyclerView mRecyclerView;
    //private HomeAdapter mAdapter;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //shared preference to save and share user email to each pages
        sharedPreferences = getSharedPreferences(SP_EMAIL, MODE_PRIVATE);
        String sp_email = sharedPreferences.getString(SP_EMAIL, null);

        //initialise fields
        test_textview = findViewById(R.id.tv_test);
        test_textview.setText(sp_email);

        //refer to https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //initialise and assign variable to bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        //perform item selectedlistener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),
                                ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.upload:
                        startActivity(new Intent(getApplicationContext(),
                                NewPostActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.dictionary:
                        startActivity(new Intent(getApplicationContext(),
                                DictionaryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

/*        //initialise recyclerview
        mRecyclerView = findViewById(R.id.rv_home_posts);
        mRecyclerView.setHasFixedSize(true);
        HomeAdapter.RecyclerViewClickListener listener = new HomeAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, String id) {

            }
        };

        mAdapter = new HomeAdapter(new ArrayList<Post>(), listener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));*/

        //create recycler view
        mRecyclerView = findViewById(R.id.rv_home_posts);
        mRecyclerView.setHasFixedSize(true);
        HomeAdapter.RecyclerViewClickListener listener = new HomeAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, String id) {
            }
        };
        //pull movies from movie adapter to display in main activity
        mAdapter = new HomeAdapter(Post.getPosts(), listener);
        mRecyclerView.setAdapter(mAdapter);


    }

}