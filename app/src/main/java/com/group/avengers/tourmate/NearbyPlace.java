package com.group.avengers.tourmate;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.group.avengers.tourmate.Adapters.NearByPlaceAdapter;
import com.group.avengers.tourmate.Api.NearbyPlaceApi;
import com.group.avengers.tourmate.NearByPlaces.NearbyByPlaceContent;
import com.group.avengers.tourmate.NearByPlaces.Result;
import com.group.avengers.tourmate.Sharepreferences.Userpreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NearbyPlace extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

    private static final String TAG = "NearbyPlace";
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;

    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;

    private LocationManager locationManager;

    private Userpreferences userPrferences;

    private Spinner spinnerCatagory,spinnerDestnce;

    public static  double nlatidud;
    public static  double nlongtide;


    private Button btnFind;
    private ListView lstPlaceSearchItem;

    private List<Result> nearbyByPlaceContents=new ArrayList<>();
    private NearByPlaceAdapter nearByPlaceAdapter;
    private NearbyPlaceApi placeApi;

    public static final String BASE_URL="https://maps.googleapis.com/maps/api/place/nearbysearch/";

    final String[] itemcatagory={"Restaurant","Bank","ATM","Hospital","Shopping Mall","Mosque","Bus Station","Police Station"};
    String[] destance={"0.5km","1km","1.5km","2km","2.5km","3km","4km","5km","10km"};
    String itemCatagoryPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_place);


        spinnerCatagory=findViewById(R.id.spinnerCategory);
        spinnerDestnce=findViewById(R.id.spinnerDistance);
        btnFind=findViewById(R.id.btnFind);
        lstPlaceSearchItem=findViewById(R.id.listView_displayNearbyPlace);

        ArrayAdapter itemarrayAdapter=new ArrayAdapter(NearbyPlace.this,R.layout.support_simple_spinner_dropdown_item,itemcatagory);
        ArrayAdapter destancearrayAdapter=new ArrayAdapter(NearbyPlace.this,R.layout.support_simple_spinner_dropdown_item,destance);

        spinnerCatagory.setAdapter(itemarrayAdapter);
        spinnerDestnce.setAdapter(destancearrayAdapter);



        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        checkLocation(); //check whether location service is enable or not in your  phone

        userPrferences=new Userpreferences(this);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        placeApi=retrofit.create(NearbyPlaceApi.class);

        spinnerCatagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemCatagoryPosition=itemcatagory[position].toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String api_key=getString(R.string.place_api);

                String placeUrl=String.format("json?location=23.7529083,90.3590617&radius=500&type=restaurant&key=AIzaSyCeT87i2H7fOBd_4lKLJ-xRW1vayY8I4AY");


                Call<NearbyByPlaceContent> getNearbyPlaceContet=placeApi.getNearbyPlace(placeUrl);

                getNearbyPlaceContet.enqueue(new Callback<NearbyByPlaceContent>() {
                    @Override
                    public void onResponse(Call<NearbyByPlaceContent> call, Response<NearbyByPlaceContent> response) {
                        if (response.code()==200){

                            Toast.makeText(NearbyPlace.this, "Found", Toast.LENGTH_SHORT).show();

                            NearbyByPlaceContent nearbyByPlaceContent=response.body();

                            nearbyByPlaceContents=nearbyByPlaceContent.getResults();

                            if (nearbyByPlaceContents==null){
                                Toast.makeText(NearbyPlace.this, "No Data Found From Your Location", Toast.LENGTH_SHORT).show();
                            }else {

                                nearByPlaceAdapter=new NearByPlaceAdapter(NearbyPlace.this,nearbyByPlaceContents);

                                lstPlaceSearchItem.setAdapter(nearByPlaceAdapter);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<NearbyByPlaceContent> call, Throwable t) {
                        Toast.makeText(NearbyPlace.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(mLocation == null){
            startLocationUpdates();
        }
        if (mLocation != null) {

            // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

    @Override
    public void onLocationChanged(Location location) {

        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());


        nlatidud=location.getLatitude();
        nlongtide=location.getLongitude();
        //Save Data Share Preferences

        userPrferences.saveLastLocation((float)location.getLatitude(),(float)location.getLongitude());



        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

}
