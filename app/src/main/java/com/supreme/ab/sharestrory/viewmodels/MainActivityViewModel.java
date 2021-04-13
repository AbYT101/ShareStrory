package com.supreme.ab.sharestrory.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.supreme.ab.sharestrory.model.DataModel;
import com.supreme.ab.sharestrory.repository.DataRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<DataModel>> mData;
    private DataRepository mDataRepository;


    //Singleton pattern applied here
    public void init(){
        if(mData != null){
            return;
        }
        mDataRepository = DataRepository.getInstance();
        mData = mDataRepository.getNicePlaces();
    }


    public LiveData<List<DataModel>> getmData() {
        return mData;
    }
}
