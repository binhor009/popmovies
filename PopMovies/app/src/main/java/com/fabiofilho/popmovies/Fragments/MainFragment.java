package com.fabiofilho.popmovies.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.fabiofilho.popmovies.Objects.Connections.AsyncTaskRequest;
import com.fabiofilho.popmovies.Objects.Connections.NetworkUtils;
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
            //TODO: Change the way this stores the current movie order.
            Movie.sChosenPopularMovieOrder = !Movie.sChosenPopularMovieOrder;
            updateMoviesAdapter();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_main, container, false);

        creationOfObjects();

        return mRootView;
    }

    private void creationOfObjects() {

        mGridView = (GridView) mRootView.findViewById(R.id.FragmentMainMoviesGridView);
        updateMoviesAdapter();
    }

    private void updateMoviesAdapter() {

        try {
            String movieOrderBy = Movie.sChosenPopularMovieOrder ? Movie.MOVIES_ORDER_POPULAR : Movie.MOVIES_ORDER_TOP_RATED;
            URL url = NetworkUtils.buildURL(Movie.MOVIES_URL + movieOrderBy, true);

            AsyncTaskRequest asyncTaskRequest = new AsyncTaskRequest() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    Toast.makeText(mRootView.getContext(), getString(R.string.info_message_loading_movies), Toast.LENGTH_SHORT).show();
                }

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
