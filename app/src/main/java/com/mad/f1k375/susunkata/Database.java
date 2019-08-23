package com.mad.f1k375.susunkata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by K.Fikri on 08-May-17.
 */

public class Database extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "game.db";
    private static final int DATABASE_VERSION = 2;
    public Database(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }

    public Cursor getInfoKategori(String kategori){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("SELECT * FROM "+kategori+"",new String[]{});
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor getInfoGambar(String[] conten){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("SELECT * FROM "+conten[0]+" WHERE id=?",new String[]{conten[1]});
        cursor.moveToFirst();
        return cursor;
    }

    public void UpdateLvl(String[] content){
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues cv = new ContentValues();
            cv.put("status",1);
            db.update(content[0],cv,"id="+Integer.parseInt(content[1]),null);

            ContentValues cv1 = new ContentValues();
            cv1.put("akses",1);
            db.update(content[0],cv1,"id="+(Integer.parseInt(content[1])+1),null);
            db.close();
        }catch (Exception e){
            Log.d("updateLvl", String.valueOf(e));
        }

    }
}

