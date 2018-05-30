package com.group.avengers.tourmate.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group.avengers.tourmate.Adapters.AllEventAdapter;
import com.group.avengers.tourmate.Models.Event;
import com.group.avengers.tourmate.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventListFragment extends Fragment {

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private RecyclerView mEventRecyclerView;
    private AllEventAdapter allEventAdapter;
    private List<Event> eventList = new ArrayList<>();
    LinearLayoutManager llm;
    private SkeletonScreen skeletonScreen;

    public interface EventListInterface{
        public void goToEventList();
    }

    public EventListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        mEventRecyclerView = view.findViewById(R.id.eventListRV);
        llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mEventRecyclerView.setLayoutManager(llm);

       // Toast.makeText(getActivity(), "Hello every I am here", Toast.LENGTH_SHORT).show();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("eventlist");

        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Event event = dataSnapshot.getValue(Event.class);
                eventList.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){

                    Event event =data.getValue(Event.class);
                    eventList.add(event);
                    System.out.println(event);

                }
                setEventRecyclerView (eventList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The to read event data: " + databaseError.getCode());
            }
        });






        // Inflate the layout for this fragment
        return view;
    }

    private void setEventRecyclerView(List<Event> eventList) {

        allEventAdapter = new AllEventAdapter(eventList,getActivity());
        skeletonScreen = Skeleton.bind(mEventRecyclerView)
                .adapter(allEventAdapter)
                .load(R.layout.item_skeleton_news)
                .show();

        mEventRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                skeletonScreen.hide();
            }
        }, 1000);

        //mEventRecyclerView.setAdapter(allEventAdapter);
        //allEventAdapter.notifyDataSetChanged();
    }

}
