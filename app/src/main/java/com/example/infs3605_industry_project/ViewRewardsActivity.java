package com.example.infs3605_industry_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewRewardsActivity extends AppCompatActivity {

    private RecyclerView rvRewards;
    private RecyclerView mRecyclerView;
    private RewardsAdapter mAdapter;

    private TextView mDetailReward;
    private ImageView mDetailBack;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private ImageButton logoutButton,backButton;
    private Button bt_logout_yes, bt_logout_no;


    //shared preference to save and share user email to each pages
    SharedPreferences sharedPreferences;
    private static final String SP_EMAIL = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rewards);

        //top tool bar
        logoutButton = findViewById(R.id.bt_top_menu_bar_log_out);
        backButton = findViewById(R.id.bt_top_menu_bar_back);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logOutDialog();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });


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
                        String reward = Reward.getReward(id, rewardList);
                        rewardDetail(reward);
                        

                    }

                };

                mAdapter = new RewardsAdapter(ViewRewardsActivity.this, rewardListBasedOnUser, listener);
                mRecyclerView.setAdapter(mAdapter);

            }

        });

    }

    //launch note detail pop up dialog
    public void rewardDetail(String reward_name){
        dialogBuilder = new AlertDialog.Builder(this);
        final View noteDetailView = getLayoutInflater().inflate(R.layout.activity_reward_detail, null);
        mDetailReward = (TextView) noteDetailView.findViewById(R.id.tv_detail_reward);
        mDetailBack = noteDetailView.findViewById(R.id.iv_reward_detail_back);

        dialogBuilder.setView(noteDetailView);
        dialog = dialogBuilder.create();
        dialog.show();

        mDetailReward.setText(reward_name);

        mDetailBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                dialog.dismiss();

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

}