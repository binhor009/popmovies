package com.fabiofilho.popmovies.Objects.Movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.fabiofilho.popmovies.R;

import java.util.ArrayList;

/**
 * Created by dialam on 21/01/17.
 */

public class MoviesAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Movie> mMovieList;
    private LayoutInflater mLayoutInflater;

    public MoviesAdapter(Context context, ArrayList<Movie> movieList) {
        mContext = context;
        mMovieList = movieList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return 10/*mMovieList.size()*/;
    }

    public Object getItem(int position) {
        return mMovieList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // Create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View view, ViewGroup viewGroup) {

        ImageView imageView;
        if (view == null) {
            // if it's not recycled, initialize the object.
            view = mLayoutInflater.inflate(R.layout.movie_thumbnail, null);
            imageView = (ImageView) view.findViewById(R.id.MovieThumbnailImageView);

        } else
            imageView = (ImageView) view;

        imageView.setImageResource(R.drawable.sample_0);
        return imageView;
    }

}