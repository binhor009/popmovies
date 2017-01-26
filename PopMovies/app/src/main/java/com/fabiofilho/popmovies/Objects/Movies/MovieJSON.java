package com.fabiofilho.popmovies.Objects.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dialam on 25/01/17.
 */

public class MovieJSON {

    private static final String RESULTS_FIELD      = "results";
    private static final String TITLE_FIELD        = "title";
    private static final String RELEASE_DATE_FIELD = "release_date";
    private static final String VOTE_AVERAGE_FIELD = "vote_average";
    private static final String POSTER_PATH_FIELD  = "poster_path";
    private static final String OVERVIEW_FIELD     = "overview";



    public static List<Movie> createMovieListByJSON(String response) throws JSONException {

        List<Movie> movieList = new ArrayList<>();

        if(response==null) return movieList;

        try {
            JSONObject responseJson = new JSONObject(response);
            JSONArray results = responseJson.getJSONArray(RESULTS_FIELD);

            for(int index = 0; index < results.length(); index++){

                JSONObject jsonObject = results.getJSONObject(index);

                Movie movie = new Movie(
                        index,
                        jsonObject.getString(TITLE_FIELD),
                        jsonObject.getString(RELEASE_DATE_FIELD),
                        jsonObject.getDouble(VOTE_AVERAGE_FIELD),
                        jsonObject.getString(OVERVIEW_FIELD),
                        jsonObject.getString(POSTER_PATH_FIELD)
                );

                movieList.add(movie);
            }

        }catch (JSONException e){
            throw e;
        }

        return movieList;
    }

}
