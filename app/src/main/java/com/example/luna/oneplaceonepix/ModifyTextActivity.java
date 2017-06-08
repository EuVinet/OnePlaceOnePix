package com.example.luna.oneplaceonepix;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

public class ModifyTextActivity extends AppCompatActivity {

    private double lat;
    private double lon;
    private int id;
    private String picturePath;

    private Picture pix;
    private PictureManager pixManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_modify_text);
        pixManager = new PictureManager(this);
        pix = new Picture(0, null, null, null);//constructeur vide, on va ajouter les infos au fur et à mesure
    }

    @Override
    protected void onStart() {
        super.onStart();
        final TextView txt = (TextView) findViewById(R.id.textView);
        pixManager.open();

        //on récupère les infos transmises par les activités précédentes
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            lat = extras.getDouble("latitude");
            lon = extras.getDouble("longitude");
            id = extras.getInt("id");
            picturePath = extras.getString("path");
        }
        //on ajoute ces infos a notre picture
        pix.setLocation(lat,lon);
        pix.setId(id);
        pix.setPictureFile(picturePath);

        //des que valid est cliqué, toutes les infos vont dans la bdd !
        Button valid = (Button) findViewById(R.id.validButton2);
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            pix.setTextPicture(txt.getText().toString());
            //notre Picture est complète on l'ajoute à la BDD
            pixManager.addPicture(pix);
            //puis on retourne à map
            Intent returnToMap = new Intent(ModifyTextActivity.this, MapsActivity.class);
            returnToMap.putExtra("latitude", lat);
            returnToMap.putExtra("longitude", lon);
            returnToMap.putExtra("type", 1);

            pixManager.close();

            startActivity(returnToMap);
            }
        });
    }
}
