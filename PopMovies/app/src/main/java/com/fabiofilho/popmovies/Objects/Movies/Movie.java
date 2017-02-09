package com.fabiofilho.popmovies.Objects.Movies;

import java.io.Serializable;

/**
 * Created by dialam on 23/01/17.
 */

public class Movie implements Serializable {

    public static final String MOVIES_URL =  "https://api.themoviedb.org/3/movie/";
    public static final String MOVIES_IMAGE_URL =  "http://image.tmdb.org/t/p/";

    public static final String[] MOVIE_ORDER = {
            "popular", "top_rated"
    };

    private static final String IMAGE_SIZE = "w342";

    private int id;
    private String title;
    private String releaseDate;
    private double votesAverage;
    private String overview;
    private String posterPath;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    private String duration;

    public Movie(int id, String title, String releaseDate, double votesAverage, String overview, String posterPath) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.votesAverage = votesAverage;
        this.overview = overview;
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return MOVIES_IMAGE_URL+IMAGE_SIZE+ posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getVotesAverage() {

        return String.valueOf(votesAverage)+"/10";
    }

    public String getOverview() {
        return overview;
    }

    public String getYearOfReleaseDate(){
        return releaseDate.split("-")[0];
    }

}
