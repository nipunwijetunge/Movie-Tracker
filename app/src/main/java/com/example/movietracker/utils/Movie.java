package com.example.movietracker.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * this class is defined to implement the Movie model with necessary information about it
 * also this class is implemented by Parcelable to pass Movie objects along with intent extra
 *
 * @author Nipun Wijetunge
 * @version 1.0
 * @since 2021-04-13*/
public class Movie implements Parcelable {
    private String title;
    private int releasedYear;
    private String director;
    private String cast;
    private int rating;
    private String review;
    private boolean favourite;

    private String imdbYear;
    private String imdBRating;

    public Movie(String title, String year, String rating) {
        this.title = title;
        this.imdbYear = year;
        this.imdBRating = rating;
    }

    public Movie(String title, int releasedYear, String director, String cast, int rating, String review) {
        this.title = title;
        this.releasedYear = releasedYear;
        this.director = director;
        this.cast = cast;
        this.rating = rating;
        this.review = review;
    }

    public Movie(String title, int releasedYear, String director, String cast, int rating, String review, boolean favourite) {
        this.title = title;
        this.releasedYear = releasedYear;
        this.director = director;
        this.cast = cast;
        this.rating = rating;
        this.review = review;
        this.favourite = favourite;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleasedYear() {
        return releasedYear;
    }

    public void setReleasedYear(int releasedYear) {
        this.releasedYear = releasedYear;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getImdbYear() {
        return imdbYear;
    }

    public void setImdbYear(String imdbYear) {
        this.imdbYear = imdbYear;
    }

    public String getImdBRating() {
        return imdBRating;
    }

    public void setImdBRating(String imdBRating) {
        this.imdBRating = imdBRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.releasedYear);
        dest.writeString(this.director);
        dest.writeString(this.cast);
        dest.writeInt(this.rating);
        dest.writeString(this.review);
        dest.writeByte(this.favourite ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.title = source.readString();
        this.releasedYear = source.readInt();
        this.director = source.readString();
        this.cast = source.readString();
        this.rating = source.readInt();
        this.review = source.readString();
        this.favourite = source.readByte() != 0;
    }

    protected Movie(Parcel in) {
        this.title = in.readString();
        this.releasedYear = in.readInt();
        this.director = in.readString();
        this.cast = in.readString();
        this.rating = in.readInt();
        this.review = in.readString();
        this.favourite = in.readByte() != 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
