package com.example.infs3605_industry_project;


import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    //array list of all movies
    private ArrayList<Post> mPosts;
    private HomeAdapter.RecyclerViewClickListener mListener;

    public HomeAdapter(ArrayList<Post> posts, HomeAdapter.RecyclerViewClickListener listener) {
        mPosts = posts;
        mListener = listener;
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, String id);
    }

    //displays movies in rows in a recycler view
    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_view_layout, parent, false);
        return new HomeAdapter.HomeViewHolder(v, mListener);
    }

    //gets movie information from movie object and displays in containers in holders in recycler view
    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeViewHolder holder, int position) {
        Resources res = holder.itemView.getContext().getResources();
        Post post = mPosts.get(position);
        holder.tvUserName.setText(post.getUser_name());
        holder.tvLocation.setText(String.valueOf(post.getLocation()));
        holder.tvCaption.setText(String.valueOf(post.getCaption()));
        holder.ivPost.setImageResource(res.getIdentifier("photo", "drawable", "com.example.infs3605_industry_project"));
        holder.itemView.setTag(post.getPost_id());

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvUserName, tvLocation, tvCaption;
        private ImageView ivProfile, ivPost, ivFollow, ivLike, ivComment, ivFlag;

        private HomeAdapter.RecyclerViewClickListener listener;


        public HomeViewHolder(@NonNull View itemView, HomeAdapter.RecyclerViewClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            tvUserName = itemView.findViewById(R.id.tv_post_username);
            tvLocation = itemView.findViewById(R.id.tv_post_location);
            tvCaption = itemView.findViewById(R.id.tv_post_caption);
            ivPost = itemView.findViewById(R.id.iv_post_content);

        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, (String) v.getTag());
        }

    }


}
