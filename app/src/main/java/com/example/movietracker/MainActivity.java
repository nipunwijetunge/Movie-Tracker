package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Movie Tracker
 * a simple android application that can be used to track watched movies
 * with features like personalized ratings to each movie and add to favourite functions
 *
 * @author Nipun Wijetunge
 * @version 1.0
 * @since 2021-04-13*/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView logo = findViewById(R.id.imageView_main);
        logo.setImageResource(R.drawable.logo);
    }

    /**
     * this method is used to launch RegisterMovie activity*/
    public void registerMovieLauncher(View view){
        Intent intent = new Intent(this, RegisterMovie.class);
        startActivity(intent);
    }

    /**
     * this method is used to launch DisplayMovies activity*/
    public void displayMoviesLauncher(View view){
        Intent intent = new Intent(this, DisplayMovies.class);
        startActivity(intent);
    }

    /**
     * this method is used to launch FavouriteMovies activity*/
    public void favouriteMoviesLauncher(View view){
        Intent intent = new Intent(this, FavouriteMovies.class);
        startActivity(intent);
    }

    /**
     * this method is used to launch EditMovie activity*/
    public void editMovieLauncher(View view){
        Intent intent = new Intent(this, EditMovie.class);
        startActivity(intent);
    }

    /**
     * this method is used to launch SearchMovie activity*/
    public void searchMovieLauncher(View view){
        Intent intent = new Intent(this, SearchMovie.class);
        startActivity(intent);
    }

    /**
     * this method is used to launch MovieRatings activity*/
    public void movieRatingsLauncher(View view){
        Intent intent = new Intent(this, MovieRatings.class);
        startActivity(intent);
    }
}