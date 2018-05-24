package com.group.avengers.tourmate.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.group.avengers.tourmate.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearByplace extends Fragment {

    private Spinner spinnerCatagory,spinnerDestnce;
    private Button btnFind;
    private ListView lstPlaceSearchItem;

    public NearByplace() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_near_byplace, container, false);

        String[] itemcatagory={"Restaurant","Bank","ATM","Hospital","Shopping Mall","Mosque","Bus Station","Police Station"};
        String[] destance={"0.5km","1km","1.5km","2km","2.5km","3km","4km","5km","10km"};

        spinnerCatagory=view.findViewById(R.id.spinnerCategory);
        spinnerDestnce=view.findViewById(R.id.spinnerDistance);
        btnFind=view.findViewById(R.id.btnFind);
        lstPlaceSearchItem=view.findViewById(R.id.listView_displayNearbyPlace);

        ArrayAdapter itemarrayAdapter=new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,itemcatagory);
        ArrayAdapter destancearrayAdapter=new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,destance);

        spinnerCatagory.setAdapter(itemarrayAdapter);
        spinnerDestnce.setAdapter(destancearrayAdapter);

        return view;
    }

}
