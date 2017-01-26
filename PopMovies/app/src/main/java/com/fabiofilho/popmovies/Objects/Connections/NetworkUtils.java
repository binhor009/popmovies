package com.fabiofilho.popmovies.Objects.Connections;

import android.net.Uri;

import com.fabiofilho.popmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by dialam on 25/01/17.
 */

public class NetworkUtils {

    private static final String PARAMETER_API_KEY = "api_key";


    public static URL buildURL(String url, boolean appendsApiKey) throws MalformedURLException{

        Uri.Builder uriBuilder = Uri.parse(url).buildUpon();

        uriBuilder = appendsApiKey ? uriBuilder.appendQueryParameter(PARAMETER_API_KEY, BuildConfig.THE_MOVIE_DB_API_KEY) : uriBuilder;

        Uri uri = uriBuilder.build();

        return new URL(uri.toString());
    }


    public static String getResponse(URL url) throws IOException{

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try{
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            return scanner.hasNext() ? scanner.next() : null;

        }finally {
            urlConnection.disconnect();
        }
    }
}
