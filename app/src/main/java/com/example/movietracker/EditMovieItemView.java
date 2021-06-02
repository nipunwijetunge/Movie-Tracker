package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movietracker.database.CoreDatabase;
import com.example.movietracker.utils.MinMaxFilter;
import com.example.movietracker.utils.Movie;
import com.google.android.material.textfield.TextInputLayout;

/**
 * this class is defined to implement the functionality that is needed to edit movie details
 *
 * @author Nipun Wijetunge
 * @version 1.0
 * @since 2021-04-13*/
public class EditMovieItemView extends AppCompatActivity {
    // database reference
    CoreDatabase database;
    // Movie object to store the selected movie from previous activity
    Movie movie;
    // strings to store picked date from number picker and movie title before editing
    String pickedDate, defaultTitle;
    // variables to store updated data about movie
    String updatedTitle, updatedDirector, updatedCast, updatedReview;
    int updatedYear, updatedRating;

    CheckBox favourite;
    TextInputLayout title, year, director, cast, review;
    NumberPicker yearPicker;
    RatingBar rating1, rating2;
    TextView favStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie_item_view);

        database = new CoreDatabase(this);

        favourite = findViewById(R.id.edit_fav_checkbox);
        title = findViewById(R.id.edit_movie_title);
        year = findViewById(R.id.edit_movie_year);
        year.getEditText().setFilters(new InputFilter[]{new MinMaxFilter(1895, 2021)});
        director = findViewById(R.id.edit_movie_director);
        cast = findViewById(R.id.edit_movie_cast);
        rating1 = findViewById(R.id.edit_ratingBar1);
        rating2 = findViewById(R.id.edit_ratingBar2);
        review = findViewById(R.id.edit_movie_review);
        yearPicker = findViewById(R.id.edit_year_picker);
        favStatus = findViewById(R.id.edit_fav_label);

        yearPicker.setMinValue(1895);
        yearPicker.setMaxValue(2021);

        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                pickedDate = String.valueOf(picker.getValue());
                year.getEditText().setText(pickedDate);
            }
        });

        if (getIntent().getExtras() != null){
            movie = getIntent().getParcelableExtra("movie");
        }

        title.getEditText().setText(movie.getTitle());
        year.getEditText().setText(String.valueOf(movie.getReleasedYear()));
        director.getEditText().setText(movie.getDirector());
        cast.getEditText().setText(movie.getCast());
        review.getEditText().setText(movie.getReview());
        if (movie.getRating() <= 5){
            rating1.setRating(movie.getRating());
            rating2.setRating(0);
        } else {
            rating1.setRating(5);
            rating2.setRating(movie.getRating() - 5);
        }
        favourite.setChecked(movie.isFavourite());

        if (movie.isFavourite()){
            favStatus.setText("Favourite");
        } else {
            favStatus.setText("Not Favourite");
        }

        defaultTitle = title.getEditText().getText().toString();
    }

    /**
     * this method is used to update the database with updated data for the selected movie
     *
     * @param view to be called on a view**/
    public void updateMovie(View view){
        updatedTitle = title.getEditText().getText().toString().toLowerCase();
        updatedYear = Integer.parseInt(year.getEditText().getText().toString());
        updatedDirector = director.getEditText().getText().toString().toLowerCase();
        updatedCast = cast.getEditText().getText().toString().toLowerCase();
        updatedReview = review.getEditText().getText().toString().toLowerCase();
        updatedRating = (int) (rating1.getRating() + rating2.getRating());
        database.updateAllData(updatedTitle, updatedYear, updatedDirector, updatedCast, updatedRating, updatedReview, favourite.isChecked(), defaultTitle);

        Toast.makeText(getApplicationContext(), "Successfully updated!", Toast.LENGTH_SHORT).show();

        movie.setTitle(updatedTitle);
        movie.setReleasedYear(updatedYear);
        movie.setDirector(updatedDirector);
        movie.setCast(updatedCast);
        movie.setRating(updatedRating);
        movie.setReview(updatedReview);
        movie.setFavourite(favourite.isChecked());

        // return the updated movie along with intent extra
        Intent returnIntent = new Intent();
        returnIntent.putExtra("movieReturn", movie);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}