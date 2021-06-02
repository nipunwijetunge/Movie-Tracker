package com.example.movietracker.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movietracker.R;

import java.util.List;

/**
 * this custom array adapter class is defined to be set to the list view
 *
 * @author Nipun Wijetunge
 * @version 1.0
 * @since 2021-04-13*/
public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    private final Context context;
    private List<Movie> movieList;

    public MovieArrayAdapter(@NonNull Context context, @NonNull List<Movie> movieList) {
        super(context, 0, movieList);
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.list_view, parent,false);
        }

        Movie currentMovie = movieList.get(position);

        TextView number = listItem.findViewById(R.id.list_item_number);
        number.setText(String.valueOf(position+1));

        TextView title = listItem.findViewById(R.id.list_item_title);
        title.setText("\""+currentMovie.getTitle()+"\"");

        TextView year = listItem.findViewById(R.id.list_item_year);
        year.setText(String.valueOf(currentMovie.getReleasedYear()));

        CheckBox checkBox = listItem.findViewById(R.id.list_item_checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentMovie.setFavourite(buttonView.isChecked());
            }
        });
        if (currentMovie.isFavourite()){
            checkBox.setChecked(true);
        }

        return listItem;
    }
}
