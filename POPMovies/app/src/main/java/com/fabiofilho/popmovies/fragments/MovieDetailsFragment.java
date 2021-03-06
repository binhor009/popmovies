package com.fabiofilho.popmovies.fragments;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fabiofilho.popmovies.R;
import com.fabiofilho.popmovies.objects.movies.gson.Movie;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.ButterKnife;

/**
 * Created by dialam on 02/02/17.
 */

public class MovieDetailsFragment extends Fragment {

    private View mRootView;

    private Movie mMovieChosen;
    private TextView mTextViewMovieTitle, mTextViewMovieYear, mTextViewMovieDuration, mTextViewMovieRating, mTextViewMovieDescription;
    private Button mButtonFavoriteMarker;
    private ImageView mImageView;

    public MovieDetailsFragment() {

        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, mRootView);

        // Refers the objects to class variables.
        referScreenObjects();

        // Loads the movie chosen.
        loadMovie();

        return mRootView;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void referScreenObjects() {

        // Makes the shadow objects invisible if the android version is Lollipop or newer.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mRootView.findViewById(R.id.content_movie_details_toolbar_shadow).setVisibility(View.GONE);
            mRootView.findViewById(R.id.content_movie_details_text_view_title_shadow).setVisibility(View.GONE);
        }

        mImageView = (ImageView) mRootView.findViewById(R.id.content_movie_details_image_view_post);

        mTextViewMovieTitle = (TextView) mRootView.findViewById(R.id.content_movie_details_text_view_title);
        mTextViewMovieYear = (TextView) mRootView.findViewById(R.id.content_movie_details_text_view_year);
        mTextViewMovieDuration = (TextView) mRootView.findViewById(R.id.content_movie_details_text_view_duration);
        mTextViewMovieRating = (TextView) mRootView.findViewById(R.id.content_movie_details_text_view_rating);
        mButtonFavoriteMarker = (Button) mRootView.findViewById(R.id.content_movie_details_button_favorite_marker);
        mTextViewMovieDescription = (TextView) mRootView.findViewById(R.id.content_movie_details_text_view_description);
    }

    private void loadMovie() {

        // Loads the movie from the caller activity.
        mMovieChosen = (Movie) Parcels.unwrap(getActivity().getIntent().getParcelableExtra(Movie.PARCELABLE_KEY));

        //Loads the post image using Picasso API.
        Picasso.with(mRootView.getContext())
                .load(mMovieChosen.getPosterPath()).into(mImageView);

        // Loads the content.
        mTextViewMovieTitle.setText(mMovieChosen.getTitle());
        mTextViewMovieYear.setText(mMovieChosen.getReleaseDate(mRootView.getContext()));
        mTextViewMovieDuration.setVisibility(View.GONE);
        mTextViewMovieRating.setText(String.valueOf(mMovieChosen.getVoteAverage()));
        mTextViewMovieDescription.setText(String.valueOf(mMovieChosen.getOverview()));

        // Sets temporally the button bellow as invisible.
        mButtonFavoriteMarker.setVisibility(View.INVISIBLE);
    }

}
