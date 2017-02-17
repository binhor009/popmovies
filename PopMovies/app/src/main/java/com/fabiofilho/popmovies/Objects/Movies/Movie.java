package com.fabiofilho.popmovies.Objects.Movies;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * Created by dialam on 23/01/17.
 */

public class Movie implements Parcelable {

    public static final String PARCELABLE_KEY = "com.fabiofilho.popmovies.Objects.Movies.PARCELABLE_KEY";

    public static final String MOVIES_URL =  "https://api.themoviedb.org/3/movie/";
    public static final String MOVIES_IMAGE_URL =  "http://image.tmdb.org/t/p/";

    public static final String[] MOVIE_ORDER = {
            "popular", "top_rated"
    };

    private static final String IMAGE_SIZE = "w342";

    private int id;
    private String title;
    private String releaseDate;
    private double votesAverage;
    private String overview;
    private String posterPath;
    private String duration;

    public Movie(int id, String title, String releaseDate, double votesAverage, String overview, String posterPath) {

        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.votesAverage = votesAverage;
        this.overview = overview;
        this.posterPath = posterPath;
    }

    public Movie(Parcel in){

        id = in.readInt();
        title = in.readString();
        releaseDate = in.readString();
        votesAverage = in.readDouble();
        overview = in.readString();
        posterPath = in.readString();
        duration = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        @Override
        public Movie createFromParcel(Parcel parcel) {

            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {

            return new Movie[0];
        }
    };

    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(releaseDate);
        parcel.writeDouble(votesAverage);
        parcel.writeString(overview);
        parcel.writeString(posterPath);
        parcel.writeString(duration);
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getPosterPath() {

        return MOVIES_IMAGE_URL+IMAGE_SIZE+ posterPath;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getDuration() {

        return duration;
    }

    public void setDuration(String duration) {

        this.duration = duration;
    }


    public String getVotesAverage() {

        return String.valueOf(votesAverage)+"/10";
    }

    public String getOverview() {

        return overview;
    }

    /**
     * Formats the releaseDate correctly.
     * @param context
     * @return releaseDate
     */
    public String getReleaseDate(Context context){

        Calendar calendar = Calendar.getInstance();
        calendar.set(
                Integer.parseInt(releaseDate.split("-")[0]),
                Integer.parseInt(releaseDate.split("-")[1]),
                Integer.parseInt(releaseDate.split("-")[2])
        );

        return DateFormat.getMediumDateFormat(context).format(calendar.getTime());
    }

}
