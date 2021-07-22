package com.example.infs3605_industry_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.UUID;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    //initialise sign up fields
    private EditText editTextName, editTextEmail, editTextPassword, editTextConfirmPassword, editTextLocation, editTextLanguage;
    private ProgressBar progressBar;
    private CheckBox checkBoxUserType;
    private Button signup;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    //shared preference to save and share user email to each pages
    private SharedPreferences sharedPreferences;
    private static final String SP_EMAIL = "mypref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("User");

        editTextName = (EditText) findViewById(R.id.et_signup_name);
        editTextEmail = (EditText) findViewById(R.id.et_signup_email);
        editTextPassword = (EditText) findViewById(R.id.et_signup_password);
        editTextConfirmPassword = (EditText) findViewById(R.id.et_signup_confirm_password);
        editTextLocation = (EditText) findViewById(R.id.et_signup_location);
        editTextLanguage = (EditText) findViewById(R.id.et_signup_language);
        progressBar = (ProgressBar) findViewById(R.id.pb_signup);
        progressBar.setVisibility(View.INVISIBLE);
        checkBoxUserType = (CheckBox) findViewById(R.id.cb_signup_usertype);

        sharedPreferences = getSharedPreferences(SP_EMAIL, MODE_PRIVATE);

        signup = (Button) findViewById(R.id.bt_signup);
        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_signup:
                registerUser();
                break;

        }
    }

    private void registerUser(){
        String user_type;
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().toLowerCase().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        String language = editTextLanguage.getText().toString().trim();

        if (checkBoxUserType.isChecked()){
            user_type = "aboriginal";
        }else{
            user_type = "non-aboriginal";
        }


        if(name.isEmpty()){
            editTextName.setError("Name is required!");
            editTextName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Minimum password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }
        if(confirmPassword.isEmpty()){
            editTextConfirmPassword.setError("Confirm Password is required!");
            editTextConfirmPassword.requestFocus();
            return;
        }
        if(!confirmPassword.equals(password)){
            editTextConfirmPassword.setError("Ensure password matches!");
            editTextConfirmPassword.requestFocus();
            return;
        }
        if(location.isEmpty()){
            editTextLocation.setError("Location is required!");
            editTextLocation.requestFocus();
            return;
        }
        if(language.isEmpty()){
            editTextLanguage.setError("Language is required!");
            editTextLanguage.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User (email, password, name, user_type, location, language);

                            //Add empty profile for new users
                            //get unique id for profile
                            String profileID = UUID.randomUUID().toString().replace("-", "");
                            //create new experience object
                            Profile profile = new Profile ();
                            profile.setProfile_id(profileID);
                            profile.setUser_id(email);
                            profile.setUser_name(name);
                            profile.setLocation(location);
                            profile.setNo_of_posts("0");
                            profile.setNo_of_followers("0");
                            profile.setTotal_points("0");
                            profile.setNo_of_points("0");
                            profile.setNo_rewards_redeemed("0");

                            //add new experience object to database
                            new FirebaseDatabaseHelper().addNewProfile(profileID, profile);


                            //add user into Firebase
                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                        launchHomeActivity();
                                        progressBar.setVisibility(View.GONE);

                                        //redirect to login layout!


                                    }else{
                                        Toast.makeText(SignUpActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(SignUpActivity.this, "Failed to register!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
    }

    //launch home page
    private void launchHomeActivity() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SP_EMAIL, editTextEmail.getText().toString());
        editor.apply();

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }


}