package com.fabiofilho.popmovies.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.fabiofilho.popmovies.Objects.Movies.Movie;
import com.fabiofilho.popmovies.Objects.Movies.MoviesAdapter;
import com.fabiofilho.popmovies.R;

import java.util.ArrayList;

public class MainActivityFragment extends Fragment {

    private View mRootView;
    private GridView mGridview;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_main, container, false);

        creationOfObjects();

        return mRootView;
    }

    private void creationOfObjects() {

        mGridview = (GridView) mRootView.findViewById(R.id.FragmentMainMoviesGridView);
        updateMoviesAdapter();
    }

    private void updateMoviesAdapter(){


        mGridview.setAdapter(new MoviesAdapter(mRootView.getContext(), new ArrayList<Movie>()));
    }

}
