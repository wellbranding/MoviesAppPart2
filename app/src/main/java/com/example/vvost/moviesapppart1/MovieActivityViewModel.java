package com.example.vvost.moviesapppart1;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.vvost.moviesapppart1.Models.MovieArray;
import com.example.vvost.moviesapppart1.Network.APIUrl;
import com.example.vvost.moviesapppart1.Network.API_Request;
import com.example.vvost.moviesapppart1.Models.FullMovie;
import com.example.vvost.moviesapppart1.Models.Review;
import com.example.vvost.moviesapppart1.Models.ReviewArray;
import com.example.vvost.moviesapppart1.Models.Trailer;
import com.example.vvost.moviesapppart1.Models.TrailerArray;
import com.example.vvost.moviesapppart1.Network.RetrofitFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * View model for each item in the repositories RecyclerView
 */
public class MovieActivityViewModel extends AndroidViewModel {

    private static final String TAG = "trxt";
    private int id;
    private MutableLiveData<FullMovie> movie;
    private MutableLiveData<List<Review>> reviews;
    private MutableLiveData<List<Trailer>> trailers;

    public LiveData<List<Trailer>> getTrailers() {
        if (trailers == null) {
            trailers = new MutableLiveData<>();
            QueryMovies();

        }
        return trailers;
    }

    public LiveData<FullMovie> getMovie() {
        if (movie == null) {
            movie = new MutableLiveData<>();
            Query();
        }
        return movie;
    }

    public LiveData<List<Review>> getReviews() {
        if (reviews == null) {
            reviews = new MutableLiveData<>();
            QueryReviews();
        }
        return reviews;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MovieActivityViewModel(@NonNull Application application) {
        super(application);
    }

    private void QueryMovies() {

        Call<TrailerArray> call =  RetrofitFactory.create().getMovieVideos(
                id, getApplication().getString(R.string.api_key));
        call.enqueue(new Callback<TrailerArray>() {

            @Override
            public void onResponse(@NonNull Call<TrailerArray> call, @NonNull Response<TrailerArray> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body() != null) {
                        assert response.body() != null;
                        trailers.postValue(response.body().getResults());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TrailerArray> call, @NonNull Throwable t) {
                Toast.makeText(getApplication().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });

    }

    private void QueryReviews() {

        Call<ReviewArray> call =  RetrofitFactory.create().getMovieReviews(
                id, getApplication().getString(R.string.api_key));
        call.enqueue(new Callback<ReviewArray>() {

            @Override
            public void onResponse(@NonNull Call<ReviewArray> call, @NonNull Response<ReviewArray> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body() != null) {
                        assert response.body() != null;
                        reviews.postValue(response.body().getReviewList());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewArray> call, @NonNull Throwable t) {
                Toast.makeText(getApplication().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });


    }

    private void Query() {

        Call<FullMovie> call =  RetrofitFactory.create().getParticularMovie(
                id, getApplication().getString(R.string.api_key));
        call.enqueue(new Callback<FullMovie>() {

            @Override
            public void onResponse(@NonNull Call<FullMovie> call, @NonNull Response<FullMovie> response) {
                if (response.isSuccessful()) {
                    movie.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<FullMovie> call, @NonNull Throwable t) {
                Toast.makeText(getApplication().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                //  Toast.makeText(Movie_Activity.this, R.string.data_parse_error, Toast.LENGTH_SHORT).show();

            }
        });


    }


}
