package com.example.infs3605_industry_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DictionaryActivity extends AppCompatActivity {

    private RecyclerView rvLanguage;
    private RecyclerView mRecyclerView;
    private LanguageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        //create recycler view
        mRecyclerView = findViewById(R.id.rv_dictionary_languages);
        mRecyclerView.setHasFixedSize(true);

        new FirebaseDatabaseHelper().readLanguage(new FirebaseDatabaseHelper.MyCallbackLanguage() {
            @Override
            public void onCallback(List<Language> languageList) {

                LanguageAdapter.RecyclerViewClickListener listener = new LanguageAdapter.RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, String id) {

                    }

                };

                mAdapter = new LanguageAdapter(DictionaryActivity.this, languageList, listener);
                mRecyclerView.setAdapter(mAdapter);

            }

        });
    }
}