package com.fabiofilho.popmovies.Objects.Movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.fabiofilho.popmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dialam on 21/01/17.
 */

public class MovieAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Movie> mMovieList;

    public MovieAdapter(Context context, ArrayList<Movie> movieList) {
        mContext = context;
        mMovieList = movieList;
    }

    public int getCount() {
        return mMovieList.size();
    }

    public Object getItem(int position) {
        return mMovieList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // Create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View view, ViewGroup viewGroup) {

        // if it's not recycled, initialize the object.
         if (view == null) {

             ImageView imageView;
             LayoutInflater inflater = LayoutInflater.from(mContext);

             view = inflater.inflate(R.layout.movie_thumbnail, null);
             imageView = (ImageView) view.findViewById(R.id.MovieThumbnailImageView);

             Picasso.with(mContext).load(mMovieList.get(position).getPosterPath()).into(imageView);
        }

        return view;
    }

}