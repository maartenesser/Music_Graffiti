package com.example.maartenesser.musicgraffitti;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int MY_PERMISSONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private FusedLocationProviderClient mFusedLocationClient;
    private String TITLE;
    private String SNIPPIT;

    private EditText typedTitle;
    private EditText typedSnippit;
    private Button submit;
    private Button markerMap;

    private Marker Location;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        setTitle("Add Music marker");


        //Declare the defferent Elements from the xml
        submit = (Button) findViewById(R.id.button_submitMarker);
        markerMap = (Button) findViewById(R.id.button_Marker_Map);
        typedTitle = (EditText) findViewById(R.id.markerTitle);
        typedSnippit = (EditText) findViewById(R.id.markerSnippit);

        //Set onclicklistener for submit button
        submit.setOnClickListener(this);
        markerMap.setOnClickListener(this);

        //Get location services
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    //TODO: Functie die de huidige Locatie ophaalt

    //Check for permissions
    public void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.d("Location permission", "Location not requested. user needs to activate or allow location permission");

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
                Log.d("Location permission", "Callback gets the result of the request");

            }
        }
// else {
//            // Permission has already been granted
//            Log.d("Location permission", "Permission location already granted");
//        }
    }

    //Permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    //OnClick handler
    @SuppressLint({"Permission handled in requestPermission() function", "MissingPermission"})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.button_submitMarker):
                //TODO: Sla de marker op in de arraylist van de Constant map

                //Initialize request permission function
                requestPermissions();


                //Call Last Location function.
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object

                                    //get current Location
//                                    LatLng coordinates = new LatLng(location.getLatitude(), location.getLongitude());
//                                    Log.d("title", String.valueOf(coordinates));
                                    //put typed input in Constants
                                    TITLE = String.valueOf(typedTitle.getText());
                                    SNIPPIT = String.valueOf(typedSnippit.getText());

                                    //Initialize your Reference
                                    DatabaseReference newMarker = FirebaseDatabase.getInstance().getReference()
                                            .child(getString(R.string.dbnode_markers));

                                    Double lng = location.getLongitude();
                                    Double lat = location.getLatitude();

//                                    Log.d("Longitude", String.valueOf(lng));
//                                    Log.d("Latitude", String.valueOf(lat));

                                    String key = newMarker.push().getKey();

                                    newMarker.child(key).child("lng").setValue(lng);
                                    newMarker.child(key).child("lat").setValue(lat);
                                    newMarker.child(key).child("snippet").setValue(SNIPPIT);
                                    newMarker.child(key).child("title").setValue(TITLE);
                                    newMarker.child(key).child("marker_id").setValue(key);

                                    Toast t = Toast.makeText(getApplicationContext(), "Marker is " + TITLE + " Saved and placed on Map ", Toast.LENGTH_SHORT);
                                    t.show();

                                    //Save LatLong, Title and snippit into constants
                                    Constants.MARKERTITLE = TITLE;
                                    Constants.MARKERSNIPPIT = SNIPPIT;
//                                    Constants.COORDINATES = coordinates;

                                    //TODO: How to save the constants into a database.
                                    Log.d("title", Constants.MARKERTITLE);
                                    Log.d("snippit", Constants.MARKERSNIPPIT);
                                    Log.d("Coordinates", String.valueOf(Constants.COORDINATES));
                                    Log.d("Markers", String.valueOf(Constants.MARKERS));
                                    Log.d("submit", "Marker updated in COnstan list");
                                }
                            }
                        });
                break;
            case (R.id.button_Marker_Map):
                Intent MusicGraffittiMapActivity = new Intent(MusicActivity.this, MusicGraffittiMapsActivity.class );
                startActivity(MusicGraffittiMapActivity);
                break;
        }

    }

}
