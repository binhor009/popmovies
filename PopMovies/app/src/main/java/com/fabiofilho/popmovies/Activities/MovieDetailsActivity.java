package com.fabiofilho.popmovies.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fabiofilho.popmovies.Objects.Movies.Movie;
import com.fabiofilho.popmovies.R;

/**
 * Created by dialam on 30/01/17.
 */

public class MovieDetailsActivity extends AppCompatActivity {


    public static final String EXTRA_KEY = "com.fabiofilho.popmovies.Activities.MovieDetailsActivity.EXTRA_KEY";

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        // Loads the movie from an array received.
        mMovie = Movie.loadFromArray(getIntent().getStringArrayExtra(EXTRA_KEY));
    }




}
