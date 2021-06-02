package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.movietracker.utils.Movie;
import com.example.movietracker.utils.MovieArrayAdapter;
import com.example.movietracker.database.CoreDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.movietracker.database.Constants.TABLE_NAME;
import static com.example.movietracker.database.Constants.TITLE;

/**
 * this class is defined to implement the functionality that is needed to search movies
 *
 * @author Nipun Wijetunge
 * @version 1.0
 * @since 2021-04-13*/
public class SearchMovie extends AppCompatActivity {
    // database reference
    CoreDatabase database;
    // a list to store all the movies from the database
    List<Movie> movies;

    RelativeLayout layout;
    ListView listView;
    EditText searchBar;

    // a string to store search bar value
    String searchBarValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        database = new CoreDatabase(this);
        movies = new ArrayList<>();
        layout = findViewById(R.id.search_relative_layout);
        listView = findViewById(R.id.search_listView);
        searchBar = findViewById(R.id.search_bar);
    }

    /**
     * this method is used to search the input value from database and display in list view
     *
     * @param view to be called on a view*/
    public void search(View view){
        searchBarValue = searchBar.getText().toString().toLowerCase();
        
        String query = "select * from "+ TABLE_NAME + " order by "+ TITLE + " ASC";
        Cursor cursor = database.getData(query);
        retrieveData(cursor);

        MovieArrayAdapter adapter = new MovieArrayAdapter(this, movies);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }

    /**
     * this method is used to get data from database based on the search bar value
     *
     * @param cursor cursor object to get database rows on by one*/
    public void retrieveData(Cursor cursor){
        movies.clear();
        while (cursor.moveToNext()) {
            String title = cursor.getString(1);
            int year = cursor.getInt(2);
            String director = cursor.getString(3);
            String cast = cursor.getString(4);
            int rating = cursor.getInt(5);
            String review = cursor.getString(6);
            boolean favourite = cursor.getInt(7) > 0;

            if (title.contains(searchBarValue) || director.contains(searchBarValue) || cast.contains(searchBarValue)) {
                movies.add(new Movie(title, year, director, cast, rating, review, favourite));
            }
        }
    }
}