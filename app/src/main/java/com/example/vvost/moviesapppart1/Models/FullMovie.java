package com.example.vvost.moviesapppart1.Models;

import android.arch.lifecycle.MutableLiveData;

/**
 * Created by vvost on 3/18/2018.
 */

public class FullMovie extends MutableLiveData<Movie> {
    private Integer id;
    String title;
    String release_date;
    String poster_path;
    Float vote_average;
    String overview;

    public FullMovie(String title, String release_date, String poster_path, Float vote_average, String overview, Integer id) {
        this.id = id;
        this.title = title;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.overview = overview;
    }

    public FullMovie() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public Float getVote_average() {
        return vote_average;
    }

    public void setVote_average(Float vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
