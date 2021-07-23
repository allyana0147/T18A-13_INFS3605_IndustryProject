package com.example.infs3605_industry_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //initialise fields
    private EditText editTextEmail, editTextPassword;
    private Button login, signup;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    //shared preference for user log in
    private SharedPreferences sharedPreferences;
    private static final String SP_EMAIL = "mypref";

    //user log in
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceUsers;
    private List<User> users = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(SP_EMAIL, MODE_PRIVATE);

        editTextEmail = findViewById(R.id.et_login_email);
        editTextPassword = findViewById(R.id.et_login_password);

        progressBar = (ProgressBar) findViewById(R.id.pb_login);
        progressBar.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();

        login = findViewById(R.id.bt_login);
        login.setOnClickListener(this);
        signup = (Button) findViewById(R.id.bt_login_signup);
        signup.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                userLogin();
                break;

            case R.id.bt_login_signup:
                startActivity(new Intent(this, SignUpActivity.class));
                break;

        }

    }

    private void userLogin() {
        String username = editTextEmail.getText().toString().toLowerCase().trim();
        String password = editTextPassword.getText().toString().trim();

        if (username.isEmpty()) {
            editTextEmail.setError("Username is required!");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Minimum password length is 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    //redirect to home page
                    launchHomeActivity();
                    progressBar.setVisibility(View.GONE);

                }
                else{
                    progressBar.setVisibility(View.GONE);
                    editTextPassword.setError("Failed to login! Please check your credentials");
                    editTextPassword.requestFocus();
                }
            }

        });


    }


    //launch home activity
    private void launchHomeActivity() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SP_EMAIL, editTextEmail.getText().toString());
        editor.apply();

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }


}