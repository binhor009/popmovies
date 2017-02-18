package com.fabiofilho.popmovies.Objects.Movies;

import com.fabiofilho.popmovies.Objects.Connections.AsyncTaskRequest;

/**
 * Created by fabiofilho on 2/18/17.
 */

public class AsyncTaskRequestMovies extends AsyncTaskRequest {


    public static final String MOVIES_URL =  "https://api.themoviedb.org/3/movie/";
    public static final String MOVIES_IMAGE_URL =  "http://image.tmdb.org/t/p/";

    public static final String[] MOVIE_ORDER = {
            "popular", "top_rated"
    };
}
