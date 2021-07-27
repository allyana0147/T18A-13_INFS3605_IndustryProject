package com.example.infs3605_industry_project;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;

    private DatabaseReference mReferenceUsers;
    private DatabaseReference mReferencePosts;
    private DatabaseReference mReferenceComments;
    private DatabaseReference mReferenceProfile;
    private DatabaseReference mReferenceReward;
    private DatabaseReference mReferenceLanguage;
    private DatabaseReference mReferenceDictionary;

    private List<User> users = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<Profile> profiles = new ArrayList<>();
    private List<Reward> rewards = new ArrayList<>();
    private List<Language> languages = new ArrayList<>();
    private List<Dictionary> dictionaries = new ArrayList<>();

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceUsers = mDatabase.getReference("User");
        mReferencePosts = mDatabase.getReference("Post");
        mReferenceComments = mDatabase.getReference("Comment");
        mReferenceProfile = mDatabase.getReference("Profile");
        mReferenceReward = mDatabase.getReference("Reward");
        mReferenceLanguage = mDatabase.getReference("Language");
        mReferenceDictionary = mDatabase.getReference("Dictionary");

    }

    //USER
    //handling asynchronous data
    public interface MyCallbackUser {
        void onCallback(List<User> userList);
    }

    public void readUser(MyCallbackUser myCallback) {
        mReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    User user = keyNode.getValue(User.class);
                    users.add(user);
                }
                myCallback.onCallback(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //POST
    //handling asynchronous data
    public interface MyCallbackPost {
        void onCallback(List<Post> postList);
    }

    public void readPost(MyCallbackPost myCallback) {
        mReferencePosts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Post post = keyNode.getValue(Post.class);
                    posts.add(post);
                }
                myCallback.onCallback(posts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //delete post for flag
    public void flagPost(String post_id) {
        mReferencePosts.child(post_id).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
    }




    //COMMENTS
    //handling asynchronous data
    public interface MyCallbackComment {
        void onCallback(List<Comment> commentList);
    }

    //list comments
    public void readComments(MyCallbackComment myCallback) {
        mReferenceComments.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comments.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Comment comment = keyNode.getValue(Comment.class);
                    comments.add(comment);
                }
                myCallback.onCallback(comments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //add comment
    public void addComment(String comment_id, Comment comment) {
        mReferenceComments.child(comment_id).setValue(comment)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }

                });
    }


    //update comment
    public void updateComment(String comment_id, String new_comment) {
        mReferenceComments.child(comment_id).child("comment").setValue(new_comment)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
    }


    //delete comment
    public void deleteComment(String comment_id) {
        mReferenceComments.child(comment_id).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
    }


    //PROFILE
    //add profile
    public void addNewProfile(String id, Profile profile) {
        mReferenceProfile.child(id).setValue(profile)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }

                });
    }

    //handling asynchronous data
    public interface MyCallbackProfile {
        void onCallback(List<Profile> profileList);
    }

    //list profile
    public void readProfile(MyCallbackProfile myCallback) {
        mReferenceProfile.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profiles.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Profile profile = keyNode.getValue(Profile.class);
                    profiles.add(profile);
                }
                myCallback.onCallback(profiles);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //update number of posts
    public void updateNumberOfPosts(String id, String new_no_posts) {
        mReferenceProfile.child(id).child("no_of_posts").setValue(new_no_posts)
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
    }

    //update number of followers
    public void updateNumberOfFollowers(String id, String new_no_followers) {
        mReferenceProfile.child(id).child("no_of_followers").setValue(new_no_followers)
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
    }

    //update total points
    public void updateTotalPoints(String id, String total_points) {
        mReferenceProfile.child(id).child("total_points").setValue(total_points)
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
    }

    //update current number points
    public void updateCurrentPoints(String id, String current_points) {
        mReferenceProfile.child(id).child("no_of_points").setValue(current_points)
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
    }

    //update no.of rewards
    public void updateNumberRewards(String id, String no_of_rewards_redeemed) {
        mReferenceProfile.child(id).child("no_rewards_redeemed").setValue(no_of_rewards_redeemed)
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
    }


    //REWARDS
    //handling asynchronous data
    public interface MyCallbackReward{
        void onCallback(List<Reward> rewardList);
    }

    //list rewards
    public void readRewards(MyCallbackReward myCallback) {
        mReferenceReward.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rewards.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Reward reward = keyNode.getValue(Reward.class);
                    rewards.add(reward);
                }
                myCallback.onCallback(rewards);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //add reward
    public void addReward(String reward_id, Reward reward) {
        mReferenceReward.child(reward_id).setValue(reward)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }

                });
    }

    //LANGUAGE
    //handling asynchronous data
    public interface MyCallbackLanguage{
        void onCallback(List<Language> languageList);
    }

    //list languages
    public void readLanguage(MyCallbackLanguage myCallback) {
        mReferenceLanguage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                languages.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Language language = keyNode.getValue(Language.class);
                    languages.add(language);
                }
                myCallback.onCallback(languages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //DICTIONARY
    //handling asynchronous data
    public interface MyCallbackDictionary{
        void onCallback(List<Dictionary> dictionaryList);
    }

    //list dictionary
    public void readDictionary(MyCallbackDictionary myCallback) {
        mReferenceDictionary.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dictionaries.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Dictionary dictionary = keyNode.getValue(Dictionary.class);
                    dictionaries.add(dictionary);
                }
                myCallback.onCallback(dictionaries);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
