package com.fabiofilho.popmovies.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class MainFragment extends Fragment {

    private View mRootView;
    private GridView mGridView;


    public MainFragment() {

        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_movie_sort) {

            //Opens the movie dialog order.
            openMovieDialogOrder();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_main, container, false);

        //Refers the objects to class variables.
        referScreenObjects();

        //Setting the default values for them.
        loadObjects();

        return mRootView;
    }

    /**
     * Loads the properties from the objects.
     */
    private void loadObjects() {

        setGridViewListeners();
        updateMoviesAdapter(Movie.MOVIE_ORDER[0]);
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
     * chosen by the index @position.
     * @param position
     */
    private void openMovieDetails(int position){

        Movie movie = ((Movie) mGridView.getAdapter().getItem(position));
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
            AsyncTaskRequest asyncTaskRequest = new AsyncTaskRequest() {


                @Override
                protected void onPostExecute(String response) {
                    super.onPostExecute(response);

                    try {

                        if(response !=null) {
                            mGridView.setAdapter(
                                    new MovieAdapter(
                                            mRootView.getContext(),
                                            (ArrayList<Movie>) MovieJSON.createMovieListByJSON(response)
                                    )
                            );
                        }else{
                            if(isAdded()) askToUserTryGetContentAgain();
                        }

                    } catch (JSONException e) {
                        Log.e(Utilities.getMethodNameForLog(), e.toString());
                    }
                }

            };

            asyncTaskRequest.execute(url);

        }catch (MalformedURLException e){
            Log.e(Utilities.getMethodNameForLog(), e.toString());
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
                updateMoviesAdapter(Movie.MOVIE_ORDER[MovieOrderDialog.getLastItemIndexChosen()]);
            }
        }, null).show();
    }

}
