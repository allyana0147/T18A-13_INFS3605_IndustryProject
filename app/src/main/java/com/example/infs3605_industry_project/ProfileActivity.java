package com.example.infs3605_industry_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ProgressBar;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    //shared preference to save and share user email to each pages
    SharedPreferences sharedPreferences;
    private static final String SP_EMAIL = "mypref";

    //initialise variables
    private TextView tvName, tvLocation, tvNoPost, tvNoFollowers, tvTotalPoints, tvCurrentPoints, tvRewardsRedeemed, tvTextRedeem;
    private ImageView btEdit, btPrivacy, btHelp, btLogout;
    private Button btRedeemRewards, btViewRewards;
    private ProgressBar pbRewards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //shared preference to save and share user email to each pages
        sharedPreferences = getSharedPreferences(SP_EMAIL, MODE_PRIVATE);
        String sp_email = sharedPreferences.getString(SP_EMAIL, null);

        tvName = findViewById(R.id.tv_profile_name);
        tvLocation = findViewById(R.id.tv_profile_location);
        tvNoPost = findViewById(R.id.tv_profile_no_posts);
        tvNoFollowers = findViewById(R.id.tv_profile_no_followers);
        tvTotalPoints = findViewById(R.id.tv_profile_total_points);
        tvCurrentPoints = findViewById(R.id.tv_profile_no_points);
        tvRewardsRedeemed = findViewById(R.id.tv_profile_no_rewards_redeemed);
        tvTextRedeem = findViewById(R.id.tv_profile_redeem);

        btEdit = findViewById(R.id.bt_profile_edit);
        btPrivacy = findViewById(R.id.bt_profile_privacy);
        btHelp = findViewById(R.id.bt_profile_help);
        btLogout = findViewById(R.id.bt_profile_logout);

        btRedeemRewards = findViewById(R.id.bt_profile_redeem);
        btViewRewards = findViewById(R.id.bt_profile_view_rewards);
        pbRewards = findViewById(R.id.pb_profile_rewards);


        new FirebaseDatabaseHelper().readProfile(new FirebaseDatabaseHelper.MyCallbackProfile() {
            @Override
            public void onCallback(List<Profile> profileList) {
                Profile profile = Profile.getProfile(sp_email, profileList);
                tvName.setText(profile.getUser_name());
                tvLocation.setText(profile.getLocation());
                tvNoPost.setText(profile.getNo_of_posts());
                tvNoFollowers.setText(profile.getNo_of_followers());
                tvTotalPoints.setText(profile.getTotal_points());

                int points_needed_to_redeem_rewards = 250 - Integer.parseInt(profile.getNo_of_points());
                tvCurrentPoints.setText(String.valueOf(points_needed_to_redeem_rewards));
                int progress_rewards = ((Integer.parseInt(profile.getNo_of_points()))*100)/250;
                pbRewards.setProgress(progress_rewards);

                if(points_needed_to_redeem_rewards == 0){
                    btRedeemRewards.setVisibility(View.VISIBLE);
                    tvTextRedeem.setVisibility(View.INVISIBLE);
                }

                }

        });

        new FirebaseDatabaseHelper().readRewards(new FirebaseDatabaseHelper.MyCallbackReward() {
            @Override
            public void onCallback(List<Reward> rewardList) {
                List<Reward> rewardListBasedOnUser = new ArrayList<>();
                rewardListBasedOnUser = Reward.getRewardBasedOnUser(sp_email, rewardList);

                tvRewardsRedeemed.setText(String.valueOf(rewardListBasedOnUser.size()));

            }

        });


        btRedeemRewards.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, RedeemRewardsActivity.class));

            }
        });

        btViewRewards.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ViewRewardsActivity.class));

            }
        });
    }

}