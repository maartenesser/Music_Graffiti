package com.example.maartenesser.musicgraffitti;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static final String PREF_FILE_NAME = "my_pref_file";
    public static List<Marker> MARKERS = new ArrayList<Marker>();
    public static String MARKERTITLE = "";
    public static String MARKERSNIPPIT = "";
    public static LatLng COORDINATES = new LatLng(0,0);

}


//    LatLng test = new LatLng(location.getLatitude(), location.getLongitude());
//                                    TITLE = String.valueOf(typedTitle.getText());
//                                            SNIPPIT = String.valueOf(typedSnippit.getText());
//
//                                            Log.d("location", TITLE);
//                                            Log.d("location", SNIPPIT);

