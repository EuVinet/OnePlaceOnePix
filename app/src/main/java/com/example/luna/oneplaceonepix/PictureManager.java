package com.example.luna.oneplaceonepix;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Luna on 29/10/2016.
 */

public class PictureManager {

    public static final String PICTURE_LAT = "PictureLat";
    public static final String PICTURE_LONG = "PictureLong";
    public static final String PICTURE_ID = "PictureId";
    public static final String PICTURE_PATH = "PicturePath";
    public static final String PICTURE_TEXT = "PictureText";
    public static final String PICTURE_TABLE_NAME = "PictureTable";

    public static final String PICTURE_TABLE_CREATE =  "CREATE TABLE " + PICTURE_TABLE_NAME +
            " (" + PICTURE_ID+ " INTEGER primary key, " + PICTURE_LAT + " DOUBLE, " + PICTURE_LONG+ " DOUBLE, "
            +PICTURE_PATH + " TEXT, " + PICTURE_TEXT + " TEXT);";

    public static final String PICTURE_TABLE_DROP = "DROP TABLE IF EXISTS " + PICTURE_TABLE_NAME + ";";
    private MySQLitePicture maBaseSQLite;
    private SQLiteDatabase db;

    public PictureManager(Context context) {
        maBaseSQLite = MySQLitePicture.getInstance(context);
    }

    public void open()
    {
        //on ouvre la table en lecture/écriture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close()
    {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addPicture(Picture picture) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(PICTURE_PATH, picture.getPictureFile());
        values.put(PICTURE_LAT, picture.getLatitude());
        values.put(PICTURE_TEXT, picture.getTextPicture());
        values.put(PICTURE_LONG, picture.getLongitude());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(PICTURE_TABLE_NAME,null,values);
    }

    public int supPicture(Picture picture){
        String where = PICTURE_PATH+" = ?";
        String[] whereArgs = {picture.getPictureFile()+""};

        return db.delete(PICTURE_TABLE_NAME, where, whereArgs);
    }

    public double getLatFromPicturePath(String picturePath) {
        Cursor c = db.rawQuery("SELECT * FROM "+PICTURE_TABLE_NAME+" WHERE "+PICTURE_PATH+"="+picturePath, null);
        return c.getDouble(c.getColumnIndex(PICTURE_LAT));
    }

    public double getLongFromPicturePath(String picturePath){
        Cursor c = db.rawQuery("SELECT * FROM " +PICTURE_TABLE_NAME+" WHERE "+PICTURE_PATH+"="+picturePath, null);
        return c.getDouble(c.getColumnIndex(PICTURE_LONG));
    }

    public Cursor getPictures(){
        return db.rawQuery("SELECT * FROM "+PICTURE_TABLE_NAME, null);
    }



    public int getNumberOfMarker(){
        Cursor cursor = db.rawQuery("SELECT * FROM "+PICTURE_TABLE_NAME, null);
        return cursor.getCount();
    }

    public String getPictureTextFromLocation(double lati, double longi)
    {
        String Query = "SELECT * FROM " +PICTURE_TABLE_NAME+ " WHERE "+PICTURE_LAT+" = "+lati + " AND "+PICTURE_LONG+" = " +longi;
        Cursor c = db.rawQuery(Query, null);
        String txt = "";
        if(c != null && c.moveToFirst()) {
            txt = c.getString(4);
            return txt;
        }
        c.close();
        return txt;
    }

    public String getPicturePathFromLocation(double lati, double longi)
    {
        String Query = "SELECT * FROM " +PICTURE_TABLE_NAME+ " WHERE "+PICTURE_LAT+" = "+lati + " AND "+PICTURE_LONG+" = " +longi;
        Cursor c = db.rawQuery(Query, null);
        String path = "";
        if(c != null && c.moveToFirst()) {
            path = c.getString(3);
            return path;
        }
        c.close();
        return path;
    }

    public int getIdFromLocation(double lat, double lon)
    {
        String Query = "SELECT * FROM " +PICTURE_TABLE_NAME+ " WHERE "+PICTURE_LAT+" = "+lat + " AND "+PICTURE_LONG+" = " +lon;
        Cursor c = db.rawQuery(Query, null);
        int id =0 ;
        if (c.getCount() > 1)
        {
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                id = c.getInt(0);
            }
            c.close();
            return id;
        }
        id = c.getInt(0);
        c.close();
        return id;
    }

    public  Boolean ChecksIsLocationAlreadyInDBorNot(double lati, double longi){
        String Query = "SELECT * FROM " +PICTURE_TABLE_NAME+ " WHERE "+PICTURE_LAT+" = "+lati + " AND "+PICTURE_LONG+" = " +longi;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
