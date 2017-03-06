package com.fabiofilho.popmovies.objects.movies;

import com.fabiofilho.popmovies.objects.connections.AsyncTaskRequest;

/**
 * Created by fabiofilho on 2/18/17.
 */

public class MoviesRequests extends AsyncTaskRequest {


    public static final String MOVIES_URL =  "https://api.themoviedb.org/";
    public static final String MOVIES_IMAGE_URL =  "http://image.tmdb.org/t/p/";

    public static final String[] MOVIE_ORDER = {
            "popular", "top_rated"
    };
}