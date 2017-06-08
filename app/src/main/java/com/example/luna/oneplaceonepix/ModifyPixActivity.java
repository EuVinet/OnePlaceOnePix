package com.example.luna.oneplaceonepix;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ModifyPixActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;

    ImageView goText;
    Button valid;

    private double lat;
    private double lon;
    private int id;
    private String picturePath;
    Intent add = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pix);
    }

    protected void onStart() {
        super.onStart();
        goText = (ImageView) findViewById(R.id.imageView);
        valid = (Button) findViewById(R.id.validButton);

        //on récupère les infos de la maps
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            lat = extras.getDouble("latitude");
            lon = extras.getDouble("longitude");
            id = extras.getInt("id");
        }

        if(add == null){
            add = new Intent(
                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(add, RESULT_LOAD_IMAGE);
        }



        //maintenant on regarde si valid est cliqué
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent vers l activite de modification de texte
                //on peut maintenant envoyer aussi le picture path
                Intent toText = new Intent(ModifyPixActivity.this, ModifyTextActivity.class);
                toText.putExtra("latitude", lat);
                toText.putExtra("longitude", lon);
                toText.putExtra("id", id);
                toText.putExtra("path", picturePath);

                startActivity(toText);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            goText.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
}
