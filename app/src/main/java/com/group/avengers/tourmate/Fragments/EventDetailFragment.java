package com.group.avengers.tourmate.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group.avengers.tourmate.MainActivity;
import com.group.avengers.tourmate.Models.Event;
import com.group.avengers.tourmate.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailFragment extends Fragment {

    private TextView eventNameShow,addExpence,viewExpense,addMoreBudget,takePhoto,viewGallery,viewMomonts,editEvent,deleteEvent,budSts1,budSta2;
    private SeekBar seekBarBudget;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private AddMoreBudgetFragment.AddMoreBudgetInterface addMoreBudgetInterface;
    private Event modelEvent;

    public interface EventDetailInterface {
        public void goToEventDetail (Event event);
    }
    public EventDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(getArguments().getString("eventName"));
        View view= inflater.inflate(R.layout.activity_event_details, container, false);
        addMoreBudgetInterface = (AddMoreBudgetFragment.AddMoreBudgetInterface) getActivity();
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
        budSts1=view.findViewById(R.id.bud1);
        budSta2=view.findViewById(R.id.bud2);

        if (getArguments()!=null)
        {
            Toast.makeText(getActivity(), getArguments().getString("eventName"), Toast.LENGTH_SHORT).show();



            final String names =(getArguments().getString("eventName"));
            final String budgets=(getArguments().getString("budget"));
            final String ids=(getArguments().getString("id"));
            final String destinations=(getArguments().getString("destination"));
            final String departDates=(getArguments().getString("departureDate"));
            final String locations=(getArguments().getString("location"));
            final String createdDates=(getArguments().getString("createdDate"));
            eventNameShow.setText(names);
            budSta2.setText(budgets);
            budSts1.setText("0");
            final Event event = new Event(ids,names,locations,destinations,createdDates,departDates,budgets);

            String idOfEvent=(getArguments().getString("id"));

            addMoreBudget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Add more Budget", Toast.LENGTH_SHORT).show();
                    addMoreBudgetInterface.gotoAddMoreBudget(event);
                }
            });

            seekBarBudget.setMax(0);
            seekBarBudget.setMax(Integer.parseInt(getArguments().getString("budget")));
            seekBarBudget.setProgress(0);


//,,,,,,,,,,,,,,,,,,,,expense adding in event tree


        }
        return view;
    }

}
