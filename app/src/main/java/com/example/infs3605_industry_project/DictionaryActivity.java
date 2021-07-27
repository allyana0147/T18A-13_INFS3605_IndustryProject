package com.example.infs3605_industry_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class DictionaryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LanguageAdapter mAdapter;
    private TextView mSearchView;

    private ImageButton logoutButton,backButton;
    private Button bt_logout_yes, bt_logout_no;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

//top tool bar
        logoutButton = findViewById(R.id.bt_top_menu_bar_log_out);
        backButton = findViewById(R.id.bt_top_menu_bar_back);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logOutDialog();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });

        //refer to https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //initialise and assign variable to bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.dictionary);

        //perform item selectedlistener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),
                                HomeActivity.class));
                        overridePendingTransition(0,0);
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
                        return true;
                }
                return false;
            }
        });

        //create recycler view
        mRecyclerView = findViewById(R.id.rv_dictionary_languages);
        mRecyclerView.setHasFixedSize(true);

        new FirebaseDatabaseHelper().readLanguage(new FirebaseDatabaseHelper.MyCallbackLanguage() {
            @Override
            public void onCallback(List<Language> languageList) {

                LanguageAdapter.RecyclerViewClickListener listener = new LanguageAdapter.RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, String id) {

                        String languageSelected = Language.getLanguage(id,languageList);
                        launchDetailDictionary(languageSelected);

                    }

                };

                mAdapter = new LanguageAdapter(DictionaryActivity.this, languageList, listener);
                mRecyclerView.setAdapter(mAdapter);

            }

        });

        //initialise search field
        mSearchView = findViewById(R.id.search_view_dictionary);

        mSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAdapter.getFilter().filter(s.toString());
            }
        });

    }

    //LAUNCH DETAILED VIEW DICTIONARY
    private void launchDetailDictionary(String language) {
        Intent intent = new Intent(this, DictionaryDetailActivity.class);
        intent.putExtra(DictionaryDetailActivity.INTENT_LANGUAGE, language);
        startActivity(intent);

    }

    public void logOutDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View logOutView = getLayoutInflater().inflate(R.layout.alert_logout, null);
        bt_logout_yes = (Button)logOutView.findViewById(R.id.bt_logout_yes);
        bt_logout_no = (Button)logOutView.findViewById(R.id.bt_logout_no);

        dialogBuilder.setView(logOutView);
        dialog = dialogBuilder.create();
        dialog.show();

        bt_logout_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_logout();
            }
        });

        bt_logout_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    public void user_logout(){
        SharedPreferences myPrefs = getSharedPreferences("mypref",
                MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(this, MainActivity.class));
    }


}