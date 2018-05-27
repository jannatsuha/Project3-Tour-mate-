package com.group.avengers.tourmate.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.group.avengers.tourmate.NearByPlaces.NearbyByPlaceContent;
import com.group.avengers.tourmate.NearByPlaces.Result;
import com.group.avengers.tourmate.R;

import java.util.List;

public class NearByPlaceAdapter extends ArrayAdapter<NearbyByPlaceContent>{


    private Context context;
    private List<NearbyByPlaceContent> getNearbyitems;


    public NearByPlaceAdapter(@NonNull Context context, List<NearbyByPlaceContent> getNearbyitems) {
        super(context,R.layout.nearby_place_custome_row , getNearbyitems);
        this.context=context;
        this.getNearbyitems=getNearbyitems;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(context);
        convertView=inflater.inflate(R.layout.nearby_place_custome_row,parent,false);
        TextView txtitemName=convertView.findViewById(R.id.txt_nearby_place_itemName);
        TextView txtitemLocation=convertView.findViewById(R.id.txt_nearby_place_itemAddress);

        for (Result result:getNearbyitems.get(position).getResults()){

            txtitemName.setText(result.getName());
            txtitemLocation.setText(result.getVicinity());
        }



        return convertView;
    }
}
