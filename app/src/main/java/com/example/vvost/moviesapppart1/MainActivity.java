package com.example.vvost.moviesapppart1;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vvost.moviesapppart1.Network.APIUrl;
import com.example.vvost.moviesapppart1.Network.API_Request;
import com.example.vvost.moviesapppart1.Data.MovieContractDB;
import com.example.vvost.moviesapppart1.Models.Movie;
import com.example.vvost.moviesapppart1.Models.MovieArray;
import com.example.vvost.moviesapppart1.Network.RetrofitFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "erorr123";
    private static final String LIST_STATE_KEY = "state_key";
    List<Movie> movieList = null;
    RecyclerView recyclerView;
    MainActivityAdapter mainActivityAdapter;
    ProgressBar progressBar;
    private int TASK_LOADER_ID = 0;
    private boolean isfavourites = false;
    GridLayoutManager gridLayoutManager;
    Parcelable ListState;
    Parcelable savedRecyclerLayoutState;
    private String BUNDLE_RECYCLER_LAYOUT = "savedvalue";
    private int CURRENT_MENU_ITEM;
    SharedPreferences sharedPreferences;

    public static final String MY_SHARED_PREFERNCES = "savedsearch";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressBar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        GetSharedPrefernces();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        InitializeAdapter();
        if (CURRENT_MENU_ITEM == 0)
            GetPopularMovies();
        else if (CURRENT_MENU_ITEM == 1)
            GetTopMovies();
        else
            new MoviesFavouritesGet().execute();

    }

    private void GetSharedPrefernces() {
        Context context = MainActivity.this;
        sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERNCES, Context.MODE_PRIVATE);
        CURRENT_MENU_ITEM = sharedPreferences.getInt("choice", 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular: {
                GetPopularMovies();
                CURRENT_MENU_ITEM = 0;
                break;
            }
            case R.id.top_rated: {
                GetTopMovies();
                CURRENT_MENU_ITEM = 1;
                break;
            }
            case R.id.favourites: {
                GetFavouritesMovies();
                CURRENT_MENU_ITEM = 2;
                break;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
        SharedPreferencesUpdate();
        return true;
    }

    private void InitializeAdapter() {

        mainActivityAdapter = new MainActivityAdapter(getApplicationContext());
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mainActivityAdapter);
    }

    private void SharedPreferencesUpdate() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("choice", CURRENT_MENU_ITEM);
        editor.apply();

    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        if (state != null) {
            savedRecyclerLayoutState = state.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
    }


    private void NotifyAdapter() {
        mainActivityAdapter.setMovieList(movieList);
        mainActivityAdapter.notifyDataSetChanged();
    }


    private void GetPopularMovies() {
        Call<MovieArray> call =  RetrofitFactory.create().getPopularMovies( getString(R.string.api_key));
        call.enqueue(new Callback<MovieArray>() {
            @Override
            public void onResponse(@NonNull Call<MovieArray> call, @NonNull Response<MovieArray> response) {
                if (response.isSuccessful()) {
                    recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
                    movieList = response.body().getMovieList();
                    progressBar.setVisibility(View.INVISIBLE);
                    NotifyAdapter();
                }

            }

            @Override
            public void onFailure(@NonNull Call<MovieArray> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, R.string.movies_load_error, Toast.LENGTH_SHORT).show();

            }

        });
    }

    private void GetTopMovies() {
        Call<MovieArray> call =  RetrofitFactory.create().getTopRatedMovies( getString(R.string.api_key));
        call.enqueue(new Callback<MovieArray>() {
            @Override
            public void onResponse(@NonNull Call<MovieArray> call, @NonNull Response<MovieArray> response) {
                if (response.isSuccessful()) {
                    recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
                    movieList = response.body().getMovieList();
                    progressBar.setVisibility(View.INVISIBLE);
                    NotifyAdapter();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieArray> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, R.string.movies_load_error, Toast.LENGTH_SHORT).show();

            }

        });
    }

    public void GetFavouritesMovies() {
        // getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);
        new MoviesFavouritesGet().execute();


    }

    public class MoviesFavouritesGet extends AsyncTask<Void, Void, Void> {

        List<Movie> moviesquery;

        @Override
        protected Void doInBackground(Void... params) {
            Uri uri = MovieContractDB.SingleMovieEntry.CONTENT_URI;
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            int count = cursor.getCount();
            moviesquery = new ArrayList<>();
            if (count == 0) {
                return null;
            }

            if (cursor.moveToFirst()) {
                do {
                    String poster_path = cursor.getString(cursor.getColumnIndex(MovieContractDB.SingleMovieEntry.COLUMN_POSTER_PATH));
                    int movie_id = cursor.getInt(cursor.getColumnIndex(MovieContractDB.SingleMovieEntry.COLUMN_MOVIE_ID));
                    moviesquery.add(new Movie(poster_path, movie_id));
                    //  Log.d("taaa", mo)
                } while (cursor.moveToNext());
            }

            cursor.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.INVISIBLE);
            mainActivityAdapter.setMovieList(moviesquery);
            mainActivityAdapter.notifyDataSetChanged();
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }


}
