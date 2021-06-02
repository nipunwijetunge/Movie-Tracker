package com.example.movietracker;

import  androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.movietracker.database.CoreDatabase;
import com.example.movietracker.utils.MinMaxFilter;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.movietracker.database.Constants.TABLE_NAME;
import static com.example.movietracker.database.Constants.TITLE;

/**
 * this this class is used to implement the movie registering functionality
 *
 * @author Nipun Wijetunge
 * @version 1.0
 * @since 2021-04-13*/
public class RegisterMovie extends AppCompatActivity {
    //variables to store user inputs of movie details
    TextInputLayout title, year, director, casting, rating, review;
    //database reference
    public CoreDatabase database;
    //a string to store the picked date from the number picker
    private String pickedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);

        database = new CoreDatabase(this);
        title = findViewById(R.id.register_movie_title);
        year = findViewById(R.id.register_movie_year);
        year.getEditText().setFilters(new InputFilter[]{new MinMaxFilter(1895, 2021)});
        director = findViewById(R.id.register_movie_director);
        casting = findViewById(R.id.register_movie_cast);
        rating = findViewById(R.id.register_movie_rating);
        rating.getEditText().setFilters(new InputFilter[]{new MinMaxFilter(1, 10)});
        review = findViewById(R.id.register_movie_review);

        NumberPicker picker = findViewById(R.id.year_picker);

        picker.setMaxValue(2021);
        picker.setMinValue(1895);

        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                pickedDate = String.valueOf(picker.getValue());
                year.getEditText().setText(pickedDate);
            }
        });
    }

    /**
     * this method is used to insert user inputs to the database
     *
     * @param view to be called on a view*/
    public void registerMovie(View view){
        String query = "select * from "+ TABLE_NAME +" where "+ TITLE +" = '"+title.getEditText().getText().toString()+"'";

        try {
            if (database.getData(query).getCount() > 0){
                Toast.makeText(getApplicationContext(), "Already Added!", Toast.LENGTH_SHORT).show();
            } else {
                if (title.getEditText().getText().toString().equals("") ||
                year.getEditText().getText().toString().equals("") ||
                director.getEditText().getText().toString().equals("") ||
                casting.getEditText().getText().toString().equals("") ||
                rating.getEditText().getText().toString().equals("") ||
                review.getEditText().getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "Fill all fields!", Toast.LENGTH_SHORT).show();

                } else {
                    database.insertData(title.getEditText().getText().toString().toLowerCase(),
                            Integer.parseInt(year.getEditText().getText().toString()),
                            director.getEditText().getText().toString().toLowerCase(),
                            casting.getEditText().getText().toString().toLowerCase(),
                            Integer.parseInt(rating.getEditText().getText().toString()),
                            review.getEditText().getText().toString().toLowerCase());
                    Toast.makeText(getApplicationContext(), "Successfully added!", Toast.LENGTH_SHORT).show();
                }
            }
        } finally {
            database.close();
        }
    }

}