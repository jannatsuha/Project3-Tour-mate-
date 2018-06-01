package com.group.avengers.tourmate.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group.avengers.tourmate.Adapters.GalleryAdapter;
import com.group.avengers.tourmate.Adapters.View_All_moment_adapter;
import com.group.avengers.tourmate.Classes.CameraContainer;
import com.group.avengers.tourmate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {

    private List<CameraContainer> containers=new ArrayList<>();
    private DatabaseReference reference;
    private ProgressDialog dialog;
    private GalleryAdapter adapter;
    private GridView gridView;
    private ViewGalleryInterface galleryInterface;

    public GalleryFragment() {
        // Required empty public constructor
    }

    public interface  ViewGalleryInterface{
        void gotoGalleryFragment(CameraContainer cameraContainer);
        void gotoFullGalleryFragment(CameraContainer cameraContainer);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_gallery, container, false);

        gridView=view.findViewById(R.id.gridView);

        String id=getArguments().getString("id");

        dialog=new ProgressDialog(getContext());
        dialog.setMessage("Please Whit.....");
        dialog.show();

        galleryInterface= (ViewGalleryInterface) getActivity();

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("eventlist").child(id).child("images").child(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                containers.clear();

                for (DataSnapshot data:dataSnapshot.getChildren()){
                    dialog.dismiss();
                    CameraContainer info=data.getValue(CameraContainer.class);
                    containers.add(info);
                    adapter=new GalleryAdapter(getActivity(),containers);
                    adapter.notifyDataSetChanged();
                    gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            CameraContainer imageposition=containers.get(position);
                            galleryInterface.gotoFullGalleryFragment(imageposition);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
            }
        });

        return view;
    }

}
