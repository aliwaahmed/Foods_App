package com.logapps.foods_app.person;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.logapps.foods_app.R;

import java.util.HashMap;

public class Person_Register extends AppCompatActivity {


    TextInputEditText name , email , pass , phone , address ;
    Button register ;

    private ProgressDialog mProgress;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__register);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        register = findViewById(R.id.register_btn);


        mProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.length() == 0 || name.length() == 0 || pass.length() == 0 || phone.length() == 0
                ||address.length() == 0){

                    Toast.makeText(Person_Register.this, "Error .. please complete the information", Toast.LENGTH_SHORT).show();

                }else {



                String getemail = email.getText().toString().trim();
                String getname = name.getText().toString().trim();
                String getpassword = pass.getText().toString().trim();
                String getphone = phone.getText().toString().trim();
                String getaddress = address.getText().toString().trim();


                if (!TextUtils.isEmpty(getemail) || !TextUtils.isEmpty(getpassword)){
                    mProgress.setTitle("Registering User");
                    mProgress.setMessage("Wait while we create your account");
                    mProgress.setCanceledOnTouchOutside(false);
                    mProgress.show();
                    callsignup(getemail, getpassword, getname , getphone , getaddress);
                }

            }
            }
        });
    }

    private void callsignup(final String email,final String password,final String getname , final String phone ,
                            final String address) {


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Testing","Signup successful" + task.isSuccessful());

                        if (!task.isSuccessful()){
                            Toast.makeText(Person_Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            mProgress.dismiss();
                        }
                        else {

                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();

                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                            HashMap<String , String> userMap = new HashMap<>();
                            userMap.put("email", email);
                            userMap.put("name", getname);
                            userMap.put("phone" , phone);
                            userMap.put("address" , address);
                            userMap.put("image","default");
                            userMap.put("thumb_image","default");

                            mDatabase.setValue(userMap);

                        }
                        if (task.isSuccessful()){
                            userProfile();
                            Toast.makeText(Person_Register.this, "Created Account", Toast.LENGTH_SHORT).show();
                            Log.d("TESTING", "Created Account");
                            mProgress.hide();
                            Intent i = new Intent(Person_Register.this , Person_Home_Activity.class);
                            startActivity(i);
                        }
                    }
                });

    }

    private void userProfile() {

        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null){
            UserProfileChangeRequest profileupdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name.getText().toString().trim()).build();

            user.updateProfile(profileupdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){
                        Log.d("TESTING","User profile updated.");
                    }
                }
            });

        }

    }
}
