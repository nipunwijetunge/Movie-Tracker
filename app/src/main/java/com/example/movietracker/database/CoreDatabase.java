package com.example.movietracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.movietracker.database.Constants.CAST;
import static com.example.movietracker.database.Constants.DIRECTOR;
import static com.example.movietracker.database.Constants.FAVOURITES;
import static com.example.movietracker.database.Constants.RATING;
import static com.example.movietracker.database.Constants.REVIEW;
import static com.example.movietracker.database.Constants.TABLE_NAME;
import static com.example.movietracker.database.Constants.TITLE;
import static com.example.movietracker.database.Constants.YEAR;

public class CoreDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movie_tracker.db";
    private static final int DATABASE_VERSION = 8;

    private static String[] FROM = { _ID, TITLE, YEAR, DIRECTOR, CAST, RATING, REVIEW, FAVOURITES};

    SQLiteDatabase myDb;

    public CoreDatabase(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE + " TEXT NOT NULL, "
                + YEAR + " INTEGER, "
                + DIRECTOR + " TEXT, "
                + CAST + " TEXT, "
                + RATING + " INTEGER, "
                + REVIEW + " TEXT, "
                + FAVOURITES + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String title, int year, String director, String casting, int rating, String review){
        myDb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, title);
        values.put(YEAR, year);
        values.put(DIRECTOR, director);
        values.put(CAST, casting);
        values.put(RATING, rating);
        values.put(REVIEW, review);
        myDb.insertOrThrow(TABLE_NAME, null, values);
    }

    public Cursor getData(String query){
        myDb = getReadableDatabase();
        return myDb.rawQuery(query, null);
    }

    public void updateFavouriteData(String title, boolean favourite){
        myDb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FAVOURITES, favourite);
        myDb.update(TABLE_NAME, values, "title = ?", new String[]{title});
    }

    public void updateAllData(String title, int year, String director, String cast, int rating, String review, boolean favourite, String defaultTitle){
        myDb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, title);
        values.put(YEAR, year);
        values.put(DIRECTOR, director);
        values.put(CAST, cast);
        values.put(RATING, rating);
        values.put(REVIEW, review);
        values.put(FAVOURITES, favourite);
        myDb.update(TABLE_NAME, values, "title = ?", new String[]{defaultTitle});
    }
}
