package com.example.infs3605_industry_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;
import java.util.UUID;

public class UploadActivityText extends AppCompatActivity {

    //shared preference to save and share user email to each pages
    SharedPreferences sharedPreferences;
    private static final String SP_EMAIL = "mypref";

    //initialise variables
    private Button bt_upload_text_post;
    private ImageView ivBack;
    private EditText etCaption, etHashtag;
    public Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_text);

        //shared preference to save and share user email to each pages
        sharedPreferences = getSharedPreferences(SP_EMAIL, MODE_PRIVATE);
        String sp_email = sharedPreferences.getString(SP_EMAIL, null);

        //initialise variables
        bt_upload_text_post = findViewById(R.id.bt_upload_text_post);
        etCaption = findViewById(R.id.et_upload_text_caption);
        etHashtag = findViewById(R.id.et_upload_text_hashtag);

        ivBack = findViewById(R.id.iv_upload_text_back);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });


        bt_upload_text_post.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (etCaption.getText().toString() != null){
                    String hashtag = "";
                    if(etHashtag.getText().toString().isEmpty()){
                        hashtag = " ";
                    }else{
                        hashtag = etHashtag.getText().toString();
                    }

                    new FirebaseDatabaseHelper().readUser(new FirebaseDatabaseHelper.MyCallbackUser() {
                        @Override
                        public void onCallback(List<User> userList) {
                            User user = User.getUser(sp_email, userList);
                            String postId = UUID.randomUUID().toString().replace("-", "");

                            Post post = new Post(postId, null, sp_email, user.getName(), user.getLocation(), etCaption.getText().toString(),etHashtag.getText().toString(), "text", "0");
                            new FirebaseDatabaseHelper().addNewPost(postId, post);

                        }
                    });

                    //update number of posts in profile
                    new FirebaseDatabaseHelper().readProfile(new FirebaseDatabaseHelper.MyCallbackProfile() {
                        @Override
                        public void onCallback(List<Profile> profileList) {

                            Profile profile = Profile.getProfile(sp_email, profileList);
                            //update no. of posts
                            int new_no_post = Integer.parseInt(profile.getNo_of_posts()) + 1;
                            new FirebaseDatabaseHelper().updateNumberOfPosts(profile.getProfile_id(), String.valueOf(new_no_post));

                            //update no.of total points
                            int new_total_points = Integer.parseInt(profile.getTotal_points()) + 50;
                            new FirebaseDatabaseHelper().updateTotalPoints(profile.getProfile_id(), String.valueOf(new_total_points));

                            //update no.of current points until rewards are redeemed
                            int current_points = Integer.parseInt(profile.getNo_of_points());
                            if(current_points==250){
                                current_points = 0;
                                new FirebaseDatabaseHelper().updateCurrentPoints(profile.getProfile_id(), "0");
                            }else {
                                current_points = current_points + 50;
                                new FirebaseDatabaseHelper().updateCurrentPoints(profile.getProfile_id(), String.valueOf(current_points));
                            }
                        }

                    });

                }else if (etCaption.getText().toString().isEmpty()){
                    etCaption.setError("Cannot add an empty note!");
                    etCaption.requestFocus();
                    return;
                }else{
                    Toast.makeText(getApplicationContext(), "Something went wrong! Please close the app and start again.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}