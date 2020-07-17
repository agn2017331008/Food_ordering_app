package com.example.attempt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.attempt.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddmenuActivity extends AppCompatActivity {


    private EditText add_menu_name;
    private EditText add_menu_price;
    private EditText add_menu_description;
    private String saveCurrentDate,saveCurrentTime , productRandomKey,downloadImageUrl,menu_name,menu_price,menu_description;
    private Button add_menu_to_db;
//    private static  final  int GalleryPick = 1;
////    ImageView addMenuImg ;
//    private  StorageReference ProductImageRef;
    private DatabaseReference productRef;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        add_menu_to_db = findViewById(R.id.add_new_menu);

        add_menu_name = findViewById(R.id.admin_menu_name);
        add_menu_price =  findViewById(R.id.admin_menu_price);
        add_menu_description = findViewById(R.id.admin_menu_description);


       // ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        productRef = FirebaseDatabase.getInstance().getReference().child("Admins").child(Prevalent.currentOnlineAdmin.getAdmin_phoneNumber()).child("menu");
//        addMenuImg = findViewById(R.id.add_menu_image);
//
//        addMenuImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OpenGallery();
//            }
//        });


        add_menu_to_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateMenu();
            }
         });

    }

    private void CreateMenu() {


        menu_name = add_menu_name.getText().toString();
        menu_price = add_menu_price.getText().toString();
        menu_description = add_menu_description.getText().toString();

//        if(imageUri == null ||
                if(TextUtils.isEmpty(menu_name)|| TextUtils.isEmpty(menu_price)|| TextUtils.isEmpty(menu_description)){
          //  Toast.makeText(AddmenuActivity.this,menu_name+menu_price+menu_description,Toast.LENGTH_LONG).show();
             Toast.makeText(AddmenuActivity.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
         }
         else{

                    saveMenuInfotoDatabase();
         }
    }
//
//    private void storeImageInformation() {
//
//        Calendar calender = Calendar.getInstance();
//
//        SimpleDateFormat currentDate = new SimpleDateFormat("MMddyyyy");
//        saveCurrentDate = currentDate.format(calender.getTime());
//
//        SimpleDateFormat currentTime = new SimpleDateFormat("HHmmss");
//        saveCurrentTime = currentTime.format(calender.getTime());
//
//        productRandomKey = saveCurrentDate + saveCurrentTime;
//
//        final StorageReference filePath = ProductImageRef.child(imageUri.getLastPathSegment() + productRandomKey + ".jpg");
//        final UploadTask uploadTask = filePath.putFile(imageUri);
//
//
//
//
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                String massage = e.toString();
//
//                Toast.makeText(AddmenuActivity.this,"Error"+massage,Toast.LENGTH_SHORT).show();
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(AddmenuActivity.this,"Here",Toast.LENGTH_LONG).show();
//                Toast.makeText(AddmenuActivity.this,"Image uploaded successfully",Toast.LENGTH_SHORT).show();
//
//                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                   @Override
//                   public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//
//                       if(!task.isSuccessful()){
//                           throw task.getException();
//                       }
//                       downloadImageUrl = filePath.getDownloadUrl().toString();
//                       return  filePath.getDownloadUrl();
//                   }
//               }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                   @Override
//                   public void onComplete(@NonNull Task<Uri> task) {
//                      if(task.isSuccessful()){
//                          downloadImageUrl = task.getResult().toString();
//                          Toast.makeText(AddmenuActivity.this,"Image saved to database Successfully",Toast.LENGTH_SHORT).show();
//                          saveMenuInfotoDatabase();
//                      }
//                   }
//               });
//
//            }
//        });
//    }

    private void saveMenuInfotoDatabase() {


        Calendar calender = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMddyyyy");
        saveCurrentDate = currentDate.format(calender.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HHmmss");
        saveCurrentTime = currentTime.format(calender.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;


        HashMap<String,Object> menumap = new HashMap<>();
        menumap.put("menuid",productRandomKey);
        menumap.put("date",saveCurrentDate);
        menumap.put("time",saveCurrentTime);
        menumap.put("description",menu_description);
        menumap.put("price",menu_price);
        menumap.put("name",menu_name);
      //  menumap.put("menu_image",downloadImageUrl);

        productRef.child(productRandomKey).updateChildren(menumap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AddmenuActivity.this,"Your menu is uploaded",Toast.LENGTH_SHORT).show();
                             Intent intent = new Intent(AddmenuActivity.this,AdminHomeActivity.class);
                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                             startActivity(intent);
                             finish();

                        }else {
                            Toast.makeText(AddmenuActivity.this,"Error"+task.getException().toString(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

//    private void OpenGallery() {
//
//        Intent galleryIntent = new Intent();
//        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//        galleryIntent.setType("image/*");
//        startActivityForResult(galleryIntent,GalleryPick);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == GalleryPick && resultCode == RESULT_OK && data!=null){
//            imageUri = data.getData();
//            addMenuImg.setImageURI(imageUri);
//        }
//    }
}
