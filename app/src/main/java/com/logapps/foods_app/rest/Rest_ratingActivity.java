package com.logapps.foods_app.rest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.logapps.foods_app.R;

import java.util.HashMap;
import java.util.Map;

public class Rest_ratingActivity extends AppCompatActivity {

    RatingBar ratingBar ;
    TextView textView ;
    Button button ;

    private DatabaseReference databaseReference ;
    private FirebaseUser mCurrentUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_rating);

        mCurrentUser = mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Rating").child(mCurrentUser.getUid());

        //_________________________________
        ratingBar = findViewById(R.id.rating_value);
        textView = findViewById(R.id.rating_text);
        button = (Button)findViewById(R.id.submit);
        //____________________________________


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String x ;
                int noofstars = ratingBar.getNumStars();
                float getrating = ratingBar.getRating();
                textView.setText(getrating+"/"+noofstars);
                x = String.valueOf(getrating);



                Map map = new HashMap();
                final String key = databaseReference.push().getKey();
                Map map1 = new HashMap();
                map1.put(key, map);
                Map mParent = new HashMap();
                databaseReference.push().setValue(mParent);

                //add name one
                databaseReference.child("my_rate"+key).child("rating")
                        .setValue(x)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){

                                    Toast.makeText(Rest_ratingActivity.this, "successfully rated", Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(Rest_ratingActivity.this , Rest_Home_Activity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);

                                } else {

                                    Toast.makeText(Rest_ratingActivity.this, "Error", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
            }
        });


    }
}