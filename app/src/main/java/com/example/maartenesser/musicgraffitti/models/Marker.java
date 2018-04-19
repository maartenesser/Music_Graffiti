package com.example.maartenesser.musicgraffitti.models;

import com.google.android.gms.maps.model.LatLng;

public class Marker {

    private String title;
    private String snippet;
    private Double lat;
    private Double lng;
    private String marker_id;

    public Marker() {
        // Default constructor
    }

    public Marker(String title, String snippet, Double lat, Double lng, String marker_id) {

        this.title = title;
        this.snippet = snippet;
        this.lat = lat;
        this.lng = lng;
        this.marker_id = marker_id;
    }


    //Getter and Setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() { return lng; }

    public void setLng(Double lng) { this.lng = lng; }

    public String getMarker_id() {
        return marker_id;
    }

    public void setMarker_id(String marker_id) {
        this.marker_id = marker_id;
    }

    @Override
    public String toString() {
        return "Marker{" +
                "title='" + title + '\'' +
                ", snippet='" + snippet + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", marker_id='" + marker_id + '\'' +
                '}';
    }
}
