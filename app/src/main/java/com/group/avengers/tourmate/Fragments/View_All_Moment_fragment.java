package com.group.avengers.tourmate.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group.avengers.tourmate.Adapters.View_All_moment_adapter;
import com.group.avengers.tourmate.Classes.CameraContainer;
import com.group.avengers.tourmate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class View_All_Moment_fragment extends Fragment {

    private RecyclerView recyclerView;
    private List<CameraContainer> containers=new ArrayList<>();
    private DatabaseReference reference;
    private ProgressDialog dialog;
    private View_All_moment_adapter adapter;

    public View_All_Moment_fragment() {
        // Required empty public constructor
    }

    public interface  View_all_moment_interface{
        void gotoAllMomentsection(CameraContainer cameraContainer);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_view__all__moment_fragment, container, false);

        recyclerView=view.findViewById(R.id.recyclerView);
        getActivity().setTitle("Moments");


        String id=getArguments().getString("id");

        dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Please Whit.....");
        dialog.show();

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
                    adapter=new View_All_moment_adapter(getActivity(),containers);
                    adapter.notifyDataSetChanged();

                    LinearLayoutManager llm=new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setAdapter(adapter);

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
