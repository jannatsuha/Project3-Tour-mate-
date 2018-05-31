package com.group.avengers.tourmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.group.avengers.tourmate.Classes.CameraContainer;
import com.group.avengers.tourmate.Classes.Expense;
import com.group.avengers.tourmate.Fragments.AddMoreBudgetFragment;
import com.group.avengers.tourmate.Fragments.AddnewExpenseFragment;
import com.group.avengers.tourmate.Fragments.EventDetailFragment;
import com.group.avengers.tourmate.Fragments.EventListFragment;
import com.group.avengers.tourmate.Fragments.EventRegisterFragment;
import com.group.avengers.tourmate.Fragments.ExpenseListShow;
import com.group.avengers.tourmate.Fragments.TakeAphotoFragment;
import com.group.avengers.tourmate.Models.Event;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener
        ,EventRegisterFragment.TourRegistrationInterface
        ,EventListFragment.EventListInterface
        ,EventDetailFragment.EventDetailInterface
        ,AddMoreBudgetFragment.AddMoreBudgetInterface
        ,AddnewExpenseFragment.AddnewExpenseInterface
        ,ExpenseListShow.ExpenseListInterface
        ,TakeAphotoFragment.TakeaPhotoInterface
        {


    private android.support.v4.app.FragmentManager fm;
    private android.support.v4.app.FragmentTransaction ft;
    private FrameLayout fragmentContainer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentContainer = findViewById(R.id.fragmentContainer);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();


        //------------------------------------------------------------------------------------------
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEventRegistration();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //------------------------------------------------------------------------------------------

    } //Enf Of On Create



    //----------------------------------------------------------------------------------------------
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        /*if (getLocalClassName().equals("MainActivity"))
        {
            MenuItem mHomeItem = menu.findItem(R.id.item_home);
            mHomeItem.setVisible(false);
        }*/
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.item_home) {
            goToEventList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addNewEvent)
        {
            goToEventRegistration();

        } else if (id == R.id.nav_showAllEvent)
        {
                goToEventList();

        } else if (id == R.id.nav_myactivity)
        {

        }
//        else if (id==R.id.nav_nearbyPlace){
//            gotoNearbyPlaceList();
//        }
        else if (id == R.id.nav_settings)
        {

        } else if (id == R.id.nav_share)
        {
            /*Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
            i.putExtra(Intent.EXTRA_TEXT, "http://www.facebook.com/aqibmehedi007");
            startActivity(Intent.createChooser(i, "Share URL"));*/

        } else if (id == R.id.nav_logout)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    private void gotoNearbyPlaceList() {
//        ft=fm.beginTransaction();
//        NearByplace nearByplace=new NearByplace();
//        ft.replace(R.id.fragmentContainer,nearByplace);
//        ft.addToBackStack("goto Nearby Place Section");
//        ft.commit();
//    }


    @Override
    public void goToEventRegistration() {
        ft = fm.beginTransaction();
        EventRegisterFragment eventRegisterFragment = new EventRegisterFragment();
        ft.replace(R.id.fragmentContainer, eventRegisterFragment);
        ft.addToBackStack("goToEventRegistration");
        ft.commit();
    }

    @Override
    public void goToEventList() {
        ft = fm.beginTransaction();
        EventListFragment eventListFragment = new EventListFragment();
        ft.replace(R.id.fragmentContainer, eventListFragment);
        ft.addToBackStack("goToEventList");
        ft.commit();
    }

    @Override
    public void goToEventDetail(Event event) {
        Bundle bundle = new Bundle();
        bundle.putString("eventName",event.getEventName());
        bundle.putString("budget",event.getBudget());
        bundle.putString("destination",event.getDestination());
        bundle.putString("location",event.getLocation());
        bundle.putString("departureDate",event.getDeparatureDate());
        bundle.putString("id",event.getId());
        bundle.putString("createdDate",event.getCreatedDate());

       // bundle.putString("expense",event.getExpense());


        ft = fm.beginTransaction();
        EventDetailFragment eventDetailFragment = new EventDetailFragment();
        eventDetailFragment.setArguments(bundle);
        ft.replace(R.id.fragmentContainer, eventDetailFragment);
        ft.addToBackStack("goToEventDetail");
        ft.commit();
    }

    @Override
    public void gotoAddMoreBudget(Event event) {
        Bundle bundle = new Bundle();
        bundle.putString("eventName",event.getEventName());
        bundle.putString("budget",event.getBudget());
        bundle.putString("destination",event.getDestination());
        bundle.putString("location",event.getLocation());
        bundle.putString("departureDate",event.getDeparatureDate());
        bundle.putString("id",event.getId());
        bundle.putString("createdDate",event.getCreatedDate());

        ft = fm.beginTransaction();
        AddMoreBudgetFragment addMoreBudgetFragment1 = new AddMoreBudgetFragment();
        addMoreBudgetFragment1.setArguments(bundle);
        ft.replace(R.id.fragmentContainer, addMoreBudgetFragment1);
        //ft.addToBackStack("gotoAddMoreBudget");
        ft.commit();
    }

    @Override
    public void gotoAddnewExpense(Event event) {

        Bundle bundle = new Bundle();
        bundle.putString("eventName",event.getEventName());
        bundle.putString("budget",event.getBudget());
        bundle.putString("destination",event.getDestination());
        bundle.putString("location",event.getLocation());
        bundle.putString("departureDate",event.getDeparatureDate());
        bundle.putString("id",event.getId());
        bundle.putString("createdDate",event.getCreatedDate());

        ft = fm.beginTransaction();
        AddnewExpenseFragment addnewExpenseFragment = new AddnewExpenseFragment();
        addnewExpenseFragment.setArguments(bundle);
        ft.replace(R.id.fragmentContainer, addnewExpenseFragment);
        //ft.addToBackStack("gotoAddnewExpense");
        ft.commit();
    }

    @Override
    public void goToExpenseList(Event event) {

        Bundle bundle = new Bundle();
        bundle.putString("eventName",event.getEventName());
        bundle.putString("budget",event.getBudget());
        bundle.putString("destination",event.getDestination());
        bundle.putString("location",event.getLocation());
        bundle.putString("departureDate",event.getDeparatureDate());
        bundle.putString("id",event.getId());
        bundle.putString("createdDate",event.getCreatedDate());

        ft = fm.beginTransaction();
        ExpenseListShow expenseListShow = new ExpenseListShow();
        expenseListShow.setArguments(bundle);
        ft.replace(R.id.fragmentContainer, expenseListShow);
        ft.addToBackStack("goToExpenseList");
        ft.commit();

    }

    @Override
    public void gotoimage(CameraContainer cameraContainer) {

        Bundle bundle=new Bundle();
        bundle.putString("imageName",cameraContainer.getImageName());
        bundle.putString("imageUrl",cameraContainer.getImageURL());
        bundle.putString("imageDateTime",cameraContainer.getDateTime());
        bundle.putString("id",cameraContainer.getId());

        ft=fm.beginTransaction();
        TakeAphotoFragment aphotoFragment=new TakeAphotoFragment();
        aphotoFragment.setArguments(bundle);
        ft.replace(R.id.fragmentContainer,aphotoFragment);
        ft.addToBackStack("gotocameraFragment");
        ft.commit();

    }


            //----------------------------------------------------------------------------------------------

}
