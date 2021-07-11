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

public class UploadActivity extends AppCompatActivity {

    public static final String INTENT_IMAGE_URI = "SAMPLE";

    //shared preference to save and share user email to each pages
    SharedPreferences sharedPreferences;
    private static final String SP_EMAIL = "mypref";

    //initialise variables
    private Button btPost;
    private ImageView ivImage;
    public Uri imageUri;


    // Create a Cloud Storage reference from the app
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    // Create link to real time database
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Post");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        //shared preference to save and share user email to each pages
        sharedPreferences = getSharedPreferences(SP_EMAIL, MODE_PRIVATE);
        String sp_email = sharedPreferences.getString(SP_EMAIL, null);

        //get Image URI Intent
        Intent intent = getIntent();
        imageUri = intent.getParcelableExtra("imageUri");

        //initialise variables
        ivImage = findViewById(R.id.iv_upload_image);
        btPost = findViewById(R.id.bt_upload_post);
        ivImage.setImageURI(imageUri);

/*        ivCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //choosePicture();

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);

            }
        });*/

        btPost.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (imageUri != null){
                    uploadToFirebase(imageUri, sp_email);

                }else{
                    Toast.makeText(UploadActivity.this, "Please Select Image", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

/*        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageUri = data.getData();
                //ivPost.setImageURI(imageUri);
            }

        }*/

    public void uploadToFirebase(Uri uri, String user_name){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Model model = new Model(uri.toString(), user_name);
                        String modelId = root.push().getKey();
                        root.child(modelId).setValue(model);

                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();
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