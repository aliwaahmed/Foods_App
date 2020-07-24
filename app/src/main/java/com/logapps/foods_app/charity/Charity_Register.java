package com.logapps.foods_app.charity;

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
import com.logapps.foods_app.person.Person_Home_Activity;
import com.logapps.foods_app.R;

import java.util.HashMap;

public class Charity_Register extends AppCompatActivity {

    TextInputEditText  man_name , name  , cha_number, email , pass , phone , address ;
    Button register ;

    private ProgressDialog mProgress;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity__register);


        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        man_name = findViewById(R.id.man_name);
        cha_number = findViewById(R.id.cha_num);
        register = findViewById(R.id.register_btn);

        mProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();



            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (name.length() == 0 || email.length() == 0 || pass .length() == 0 || phone .length() == 0
                            || address .length() == 0 || man_name.length() == 0
                            || cha_number.length() == 0){

                        Toast.makeText(Charity_Register.this, "Error .. please complete the information", Toast.LENGTH_SHORT).show();

                    }
                    else {

                    String getemail = email.getText().toString().trim();
                    String getcha_name = name.getText().toString().trim();
                    String getman_name = man_name.getText().toString().trim();
                    String getpassword = pass.getText().toString().trim();
                    String getphone = phone.getText().toString().trim();
                    String getaddress = address.getText().toString().trim();
                    String getcha_num = cha_number.getText().toString().trim();


                    if (!TextUtils.isEmpty(getemail) || !TextUtils.isEmpty(getpassword)){
                        mProgress.setTitle("Registering User");
                        mProgress.setMessage("Wait while we create your account");
                        mProgress.setCanceledOnTouchOutside(false);
                        mProgress.show();
                        callsignup(getemail, getpassword, getcha_name , getphone , getaddress , getman_name , getcha_num);
                    }

                }
                }
            });




    }

    private void callsignup( final String email, final String password,
                             final   String getcha_name, final String getphone, final String getaddress,
                             final String getman_name , final String getcha_num) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Testing","Signup successful" + task.isSuccessful());

                        if (!task.isSuccessful()){
                            Toast.makeText(Charity_Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            mProgress.dismiss();
                        }
                        else {

                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();

                            mDatabase = FirebaseDatabase.getInstance().getReference().child("charity_Users").child(uid);

                            HashMap<String , String> userMap = new HashMap<>();
                            userMap.put("cha_email", email);
                            userMap.put("cha_name", getcha_name);
                            userMap.put("cha_phone",getphone);
                            userMap.put("cha_address",getaddress);
                            userMap.put("man_name", getman_name);
                            userMap.put("cha_num" , getcha_num) ;
                            userMap.put("cha_image" , "default");
                            userMap.put("cha_thumb_image" , "default");

                            mDatabase.setValue(userMap);

                        }
                        if (task.isSuccessful()){
                            userProfile();
                            Toast.makeText(Charity_Register.this, "Created Account", Toast.LENGTH_SHORT).show();
                            Log.d("TESTING", "Created Account");
                            mProgress.hide();
                            Intent i = new Intent(Charity_Register.this , Charity_Home_Activity.class);
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
