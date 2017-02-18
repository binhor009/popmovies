package com.fabiofilho.popmovies.Objects.Movies;

import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dialam on 25/01/17.
 */

public class MovieJSONUtil {

    private static final String RESULTS_FIELD      = "results";
    private static final String TITLE_FIELD        = "title";
    private static final String RELEASE_DATE_FIELD = "release_date";
    private static final String VOTE_AVERAGE_FIELD = "vote_average";
    private static final String POSTER_PATH_FIELD  = "poster_path";
    private static final String OVERVIEW_FIELD     = "overview";


    /**
     * Creates a movie list through a json string that has a singular struct.
     * This struct belongs to TheMovieDB.org.
     * @param response
     * @return movieList
     * @throws JSONException e
     */
    public static List<Movie> createMovieListByJSON(@Nullable String response) throws JSONException {

        List<Movie> movieList = new ArrayList<>();

        // Returns an empty list if the response is null.
        if(response==null) return movieList;

        try {
            // Initialize a json object.
            JSONObject responseJson = new JSONObject(response);

            // Gets an array from the object created that contains each movie.
            JSONArray results = responseJson.getJSONArray(RESULTS_FIELD);

            // Looping the array.
            for(int index = 0; index < results.length(); index++){

                // Creates a temporary json object to transform it into a movie one.
                JSONObject jsonObject = results.getJSONObject(index);

                Movie movie = new Movie(
                        index,
                        jsonObject.optString(TITLE_FIELD),
                        jsonObject.optString(RELEASE_DATE_FIELD),
                        jsonObject.optDouble(VOTE_AVERAGE_FIELD),
                        jsonObject.optString(OVERVIEW_FIELD),
                        jsonObject.optString(POSTER_PATH_FIELD)
                );

                // Adds inside the movie list the transformed json object.
                movieList.add(movie);
            }

        }catch (JSONException e){
            throw e;
        }

        return movieList;
    }

}
