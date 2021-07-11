package com.example.infs3605_industry_project;


import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    //array list of all movies
    private List<Post> mPosts;
    private Context mContext;
    DataSnapshot dataSnapshot;
    private HomeAdapter.RecyclerViewClickListener mListener;

    public HomeAdapter(Context context, List<Post> posts, HomeAdapter.RecyclerViewClickListener listener) {
        mContext= context;
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
        Post post = mPosts.get(position);
        holder.tvUserName.setText(post.getName());
        holder.tvLocation.setText(post.getLocation());

        holder.tvCaption.setText(post.getCaption());
        //retrieve image URL from firebase and add to imageviewer
        Glide.with(mContext).load(mPosts.get(position).getImageUrl()).into(holder.ivPost);

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
