package com.fabiofilho.popmovies.Objects.Movies;

/**
 * Created by dialam on 23/01/17.
 */

public class Movie {

    private int id;
    private String title;
    private String releaseDate;
    private double votesAverage;
    private String poster;
    private String synopsis;
    private String imageUrl;

    public Movie(int id, String title, String releaseDate, double votesAverage, String poster, String synopsis, String imageUrl) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.votesAverage = votesAverage;
        this.poster = poster;
        this.synopsis = synopsis;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVotesAverage() {
        return votesAverage;
    }

    public void setVotesAverage(double votesAverage) {
        this.votesAverage = votesAverage;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
