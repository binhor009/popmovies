package com.fabiofilho.popmovies.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fabiofilho.popmovies.Objects.Movies.Movie;
import com.fabiofilho.popmovies.R;

import static com.fabiofilho.popmovies.Activities.MovieDetailsActivity.EXTRA_KEY;

/**
 * Created by dialam on 02/02/17.
 */

public class MovieDetailsFragment extends Fragment {

    private View mRootView;

    private Movie mMovie;
    private TextView mMovieTitle;

    public MovieDetailsFragment() {

        setHasOptionsMenu(true);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        referScreenObjects();
        loadMovie();

        return mRootView;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void referScreenObjects() {

        mMovieTitle = (TextView) mRootView.findViewById(R.id.content_movie_details_title);
    }

    private void loadMovie() {

        // Loads the movie from the caller activity.
        mMovie = (Movie) getActivity().getIntent().getSerializableExtra(EXTRA_KEY);

        mMovieTitle.setText(mMovie.getTitle());
    }

}
