package com.example.infs3605_industry_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private ImageView ivAddComment, ivBack;
    private RecyclerView mRecyclerView;
    private CommentAdapter mAdapter;

    //shared preference to save and share user email to each pages
    SharedPreferences sharedPreferences;
    private static final String SP_EMAIL = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        //shared preference to save and share user email to each pages
        sharedPreferences = getSharedPreferences(SP_EMAIL, MODE_PRIVATE);
        String sp_email = sharedPreferences.getString(SP_EMAIL, null);

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
        ivBack = findViewById(R.id.iv_comments_back);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });

        ivAddComment.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (etComment.getText().toString().isEmpty()) {
                    etComment.setError("Cannot add an empty note!");
                    etComment.requestFocus();
                    return;
                }

                //get current user based on email
                new FirebaseDatabaseHelper().readUser(new FirebaseDatabaseHelper.MyCallbackUser() {
                    @Override
                    public void onCallback(List<User> userList) {
                        User user = User.getUser(sp_email,userList);
                        String user_location = user.getLocation();
                        String user_name = user.getName();

                        String commentID = UUID.randomUUID().toString().replace("-", "");

                        Comment comment = new Comment();
                        comment.setComment_id(commentID);
                        comment.setComment(etComment.getText().toString());
                        comment.setLocation(user_location);
                        comment.setPost_id(post_id);
                        comment.setUser_name(user_name);

                        new FirebaseDatabaseHelper().addComment(commentID, comment);
                        etComment.setText(" ");
                        finish();
                        startActivity(getIntent());

                    }


                });


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