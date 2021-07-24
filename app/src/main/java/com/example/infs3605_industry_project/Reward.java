package com.example.infs3605_industry_project;

import java.util.ArrayList;
import java.util.List;

public class Reward {

    public String reward_id, user_name, reward;

    public Reward(){

    }

    public Reward(String reward_id, String user_name, String reward) {
        this.reward_id = reward_id;
        this.user_name = user_name;
        this.reward = reward;
    }

    public String getReward_id() {
        return reward_id;
    }

    public void setReward_id(String reward_id) {
        this.reward_id = reward_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public static List<Reward> getRewardBasedOnUser(String user_name, List<Reward> rewardList) {
        List<Reward> rewardListBasedOnUser = new ArrayList<>();
        for(final Reward reward : rewardList) {
            if (reward.getUser_name().equals(user_name)) {
                rewardListBasedOnUser.add(reward);
            }
        }
        return rewardListBasedOnUser;
    }
}
