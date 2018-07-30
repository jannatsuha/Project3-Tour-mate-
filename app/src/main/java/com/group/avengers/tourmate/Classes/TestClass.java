package com.group.avengers.tourmate.Classes;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class TestClass extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
