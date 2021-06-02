package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.movietracker.utils.Movie;
import com.example.movietracker.utils.MovieArrayAdapter;
import com.example.movietracker.database.CoreDatabase;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.movietracker.database.Constants.TABLE_NAME;
import static com.example.movietracker.database.Constants.TITLE;

/**
 * this class is defined to define the functionality for the favourite movies
 *
 * @author Nipun Wijetunge
 * @version 1.0
 * @since 2021-04-13*/
public class FavouriteMovies extends AppCompatActivity {
    //database reference
    CoreDatabase database;
    // a Movie object list to store movies from database
    List<Movie> movies;

    RelativeLayout layout;
    ListView listView;
    ExtendedFloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);

        layout = findViewById(R.id.fav_relativeLayout);
        listView = findViewById(R.id.fav_listView);
        fab = findViewById(R.id.favourite_save_btn);
        movies = new ArrayList<>();

        database = new CoreDatabase(this);
        String query = "select * from "+ TABLE_NAME + " order by "+ TITLE + " ASC";
        Cursor cursor = database.getData(query);
        retrieveData(cursor);

        MovieArrayAdapter adapter = new MovieArrayAdapter(this, movies);
        listView.setAdapter(adapter);
    }

    /**
     * this method is used to get movies from database according to the query
     *
     * @param cursor cursor object to get database rows on by one*/
    public void retrieveData(Cursor cursor){
        while (cursor.moveToNext()){
            String title = cursor.getString(1);
            int year = cursor.getInt(2);
            String director = cursor.getString(3);
            String cast = cursor.getString(4);
            int rating = cursor.getInt(5);
            String review = cursor.getString(6);
            boolean favourite = cursor.getInt(7) > 0;

            if (favourite) {
                movies.add(new Movie(title, year, director, cast, rating, review, true));
            }
        }
    }

    /**
     * this method is used to save the changes to the database when the button is clicked
     *
     * @param view to be called on a view*/
    public void save(View view){
        for (Movie m : movies) {
            if (!m.isFavourite()) {
                database.updateFavouriteData(m.getTitle(), m.isFavourite());

                Toast.makeText(getApplicationContext(), "Successfully removed from favourite!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}