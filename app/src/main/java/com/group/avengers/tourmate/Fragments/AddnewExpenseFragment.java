package com.group.avengers.tourmate.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group.avengers.tourmate.Classes.Expense;
import com.group.avengers.tourmate.Models.Event;
import com.group.avengers.tourmate.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddnewExpenseFragment extends Fragment {


    private  EditText etAmount1,etComment1;
    private  Button btnAdd;
    private String totalExpense, ids;
    int expOld;
    String expOld1;
    private EventDetailFragment.EventDetailInterface eventDetailInterface;
    private DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;


    public interface AddnewExpenseInterface {
        public void gotoAddnewExpense (Event event);
    }

    public AddnewExpenseFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      View view= inflater.inflate(R.layout.fragment_addnew_expense, container, false);

        etAmount1=view.findViewById(R.id.expAmount11);
        etComment1=view.findViewById(R.id.expComment11);
        btnAdd=view.findViewById(R.id.btnExpAdd);
        eventDetailInterface= (EventDetailFragment.EventDetailInterface) getActivity();


        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Expense");


        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
       final  String date = df.format(Calendar.getInstance().getTime());



        if (getArguments()!=null) {



            final String names =(getArguments().getString("eventName"));
            final String budgets=(getArguments().getString("budget"));
            ids=(getArguments().getString("id"));
            final String destinations=(getArguments().getString("destination"));
            final String departDates=(getArguments().getString("departureDate"));
            final String locations=(getArguments().getString("location"));
            final String createdDates=(getArguments().getString("createdDate"));

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final String amounts= etAmount1.getText().toString();
                    final String comments=etComment1.getText().toString();
                    final int expNew= Integer.parseInt(amounts);

                    String key= databaseReference.push().getKey();

                    final Expense expense= new Expense(amounts,date,comments);

                    FirebaseDatabase.getInstance().getReference().child("eventlist").child(ids)
                            .child("expense").child(key).setValue(expense);




                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference= firebaseDatabase.getReference("eventlist");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            expOld1= dataSnapshot.child(ids).child("totalExpense").getValue(String.class);
                            Toast.makeText(getActivity(), expOld1+"", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


//                    expOld= Integer.parseInt(expOld1);
//                    int totalExpenseInt= expOld +expNew;
//                    String totalExpFinal= String.valueOf(totalExpenseInt);

                    HashMap<String, Object> totalExp = new HashMap<>();
                    totalExp.put("totalExpense", expense.getAmount());
                    FirebaseDatabase.getInstance().getReference().child("eventlist").child(ids).updateChildren(totalExp);



                    Event event= new Event(ids,names,locations,destinations,createdDates,departDates,budgets);
                    eventDetailInterface.goToEventDetail(event);
                }
            });

        }
        else {
            Toast.makeText(getActivity(), "Null value", Toast.LENGTH_SHORT).show();
        }


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
