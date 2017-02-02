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
import com.fabiofilho.popmovies.Objects.Movies.Movie;
import com.fabiofilho.popmovies.Objects.Movies.MovieAdapter;
import com.fabiofilho.popmovies.Objects.Movies.MovieJSON;
import com.fabiofilho.popmovies.Objects.Utilities;
import com.fabiofilho.popmovies.R;

import org.json.JSONException;

import java.io.IOException;
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_movie_sort) {

            openMovieDialogOrder();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_main, container, false);

        referScreenObjects();

        return mRootView;
    }

    private void referScreenObjects() {

        mGridView = (GridView) mRootView.findViewById(R.id.FragmentMainMoviesGridView);
        setGridViewListener();
        updateMoviesAdapter(Movie.MOVIE_ORDER[0]);
    }

    private void setGridViewListener(){

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openMovieDetails(position);
            }
        });
    }


    private void openMovieDetails(int position){

        Movie movie = ((Movie) mGridView.getAdapter().getItem(position));
        Intent intent = new Intent(mRootView.getContext(), MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.EXTRA_KEY, movie);

        startActivity(intent);
    }


    private void openMovieDialogOrder(){

        MovieOrderDialog movieOrderDialog = new MovieOrderDialog();
        movieOrderDialog.onCreateDialog(getActivity(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateMoviesAdapter(Movie.MOVIE_ORDER[which]);
            }
        }).show();
    }

    private void updateMoviesAdapter(String movieOrder) {

        try {
            URL url = NetworkUtils.buildURL(Movie.MOVIES_URL + movieOrder, true);
            AsyncTaskRequest asyncTaskRequest = new AsyncTaskRequest() {


                @Override
                protected void onPostExecute(String response) {
                    super.onPostExecute(response);

                    try {
                        mGridView.setAdapter(
                                new MovieAdapter(
                                        mRootView.getContext(), (ArrayList<Movie>) MovieJSON.createMovieListByJSON(response)
                                )
                        );

                    } catch (JSONException e) {
                        Log.e(Utilities.getMethodNameForLog(), e.toString());
                    }
                }

                @Override
                public void onNetworkUtilsGetsIOException(IOException e) {
                    super.onNetworkUtilsGetsIOException(e);
                }
            };

            asyncTaskRequest.execute(url);

        }catch (MalformedURLException e){
            Log.e(Utilities.getMethodNameForLog(), e.toString());
        }
    }

}
