package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.movietracker.utils.ArrayAdapterForIMDBListView;
import com.example.movietracker.utils.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is defined to implement the functionality that is needed to display ratings from IMDB api
 *
 * @author Nipun Wijetunge
 * @version 1.0
 * @since 2021-04-13*/
public class ImdbRatings extends AppCompatActivity {
    // a string to store the movie title from intent extra
    String movie;
    // a string to store image url from imdb api
    String image_url;
    // a list to store movie titles from imdb api
    List<Movie> movieTitles;
    // a list to store bitmap type cover images from imdb api
    List<Bitmap> covers = new ArrayList<>();

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imdb_ratings);

        movieTitles = new ArrayList<>();
        listView = findViewById(R.id.imdb_rating_listView);
        movie = getIntent().getStringExtra("movie");

        Thread thread = new Thread(new MovieTitleRunnable(movie, movieTitles));
        thread.start();
    }

    /**
     * this class is defined to implement the internet functions that are needed to get data from imdb api*/
    class MovieTitleRunnable implements Runnable {
        // a string to pass as a parameter in constructor to pass movie title
        String title;
        // a list to pass as a parameter in constructor to pass movie titles
        List<Movie> movie_titles;

        public MovieTitleRunnable(String title, List<Movie> movie_titles) {
            this.title = title;
            this.movie_titles = movie_titles;
        }

        @Override
        public void run() {
            StringBuilder sb1 = new StringBuilder("");
            String sb2 = "";
            String movie_id = "";

            try {
                URL url = new URL("https://imdb-api.com/en/API/SearchTitle/k_4o4j70xc/"+title);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                while ((line = br.readLine()) != null){
                    sb1.append(line);
                }

                JSONObject json = new JSONObject(sb1.toString());
                JSONArray jsonArray = json.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject json_movie = jsonArray.getJSONObject(i);
                    String movie_title = json_movie.getString("title");
                    String movie_year = json_movie.getString("description");
                    String imdb_rating = "";
                    image_url = json_movie.getString("image");
                    movie_id = json_movie.getString("id");

                    // a bitmap to store bitmap image from imdb api
                    Bitmap bitmap;
                    try {
                        URL url1 = new URL(image_url);
                        System.out.println(image_url);
                        HttpURLConnection con1 = (HttpURLConnection) url1.openConnection();
                        InputStream bfstream = con1.getInputStream();

                        bitmap = BitmapFactory.decodeStream(bfstream);
                        // adds all bitmap images to the covers list
                        covers.add(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    URL id_url = new URL("https://imdb-api.com/en/API/UserRatings/k_4o4j70xc/"+movie_id);
                    HttpURLConnection id_con = (HttpURLConnection) id_url.openConnection();
                    BufferedReader id_br = new BufferedReader(new InputStreamReader(id_con.getInputStream()));
                    String id_line;
                    while ((id_line = id_br.readLine()) != null){
                        sb2 = id_line;
                    }

                    JSONObject id_json = new JSONObject(sb2);
                    imdb_rating = id_json.getString("totalRating");
                    // adds titles from the imdb api to to movie_titles list
                    movie_titles.add(new Movie(movie_title, movie_year, imdb_rating));
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ArrayAdapterForIMDBListView adapter = new ArrayAdapterForIMDBListView(ImdbRatings.this, movieTitles);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            ImageView image = new ImageView(ImdbRatings.this);
                            // sets image view recourse from covers list based on list view item position
                            image.setImageBitmap(covers.get(position));

                            AlertDialog.Builder builder = new AlertDialog.Builder(ImdbRatings.this).
                                            setMessage("Poster").
                                            setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            }).
                                            setView(image);
                            builder.create().show();
                        }
                    });
                }
            });
        }
    }
}