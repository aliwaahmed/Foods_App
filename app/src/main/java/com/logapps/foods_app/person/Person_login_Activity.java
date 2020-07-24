package com.logapps.foods_app.person;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.logapps.foods_app.MainActivity;
import com.logapps.foods_app.R;
import com.logapps.foods_app.rest.Rest_login_Activity;

public class Person_login_Activity extends AppCompatActivity {

    TextInputEditText email , pass ;
    TextView create ;
    Button loginBtn ;
    ImageView back ;
    private ProgressDialog LoginProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_login_);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        loginBtn =findViewById(R.id.login_btn);

        back = findViewById(R.id.back_btn);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Person_login_Activity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = email.getText().toString().trim();
                String Pass = pass.getText().toString().trim();

                if (!TextUtils.isEmpty(Email) || !TextUtils.isEmpty(Pass)){
                    LoginProgress.setTitle("Logging in");
                    LoginProgress.setMessage("Please wait while checking your information");
                    LoginProgress.setCanceledOnTouchOutside(false);
                    LoginProgress.show();
                    loginUser(Email , Pass);
                }
            }

        });

        //firebase auth
        mAuth = FirebaseAuth.getInstance();
        //progress dialog
        LoginProgress = new ProgressDialog(this);

        create = findViewById(R.id.new_account);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Person_login_Activity.this,Person_Register.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
    }

    private void loginUser(String email, String pass) {

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Person_login_Activity.this, "sucsess.",
                                    Toast.LENGTH_SHORT).show();
                            LoginProgress.dismiss();
                            Intent i = new Intent(Person_login_Activity.this, Person_Home_Activity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                            FirebaseUser user = mAuth.getCurrentUser();
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            // Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();
                            Toast.makeText(Person_login_Activity.this, "Please make sure your information is true.",
                                    Toast.LENGTH_LONG).show();
                            LoginProgress.hide();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
