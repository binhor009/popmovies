package com.fabiofilho.popmovies.Objects.Movies;

import android.content.Context;
import android.text.format.DateFormat;

import org.parceler.Parcel;

import java.util.Calendar;

import static com.fabiofilho.popmovies.Objects.Movies.AsyncTaskRequestMovies.MOVIES_IMAGE_URL;

/**
 * Created by dialam on 23/01/17.
 */

@Parcel
public class Movie {

    public static final String PARCELABLE_KEY = "com.fabiofilho.popmovies.Objects.Movies.PARCELABLE_KEY";

    private static final String IMAGE_SIZE = "w342";

    int id;
    String title;
    String releaseDate;
    double votesAverage;
    String overview;
    String posterPath;

    public Movie(){
    }

    public Movie(int id, String title, String releaseDate, double votesAverage, String overview, String posterPath) {

        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.votesAverage = votesAverage;
        this.overview = overview;
        this.posterPath = posterPath;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getPosterPath() {

        return MOVIES_IMAGE_URL + IMAGE_SIZE + posterPath;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
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
