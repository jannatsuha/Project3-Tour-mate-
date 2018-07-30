package com.group.avengers.tourmate.Fragments;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventRegisterFragment extends Fragment {


    String currentUser;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private EditText mTourName,mTourLocation,mTourDesination,mTourDeparatureDate,mTourBudget;
    private Button mCreateEventButton,mDatePickerButton;
    private String eventID;
    private Calendar calendar;
    private FirebaseUser user;
    private DatePickerDialog datePickerDialog;
    private String thisDate;
    private String deparatureDate;
    private int year,day,month;

    public interface TourRegistrationInterface {
        public void goToEventRegistration();
    }
    public EventRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_register, container, false);

        mTourName = view.findViewById(R.id.tourNameET);
        mTourLocation = view.findViewById(R.id.tourLocationET);
        mTourDesination = view.findViewById(R.id.tourDestinationET);
        mTourDeparatureDate = view.findViewById(R.id.tourDeparatureDateET);
        mTourBudget = view.findViewById(R.id.tourBudgetET);
        mCreateEventButton = view.findViewById(R.id.createEventButton);
        mDatePickerButton = view.findViewById(R.id.datePickerButton);




        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);



        // mFirebaseDatabase = mFirebaseInstance.getReference("UserData").child(String.valueOf(user)).child("Eventlist");


        mFirebaseInstance = FirebaseDatabase.getInstance();
       // mFirebaseDatabase = mFirebaseInstance.getReference("eventlist");
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mFirebaseDatabase = mFirebaseInstance.getReference("UserData").child(currentUser).child("Eventlist");


        initialization ();






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

        mCreateEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventID = mFirebaseDatabase.push().getKey();
                String name = mTourName.getText().toString();
                String location = mTourLocation.getText().toString();
                String destination = mTourDesination.getText().toString();
                String deparaturedate = mTourDeparatureDate.getText().toString();
                String budget = mTourBudget.getText().toString();

                SimpleDateFormat deparatureDate = new SimpleDateFormat("dd/MM/yyyy");
                Date todayDate = new Date();

                Date startDate = new Date();
                try {
                    startDate = deparatureDate.parse(deparaturedate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                deparaturedate = Long.toString(startDate.getTime());
                long currentDayMillis = todayDate.getTime();
                String thisDate = Long.toString(currentDayMillis);
                Event event = new Event(eventID,name,location,destination,thisDate,deparaturedate,budget);
                createNewEvent (event);
            }


        });

        return view;
    }

    private void initialization() {

        mFirebaseInstance.getReference("app_title").setValue("Tour Mate");
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);
                Toast.makeText(getActivity(), appTitle, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

    }




    private void createNewEvent(Event event) {
        mFirebaseDatabase.child(eventID).setValue(event);
        addUserChangeListener();
    }

    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(eventID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);

                // Check for null
                if (event == null) {
                    Log.e(TAG, "New Event is null!");
                    return;
                }else
                    Toast.makeText(getActivity(), "New Event Added", Toast.LENGTH_SHORT).show();

//                HashMap<String, Object> totalExp = new HashMap<>();
//                totalExp.put("totalExpense", "00");
                FirebaseDatabase.getInstance().getReference("UserData").child(currentUser).child("Eventlist").child(eventID).child("totalExpense").setValue("0");
                // clear edit text
                mTourName.setText("");
                mTourLocation.setText("");
                mTourDesination.setText("");
                mTourDeparatureDate.setText("");
                mTourBudget.setText("");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }




}
