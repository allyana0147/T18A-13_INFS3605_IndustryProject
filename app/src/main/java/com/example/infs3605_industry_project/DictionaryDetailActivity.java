package com.example.infs3605_industry_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class DictionaryDetailActivity extends AppCompatActivity {

    public static final String INTENT_LANGUAGE = "LANGUAGE";
    private TextView tvLanguage;
    private RecyclerView mRecyclerView;
    private DictionaryAdapter mAdapter;
    private TextView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_detail);

        Intent intent = getIntent();
        String language = intent.getStringExtra(INTENT_LANGUAGE);
        tvLanguage = findViewById(R.id.tv_dictionary_detail_language);
        tvLanguage.setText(language);

        //create recycler view
        mRecyclerView = findViewById(R.id.rv_dictionary_detail);
        mRecyclerView.setHasFixedSize(true);

        new FirebaseDatabaseHelper().readDictionary(new FirebaseDatabaseHelper.MyCallbackDictionary() {
            @Override
            public void onCallback(List<Dictionary> dictionaryList) {

                DictionaryAdapter.RecyclerViewClickListener listener = new DictionaryAdapter.RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, String id) {

                    }

                };

                mAdapter = new DictionaryAdapter(DictionaryDetailActivity.this, dictionaryList, listener);
                mRecyclerView.setAdapter(mAdapter);

            }

        });


        //initialise search field
        mSearchView = findViewById(R.id.search_view_dictionary_detail);
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
}