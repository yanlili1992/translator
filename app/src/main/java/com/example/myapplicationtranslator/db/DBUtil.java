package com.example.myapplicationtranslator.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by liuht on 2017/3/13.
 */

public class DBUtil {

    public static Boolean queryIfItemExist(NotebookDatabaseHelper dbHelper, String queryString){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("notebook",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String s = cursor.getString(cursor.getColumnIndex("input"));
                if(queryString.equals(s)){
                    return true;
                }
            }while (cursor.moveToNext());
        }

        cursor.close();

        return false;
    }

    public static void insertValue(NotebookDatabaseHelper dbHelper, ContentValues values){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.insert("notebook",null,values);
    }

    public static void deleteValue(NotebookDatabaseHelper dbHelper, String deleteString){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.delete("notebook","input=?",new String[]{deleteString});
    }
}
