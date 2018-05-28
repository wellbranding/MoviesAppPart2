package com.example.vvost.moviesapppart1.Models;

import java.util.List;

public class ReviewArray {

    List<Review> results;

    public ReviewArray(List<Review> movieList) {
        this.results = movieList;
    }

    public List<Review> getReviewList() {
        return results;
    }

    public void setReviewList(List<Review> movieList) {
        this.results = movieList;
    }


}
