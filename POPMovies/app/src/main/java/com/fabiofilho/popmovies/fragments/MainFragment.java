package com.fabiofilho.popmovies.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.fabiofilho.popmovies.BuildConfig;
import com.fabiofilho.popmovies.R;
import com.fabiofilho.popmovies.activities.MovieDetailsActivity;
import com.fabiofilho.popmovies.objects.Utils;
import com.fabiofilho.popmovies.objects.connections.NetworkUtils;
import com.fabiofilho.popmovies.objects.dialogs.MovieOrderDialog;
import com.fabiofilho.popmovies.objects.movies.MovieAPI;
import com.fabiofilho.popmovies.objects.movies.MovieAdapter;
import com.fabiofilho.popmovies.objects.movies.MovieAdapterOnClickHandler;
import com.fabiofilho.popmovies.objects.movies.gson.Movie;
import com.fabiofilho.popmovies.objects.movies.gson.Page;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends Fragment implements Callback<Page>, MovieAdapterOnClickHandler{

    public final String SAVED_INSTANCE_KEY_MOVIE_ORDER = "SAVED_INSTANCE_KEY_MOVIE_ORDER";

    private View mRootView;
    private int mIndexMovieOrderChosen = 0;

    @BindView(R.id.FragmentMainMoviesProgressBar)
    public ProgressBar mProgressBar;

    @BindView(R.id.FragmentMainMoviesNoInternetWarningLinearLayout)
    public LinearLayout mLinearLayoutNoInternetWarning;

    @BindView(R.id.FragmentMainMoviesGridView)
    public RecyclerView mRecyclerView;

    @BindView(R.id.FragmentMainMoviesNoInternetTryAgainButton)
    public Button mButtonNoInternetTryAgain;


    public MainFragment() {

        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_movie_sort) {

            // Opens the movie dialog order.
            openMovieDialogOrder();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, mRootView);

        // Loads the saved variables.
        loadSavedInstanceState(savedInstanceState);

        // Setting the default values for them.
        loadObjects();

        return mRootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        // Saves the current movie order.
        outState.putInt(SAVED_INSTANCE_KEY_MOVIE_ORDER, mIndexMovieOrderChosen);

        super.onSaveInstanceState(outState);
    }

    /**
     * Loads the saved variables.
     * @param savedInstanceState
     */
    private void loadSavedInstanceState(@Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null){

            // Loads the movie order.
            mIndexMovieOrderChosen = savedInstanceState.getInt(SAVED_INSTANCE_KEY_MOVIE_ORDER);
        }
    }

    /**
     * Loads the properties from the objects.
     */
    private void loadObjects() {

        prepareRecycleView();

        setObjectsListeners();

        mIndexMovieOrderChosen = getMovieOrderPreference();

        updateMoviesAdapter(MovieAPI.MOVIE_ORDER[mIndexMovieOrderChosen]);
    }

    /**
     * Defines all objects events.
     */
    private void setObjectsListeners(){

        mButtonNoInternetTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tries to update the adapter if the user wants.
                updateMoviesAdapter(MovieAPI.MOVIE_ORDER[mIndexMovieOrderChosen]);
            }
        });
    }

    /**
     * Opens the movie details activity and sends an instance of Movie class
     * chosen by user.
     * @param position
     */
    private void openMovieDetails(int position){

        // Casts the Movie instance by the position to send it through intent to MovieDetailsActivity.
        Movie movie = ((MovieAdapter) mRecyclerView.getAdapter()).getItem(position);

        // Creates an intent with a Movie instance as parameter.
        Intent intent = new Intent(mRootView.getContext(), MovieDetailsActivity.class);
        intent.putExtra(Movie.PARCELABLE_KEY, Parcels.wrap(movie));

        startActivity(intent);
    }

    /**
     * Create an instance of MovieOrderDialog class and shows it to the user.
     */
    private void openMovieDialogOrder(){

        MovieOrderDialog movieOrderDialog = new MovieOrderDialog();
        movieOrderDialog.onCreateDialog(getActivity(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Saves the preferences gotten.
                saveMovieOrderPreference(which);

                // Updates the movies adapter if the user has chosen a different order.
                updateMoviesAdapter(MovieAPI.MOVIE_ORDER[which]);
            }
        }, mIndexMovieOrderChosen).show();
    }

    private void saveMovieOrderPreference(int value) {

        SharedPreferences sharedPreferences =
                mRootView.getContext().getSharedPreferences(SAVED_INSTANCE_KEY_MOVIE_ORDER,
                    Context.MODE_PRIVATE);

        sharedPreferences.edit()
                .putInt(SAVED_INSTANCE_KEY_MOVIE_ORDER, value)
                .apply();
    }

    private int getMovieOrderPreference() {

        SharedPreferences sharedPreferences =
                mRootView.getContext().getSharedPreferences(SAVED_INSTANCE_KEY_MOVIE_ORDER,
                        Context.MODE_PRIVATE);

        return sharedPreferences.getInt(SAVED_INSTANCE_KEY_MOVIE_ORDER, 0);
    }

    /***
     *  Download the movies content and update the MoviesAdapter with this data.
     * @param movieOrder
     */
    private void updateMoviesAdapter(final String movieOrder) {

        try {
            // Checks for internet connection.
            if (NetworkUtils.isNetworkAvailable(getActivity().getApplicationContext())){
                setNoInternetWarningMode(false);
            }else{
                setNoInternetWarningMode(true);
                return;
            }

            //Clear the adapter.
            mRecyclerView.setAdapter(null);
            mRecyclerView.getRecycledViewPool().clear();

            // Creates the Retrofit instance.
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MovieAPI.MOVIES_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Binding the interface MovieAPI with the Retrofit API.
            final MovieAPI movieAPI = retrofit.create(MovieAPI.class);

            // Creates the Call<Page> instance.
            Call<Page> call = movieAPI.getMoviePage(
                    movieOrder,
                    BuildConfig.THE_MOVIE_DB_API_KEY
            );

            // Binding onResponse and onFailure methods of this class to this object.
            // Then, put a request in the queue.
            call.enqueue(this);

        }catch (Exception e){
            Log.e(Utils.getMethodName(), e.toString());
            setNoInternetWarningMode(true);
        }
    }

    private void prepareRecycleView() {

        // Sets some Recycle view properties.
        GridLayoutManager manager = new GridLayoutManager(
                mRootView.getContext(),
                Utils.calculateNumberOfColumns(mRootView.getContext())
        );

        mRecyclerView.setLayoutManager(null);

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(null);
    }

    /**
     * Sets the visibility of the layout that warns the user when there isn't a internet connection.
     * @param value objects status visibility.
     */
    private void setNoInternetWarningMode(boolean value){

        if(value){
            // Sets the objects visible when there isn't internet connection.
            mLinearLayoutNoInternetWarning.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);

        }else {
            // Sets the objects invisible when there is internet connection.
            mLinearLayoutNoInternetWarning.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResponse(Call<Page> call, Response<Page> response) {

        try {
            // Checks if the fragment is attached to activity.
            if (isAdded()) {

                // Checks if the response from http request is different of null to load
                // the data on the grid view.
                if (response != null) {

                    List<Movie> movieList;
                    movieList = response.body().getMovies();

                    MovieAdapter movieAdapter = new MovieAdapter(
                            mRootView.getContext(),
                            movieList, this
                    );

                    mRecyclerView.setAdapter(movieAdapter);

                }else{
                    setNoInternetWarningMode(true);
                }
            }

        } catch (Exception e) {
            Log.e(Utils.getMethodName(), e.toString());
            e.printStackTrace();
        }
        finally {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure(Call<Page> call, Throwable t) {
        Log.i(Utils.getMethodName(), t.toString());
        setNoInternetWarningMode(true);
    }

    @Override
    public void onClickMovieItem(int position) {
        openMovieDetails(position);
    }
}
