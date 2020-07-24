package com.logapps.foods_app.person;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.logapps.foods_app.R;
import com.logapps.foods_app.UserClick;
import com.logapps.foods_app.person.all.All_two;
import com.logapps.foods_app.person.all.All_two_adapter;
import com.logapps.foods_app.rest.Rest_donates_calss;

import java.util.ArrayList;

public class All_Posts extends Fragment implements UserClick {

    public All_Posts() {
    }


    private DatabaseReference mPatientDatabase;
    private FirebaseUser mCurrentUser , userId;


    private RecyclerView all_posts;
    private String TAG = "hhhhhhhhhh";
    private All_two_adapter userAdapter ;
    LinearLayout linearLayout ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.all_posts_fragment, container, false);
        all_posts = view.findViewById(R.id.all_donates);
        linearLayout = view.findViewById(R.id.lin);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        all_posts.setLayoutManager(layoutManager);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        String current_uid = mCurrentUser.getUid();
        mPatientDatabase = FirebaseDatabase.getInstance().getReference().child("All");

        fetchFeeds();

        return view ;
    }

    private void fetchFeeds() {
        String uid = mCurrentUser.getUid();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("All")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            final ArrayList<All_two> feeds = new ArrayList<>();
                            for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                                All_two feed = new All_two(
                                        noteDataSnapshot.child("name").getValue(String.class)
                                        , noteDataSnapshot.child("details").getValue(String.class)
                                        ,noteDataSnapshot.child("donate_address").getValue(String.class)
                                        ,noteDataSnapshot.child("donate_call").getValue(String.class)
                                        ,noteDataSnapshot.child("food_image").getValue(String.class));
                                feeds.add(feed);
                            }
                            userAdapter = new All_two_adapter(All_Posts.this);
                            userAdapter.setUsersData(feeds, All_Posts.this);
                            all_posts.setAdapter(userAdapter);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    @Override
    public void asd(All_donates_class all_donates_class) {

    }

    @Override
    public void asd(All_two all_two) {

    }

    @Override
    public void ads(Rest_donates_calss rest_donates_calss) {

    }
}
