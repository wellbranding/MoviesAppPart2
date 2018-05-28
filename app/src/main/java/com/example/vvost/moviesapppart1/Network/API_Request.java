package com.example.vvost.moviesapppart1.Network;

import com.example.vvost.moviesapppart1.Models.FullMovie;
import com.example.vvost.moviesapppart1.Models.Movie;
import com.example.vvost.moviesapppart1.Models.MovieArray;
import com.example.vvost.moviesapppart1.Models.Review;
import com.example.vvost.moviesapppart1.Models.ReviewArray;
import com.example.vvost.moviesapppart1.Models.TrailerArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by vvost on 3/18/2018.
 */

public interface API_Request {
    @GET("movie/popular")
    Call<MovieArray> getPopularMovies(
            @Query("api_key") String key

    );

    @GET("movie/top_rated")
    Call<MovieArray> getTopRatedMovies(
            @Query("api_key") String key

    );

    @GET("movie/{movie_id}/videos")
    Call<TrailerArray> getMovieVideos(
            @Path("movie_id") Integer movie_id,
            @Query("api_key") String key

    );

    @GET("movie/{movie_id}/reviews")
    Call<ReviewArray> getMovieReviews(
            @Path("movie_id") Integer movie_id,
            @Query("api_key") String key

    );

    @GET("movie/{movie_id}")
    Call<FullMovie> getParticularMovie(
            @Path("movie_id") Integer movie_id,
            @Query("api_key") String key
    );
}
