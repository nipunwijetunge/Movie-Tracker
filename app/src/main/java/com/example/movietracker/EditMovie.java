package com.example.movietracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.movietracker.utils.Movie;
import com.example.movietracker.utils.MovieArrayAdapter;
import com.example.movietracker.database.CoreDatabase;
import com.example.movietracker.utils.MovieArrayAdapterMod;
import com.example.movietracker.utils.MoviesComparatorByTitle;

import java.util.ArrayList;
import java.util.List;

import static com.example.movietracker.database.Constants.TABLE_NAME;
import static com.example.movietracker.database.Constants.TITLE;

/**
 * this class is defined the Edit movie functionality
 *
 * @author Nipun Wijetunge
 * @version 1.0
 * @since 2021-04-13*/
public class EditMovie extends AppCompatActivity {
    //request code to get intent result
    private final int REQ_COE = 1;
    //database reference
    CoreDatabase database;
    //a Movie object list to store all the movies from the database
    List<Movie> movies;

    // Movie object to store edited movie from the intent results
    Movie movieReturn;
    // Movie object to store the previous version of clicked movie to edit
    Movie clickedMovie;

    RelativeLayout layout;
    ListView listView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_COE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                // get the returned movie from the second activity along with parcelable extra
                movieReturn = data.getParcelableExtra("movieReturn");

                movies.remove(clickedMovie);
                movies.add(movieReturn);

                // sorts the movies list using custom movie comparator by title
                movies.sort(new MoviesComparatorByTitle());

                // re-arranges the list view on the result from second activity
                MovieArrayAdapterMod adapter = new MovieArrayAdapterMod(this, movies);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        layout = findViewById(R.id.edit_relative_layout);
        listView = findViewById(R.id.edit_listView);
        movies = new ArrayList<>();

        database = new CoreDatabase(this);
        String query = "select * from "+ TABLE_NAME + " order by "+ TITLE + " ASC";
        Cursor cursor = database.getData(query);
        retrieveData(cursor);

        MovieArrayAdapterMod adapter = new MovieArrayAdapterMod(this, movies);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EditMovie.this, EditMovieItemView.class);
                intent.putExtra("movie", movies.get(position));
                clickedMovie = movies.get(position);
                startActivityForResult(intent, 1);
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * this method is used to retrieve all the movies from the database
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
}