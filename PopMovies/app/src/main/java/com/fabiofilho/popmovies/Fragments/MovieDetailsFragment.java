package com.fabiofilho.popmovies.Fragments;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fabiofilho.popmovies.BuildConfig;
import com.fabiofilho.popmovies.Objects.Movies.Movie;
import com.fabiofilho.popmovies.Objects.Movies.MovieAPI;
import com.fabiofilho.popmovies.Objects.Movies.gson.Page;
import com.fabiofilho.popmovies.Objects.Utils;
import com.fabiofilho.popmovies.R;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        //loadMovie();

        test();

        return mRootView;
    }

    private void test() {


        final String BASE_URL = "https://api.themoviedb.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        Call<Page> call = movieAPI.getMoviePage("popular", BuildConfig.THE_MOVIE_DB_API_KEY);

        call.enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {

                Page page = response.body();
                Log.i(Utils.getMethodName(), "body: "+response.body());
                Log.i(Utils.getMethodName(), "size: "+ page.getResults().size());
                Log.i(Utils.getMethodName(), page.getResults().get(0).getTitle());
            }

            @Override
            public void onFailure(Call<Page> call, Throwable t) {

                Log.i(Utils.getMethodName(), "error: "+t.toString());
                t.printStackTrace();
            }
        });

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
        mTextViewMovieRating.setText(String.valueOf(mMovieChosen.getVotesAverage()));
        mTextViewMovieDescription.setText(String.valueOf(mMovieChosen.getOverview()));

        // Sets temporally the button bellow as invisible.
        mButtonFavoriteMarker.setVisibility(View.INVISIBLE);
    }

}
