package com.group.avengers.tourmate.Fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group.avengers.tourmate.Models.Event;
import com.group.avengers.tourmate.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditEventFragment extends Fragment {

    private DatabaseReference firebaseReference;
    private FirebaseDatabase firebasedatabse;
    private EditText mTourName,mTourLocation,mTourDesination,mTourDeparatureDate,mTourBudget;
    private Button btnUpdateEvent,mDatePickerButton;
    private String eventID;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private String deparatureDate;
    private int year,day,month;
    private EditEventInterface eventInterface;

    public interface EditEventInterface{
        void gotoEditEventFragment(Event event);
        void gotoEventDetailsFragment();
    }

    public EditEventFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_edit_event, container, false);

        mTourName = view.findViewById(R.id.tourNameET);
        mTourLocation = view.findViewById(R.id.tourLocationET);
        mTourDesination = view.findViewById(R.id.tourDestinationET);
        mTourDeparatureDate = view.findViewById(R.id.tourDeparatureDateET);
        mTourBudget = view.findViewById(R.id.tourBudgetET);
        btnUpdateEvent = view.findViewById(R.id.createEventButton);
        mDatePickerButton = view.findViewById(R.id.datePickerButton);

        eventInterface= (EditEventInterface) getActivity();


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        firebasedatabse = FirebaseDatabase.getInstance();
        //String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseReference = firebasedatabse.getReference("UserData").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Eventlist");


        //initialization ();


        mDatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                        deparatureDate = Integer.toString(dd)+"/"+Integer.toString(mm+1)+"/"+Integer.toString(yy);
                        mTourDeparatureDate.setText(deparatureDate);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        final String names =(getArguments().getString("eventName"));
        final String budgets=(getArguments().getString("budget"));

        final String destinations=(getArguments().getString("destination"));
        final String departDates=(getArguments().getString("departureDate"));
        final String locations=(getArguments().getString("location"));
        final String createdDates=(getArguments().getString("createdDate"));
        final String ids=(getArguments().getString("id"));

        mTourName.setText(names);
        mTourBudget.setText(budgets);
        mTourDesination.setText(destinations);
        mTourLocation.setText(locations);

        long departdate= Long.valueOf(departDates);

        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat(" dd-MM-yyyy ", Locale.getDefault());

        java.util.Date currenTimeZone1=new java.util.Date(departdate*1000);
        mTourDeparatureDate.setText(String.valueOf(sdf.format(currenTimeZone1)));


        btnUpdateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String deparaturedate = mTourDeparatureDate.getText().toString();
                SimpleDateFormat deparatureDate = new SimpleDateFormat("dd/MM/yyyy");

                Date startDate = new Date();
                try {
                    startDate = deparatureDate.parse(deparaturedate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                deparaturedate = Long.toString(startDate.getTime());

                Event event= new Event(ids,mTourName.getText().toString(),mTourLocation.getText().toString(),mTourDesination.getText().toString()
                ,createdDates,deparaturedate,mTourBudget.getText().toString());

                FirebaseDatabase.getInstance().getReference("UserData").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Eventlist").child(ids).setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(), "Update Succesfully", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    }
                });
            }

        });

        return view;
    }

}
