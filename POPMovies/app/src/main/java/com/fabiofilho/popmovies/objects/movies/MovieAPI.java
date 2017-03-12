package com.fabiofilho.popmovies.objects.movies;

import com.fabiofilho.popmovies.objects.movies.gson.Page;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by fabiofilho on 2/23/17.
 */

public interface MovieAPI {

    String MOVIES_URL =  "https://api.themoviedb.org/";
    String MOVIES_IMAGE_URL =  "http://image.tmdb.org/t/p/";

    String[] MOVIE_ORDER = {
            "popular", "top_rated"
    };

    @GET("3/movie/{order}")
    Call<Page> getMoviePage(@Path("order") String order, @Query("api_key") String apiKey);
}
