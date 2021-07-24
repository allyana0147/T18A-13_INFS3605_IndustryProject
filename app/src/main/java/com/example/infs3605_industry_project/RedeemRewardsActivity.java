package com.example.infs3605_industry_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import java.util.UUID;

public class RedeemRewardsActivity extends AppCompatActivity {

    //shared preference to save and share user email to each pages
    SharedPreferences sharedPreferences;
    private static final String SP_EMAIL = "mypref";

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button buttonRedeem, alertDone;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView tvReward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_rewards);

        //shared preference to save and share user email to each pages
        sharedPreferences = getSharedPreferences(SP_EMAIL, MODE_PRIVATE);
        String sp_email = sharedPreferences.getString(SP_EMAIL, null);

        radioGroup = findViewById(R.id.rg_rewards);
        buttonRedeem = findViewById(R.id.bt_redeem_reward);

        buttonRedeem.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

                //Add rewards to firebase
                //get unique id for reward
                String rewardID = UUID.randomUUID().toString().replace("-", "");
                //create new experience object
                Reward reward = new Reward ();
                reward.setReward(radioButton.getText().toString());
                reward.setReward_id(rewardID);
                reward.setUser_name(sp_email);


                //add new reward object to database
                new FirebaseDatabaseHelper().addReward(rewardID, reward);

                redeemAlert();

            }

        });

    }

    public void redeemAlert(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View rewardRedeemView = getLayoutInflater().inflate(R.layout.alert_redeem_rewards, null);
        alertDone = (Button)rewardRedeemView.findViewById(R.id.bt_rewards_done);
        tvReward = (TextView)rewardRedeemView.findViewById(R.id.tv_reward_redeemed);

        dialogBuilder.setView(rewardRedeemView);
        dialog = dialogBuilder.create();
        dialog.show();

        tvReward.setText(radioButton.getText());

        alertDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RedeemRewardsActivity.this, ProfileActivity.class));
            }
        });

    }

}