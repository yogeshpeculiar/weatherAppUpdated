package com.example.weatherappwithnav;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
   static final String dbName="user.db";
    final String tableName="users";
    final String col1="id";
    final String col2="userName";
    final String col3="password";
    final String col4="colorPreference";


    public DbHelper(@Nullable Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+tableName+"(id,userName,password,colorPreference)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+tableName );
        onCreate(db);
    }
    public long insert(String userName,String password,String colorPreference){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col2,userName);
        contentValues.put(col3,password);
        contentValues.put(col4,colorPreference);
        return db.insert(tableName,null,contentValues);

    }
    public Cursor get(String username){
        SQLiteDatabase db=this.getWritableDatabase();
       //in order to avoid sqlInjecttion we ll prefer using method db.query()
        // Cursor c= db.rawQuery("select * from users where userName='"+username+"'",null);
        String whereClause="userNAme=?";
        String[] whereArgs=new String[]{username};
        Cursor c = db.query(tableName, null, whereClause, whereArgs,
                null, null, null);
        return c;
    }
}
