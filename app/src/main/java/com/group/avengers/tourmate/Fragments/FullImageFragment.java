package com.group.avengers.tourmate.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.group.avengers.tourmate.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class FullImageFragment extends Fragment {

    private ImageView imageFullScren;

    public FullImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_full_image, container, false);

        String imagePosition=getArguments().getString("image");
        imageFullScren=view.findViewById(R.id.image_Details);
        Picasso.get().load(imagePosition).into(imageFullScren);

        return view;
    }

}
