package com.example.infs3605_industry_project;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
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

import java.io.ByteArrayOutputStream;


public class NewPostActivity extends AppCompatActivity {


    //shared preference to save and share user email to each pages
    SharedPreferences sharedPreferences;
    private static final String SP_EMAIL = "mypref";

    //initialise variables
    private ImageView ivCamera, ivPhoto, ivVideo;
    public Uri imageUri, videoUri, cameraUri;

    // Create a Cloud Storage reference from the app
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    // Create link to real time database
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Post");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        //shared preference to save and share user email to each pages
        sharedPreferences = getSharedPreferences(SP_EMAIL, MODE_PRIVATE);
        String sp_email = sharedPreferences.getString(SP_EMAIL, null);

        ivCamera = findViewById(R.id.iv_new_post_camera);
        ivPhoto = findViewById(R.id.iv_new_post_photo);
        ivVideo = findViewById(R.id.iv_new_post_video);


        //choose photo
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 1);

            }
        });

        //choose video
        ivVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("video/*");
                startActivityForResult(galleryIntent, 2);

            }
        });

        //use camera
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 3);
            }
        });


    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            Intent intent = new Intent(this, UploadActivity.class);
            intent.putExtra("imageUri", imageUri);
            startActivity(intent);

        }

        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            videoUri = data.getData();

            Intent intent = new Intent(this, UploadActivityVideo.class);
            intent.putExtra("videoUri", videoUri);
            startActivity(intent);

        }

        if (requestCode == 3 && resultCode == RESULT_OK) {
            Bitmap b = (Bitmap)data.getExtras().get("data");
            cameraUri = getImageUri(this, b);
            Intent intent = new Intent(this, UploadActivity.class);
            intent.putExtra("imageUri", cameraUri);
            startActivity(intent);

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
