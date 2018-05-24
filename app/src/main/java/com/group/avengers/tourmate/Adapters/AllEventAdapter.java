package com.group.avengers.tourmate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group.avengers.tourmate.R;
import com.group.avengers.tourmate.Models.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AllEventAdapter extends RecyclerView.Adapter<AllEventAdapter.Viewholder> {

    public static FirebaseDatabase firebaseDatabase;
    public static  DatabaseReference databaseReference;
    Date date1,date2;
    public static List<Event> eventList;
    private Event model;
    private Context context;

    public AllEventAdapter(List<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("eventlist");

    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_model, parent, false);

        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {

        model = eventList.get(position);
        holder.tourNameTV.setText(model.getEventName());
        holder.createdDateTV.setText("Created On: "+model.getCreatedDate());
        holder.startDateTV.setText("Starts at: "+model.getDeparatureDate());
        String daysLeft = getDaysLeft ();

        //---------------------------------------- Date To Days ------------------------------------
        String string1 = model.getDeparatureDate();
        String string2 = model.getCreatedDate();
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        try {
            date1 = sdf.parse(string1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            date2 = sdf.parse(string2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = date1.getTime()-date2.getTime();
        long days =  (millis / (60*60*24*1000)) % 365;
        //------------------------------------------------------------------------------------------
        holder.daysLeftTV.setText(days+" Days Left");

    }



    public class Viewholder extends RecyclerView.ViewHolder {

        // 1. Declare your Views here

        public TextView tourNameTV;
        public TextView createdDateTV;
        public TextView startDateTV;
        public TextView daysLeftTV;


        public Viewholder(final View itemView) {

            super(itemView);
            tourNameTV = (TextView)itemView.findViewById(R.id.tourNameTV);
            createdDateTV = (TextView)itemView.findViewById(R.id.createdDateTV);
            startDateTV = (TextView)itemView.findViewById(R.id.startDateTV);
            daysLeftTV = (TextView)itemView.findViewById(R.id.daysLeftTV);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), eventList.get(getAdapterPosition()).getEventName(), Toast.LENGTH_SHORT).show();
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    databaseReference.child(eventList.get(getAdapterPosition()).getId()).removeValue();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Toast.makeText(itemView.getContext(), "Event Deleted", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    return false;
                }
            });

        }
    }



    private String getDaysLeft() {



        return "";
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

}
