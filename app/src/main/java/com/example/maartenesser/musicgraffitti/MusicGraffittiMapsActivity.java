package com.example.maartenesser.musicgraffitti;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.maartenesser.musicgraffitti.models.Marker;

import java.util.ArrayList;
import java.util.List;

public class MusicGraffittiMapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnInfoWindowClickListener {

    private String TAG = "Music Graffitti Maps";

    private GoogleMap mMap;

//    List<Marker> markerList;

    private ArrayList<Marker> markerList = new ArrayList<Marker>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_graffitti_maps);

//        markerList = new ArrayList<>();

        setTitle("Music Graffitti Map");


        Button button_addMusicGraffitti = (Button) findViewById(R.id.button_AddMusicGraffittiTag);

        button_addMusicGraffitti.setOnClickListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnInfoWindowClickListener(this);


        //Make connection to Database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("markers");

        //Get data from database
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Clear list to be sure there is no double data
                markerList.clear();

                //Loop trough data and put into markerList
                for (DataSnapshot markerSnapshot : dataSnapshot.getChildren()) {
                    Marker marker = markerSnapshot.getValue(Marker.class);


                    Log.d(TAG, String.valueOf(marker));

//                      //Add to markerlist
//                        markerList.add(marker);

                    LatLng pointer = new LatLng(marker.getLat(), marker.getLng());
                    googleMap.addMarker(new MarkerOptions()
                            .position(pointer)
                            .title(marker.getTitle())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_music_note))
                            .snippet(marker.getSnippet())
                    );
                }
                //Show markerList;
                Log.d("MarkerList", String.valueOf(markerList));

                mMap.getUiSettings().setZoomControlsEnabled(true);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(15)                   // Sets the zoom
                    .bearing(0)                // Sets the orientation of the camera to east
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.button_AddMusicGraffittiTag):
                Intent addMusicGraffitti = new Intent (MusicGraffittiMapsActivity.this, MusicActivity.class);
                startActivity(addMusicGraffitti);
                Log.d("test", "Button graffiti clicked");
                break;
        }
    }

    @Override
    public void onInfoWindowClick(com.google.android.gms.maps.model.Marker marker) {
        String url = marker.getSnippet();

        if(url != null) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    }
}
