package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.example.movietracker.utils.ArrayAdapterForIMDB;
import com.example.movietracker.database.CoreDatabase;
import com.example.movietracker.utils.Movie;

import java.util.ArrayList;
import java.util.List;

import static com.example.movietracker.database.Constants.TABLE_NAME;
import static com.example.movietracker.database.Constants.TITLE;

/**
 * this class is defined to implement the functionality display ratings
 *
 * @author Nipun Wijetunge
 * @version 1.0
 * @since 2021-04-13*/
public class MovieRatings extends AppCompatActivity {
    // database reference
    CoreDatabase database;
    // a list to store movies from database
    List<Movie> movies;
    // a string to store the checked to movie title
    String checkedMovie;

    RelativeLayout layout;
    ListView listView;

    RadioButton radioButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_ratings);

        database = new CoreDatabase(this);
        movies = new ArrayList<>();
        layout = findViewById(R.id.ratings_layout);
        listView = findViewById(R.id.ratings_listView);

        String query = "select * from "+ TABLE_NAME + " order by "+ TITLE + " ASC";
        Cursor cursor = database.getData(query);
        retrieveData(cursor);

        ArrayAdapterForIMDB adapter = new ArrayAdapterForIMDB(this, movies);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RelativeLayout layout = (RelativeLayout) view;
                RadioButton itemCheck = layout.findViewById(R.id.list_item_radio_btn);

                itemCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (radioButton != null){
                            radioButton.setChecked(false);
                            checkedMovie = null;
                        }
                        itemCheck.setChecked(true);
                        radioButton = itemCheck;
                        checkedMovie = movies.get(position).getTitle();
                    }
                });

                if (radioButton != null){
                    radioButton.setChecked(false);
                    checkedMovie = null;
                }
                itemCheck.setChecked(true);
                radioButton = itemCheck;
                checkedMovie = movies.get(position).getTitle();

                itemCheck.setChecked(true);
            }
        });
    }

    /**
     * this method is used to get all the movies from database
     *
     * @param cursor cursor object to get database rows on by one*/
    public void retrieveData(Cursor cursor){
        while (cursor.moveToNext()){
            String title = cursor.getString(1);
            System.out.println(title);
            int year = cursor.getInt(2);
            String director = cursor.getString(3);
            String cast = cursor.getString(4);
            int rating = cursor.getInt(5);
            String review = cursor.getString(6);
            boolean favourite = cursor.getInt(7) > 0;
            System.out.println(favourite);

            movies.add(new Movie(title, year, director, cast, rating, review, favourite));
        }
    }

    /**
     * this method is used to launch the ImdbRatings activity that displays similar movies and their ratings
     * from IMDB official database
     *
     * @param view to be called on a view*/
    public void imdbRatingsLauncher(View view){
        Intent intent = new Intent(MovieRatings.this, ImdbRatings.class);
        intent.putExtra("movie", checkedMovie);
        startActivityForResult(intent, 1);
    }
}