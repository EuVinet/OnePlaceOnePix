package com.example.luna.oneplaceonepix;

/**
 * Created by Luna on 29/10/2016.
 */
import com.google.android.gms.maps.model.LatLng;

import java.io.File;

public class Picture {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(String pictureFile) {
        this.pictureFile = pictureFile;
    }


    public String getTextPicture() {
        return textPicture;
    }

    public void setTextPicture(String textPicture) {
        this.textPicture = textPicture;
    }

    public LatLng getLocation(){
        return location;
    }

    public void setLocation(LatLng location){
        this.location = location;
    }

    public void setLocation(double lat, double lng){
        this.location = new LatLng(lat,lng);
    }

    public double getLatitude(){
        return this.location.latitude;
    }

    public double getLongitude(){
        return this.location.longitude;
    }


    public Picture(int id, LatLng location, String pictureFile, String textPicture) {
        this.id = id;
        this.pictureFile = pictureFile;
        this.textPicture = textPicture;
        this.location = location;
    }

    private int id;
    private LatLng location;
    private String pictureFile;
    private String textPicture;
}
