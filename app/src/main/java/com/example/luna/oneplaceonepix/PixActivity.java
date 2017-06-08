package com.example.luna.oneplaceonepix;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;

public class PixActivity extends AppCompatActivity{

    private double lat;
    private double lon;
    private int id;
    private PictureManager pixManager;
    String picturePath;
    File imgFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pix);

        pixManager = new PictureManager(this);
    }

    protected void onStart(){
        super.onStart();

        ImageView img = (ImageView) findViewById(R.id.imageView);
        pixManager.open();

        //on récupère les infos de la maps
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            lat = extras.getDouble("latitude");
            lon = extras.getDouble("longitude");
            id = extras.getInt("id");
        }
        //on affiche l'image correspondante
        if(pixManager.ChecksIsLocationAlreadyInDBorNot(lat,lon)){
            //id = pixManager.getIdFromLocation(lat, lon);
            picturePath = pixManager.getPicturePathFromLocation(lat,lon);
            Bitmap bit = BitmapFactory.decodeFile(picturePath);
            img.setImageBitmap(bit);
        }

        Button addModify = (Button) findViewById(R.id.goText);
        //le clique sur l'image permet de voir le texte
        addModify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent toText = new Intent(PixActivity.this, TextActivity.class);
                //on envoie les coordonnées de l'image à textActivity qu'elle puisse trouver le texte correspondant
                toText.putExtra("latitude", lat);
                toText.putExtra("longitude", lon);
                toText.putExtra("id", id);
                toText.putExtra("path", picturePath);

                startActivity(toText);
            }
        });

        //clique sur l'image pour modifier qui nous renvoie donc a ModifyPiActivity
        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent toModifyPix = new Intent(PixActivity.this, ModifyPixActivity.class);
                //on envoie les coordonnées de l'image à textActivity qu'elle puisse trouver le texte correspondant
                toModifyPix.putExtra("latitude", lat);
                toModifyPix.putExtra("longitude", lon);
                toModifyPix.putExtra("id", id);
                toModifyPix.putExtra("path", picturePath);

                pixManager.close();

                startActivity(toModifyPix);
            }
        });
    }
}
