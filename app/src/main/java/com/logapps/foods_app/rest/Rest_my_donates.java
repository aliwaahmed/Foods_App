package com.logapps.foods_app.rest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.logapps.foods_app.R;

import com.logapps.foods_app.UserClick;
import com.logapps.foods_app.person.All_donates_class;
import com.logapps.foods_app.person.all.All_two;
import com.logapps.foods_app.person.my_posts_fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Rest_my_donates extends Fragment implements UserClick {


    public Rest_my_donates() {
    }


    private ProgressDialog mProgress;

    BottomNavigationView bottomNavigationView ;

    private DatabaseReference mPatientDatabase;
    private DatabaseReference databaseReference ;
    private FirebaseUser mCurrentUser , userId;

    private RecyclerView mypostst;
    private String TAG = "hhhhhhhhhh";
    private Rest_my_donates_adapter userAdapter ;
    ConstraintLayout constraintLayout ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_rest_my_donates, container, false);

        bottomNavigationView = view.findViewById(R.id.bottomNav);
        mypostst = view.findViewById(R.id.recycler);

        constraintLayout = view.findViewById(R.id.constrain);


        RecyclerView.LayoutManager recyce = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,true);
        mypostst.setLayoutManager(recyce);

        bottomNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "add post ...", Toast.LENGTH_SHORT).show();
            }
        });


        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mPatientDatabase = FirebaseDatabase.getInstance().getReference().child("rest_Donates").child(current_uid);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("All");

        fetchFeeds();


        bottomNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                final View view = getLayoutInflater().inflate(R.layout.add_donate_dialog , null);
                final TextInputEditText your_name = view.findViewById(R.id.d_name);
                final TextInputEditText details = view.findViewById(R.id.details);
                final TextInputEditText address = view.findViewById(R.id.d_address);
                final TextInputEditText phone = view.findViewById(R.id.d_call);
                final Button addDonate = view.findViewById(R.id.add_donate);
                alert.setView(view);
                final AlertDialog dialog = alert.create();
                dialog.show();



                addDonate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String donate_details = details.getText().toString();
                        String donate_address = address.getText().toString();
                        String donate_call = phone.getText().toString();
                        String donate_name = your_name.getText().toString();


                        mProgress = new ProgressDialog(view.getContext());
                        mProgress.setTitle("Saving Changes");
                        mProgress.setMessage("Please wait while we save the changes");
                        mProgress.show();

                        Map map = new HashMap();
                        final String key = mPatientDatabase.push().getKey();
                        Map map1 = new HashMap();
                        map1.put(key, map);
                        Map mParent = new HashMap();
                        mPatientDatabase.push().setValue(mParent);

                        //add name one
                        mPatientDatabase.child("donates"+key).child("name")
                                .setValue(donate_name)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){

                                            mProgress.dismiss();

                                        } else {

                                            Toast.makeText(view.getContext(), "error", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });
                        //add name two
                        databaseReference.child("donates"+key).child("name")
                                .setValue(donate_name)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){

                                            mProgress.dismiss();

                                        } else {

                                            Toast.makeText(view.getContext(), "error", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });


                        //add details one
                        mPatientDatabase.child("donates"+key).child("details")
                                .setValue(donate_details)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){

                                            mProgress.dismiss();

                                        } else {

                                            Toast.makeText(view.getContext(), "error", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });

                        //add details two
                        databaseReference.child("donates"+key).child("details")
                                .setValue(donate_details)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){

                                            mProgress.dismiss();

                                        } else {

                                            Toast.makeText(view.getContext(), "error", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });

                        //add address one
                        mPatientDatabase.child("donates"+key).child("donate_address")
                                .setValue(donate_address)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){

                                            mProgress.dismiss();

                                        } else {

                                            Toast.makeText(view.getContext(), "error", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });

                        //add address two
                        databaseReference.child("donates"+key).child("donate_address")
                                .setValue(donate_address)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){

                                            mProgress.dismiss();

                                        } else {

                                            Toast.makeText(view.getContext(), "error", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });


                        //add phone number one
                        mPatientDatabase.child("donates"+key).child("donate_call").setValue(donate_call)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){

                                            mProgress.dismiss();

                                        } else {

                                            Toast.makeText(view.getContext(), "error", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });


                        //add phone number two
                        databaseReference.child("donates"+key).child("donate_call").setValue(donate_call)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){

                                            mProgress.dismiss();

                                        } else {

                                            Toast.makeText(view.getContext(), "error", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });

                        dialog.dismiss();
                    }
                });
            }
        });

        return view ;
    }


    public void fetchFeeds() {

        String uid = mCurrentUser.getUid();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("rest_Donates").child(uid)

                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {

                            final ArrayList<Rest_donates_calss> feeds = new ArrayList<>();

                            for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {

                                Rest_donates_calss feed = new Rest_donates_calss(
                                        noteDataSnapshot.child("name").getValue(String.class)
                                        , noteDataSnapshot.child("details").getValue(String.class));

                                feeds.add(feed);

                            }
                            userAdapter = new Rest_my_donates_adapter(Rest_my_donates.this);
                            userAdapter.setUsersData(feeds, Rest_my_donates.this);
                            mypostst.setAdapter(userAdapter);
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
