package com.example.infs3605_industry_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DictionaryDetailActivity extends AppCompatActivity {

    public static final String INTENT_LANGUAGE = "LANGUAGE";
    private TextView tvLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_detail);

        Intent intent = getIntent();
        String language = intent.getStringExtra(INTENT_LANGUAGE);
        tvLanguage = findViewById(R.id.tv_dictionary_detail_language);
        tvLanguage.setText(language);

    }
}