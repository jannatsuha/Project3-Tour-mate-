package com.group.avengers.tourmate.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group.avengers.tourmate.Adapters.AllExpenseAdapter;
import com.group.avengers.tourmate.Classes.Expense;
import com.group.avengers.tourmate.Models.Event;
import com.group.avengers.tourmate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseListShow extends Fragment {

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    private List<Expense> expenses;

    private RecyclerView recyclerView;
    private AllExpenseAdapter adapter;


    public interface ExpenseListInterface{
        public void goToExpenseList(Event event);
    }
    public ExpenseListShow() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_expense_list_show, container, false);
        expenses=new ArrayList<>();

        recyclerView = view.findViewById(R.id.expenseListRV);





        final String names =(getArguments().getString("eventName"));
        final String budgets=(getArguments().getString("budget"));
        final  String ids=(getArguments().getString("id"));
        final String destinations=(getArguments().getString("destination"));
        final String departDates=(getArguments().getString("departureDate"));
        final String locations=(getArguments().getString("location"));
        final String createdDates=(getArguments().getString("createdDate"));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("eventlist").child(ids).child("expense");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Event event = dataSnapshot.getValue(Event.class);

                for(DataSnapshot data:dataSnapshot.getChildren()){
                    Expense expense =data.getValue(Expense.class);
                    expenses.add(expense);
                    //System.out.println(expense);


                    adapter = new AllExpenseAdapter(expenses);
                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);

                    recyclerView.setLayoutManager(llm);

                    recyclerView.setAdapter(adapter);

                }
               // setEventRecyclerView (expenseList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The to read event data: " + databaseError.getCode());
            }
        });


        return view;
    }

}
