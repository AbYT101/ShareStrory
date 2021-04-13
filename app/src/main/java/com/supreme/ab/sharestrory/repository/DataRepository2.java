package com.supreme.ab.sharestrory.repository;

import androidx.lifecycle.MutableLiveData;

import com.supreme.ab.sharestrory.model.DataModel;

public class DataRepository2 {
    private static DataRepository2 instance;
    private DataModel dataModel;

    public static DataRepository2 getInstance(){
        if(instance == null){
            instance = new DataRepository2();
        }
        return instance;
    }
    //Initialize an empty DataModel object
    public MutableLiveData<DataModel> getDataModel(){
        dataModel= new DataModel("","","");
       MutableLiveData<DataModel> mutableLiveData= new MutableLiveData<DataModel>();
       mutableLiveData.setValue(dataModel);
       return mutableLiveData;
    }

    public void setDataModel(String text, String img, String link){
        dataModel= new DataModel(text,img,link);
    }
    public void saveDataModel(){
        //Firebase proccessing done here
    }

}
