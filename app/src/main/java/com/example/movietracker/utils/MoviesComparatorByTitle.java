package com.example.movietracker.utils;

import com.example.movietracker.utils.Movie;

import java.util.Comparator;

/**
 * this class is defined to implement a comparator to compare Movie objects on movie title
 *
 * @author Nipun Wijetunge
 * @version 1.0
 * @since 2021-04-13*/
public class MoviesComparatorByTitle implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }
}
