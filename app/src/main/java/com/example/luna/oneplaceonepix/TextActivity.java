package com.example.luna.oneplaceonepix;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TextActivity extends AppCompatActivity{

    private double lat;
    private double lon;
    private int id;
    private String path;
    private Picture pix;
    private PictureManager pixManager;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        pixManager = new PictureManager(context);

    }

    @Override
    protected void onStart() {
        super.onStart();
        final TextView txt = (TextView) findViewById(R.id.textView);
        pixManager.open();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            lat = extras.getDouble("latitude");
            lon = extras.getDouble("longitude");
            id = extras.getInt("id");
            path = extras.getString("path");
        }

        //on importe le texte depuis la bdd
        txt.setText(pixManager.getPictureTextFromLocation(lat, lon));

        //le clique sur le texte envoie a la page de modification du texte
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toModify = new Intent(TextActivity.this, ModifyTextActivity.class);
                toModify.putExtra("latitude", lat);
                toModify.putExtra("longitude", lon);
                toModify.putExtra("id", id);
                toModify.putExtra("path", path);
                startActivity(toModify);
            }
        });

        Button addModify = (Button) findViewById(R.id.goMap);
        addModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMap = new Intent(TextActivity.this, MapsActivity.class);
                toMap.putExtra("latitude", lat);
                toMap.putExtra("longitude", lon);
                toMap.putExtra("id", id);
                toMap.putExtra("path", path);
                startActivity(toMap);
            }
        });
    }
}
