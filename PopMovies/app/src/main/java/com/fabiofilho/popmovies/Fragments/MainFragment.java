package com.fabiofilho.popmovies.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.fabiofilho.popmovies.Activities.MovieDetailsActivity;
import com.fabiofilho.popmovies.Objects.Connections.AsyncTaskRequest;
import com.fabiofilho.popmovies.Objects.Connections.NetworkUtils;
import com.fabiofilho.popmovies.Objects.Dialogs.MovieOrderDialog;
import com.fabiofilho.popmovies.Objects.Dialogs.SimpleDialog;
import com.fabiofilho.popmovies.Objects.Movies.Movie;
import com.fabiofilho.popmovies.Objects.Movies.MovieAdapter;
import com.fabiofilho.popmovies.Objects.Movies.MovieJSON;
import com.fabiofilho.popmovies.Objects.Utilities;
import com.fabiofilho.popmovies.R;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private View mRootView;
    private GridView mGridView;
    private AsyncTaskRequest mAsyncTaskRequest;
    private int mIndexMovieOrderChosen = 0;


    private final String SAVED_INSTANCE_KEY_MOVIE_ORDER_CHOSEN = "SAVED_INSTANCE_KEY_MOVIE_ORDER_CHOSEN";

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

        // Loads the saved variables.
        loadSavedInstanceState(savedInstanceState);

        // Refers the objects to class variables.
        referScreenObjects();

        // Setting the default values for them.
        loadObjects();

        return mRootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        // Saves the current movie order.
        outState.putInt(SAVED_INSTANCE_KEY_MOVIE_ORDER_CHOSEN, mIndexMovieOrderChosen);

        super.onSaveInstanceState(outState);
    }

    /**
     * Loads the saved variables.
     * @param savedInstanceState
     */
    private void loadSavedInstanceState(@Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null){

            // Loads the movie order.
            mIndexMovieOrderChosen = savedInstanceState.getInt(SAVED_INSTANCE_KEY_MOVIE_ORDER_CHOSEN);
        }

    }

    /**
     * Loads the properties from the objects.
     */
    private void loadObjects() {

        setGridViewListeners();
        updateMoviesAdapter(Movie.MOVIE_ORDER[mIndexMovieOrderChosen]);
    }

    /**
     * Refers the objects from screen.
     */
    private void referScreenObjects() {

        mGridView = (GridView) mRootView.findViewById(R.id.FragmentMainMoviesGridView);
    }

    /**
     * Defines all grid view events.
     */
    private void setGridViewListeners(){

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openMovieDetails(position);
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
        Movie movie = ((Movie) mGridView.getAdapter().getItem(position));

        // Creates an intent with a Movie instance as parameter.
        Intent intent = new Intent(mRootView.getContext(), MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.EXTRA_KEY, movie);

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

                // Updates the movies adapter if the user has chosen a different order.
                updateMoviesAdapter(Movie.MOVIE_ORDER[which]);
            }
        }).show();
    }

    /***
     *  Download the movies content and update the MoviesAdapter with this data.
     * @param movieOrder
     */
    private void updateMoviesAdapter(String movieOrder) {

        try {
            URL url = NetworkUtils.buildURL(Movie.MOVIES_URL + movieOrder, true);
            mAsyncTaskRequest = new AsyncTaskRequest() {

                @Override
                protected void onPostExecute(String response) {
                    super.onPostExecute(response);

                    try {
                        // Checks if the fragment is attached to activity.
                        if (isAdded()) {

                            // Checks if the response from http request is different of null to load
                            // the data on the grid view.
                            if (response != null) {

                                ArrayList<Movie> movieList;
                                movieList = (ArrayList<Movie>) MovieJSON.createMovieListByJSON(response);

                                mGridView.setAdapter(
                                        new MovieAdapter(
                                                mRootView.getContext(),
                                                movieList
                                        )
                                );
                            }else{
                                askToUserTryGetContentAgain();
                            }
                        }

                    } catch (JSONException e) {
                        Log.e(Utilities.getMethodName(), e.toString());
                    }
                }

            };

            // Initialize the parallel process to load content.
            mAsyncTaskRequest.execute(url);

        }catch (MalformedURLException e){
            Log.e(Utilities.getMethodName(), e.toString());
        }
    }

    /**
     *  Ask if the user wants try to get the content again.
     */
    private void askToUserTryGetContentAgain() {

        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.onCreateDialog(getActivity(), R.string.dialog_message_failed_load_the_content_try_again, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Tries to update the adapter if the user wants.
                updateMoviesAdapter(Movie.MOVIE_ORDER[mIndexMovieOrderChosen]);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Finalizes the activity if the user doesn't want.
                getActivity().finish();
            }
        }).show();
    }

}
