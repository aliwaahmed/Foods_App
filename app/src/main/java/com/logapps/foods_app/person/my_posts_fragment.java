package com.logapps.foods_app.person;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.logapps.foods_app.R;
import com.logapps.foods_app.UserClick;
import com.logapps.foods_app.person.all.All_two;
import com.logapps.foods_app.rest.Rest_donates_calss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;

public class my_posts_fragment extends Fragment implements UserClick {



    public my_posts_fragment() {

    }

    TextView textView ;
    Context context ;
    private ProgressDialog mProgress;
    private static final int GALLERY_PICK = 1;

    Uri imageUri ;
    ImageView t_image ;

    BottomNavigationView bottomNavigationView ;
    private StorageReference mImageStorage;


    private DatabaseReference mPatientDatabase;
    private DatabaseReference databaseReference ;
    private FirebaseUser mCurrentUser , userId;
    private long cutoff ;

    private RecyclerView mypostst;
    private String TAG = "hhhhhhhhhh";
    private  All_donates_adapter userAdapter ;
    ConstraintLayout constraintLayout ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_my_posts_fragment, container, false);

        bottomNavigationView = view.findViewById(R.id.bottomNav);
        mypostst = view.findViewById(R.id.recycler);

        constraintLayout = view.findViewById(R.id.constrain);

        mImageStorage = FirebaseStorage.getInstance().getReference();


        RecyclerView.LayoutManager recyce = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,true);
        mypostst.setLayoutManager(recyce);

        bottomNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "add post ...", Toast.LENGTH_SHORT).show();
            }
        });


        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String current_uid = mCurrentUser.getUid();
        mPatientDatabase = FirebaseDatabase.getInstance().getReference().child("All Donates").child(current_uid);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("All");

        fetchFeeds();


        bottomNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                final View view = getLayoutInflater().inflate(R.layout.add_donate_dialog , null);
                final TextView timeStamp = view.findViewById(R.id.time);
                final TextInputEditText your_name = view.findViewById(R.id.d_name);
                final TextInputEditText details = view.findViewById(R.id.details);
                final TextInputEditText address = view.findViewById(R.id.d_address);
                final TextInputEditText phone = view.findViewById(R.id.d_call);
                final Button addDonate = view.findViewById(R.id.add_donate);
                t_image = view.findViewById(R.id.dia_t_image);
                alert.setView(view);
                final AlertDialog dialog = alert.create();
                dialog.show();

                Long tsLong = System.currentTimeMillis();
                final String time = tsLong.toString();
                timeStamp.setText(time);



                t_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickImage();

                    }
                });


                addDonate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String donate_details = details.getText().toString();
                        String donate_address = address.getText().toString();
                        String donate_call = phone.getText().toString();
                        String donate_name = your_name.getText().toString();

                        final Uri resultUri = imageUri.normalizeScheme();

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


                        final StorageReference filepath = mImageStorage.child("food_images").child(current_uid + System.currentTimeMillis() + ".jpg");

                        filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        final String downloadUrl = uri.toString();

                                        mPatientDatabase.child("my_donates"+key).child("food_image").setValue(downloadUrl)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful()) {
                                                        } else {
                                                            String message = task.getException().getMessage();
                                                        }
                                                    }

                                                });

                                        databaseReference.child("donates"+key).child("food_image").setValue(downloadUrl)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful()) {
                                                        } else {
                                                            String message = task.getException().getMessage();
                                                        }
                                                    }

                                                });

                                    }
                                });
                            }
                        });



                        //add name one
                        mPatientDatabase.child("my_donates"+key).child("name")
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


                        //add time ______________________________

                        //add time one
                        mPatientDatabase.child("my_donates"+key).child("timestamp")
                                .setValue(time)
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
                        //add time two
                        databaseReference.child("donates"+key).child("timestamp")
                                .setValue(time)
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


                        //_______________________________________

                        //add details one
                        mPatientDatabase.child("my_donates"+key).child("details")
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
                        mPatientDatabase.child("my_donates"+key).child("donate_address")
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
                        mPatientDatabase.child("my_donates"+key).child("donate_call").setValue(donate_call)
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



        // delete post after 24 hrs
        long cutoff = new Date().getTime() - TimeUnit.MILLISECONDS.convert(24, TimeUnit.HOURS);
        Query oldItems = mPatientDatabase.orderByChild("timestamp").endAt(cutoff);
        oldItems.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot: snapshot.getChildren()) {
                    itemSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        return view ;

    }

    private void pickImage() {

        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, GALLERY_PICK);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK && data != null) {

            imageUri = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), imageUri);

                t_image.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();

            }
        }
    }


    public void fetchFeeds() {

        String uid = mCurrentUser.getUid();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();


        database.child("All Donates").child(uid)

                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {

                            final ArrayList<All_donates_class> feeds = new ArrayList<>();

                            for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {

                                All_donates_class feed = new All_donates_class(
                                        noteDataSnapshot.child("name").getValue(String.class)
                                        , noteDataSnapshot.child("details").getValue(String.class)
                                ,noteDataSnapshot.child("donate_address").getValue(String.class)
                                        ,noteDataSnapshot.child("donate_call").getValue(String.class)
                                        ,noteDataSnapshot.child("food_image").getValue(String.class));

                                feeds.add(feed);

                            }
                            userAdapter = new All_donates_adapter(my_posts_fragment.this);
                            userAdapter.setUsersData(feeds, my_posts_fragment.this);
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
