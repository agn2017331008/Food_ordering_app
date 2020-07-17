package com.example.attempt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attempt.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminSettingsActivity extends AppCompatActivity {

    private CircleImageView profileImageView;
    private EditText nameEditTxt,  addressEditText;
    private TextView profileChageTextBtn , closeTextBtn , saveTextButton;
    private Uri imageUri;
    private  String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePictureRef;
    private String checker = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        storageProfilePictureRef = FirebaseStorage.getInstance().getReference().child("Profile pictures Admins");
        profileImageView = findViewById(R.id.admin_settings_profile_image);
        nameEditTxt = findViewById(R.id.admin_settings_full_name);

        addressEditText = findViewById(R.id.admin_settings_address);
        profileChageTextBtn = findViewById(R.id.admin_profile_image_change_btn);
        closeTextBtn = findViewById(R.id.admin_close_settings_btn);
        saveTextButton = findViewById(R.id.admin_update_account_settings_btn);

        userInfoDisplay(profileImageView,nameEditTxt,addressEditText);

        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checker.equals("clicked")){

                    userInfoSaved();
                }else{

                    updateOnlyUserInfo();
                }
            }
        });

        profileChageTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(AdminSettingsActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data!=null){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImageView.setImageURI(imageUri);
        }
        else {
            Toast.makeText(AdminSettingsActivity.this,"Error Occured",Toast.LENGTH_SHORT).show();

            startActivity(new Intent(AdminSettingsActivity.this,AdminSettingsActivity.class));
            finish();
        }
    }

    private void updateOnlyUserInfo() {

        DatabaseReference AdminRef = FirebaseDatabase.getInstance().getReference().child("Admins");

        HashMap<String , Object> userMap = new HashMap<>();
        userMap.put("admin_name",nameEditTxt.getText().toString());
        userMap.put("admin_address",addressEditText.getText().toString());

        AdminRef.child(Prevalent.currentOnlineAdmin.getAdmin_phoneNumber()).updateChildren(userMap);

        startActivity(new Intent(AdminSettingsActivity.this,AdminHomeActivity.class));

        Toast.makeText(AdminSettingsActivity.this,"Profile info saved",Toast.LENGTH_SHORT).show();
        finish();
    }

    private void userInfoSaved() {

        if(TextUtils.isEmpty(nameEditTxt.getText().toString())  || TextUtils.isEmpty(addressEditText.getText().toString())){
            Toast.makeText(AdminSettingsActivity.this, "Please fill up all the fields",Toast.LENGTH_SHORT).show();

        }
        else if(checker.equals("clicked")){
            uploadImage();
        }
    }

    private void uploadImage() {

        if( imageUri != null){

            final StorageReference fileRef = storageProfilePictureRef
                    .child(Prevalent.currentOnlineAdmin.getAdmin_phoneNumber() + ".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if(task.isSuccessful()){
                        Uri downloadUrl = task.getResult();
                        myUrl = downloadUrl.toString();

                        DatabaseReference AdminRef = FirebaseDatabase.getInstance().getReference().child("Admins");

                        HashMap<String , Object> userMap = new HashMap<>();
                        userMap.put("admin_name",nameEditTxt.getText().toString());
                        userMap.put("admin_address",addressEditText.getText().toString());
                        userMap.put("admin_image",myUrl);

                        AdminRef.child(Prevalent.currentOnlineAdmin.getAdmin_phoneNumber()).updateChildren(userMap);

                        startActivity(new Intent(AdminSettingsActivity.this,AdminHomeActivity.class));

                        Toast.makeText(AdminSettingsActivity.this,"Profile info saved",Toast.LENGTH_SHORT).show();
                        finish();


                    }else{
                        Toast.makeText(AdminSettingsActivity.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(AdminSettingsActivity.this,"Image is not selected",Toast.LENGTH_SHORT).show();
        }


    }

    private void userInfoDisplay(final CircleImageView profileImageView, final EditText nameEditTxt,  final EditText addressEditText)
    {

        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Admins").child(Prevalent.currentOnlineAdmin.getAdmin_phoneNumber());

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if(dataSnapshot.child("admin_image").exists()){
                        String image = dataSnapshot.child("admin_image").getValue().toString();
                        String name = dataSnapshot.child("admin_name").getValue().toString();
                        String address = dataSnapshot.child("admin_address").getValue().toString();


                        Picasso.get().load(image).into(profileImageView);
                        nameEditTxt.setText(name);
                        addressEditText.setText(address);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
