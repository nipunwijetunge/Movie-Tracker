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
 * This class iis used to implement he display movies functionality
 *
 * @author Nipun Wijetunge
 * @version 1.0
 * @since 2021-04-13 */
public class DisplayMovies extends AppCompatActivity {
    // a boolean to keep track if a movie is checked
    public boolean flag;
    // database reference
    CoreDatabase database;
    // a Movie object list to store all the movies from database
    List<Movie> movies = new ArrayList<>();;

    RelativeLayout layout;
    ListView listView;

    ExtendedFloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);

        layout = findViewById(R.id.display_movie_relativeLayout);
        listView = findViewById(R.id.display_movie_listView);
        fab = findViewById(R.id.add_to_fav_btn);
        movies = new ArrayList<>();

        database = new CoreDatabase(this);
        String query = "select * from "+ TABLE_NAME + " order by "+ TITLE + " ASC";
        Cursor cursor = database.getData(query);
        retrieveData(cursor);

        //custom array adapter to set the list view adapter
        MovieArrayAdapter adapter = new MovieArrayAdapter(this, movies);
        adapter.setNotifyOnChange(true);
        listView.setAdapter(adapter);
    }

    /**
     * this method is used to retrieve data from the database
     *
     * @param cursor cursor object to get database rows on by one*/
    public void retrieveData(Cursor cursor){
        while (cursor.moveToNext()) {
            String title = cursor.getString(1);
            int year = cursor.getInt(2);
            String director = cursor.getString(3);
            String cast = cursor.getString(4);
            int rating = cursor.getInt(5);
            String review = cursor.getString(6);
            boolean favourite = cursor.getInt(7) > 0;

            movies.add(new Movie(title, year, director, cast, rating, review, favourite));
        }
    }

    /**
     * this method is used to change favourite status of movies as changed
     *
     * @param view to be called on a view*/
    public void addToFav(View view){
        for (Movie m : movies) {
            database.updateFavouriteData(m.getTitle(), m.isFavourite());
        }
    }
}