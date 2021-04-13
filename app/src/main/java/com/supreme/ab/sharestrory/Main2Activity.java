package com.supreme.ab.sharestrory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;
import com.supreme.ab.sharestrory.model.DataModel;
import com.supreme.ab.sharestrory.viewmodels.Main2ActivityViewModel;
import com.supreme.ab.sharestrory.viewmodels.MainActivityViewModel;

import java.util.List;

public class Main2Activity extends AppCompatActivity {
    public static final int PICK_IMAGE_REQUEST = 1;
    Button btn;
    EditText editText, link;
    ImageView img, vid;
    Main2ActivityViewModel mMain2ActivityViewModel;

    private String message;
    private String vidLocation;
    private String youtubeLink;

    private Uri mImage;
    DataModel dataModel;
    private FirebaseStorage storage;
    private DatabaseReference mDatabase;
    private StorageReference storageRef;
    private StorageReference imageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn= findViewById(R.id.button);
        editText= findViewById(R.id.editText);
        link = findViewById(R.id.linkText);
        img= findViewById(R.id.image2);
        vid= findViewById(R.id.video);


        mMain2ActivityViewModel = ViewModelProviders.of(this).get(Main2ActivityViewModel.class);
        mMain2ActivityViewModel.init();
        mMain2ActivityViewModel.getDataModel().observe(this, new Observer<DataModel>() {
            @Override
            public void onChanged(DataModel dataModels) {

            }
        });
        dataModel= mMain2ActivityViewModel.getDataM();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"text",Toast.LENGTH_SHORT).show();
                //get Text(caption)
                message = editText.getText().toString();
                //Image file chooser
                vidLocation = link.getText().toString();
                //Youtube link processor
                 youtubeLink = "Youtube Link";

                mMain2ActivityViewModel.init();
                mMain2ActivityViewModel.getDataRepository2().setDataModel(message,vidLocation,youtubeLink);
              //  mMain2ActivityViewModel.getDataRepository2().saveDataModel();

                mDatabase = FirebaseDatabase.getInstance().getReference();
                dataModel.setLink(vidLocation);
                dataModel.setText(message);
                mDatabase.child("users").child(System.currentTimeMillis()+"").setValue(dataModel);
                Toast.makeText(getApplicationContext(),"Saved the data Successfuly!",Toast.LENGTH_SHORT).show();

            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        vid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Coming soon for videos",Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void openFileChooser(){
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        img= findViewById(R.id.image2);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode == RESULT_OK
            && data != null && data.getData()!=null){
                mImage= data.getData();
                Picasso.get().load(mImage).into(img);
                storage.getInstance().getReference();
                storageRef= FirebaseStorage.getInstance().getReference().child("images");
                //Upload the fetched the image
                upload();


        }
    }

    void upload() {

        StorageReference riversRef = storageRef.child("images/" + mImage.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(mImage);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


            }
        });

        //get Image URL
        String fileName = ""+ System.currentTimeMillis();
        final StorageReference ref = storageRef.child(fileName);
        dataModel.setImg(fileName);


        ref.putFile(mImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Image Loaded", Toast.LENGTH_SHORT).show();
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                    }
                });
            }
        });

    }

    void download(){
        final StorageReference ref = storageRef.child("images/mountains.jpg");
        UploadTask uploadTask = ref.putFile(mImage);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                } else {

                }
            }
        });
    }





}






