package com.example.infs3605_industry_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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

public class UploadActivityVideo extends AppCompatActivity {

    //shared preference to save and share user email to each pages
    SharedPreferences sharedPreferences;
    private static final String SP_EMAIL = "mypref";

    //initialise variables
    private Button btPost;
    private EditText tvCaption, tvHashtag;
    private VideoView vvVideo;
    private ImageView ivBack;
    public Uri videoUri;
    MediaController mediaController;


    // Create a Cloud Storage reference from the app
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    // Create link to real time database
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Post");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);
        //shared preference to save and share user email to each pages
        sharedPreferences = getSharedPreferences(SP_EMAIL, MODE_PRIVATE);
        String sp_email = sharedPreferences.getString(SP_EMAIL, null);

        //get Video URI Intent
        Intent intent = getIntent();
        videoUri = intent.getParcelableExtra("videoUri");

        //initialise variables
        btPost = findViewById(R.id.bt_upload_video_post);
        tvCaption = findViewById(R.id.tv_upload_video_caption);
        tvHashtag  = findViewById(R.id.tv_upload_video_hashtag);
        ivBack = findViewById(R.id.iv_upload_video_back);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });

        vvVideo = findViewById(R.id.vv_upload_video);
        vvVideo.setVideoURI(videoUri);
        mediaController = new MediaController(this);
        vvVideo.setMediaController(mediaController);
        mediaController.setAnchorView(vvVideo);
        vvVideo.start();

        btPost.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (videoUri != null){
                    String hashtag = "";
                    if(tvHashtag.getText().toString().isEmpty()){
                        hashtag = " ";
                    }else{
                        hashtag = tvHashtag.getText().toString();
                    }

                    uploadToFirebase(videoUri, sp_email, tvCaption.getText().toString(), hashtag);

                    //update number of posts in profile
                    new FirebaseDatabaseHelper().readProfile(new FirebaseDatabaseHelper.MyCallbackProfile() {
                        @Override
                        public void onCallback(List<Profile> profileList) {

                            Profile profile = Profile.getProfile(sp_email, profileList);

                            //update number of posts
                            int new_no_post = Integer.parseInt(profile.getNo_of_posts()) + 1;
                            new FirebaseDatabaseHelper().updateNumberOfPosts(profile.getProfile_id(), String.valueOf(new_no_post));

                            //update no.of total points
                            int new_total_points = Integer.parseInt(profile.getTotal_points()) + 50;
                            new FirebaseDatabaseHelper().updateTotalPoints(profile.getProfile_id(), String.valueOf(new_total_points));
                        }

                    });

                } else if (tvCaption.getText().toString().isEmpty()){
                    tvCaption.setError("Cannot add an empty note!");
                    tvCaption.requestFocus();
                    return;
                }else{
                    Toast.makeText(getApplicationContext(), "Something went wrong! Please close the app and start again.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void uploadToFirebase(Uri uri, String user_name, String caption, String hashtag){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading post...");
        pd.show();

        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String modelId = root.push().getKey();
                        //Create new post

                        new FirebaseDatabaseHelper().readUser(new FirebaseDatabaseHelper.MyCallbackUser() {
                            @Override
                            public void onCallback(List<User> userList) {
                                User user = User.getUser(user_name, userList);

                                Post post = new Post(modelId,uri.toString(), user_name, user.getName(), user.getLocation(), caption, hashtag, "video", "0");
                                root.child(modelId).setValue(post);

                            }
                        });

                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Post Uploaded", Snackbar.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>(){

            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                pd.setMessage("Percentage: " + (int)progressPercent + "%");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to Upload", Toast.LENGTH_LONG).show();
            }
        });

    }

    private String getFileExtension(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

}