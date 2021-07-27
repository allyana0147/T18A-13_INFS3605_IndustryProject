package com.example.infs3605_industry_project;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;

import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    //array list of all movies
    private List<Post> mPosts;
    private Context mContext;
    DataSnapshot dataSnapshot;
    View mView;
    private HomeAdapter.RecyclerViewClickListener mListener;

    public HomeAdapter(Context context, List<Post> posts, HomeAdapter.RecyclerViewClickListener listener) {
        mContext= context;
        mPosts = posts;
        mListener = listener;
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, String id);
        void onAddCommentClick(View view, int position);
        void onAddFollowClick(View view, int position);
        void onAddUnFollowClick (View view, int position);
        void onFlag (View view, int position);
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
        holder.tvHashtag.setText(post.getHashtag());

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
        private TextView tvUserName, tvLocation, tvCaption, tvHashtag;
        private ImageView ivPost, ivFollowing, ivLike, ivComment, ivFlag, ivRedFlag, ivUnLike, ivFollow;
        private HomeAdapter.RecyclerViewClickListener listener;


        public HomeViewHolder(@NonNull View itemView, HomeAdapter.RecyclerViewClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            tvUserName = itemView.findViewById(R.id.tv_post_username);
            tvLocation = itemView.findViewById(R.id.tv_post_location);
            tvCaption = itemView.findViewById(R.id.tv_post_caption);
            ivPost = itemView.findViewById(R.id.iv_post_image);
            tvHashtag = itemView.findViewById(R.id.tv_post_hashtag);

            ivComment = itemView.findViewById(R.id.iv_post_comment);
            ivFollowing  = itemView.findViewById(R.id.iv_post_follow);
            ivLike  = itemView.findViewById(R.id.iv_post_empty_like);
            ivUnLike  = itemView.findViewById(R.id.iv_post_like);
            ivFlag  = itemView.findViewById(R.id.iv_post_flag);
            ivRedFlag = itemView.findViewById(R.id.iv_post_red_flag);
            ivFollow  = itemView.findViewById(R.id.iv_post_add_follow);


            //comment button
            ivComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAddCommentClick(v, position);
                        }
                    }
                }
            });

            //follow button
            ivFollowing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivFollow.setVisibility(View.VISIBLE);
                    ivFollowing.setVisibility(View.INVISIBLE);
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAddUnFollowClick(v, position);
                        }
                    }
                }
            });

            ivFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivFollow.setVisibility(View.INVISIBLE);
                    ivFollowing.setVisibility(View.VISIBLE);
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAddFollowClick(v, position);
                        }
                    }
                }
            });

            //like button
            ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivLike.setVisibility(View.INVISIBLE);
                    ivUnLike.setVisibility(View.VISIBLE);
                }
            });

            ivUnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivUnLike.setVisibility(View.INVISIBLE);
                    ivLike.setVisibility(View.VISIBLE);
                }
            });

            ivFlag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivFlag.setVisibility(View.INVISIBLE);
                    ivRedFlag.setVisibility(View.VISIBLE);
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onFlag(v, position);
                        }
                    }
                }
            });

            ivRedFlag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivRedFlag.setVisibility(View.INVISIBLE);
                    ivFlag.setVisibility(View.VISIBLE);
                }
            });

        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, (String) v.getTag());
        }

    }

}
