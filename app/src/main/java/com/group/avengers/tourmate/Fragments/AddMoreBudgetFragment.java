package com.group.avengers.tourmate.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.group.avengers.tourmate.Models.Event;
import com.group.avengers.tourmate.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMoreBudgetFragment extends Fragment {

    AddMoreBudgetInterface addMoreBudgetInterface;
    EventDetailFragment eventDetailFragment;
    EventDetailFragment.EventDetailInterface eventDetailInterface;
    EditText etMoreBudget;
    Button btnAdd;

    public interface AddMoreBudgetInterface {
        public void gotoAddMoreBudget (Event event);
    }

    public AddMoreBudgetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(getArguments().getString("eventName"));
       View view= inflater.inflate(R.layout.fragment_add_more_budget, container, false);

       etMoreBudget=view.findViewById(R.id.moreBudget);
       btnAdd=view.findViewById(R.id.btnAdd);
       eventDetailInterface= (EventDetailFragment.EventDetailInterface) getActivity();

        final String names =(getArguments().getString("eventName"));
        final String budgets=(getArguments().getString("budget"));
        final String ids=(getArguments().getString("id"));
        final String destinations=(getArguments().getString("destination"));
        final String departDates=(getArguments().getString("departureDate"));
        final String locations=(getArguments().getString("location"));
        final String createdDates=(getArguments().getString("createdDate"));



       btnAdd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               final int BudgetOld= Integer.parseInt(budgets);
               final int BudgetNew= Integer.parseInt(etMoreBudget.getText().toString());
               final int finalBudget=BudgetOld+BudgetNew;
               final String strBudget= String.valueOf(finalBudget);
               final  String tempBudget= strBudget;

               HashMap<String, Object> strBudget1 = new HashMap<>();
               strBudget1.put("budget", strBudget);
               String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
               FirebaseDatabase.getInstance().getReference("UserData").child(currentUser).child("Eventlist").child(ids).updateChildren(strBudget1);
               Toast.makeText(getActivity(), "More Budget Added", Toast.LENGTH_SHORT).show();

               Event event= new Event(ids,names,locations,destinations,createdDates,departDates,tempBudget);
               eventDetailInterface.goToEventDetail(event);

           }
       });

        return view;
    }

}
