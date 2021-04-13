package com.supreme.ab.sharestrory.viewmodels;

import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.supreme.ab.sharestrory.model.DataModel;
import com.supreme.ab.sharestrory.repository.DataRepository2;

import java.util.List;

public class Main2ActivityViewModel extends ViewModel {
    private MutableLiveData<DataModel> mutableDataModel;
    private DataRepository2 dataRepository2;

    private Uri mImage;
    private DatabaseReference mDatabase;
    private StorageReference storageRef;

    public void init(){
        if(dataRepository2!= null){
            return;
        }
        else{
            dataRepository2= DataRepository2.getInstance();
            mutableDataModel= dataRepository2.getDataModel();
        }
    }

    public DataRepository2 getDataRepository2() {
        return dataRepository2;
    }

    public LiveData<DataModel> getDataModel(){
        return  mutableDataModel;
    }

    public DataModel getDataM(){
        return new DataModel("","","");
    }






}
