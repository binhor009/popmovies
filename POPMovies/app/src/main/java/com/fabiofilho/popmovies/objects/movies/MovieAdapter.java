package com.fabiofilho.popmovies.objects.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fabiofilho.popmovies.R;
import com.fabiofilho.popmovies.objects.movies.gson.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dialam on 21/01/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private Context mContext;
    private List<Movie> mMovieList;
    private MovieAdapterOnClickHandler mClickHandler;

    public MovieAdapter(Context context, List<Movie> movieList, MovieAdapterOnClickHandler clickHandler) {
        mContext = context;
        mMovieList = movieList;
        mClickHandler = clickHandler;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.movie_thumbnail, parent, false);

        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {

        Picasso.with(mContext)
                .load(mMovieList.get(position).getPosterPath())
                .placeholder(R.mipmap.ic_movie_default)
                .into(holder.imageView);
    }

    public long getItemId(int position) {
        return position;
    }

    public Movie getItem(int position){
        return mMovieList.get(position);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    // View Holder.
    class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView imageView;

        public MovieAdapterViewHolder(View view) {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.movie_thumbnail_image_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClickMovieItem(getAdapterPosition());
        }
    }

}