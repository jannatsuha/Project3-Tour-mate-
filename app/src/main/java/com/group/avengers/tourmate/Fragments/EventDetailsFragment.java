package com.group.avengers.tourmate.Fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group.avengers.tourmate.Models.Event;
import com.group.avengers.tourmate.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailsFragment extends Fragment {

   private TextView eventNameShow,addExpence,viewExpense,addMoreBudget,takePhoto,viewGallery,viewMomonts,editEvent,deleteEvent;
   private SeekBar seekBarBudget;
   private FirebaseDatabase firebaseDatabase;
   private FirebaseAuth firebaseAuth;
   private DatabaseReference databaseReference;

    public EventDetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_details, container, false);
        eventNameShow=view.findViewById(R.id.enentNameShow);
        addExpence=view.findViewById(R.id.addNewExpense);
        viewExpense=view.findViewById(R.id.viewAllExpense);
        addMoreBudget=view.findViewById(R.id.addMoreBudget);
        takePhoto=view.findViewById(R.id.takePhoto);
        viewGallery=view.findViewById(R.id.viewGallery);
        viewMomonts=view.findViewById(R.id.viewAllMoments);
        editEvent=view.findViewById(R.id.editEvent);
        deleteEvent=view.findViewById(R.id.deleteEvent);
        seekBarBudget=view.findViewById(R.id.seekBar);

        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("EventDetails");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Event detailEvent= dataSnapshot.getValue(Event.class);
                eventNameShow.setText((CharSequence) detailEvent.getEventName());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }



}
