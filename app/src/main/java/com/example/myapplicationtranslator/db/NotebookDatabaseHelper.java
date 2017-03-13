package com.example.myapplicationtranslator.db;

import android.content.Context;
import android.database.ContentObservable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.CalendarContract;

/**
 * Created by liuht on 2017/3/13.
 */

public class NotebookDatabaseHelper extends SQLiteOpenHelper{

    private Context context;

    public static final String CREATE_NOTEBOOK = "create table Notebook("
            +"id integer primary kry autoincrement,"
            +"input text,"
            +"output text)";

    public NotebookDatabaseHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){sqLiteDatabase.execSQL(CREATE_NOTEBOOK);}

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int i, int i1){

    }
}
