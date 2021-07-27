package com.example.infs3605_industry_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.AlertDialog;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    //shared preference to save and share user email to each pages
    SharedPreferences sharedPreferences;
    private static final String SP_EMAIL = "mypref";

    //initialise fields
    private TextView test_textview;
    private RecyclerView mRecyclerView;
    //private HomeAdapter mAdapter;
    private HomeAdapter mAdapter;
    private List<User> fullUserList;
    private ImageButton logoutButton,backButton;
    private Button bt_logout_yes, bt_logout_no, bt_flag_done;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //shared preference to save and share user email to each pages
        sharedPreferences = getSharedPreferences(SP_EMAIL, MODE_PRIVATE);
        String sp_email = sharedPreferences.getString(SP_EMAIL, null);

        //top tool bar
        logoutButton = findViewById(R.id.bt_top_menu_bar_log_out);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logOutDialog();
            }
        });

        //initialise fields

        //refer to https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //initialise and assign variable to bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        //perform item selectedlistener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),
                                ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.upload:
                        startActivity(new Intent(getApplicationContext(),
                                NewPostActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.dictionary:
                        startActivity(new Intent(getApplicationContext(),
                                DictionaryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        //create recycler view
        mRecyclerView = findViewById(R.id.rv_home_posts);
        mRecyclerView.setHasFixedSize(true);

        new FirebaseDatabaseHelper().readPost(new FirebaseDatabaseHelper.MyCallbackPost() {
            @Override
            public void onCallback(List<Post> postList) {


                HomeAdapter.RecyclerViewClickListener listener = new HomeAdapter.RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, String id) {

                    }

                    @Override
                    public void onAddCommentClick(View view, int position) {
                        Post post = postList.get(position);
                        String post_id = post.getPost_id();

                        Intent intent = new Intent(view.getContext(), CommentsActivity.class);
                        intent.putExtra(CommentsActivity.POST_CAPTION, post.getCaption());
                        intent.putExtra(CommentsActivity.POST_USER_NAME, post.getName());
                        intent.putExtra(CommentsActivity.POST_ID, post.getPost_id());
                        intent.putExtra(CommentsActivity.POST_LOCATION, post.getLocation());
                        startActivity(intent);
                    }

                    @Override
                    public void onAddFollowClick(View view, int position) {
                        Post post = postList.get(position);
                        String post_id = post.getPost_id();

                        //update number of followers in profile
                        new FirebaseDatabaseHelper().readProfile(new FirebaseDatabaseHelper.MyCallbackProfile() {
                            @Override
                            public void onCallback(List<Profile> profileList) {

                                Profile profile = Profile.getProfile(sp_email, profileList);
                                int new_no_followers = Integer.parseInt(profile.getNo_of_followers()) + 1;
                                new FirebaseDatabaseHelper().updateNumberOfFollowers(profile.getProfile_id(), String.valueOf(new_no_followers));

                            }

                        });

                    }

                    @Override
                    public void onAddUnFollowClick(View view, int position) {
                        Post post = postList.get(position);
                        String post_id = post.getPost_id();

                        //update number of followers in profile
                        new FirebaseDatabaseHelper().readProfile(new FirebaseDatabaseHelper.MyCallbackProfile() {
                            @Override
                            public void onCallback(List<Profile> profileList) {

                                Profile profile = Profile.getProfile(sp_email, profileList);
                                int new_no_followers = Integer.parseInt(profile.getNo_of_followers()) - 1;
                                new FirebaseDatabaseHelper().updateNumberOfFollowers(profile.getProfile_id(), String.valueOf(new_no_followers));

                            }

                        });
                    }

                    @Override
                    public void onFlag(View view, int position) {
                        Post post = postList.get(position);
                        String post_id = post.getPost_id();
                        //remove post from home after flagging
                        new FirebaseDatabaseHelper().flagPost(post_id);
                        flagDialog();

                    }

                };

                mAdapter = new HomeAdapter(HomeActivity.this, postList, listener);
                mRecyclerView.setAdapter(mAdapter);


            }

        });

    }

    public void logOutDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View logOutView = getLayoutInflater().inflate(R.layout.alert_logout, null);
        bt_logout_yes = (Button)logOutView.findViewById(R.id.bt_logout_yes);
        bt_logout_no = (Button)logOutView.findViewById(R.id.bt_logout_no);

        dialogBuilder.setView(logOutView);
        dialog = dialogBuilder.create();
        dialog.show();

        bt_logout_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_logout();
            }
        });

        bt_logout_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void user_logout(){
        SharedPreferences myPrefs = getSharedPreferences("mypref",
                MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void flagDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View flag_view = getLayoutInflater().inflate(R.layout.alert_flag, null);
        bt_flag_done = (Button)flag_view.findViewById(R.id.bt_flag_done);

        dialogBuilder.setView(flag_view);
        dialog = dialogBuilder.create();
        dialog.show();

        bt_flag_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


}