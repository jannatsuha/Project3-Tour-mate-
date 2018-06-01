package com.group.avengers.tourmate.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.group.avengers.tourmate.Classes.CameraContainer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GalleryAdapter extends BaseAdapter {

    private Context context;
    private List<CameraContainer> containers;

    public GalleryAdapter(Context context, List<CameraContainer> containers) {
        this.context = context;
        this.containers=containers;
    }

    @Override
    public int getCount() {
        return containers.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        if (convertView==null){
            imageView=new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(240,210));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0,0,2,5);
        }else {
            imageView= (ImageView) convertView;
        }
        //imageView.setImageResource(Integer.parseInt(imageUploadInfos.get(position).getImageURL()));
        Picasso.get().load(containers.get(position).getImageURL()).into(imageView);


        return imageView;
    }
}
