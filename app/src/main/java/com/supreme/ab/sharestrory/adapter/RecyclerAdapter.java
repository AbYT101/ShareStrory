package com.supreme.ab.sharestrory.adapter;

import android.content.Context;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.supreme.ab.sharestrory.R;
import com.supreme.ab.sharestrory.model.DataModel;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DataModel> mDataModel = new ArrayList<>();
    private Context mContext;

    StorageReference httpsReference;


    public RecyclerAdapter(Context context, List<DataModel> nicePlaces) {
        mDataModel = nicePlaces;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {

        // Set the name of the 'NicePlace'
        ((ViewHolder)viewHolder).mName.setText(mDataModel.get(i).getText());


            httpsReference=FirebaseStorage.getInstance().getReference("images/"+mDataModel.get(i).getImg());

        Log.d("Firebase Image loc", mDataModel.get(i).getImg());
       httpsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(((ViewHolder)viewHolder).mImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        // Set the view count
        ((ViewHolder)viewHolder).mCount.setText("View = " + mDataModel.get(i).getViewCount());


    }

    @Override
    public int getItemCount() {
        return mDataModel.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImage;
        private TextView mCount;
        private TextView mName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.image);
            mCount = itemView.findViewById(R.id.viewCount);
            mName = itemView.findViewById(R.id.image_name);

        }
    }
}
