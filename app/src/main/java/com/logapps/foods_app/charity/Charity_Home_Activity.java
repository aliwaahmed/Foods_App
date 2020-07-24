package com.logapps.foods_app.charity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.logapps.foods_app.R;
import com.logapps.foods_app.rest.Rest_Home_Activity;
import com.logapps.foods_app.rest.Rest_Profile_Activity;
import com.logapps.foods_app.rest.Rest_login_Activity;


public class Charity_Home_Activity extends AppCompatActivity {

    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private ViewPager mViewPager;
    private Charity_adapter_holder charityAdapter;
    private TextView drName ;
    private TabLayout mTabLayout;
    private CircleImageView mDisplayImage ;

    private DatabaseReference mUserDatabase , mDatabase;
    private FirebaseUser mCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity__home_);

        mToolbar = findViewById(R.id.toolbar);
        mViewPager = findViewById(R.id.pager);
        mTabLayout = findViewById(R.id.tablayout);
        drName = findViewById(R.id.getname);
        mDisplayImage = findViewById(R.id.image);


        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("charity_Users").child(current_uid);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("cha_name").getValue().toString();
                //    final String image = dataSnapshot.child("image").getValue().toString();


                drName.setText(name);


//                if (!image.equals("default")) {
//                    Picasso.with(getBaseContext()).load(image).networkPolicy(NetworkPolicy.OFFLINE)
//                            .placeholder(R.drawable.avatar).into(mDisplayImage, new Callback() {
//                        @Override
//                        public void onSuccess() {
//
//                        }
//
//                        @Override
//                        public void onError() {
//
//                            Picasso.with(getBaseContext()).load(image).placeholder(R.drawable.avatar).into(mDisplayImage);
//                        }
//                    });
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        setSupportActionBar(mToolbar);
        mAuth = FirebaseAuth.getInstance();



        charityAdapter = new Charity_adapter_holder(getSupportFragmentManager());
        mViewPager.setAdapter(charityAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu , menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);


        if (item.getItemId() == R.id.menu_prifile){

            Intent i = new Intent(Charity_Home_Activity.this , Charity_profile_Activity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);


        }else if (item.getItemId() == R.id.menu_rating){
            Intent i = new Intent(Charity_Home_Activity.this , Charity_rateActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

        } else if (item.getItemId() == R.id.menu_logout) {
            Intent i = new Intent(Charity_Home_Activity.this , Charity_login_Activity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }

        return true ;
    }
}

