package com.example.vvost.moviesapppart1.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vvost on 3/18/2018.
 */

public class MovieArray {

    List<Movie> results;

    public MovieArray(List<Movie> movieList) {
        this.results = movieList;
    }

    public List<Movie> getMovieList() {
        return results;
    }

    public void setMovieList(List<Movie> movieList) {
        this.results = movieList;
    }
}
