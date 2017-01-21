package com.fabiofilho.popmovies.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.fabiofilho.popmovies.ImageAdapter;
import com.fabiofilho.popmovies.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private View mRootView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_main, container, false);

        objectsCreation();

        return mRootView;
    }

    private void objectsCreation() {

        GridView gridview = (GridView) mRootView.findViewById(R.id.FragmentMainMoviesGridView);
        gridview.setAdapter(new ImageAdapter(mRootView.getContext()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(mRootView.getContext(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
