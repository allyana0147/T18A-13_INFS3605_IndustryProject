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

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    //array list of all movies
    private List<Comment> mComments;
    private Context mContext;
    DataSnapshot dataSnapshot;
    View mView;
    private CommentAdapter.RecyclerViewClickListener mListener;

    public CommentAdapter(Context context, List<Comment> comments, CommentAdapter.RecyclerViewClickListener listener) {
        mContext= context;
        mComments = comments;
        mListener = listener;
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, String id);
    }


    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_list_row, parent, false);
        return new CommentAdapter.CommentViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        Comment comment = mComments.get(position);
        holder.tvUserName.setText(comment.getUser_name());
        holder.tvLocation.setText(comment.getLocation());
        holder.tvComment.setText(comment.getComment());

        holder.itemView.setTag(comment.getComment_id());

    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvUserName, tvLocation, tvComment;
        private CommentAdapter.RecyclerViewClickListener listener;


        public CommentViewHolder(@NonNull View itemView, CommentAdapter.RecyclerViewClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            tvUserName = itemView.findViewById(R.id.tv_comment_row_name);
            tvLocation = itemView.findViewById(R.id.tv_comment_row_location);
            tvComment = itemView.findViewById(R.id.tv_comment_row);

        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, (String) v.getTag());
        }

    }

}
