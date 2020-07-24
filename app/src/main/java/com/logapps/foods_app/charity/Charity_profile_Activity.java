package com.logapps.foods_app.charity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.logapps.foods_app.R;
import com.logapps.foods_app.rest.Rest_Profile_Activity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.Random;

public class Charity_profile_Activity extends AppCompatActivity {


    private static final int GALLERY_PICK = 1;
    private DatabaseReference mUserDatabase , mDatabase;
    private FirebaseUser mCurrentUser;
    private TextView mName, phoneNumber, special , address;
    private Button editBtn;
    private ImageView change_image , cover;
    private StorageReference mImageStorage;
    private ProgressDialog mProgressDilog;
    private DatabaseReference mPatientDatabase ;
    private ProgressDialog mProgress ;

    // random name for images
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_profile_);

        mName = findViewById(R.id.info_name);
        phoneNumber = findViewById(R.id.info_phone);
        address = findViewById(R.id.info_address);
        change_image = findViewById(R.id.changeImage);
        editBtn = findViewById(R.id.editBtn);
        cover = findViewById(R.id.coverr);


        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_id = mCurrentUser.getUid();
        mPatientDatabase = FirebaseDatabase.getInstance().getReference().child("charity_Users").child(current_id);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(Charity_profile_Activity.this);
                View view = getLayoutInflater().inflate(R.layout.edit_profile_dialog , null);
                final EditText Dname = view.findViewById(R.id.dia_name);
                final EditText Dphone = view.findViewById(R.id.dia_phone);
                final EditText Daddress = view.findViewById(R.id.dia_address);
                final Button Dsave = view.findViewById(R.id.dia_btn);
                alert.setView(view);
                final AlertDialog dialog = alert.create();
                dialog.show();

                //data
                mUserDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String info_name = dataSnapshot.child("cha_name").getValue().toString();
                        String info_phone = dataSnapshot.child("cha_phone").getValue().toString();
                        String info_add = dataSnapshot.child("cha_address").getValue().toString();

                        Dname.setText(info_name);
                        Dphone.setText(info_phone);
                        Daddress.setText(info_add);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Dsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mProgress = new ProgressDialog(Charity_profile_Activity.this);
                        mProgress.setTitle("Saving Changes");
                        mProgress.setMessage("Please wait while we save the changes");
                        mProgress.show();

                        String DoctorName = Dname.getText().toString();
                        String DoctorAddress = Daddress.getText().toString();
                        String DoctorPhone = Dphone.getText().toString();

                        mUserDatabase.child("cha_name").setValue(DoctorName).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){

                                    mProgress.dismiss();


                                } else {

                                    Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                                }

                            }
                        });

                        mUserDatabase.child("cha_phone").setValue(DoctorPhone).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){

                                    mProgress.dismiss();

                                } else {

                                    Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                                }

                            }
                        });

                        mUserDatabase.child("cha_address").setValue(DoctorAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){

                                    mProgress.dismiss();

                                } else {

                                    Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                                }

                            }
                        });
                        dialog.dismiss();
                    }
                });
            }
        });

        //firebase instance
        mImageStorage = FirebaseStorage.getInstance().getReference();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("cha_Users");

        change_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(Charity_profile_Activity.this);
            }
        });

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("charity_Users").child(current_uid);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("cha_name").getValue().toString();
                String phone = dataSnapshot.child("cha_phone").getValue().toString();
                String Iaddress = dataSnapshot.child("cha_address").getValue().toString();
                final String image = dataSnapshot.child("cha_image").getValue().toString();
                String thumb_image = dataSnapshot.child("cha_thumb_image").getValue().toString();

                mName.setText(name);
                phoneNumber.setText(phone);
                address.setText(Iaddress);

                //
                if (!image.equals("default")) {
                    Picasso.with(Charity_profile_Activity.this).load(image).networkPolicy(NetworkPolicy.OFFLINE)
                            .placeholder(R.color.green).into(cover, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                            Picasso.with(Charity_profile_Activity.this).load(image).placeholder(R.color.green).into(cover);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .start(Charity_profile_Activity.this);
        }
        //profile image
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                mProgressDilog = new ProgressDialog(Charity_profile_Activity.this);
                mProgressDilog.setTitle("Uploading Image...");
                mProgressDilog.setMessage("Please wait until uploading your image");
                mProgressDilog.setCanceledOnTouchOutside(false);
                mProgressDilog.show();

                final Uri resultUri = result.getUri();

                //   File thumb_filePath=new File(resultUri.getPath());

                String current_user_id = mCurrentUser.getUid();
                Uri imageUri = data.getData();

                CropImage.activity(imageUri)
                        .setAspectRatio(1, 1)
                        .setMinCropWindowSize(500, 500)
                        .start(this);
                // uploading photo to page all users
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //  imageUri.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] thumb_byte = baos.toByteArray();

                final StorageReference filepath = mImageStorage.child("profile_images").child(current_user_id + ".jpg");
                final StorageReference thumb_filepath = mImageStorage.child("profile_images").child("thumb").child(current_user_id + ".jpg");

                filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                final String downloadUrl = uri.toString();
                                mUserDatabase.child("cha_image").setValue(downloadUrl)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                mProgressDilog.dismiss();
                                                if (task.isSuccessful()) {
                                                    // Intent selfIntent = new Intent(Settings.this, Settings.class);
                                                    //startActivity(selfIntent);
                                                    Toast.makeText(Charity_profile_Activity.this, "Profile image stored to firebase database successfully.", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    String message = task.getException().getMessage();
                                                    Toast.makeText(Charity_profile_Activity.this, "Error Occured..." + message, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                mUserDatabase.child("cha_thumb_image").setValue(downloadUrl)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                mProgressDilog.dismiss();
                                                if (task.isSuccessful()) {
                                                    Intent selfIntent = new Intent(Charity_profile_Activity.this, Rest_Profile_Activity.class);
                                                    startActivity(selfIntent);
                                                    Toast.makeText(Charity_profile_Activity.this, "Profile image stored to firebase database successfully.", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    String message = task.getException().getMessage();
                                                    Toast.makeText(Charity_profile_Activity.this, "Error Occured..." + message, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        });
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
            }
        }
    }


}