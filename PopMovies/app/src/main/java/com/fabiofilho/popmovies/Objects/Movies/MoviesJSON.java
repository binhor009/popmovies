package com.fabiofilho.popmovies.Objects.Movies;

import android.util.Log;

import com.fabiofilho.popmovies.Objects.Connections.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dialam on 25/01/17.
 */

public class MoviesJSON {

    private static final String[] MOVIES_ORDENATION = {
        "popular", "top_rated"
    };


    public static List<Movie> get(){

        List<Movie> movieList = new ArrayList<>();

        String response = loadJsonResponse();

        if(response != null)

            try {
                JSONObject responseJson = new JSONObject(response);
                JSONArray results = responseJson.getJSONArray("results");

                for(int index = 0; index < results.length(); index++){

                    JSONObject jsonObject = results.getJSONObject(index);

                    Movie movie = new Movie(
                            index,
                            jsonObject.getString("title"),
                            jsonObject.getString("release_date"),
                            jsonObject.getDouble("vote_average"),
                            jsonObject.getString("poster_path"),
                            jsonObject.getString("overview"),
                            jsonObject.getString("vote_average")
                    );

                    movieList.add(movie);
                }

            }catch (Exception o){

            }

        return movieList;
    }


    private static String loadJsonResponse(){

        try{
            URL url = NetworkUtils.buildURL(MOVIES_ORDENATION[0]);
            return NetworkUtils.getResponse(url);

        }catch (Exception o){
            Log.i("loadJsonResponse", o.toString());
        }

        return null;
    }

}
