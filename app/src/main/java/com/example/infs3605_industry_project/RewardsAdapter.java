package com.example.infs3605_industry_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;

import java.util.List;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.RewardsViewHolder> {


    //array list of all movies
    private List<Reward> mRewards;
    private Context mContext;
    DataSnapshot dataSnapshot;
    View mView;
    private RewardsAdapter.RecyclerViewClickListener mListener;

    public RewardsAdapter(Context context, List<Reward> rewards, RewardsAdapter.RecyclerViewClickListener listener) {
        mContext= context;
        mRewards = rewards;
        mListener = listener;
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, String id);
    }


    @NonNull
    @Override
    public RewardsAdapter.RewardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_list_row, parent, false);
        return new RewardsAdapter.RewardsViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardsAdapter.RewardsViewHolder holder, int position) {
        Reward reward = mRewards.get(position);
        holder.tvReward.setText(reward.getReward());


        holder.itemView.setTag(reward.getReward_id());

    }

    @Override
    public int getItemCount() {
        return mRewards.size();
    }

    public class RewardsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvReward;
        private RewardsAdapter.RecyclerViewClickListener listener;


        public RewardsViewHolder(@NonNull View itemView, RewardsAdapter.RecyclerViewClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            tvReward = itemView.findViewById(R.id.tv_reward_list_name);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, (String) v.getTag());
        }

    }
}
