package com.example.infs3605_industry_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ViewRewardsActivity extends AppCompatActivity {

    private RecyclerView rvRewards;
    private RecyclerView mRecyclerView;
    private RewardsAdapter mAdapter;

    //shared preference to save and share user email to each pages
    SharedPreferences sharedPreferences;
    private static final String SP_EMAIL = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rewards);

        //shared preference to save and share user email to each pages
        sharedPreferences = getSharedPreferences(SP_EMAIL, MODE_PRIVATE);
        String sp_email = sharedPreferences.getString(SP_EMAIL, null);

        //create recycler view
        mRecyclerView = findViewById(R.id.rv_view_rewards);
        mRecyclerView.setHasFixedSize(true);

        new FirebaseDatabaseHelper().readRewards(new FirebaseDatabaseHelper.MyCallbackReward() {
            @Override
            public void onCallback(List<Reward> rewardList) {
                List<Reward> rewardListBasedOnUser = new ArrayList<>();
                rewardListBasedOnUser = Reward.getRewardBasedOnUser(sp_email, rewardList);


                RewardsAdapter.RecyclerViewClickListener listener = new RewardsAdapter.RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, String id) {

                    }

                };

                mAdapter = new RewardsAdapter(ViewRewardsActivity.this, rewardListBasedOnUser, listener);
                mRecyclerView.setAdapter(mAdapter);

            }

        });

    }
}