package com.logapps.foods_app.person;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.logapps.foods_app.R;
import com.squareup.picasso.Picasso;

public class MyPostsDetails_Activity extends AppCompatActivity {

    TextView name , details , address , phone ;
    ImageView imageView ;
    DatabaseReference mUserDatabase ;
    FirebaseUser uid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts_details_);

        name = findViewById(R.id._name);
        details = findViewById(R.id._details);
        address = findViewById(R.id._address);
        phone = findViewById(R.id._phone);
        imageView = findViewById(R.id.img);

        uid = FirebaseAuth.getInstance().getCurrentUser();
        mUserDatabase = FirebaseDatabase.getInstance().getReference();

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Dname  = getIntent().getStringExtra("name");
                String tName = getIntent().getStringExtra("details");
                String phoneN = getIntent().getStringExtra("donate_call");
                String addresss = getIntent().getStringExtra("donate_address");
                String image = getIntent().getStringExtra("food_image");


                name.setText(Dname);
                phone.setText(phoneN);
                details.setText(tName);
                address.setText(addresss);
                Picasso.with(MyPostsDetails_Activity.this).load(image).placeholder(R.drawable.imgbg).into(imageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}