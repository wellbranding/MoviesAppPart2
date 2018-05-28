package com.example.vvost.moviesapppart1.Models;

/**
 * Created by vvost on 3/18/2018.
 */

public class Movie {

    private String poster_path;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public Movie(String poster_path, Integer id) {
        this.poster_path = poster_path;
        this.id = id;
    }

    public Movie() {
    }
}
