package com.group.avengers.tourmate.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group.avengers.tourmate.Classes.CameraContainer;
import com.group.avengers.tourmate.Classes.Expense;
import com.group.avengers.tourmate.MainActivity;
import com.group.avengers.tourmate.Models.Event;
import com.group.avengers.tourmate.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailFragment extends Fragment {

    private Expense expense;
    private TextView eventNameShow,addExpence,viewExpense,addMoreBudget,takePhoto,
            viewGallery,viewMomonts,editEvent,deleteEvent,budSts1,budSta2;
    private ProgressBar seekBarBudget;
    private   String ids;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private AddMoreBudgetFragment.AddMoreBudgetInterface addMoreBudgetInterface;
    private AddnewExpenseFragment.AddnewExpenseInterface addnewExpenseInterface;
    private ExpenseListShow.ExpenseListInterface expenseListInterface;
    private TakeAphotoFragment.TakeaPhotoInterface takeaPhotoInterface;
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

        // initialize all event interface
        addMoreBudgetInterface = (AddMoreBudgetFragment.AddMoreBudgetInterface) getActivity();
        addnewExpenseInterface= (AddnewExpenseFragment.AddnewExpenseInterface) getActivity();
        expenseListInterface= (ExpenseListShow.ExpenseListInterface) getActivity();
        takeaPhotoInterface= (TakeAphotoFragment.TakeaPhotoInterface) getActivity();



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
        ids=(getArguments().getString("id"));

        if (getArguments()!=null)
        {
           // Toast.makeText(getActivity(), getArguments().getString("eventName"), Toast.LENGTH_SHORT).show();



            final String names =(getArguments().getString("eventName"));
            final String budgets=(getArguments().getString("budget"));

            final String destinations=(getArguments().getString("destination"));
            final String departDates=(getArguments().getString("departureDate"));
            final String locations=(getArguments().getString("location"));
            final String createdDates=(getArguments().getString("createdDate"));
            //final String expAmounts=(getArguments().getString("expense"));

            // get camera argument

            String imagename=getArguments().getString("imageName");
            String imageUrl=getArguments().getString("imageUrl");
            String currentDateAndTime=getArguments().getString("imageDateTime");
            String id=getArguments().getString("id");

            final CameraContainer cameraContainer=new CameraContainer(id,imagename,imageUrl,currentDateAndTime);
             eventNameShow.setText(names);
            budSta2.setText(budgets);
           // budSts1.setText("0");
          //  budSts1.setText(expAmounts);
            final Event event = new Event(ids,names,locations,destinations,createdDates,departDates,budgets);

            String idOfEvent=(getArguments().getString("id"));

            addMoreBudget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // Toast.makeText(getActivity(), "Add more Budget", Toast.LENGTH_SHORT).show();
                    addMoreBudgetInterface.gotoAddMoreBudget(event);
                }
            });


            seekBarBudget.setMax(Integer.parseInt(getArguments().getString("budget")));
          //  seekBarBudget.setMin();



            addExpence.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // Toast.makeText(getActivity(), "Add more Budget", Toast.LENGTH_SHORT).show();
                    addnewExpenseInterface.gotoAddnewExpense(event);
                }
            });

            viewExpense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    expenseListInterface.goToExpenseList(event );

                }
            });

            takePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takeaPhotoInterface.gotoimage(cameraContainer);
                }
            });

//,,,,,,,,,,,,,,,,,,,,expense adding in event tree

        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference("eventlist").child(ids).child("expense");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
/*
                String exp= dataSnapshot.getValue(String.class);
                budSts1.setText(exp);
                seekBarBudget.setProgress(Integer.parseInt(exp));*/
                List<Expense>expenses = new ArrayList<>();
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    Expense e = d.getValue(Expense.class);
                    expenses.add(e);
                }
                double amount = 0.0;
                for(Expense e : expenses){
                    amount += Double.parseDouble(e.getAmount());
                }
                budSts1.setText(String.valueOf(amount));
                seekBarBudget.setProgress((int) amount);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
