package com.group.avengers.tourmate.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.group.avengers.tourmate.Classes.CameraContainer;
import com.group.avengers.tourmate.R;

import java.util.List;

public class View_All_moment_adapter extends RecyclerView.Adapter<View_All_moment_adapter.MyViewHolder>{

    private Context context;
    List<CameraContainer> cameraContainers;


    public View_All_moment_adapter(Context context, List<CameraContainer> cameraContainers) {
        this.context = context;
        this.cameraContainers = cameraContainers;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.view_all_moment_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

//        int x=1;
//        do {
//            holder.txt_Capture.setText("Caption "+String.valueOf(x)+"");
//            x++;
//        }while (x<=imageUploadInfos.size());


//        while (x<=imageUploadInfos.size()){
//            holder.txt_Capture.setText("Caption "+String.valueOf(x)+"");
//            x++;
//        }

        for (int i=1;i<=cameraContainers.size();i++){
            holder.txt_Capture.setText("Caption "+String.valueOf(i)+"");
        }


        Picasso.get().load(cameraContainers.get(position).getImageURL()).into(holder.userImage);
        holder.txt_ImageName.setText(cameraContainers.get(position).getImageName());
        holder.txt_ImageDateAndTime.setText(cameraContainers.get(position).getDateTime());
    }

    @Override
    public int getItemCount() {
        return cameraContainers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView userImage;
        TextView txt_Capture,txt_ImageName,txt_ImageDateAndTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            userImage=itemView.findViewById(R.id.image_display_moment);
            txt_Capture=itemView.findViewById(R.id.txt_Capture_moment);
            txt_ImageDateAndTime=itemView.findViewById(R.id.txt_imageDateandTime_moment);
            txt_ImageName=itemView.findViewById(R.id.txt_imageName_moment);
        }
    }
}
