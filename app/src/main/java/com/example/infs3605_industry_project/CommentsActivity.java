package com.example.infs3605_industry_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommentsActivity extends AppCompatActivity {

    public static final String POST_CAPTION = "CAPTION";
    public static final String POST_USER_NAME = "USERNAME";
    public static final String POST_LOCATION = "LOCATION";
    public static final String POST_ID = "ID";

    private TextView tvCaption, tvUserName, tvLocation;
    private EditText etComment;
    private RecyclerView rvComments;
    private ImageView ivAddComment;
    private RecyclerView mRecyclerView;
    private CommentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Intent intent = getIntent();
        String caption = intent.getStringExtra(POST_CAPTION);
        String username = intent.getStringExtra(POST_USER_NAME);
        String location = intent.getStringExtra(POST_LOCATION);
        String post_id = intent.getStringExtra(POST_ID);

        tvCaption = findViewById(R.id.tv_comments_caption);
        tvCaption.setText(caption);
        tvUserName = findViewById(R.id.tv_comments_name);
        tvUserName.setText(username);
        tvLocation = findViewById(R.id.tv_comments_location);
        tvLocation.setText(location);
        etComment = findViewById(R.id.et_comments_add);
        rvComments = findViewById(R.id.rv_comments);
        ivAddComment = findViewById(R.id.iv_comments_add);


        ivAddComment.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (etComment.getText().toString().isEmpty()) {
                    etComment.setError("Cannot add an empty note!");
                    etComment.requestFocus();
                    return;
                }

                String commentID = UUID.randomUUID().toString().replace("-", "");

                Comment comment = new Comment();
                comment.setComment_id(commentID);
                comment.setComment(etComment.getText().toString());
                comment.setLocation(location);
                comment.setPost_id(post_id);
                comment.setUser_name(username);

                new FirebaseDatabaseHelper().addComment(commentID, comment);
                etComment.setText(" ");

        }



        });

        //create recycler view
        mRecyclerView = findViewById(R.id.rv_comments);
        mRecyclerView.setHasFixedSize(true);

        new FirebaseDatabaseHelper().readComments(new FirebaseDatabaseHelper.MyCallbackComment() {
            @Override
            public void onCallback(List<Comment> commentList) {
                List<Comment> commentListBasedOnPost = new ArrayList<>();
                commentListBasedOnPost = Comment.getCommentListBasedOnPost(post_id, commentList);


                CommentAdapter.RecyclerViewClickListener listener = new CommentAdapter.RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, String id) {

                    }

                };

                mAdapter = new CommentAdapter(CommentsActivity.this, commentListBasedOnPost, listener);
                mRecyclerView.setAdapter(mAdapter);


            }

        });


    }
}