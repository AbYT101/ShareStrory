package com.supreme.ab.sharestrory.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.supreme.ab.sharestrory.model.DataModel;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

//Singleton pattern
public class DataRepository {

    private static DataRepository instance;
    private ArrayList<DataModel> dataSet = new ArrayList<>();
    private DatabaseReference mDatabase;


    public static DataRepository getInstance(){
        if(instance == null){
            instance = new DataRepository();
        }
        return instance;
    }


    // Pretend to get data from a webservice or online source
    public MutableLiveData<List<DataModel>> getNicePlaces(){
        if(dataSet.isEmpty()) {
            getDataModel();
        }
        MutableLiveData<List<DataModel>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }


    private void setDataModel(){
        dataSet.add(
                new DataModel("12345678",
                        "Havasu Falls", "1234")
        );
        dataSet.add(
                new DataModel("abcdefg",
                        "Trondheim", "12345")
        );

    }

    void getDataModel(){
        // My top posts by number of stars
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    DataModel dm= postSnapshot.getValue(DataModel.class);
                    Log.d("data is", dm.getText());
                    dataSet.add(new DataModel(dm.getText(),dm.getLink(),dm.getViewCount()+""));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }
}



