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

    @GET("3/movie/{order}/{api_key}")
    Call<Page> getMoviePage(@Path("order") String order, @Query("api_key") String apiKey);

}
