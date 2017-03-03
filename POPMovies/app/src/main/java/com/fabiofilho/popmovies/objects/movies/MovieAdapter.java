package com.fabiofilho.popmovies.objects.movies;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.fabiofilho.popmovies.R;
import com.fabiofilho.popmovies.objects.movies.gson.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dialam on 21/01/17.
 */

public class MovieAdapter extends BaseAdapter {
    private Context mContext;
    private List<Movie> mMovieList;
    private LayoutInflater mLayoutInflater;

    public MovieAdapter(Context context, List<Movie> movieList) {
        mContext = context;
        mMovieList = movieList;
        mLayoutInflater = LayoutInflater.from(mContext);
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

    public View getView(final int position, @Nullable View view, ViewGroup viewGroup) {

        MovieHolder movieHolder;

        // If it's not recycled, initialize the object.
         if (view == null) {

             // Inflate the movie_thumbnail layout.
             view = mLayoutInflater.inflate(R.layout.movie_thumbnail, null);

             // Creates a MovieHolder instance that works as a struct of movie_thumbnail layout.
             movieHolder = new MovieHolder();

             // Refers the movie_thumbnail layout.
             movieHolder.imageView = (ImageView) view.findViewById(R.id.MovieThumbnailImageView);

             // Sets the movieHolder as a tag in the view.
             view.setTag(movieHolder);

        }else{
            movieHolder = (MovieHolder) view.getTag();
        }

        Picasso.with(view.getContext())
                .load(mMovieList.get(position).getPosterPath())
                .placeholder(R.mipmap.ic_movie_default)
                .into(movieHolder.imageView);

        return view;
    }


    private class MovieHolder{
        ImageView imageView;
    }

}