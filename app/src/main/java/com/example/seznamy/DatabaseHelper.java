package com.example.seznamy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MY_LISTS.db";
    public static final String LIST_NAME = "listName";
    public static final String LIST_ICON = "icon";
    public static final String LIST_COLOR = "color";

    public static final String LIST_OTHER = "other";
    public static final String LIST_PASTRY = "pastry";
    public static final String LIST_MIKL = "milk";
    public static final String LIST_MEAT = "meat";
    public static final String LIST_COOLED = "cooled";
    public static final String LIST_FRUIT = "fruit";
    public static final String LIST_DRINKS = "drinks";
    public static final String LIST_CANS = "cans";
    public static final String LIST_HOUSE = "house";
    public static final String LIST_DRUGSTORE = "drugstore";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE mylists " + "(id INTEGER PRIMARY KEY, listName TEXT, icon TEXT, color TEXT," +
                " other TEXT,pastry TEXT, milk TEXT, meat TEXT, cooled TEXT, fruit TEXT, drinks TEXT, cans TEXT, house TEXT, drugstore TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS mylists");
        onCreate(db);
    }


    public boolean createNewList(String listName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("listName", listName);

        long insertedId = db.insert("mylists",null,contentValues);
        if(insertedId == -1) {
            return false;
        }
        return true;
    }



    public boolean deleteList(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("mylists", "id="+id, null);
        return true;
    }



    public ArrayList<HashMap<Integer, String>> getItemList()
    {
        ArrayList<HashMap<Integer, String>> arrayList = new ArrayList<HashMap<Integer, String>>();
        HashMap<Integer, String> hash = new HashMap<Integer, String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from mylists", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String name = res.getString(res.getColumnIndex(LIST_NAME));
            int id = res.getInt(0);
            hash.put(id, name);
            res.moveToNext();
        }
        arrayList.add(hash);

        return arrayList;
    }
}
