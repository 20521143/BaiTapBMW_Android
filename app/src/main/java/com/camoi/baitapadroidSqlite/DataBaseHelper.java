package com.camoi.baitapadroidSqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_USER_PASSWORD = "USER_PASSWORD";
    public static final String COLUMN_USER_EMAIL = "USER_EMAIL";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    //this method is the first time to be access. Need to have code here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT, " + COLUMN_USER_EMAIL + " TEXT )";
        db.execSQL(createTableStatement);
    }

    //this is change when  the version of database change. It prevent previous user app breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //register function here
    public boolean addOne(UserModel userModel){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put(COLUMN_USER_NAME, userModel.getUsername());
        cv.put(COLUMN_USER_PASSWORD, userModel.getPassword());
        cv.put(COLUMN_USER_EMAIL, userModel.getEmail());
        //shave password in hash function SHA256

        long insert = db.insert(USER_TABLE, null, cv);

        if(insert == -1) {
            return false;
        }
        else {
            return true;
        }

    }

    //login function
    public UserModel getUser(String username, String password) {

        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_USER_NAME + " = " + username + " AND " + COLUMN_USER_PASSWORD + " = " + password;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        UserModel user;

        if(cursor.moveToFirst()) {
            int userID = cursor.getInt(0);
            String userName = cursor.getString(1);
            String userEmail = cursor.getString(3);

            user = new UserModel(userID, userName, "1", userEmail );


        }
        else {
            //don no thing
            cursor.close();
            db.close();
            return null;
        }

        cursor.close();
        db.close();
        return user;


    }
}
