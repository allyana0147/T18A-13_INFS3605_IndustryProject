package com.example.infs3605_industry_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DictionaryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LanguageAdapter mAdapter;
    private TextView mSearchView;

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

}