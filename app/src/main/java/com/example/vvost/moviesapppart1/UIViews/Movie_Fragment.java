package com.example.vvost.moviesapppart1.UIViews;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vvost.moviesapppart1.Models.FullMovie;
import com.example.vvost.moviesapppart1.MovieActivityViewModel;
import com.example.vvost.moviesapppart1.R;
import com.squareup.picasso.Picasso;

/**
 * Created by vvost on 3/22/2018.
 */

public class Movie_Fragment extends Fragment {
    private static final String TAG = "ahahhaha";
    TextView title;
    ImageView imageView;
    TextView summary;
    TextView ReleaseDate;
    ProgressBar loader;
    TextView VoteAvg;
    MovieActivityViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of((FragmentActivity) getActivity()).
                get(MovieActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_activity, container, false);
        rootView.setTag(TAG);
        title = rootView.findViewById(R.id.title);
        Initialize(rootView);
        return rootView;
    }

    private void UpdateUI(FullMovie fullMovie) {
        title.setText(fullMovie.getTitle());
        Picasso.with(getActivity())
                .load("http://image.tmdb.org/t/p/w185/" + fullMovie.getPoster_path())
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        loader.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(getActivity(), R.string.image_parse_error, Toast.LENGTH_SHORT).show();

                    }
                });
        summary.setText(fullMovie.getOverview());
        ReleaseDate.setText(getString(R.string.Release) + fullMovie.getRelease_date());
        VoteAvg.setText(getString(R.string.Votes_avg) + String.valueOf(fullMovie.getVote_average()));


    }

    @SuppressLint("SetTextI18n")
    private void Initialize(View view) {
        title = view.findViewById(R.id.title);
        imageView = view.findViewById(R.id.movie_iv);
        summary = view.findViewById(R.id.overview);
        ReleaseDate = view.findViewById(R.id.release);
        VoteAvg = view.findViewById(R.id.voting);
        loader = view.findViewById(R.id.progressBar);
        viewModel.getMovie().observe(this, new Observer<FullMovie>() {
            @Override
            public void onChanged(@Nullable FullMovie fullMovie) {
                assert fullMovie != null;
                Log.d("kokia", String.valueOf(fullMovie.getOverview()));
                UpdateUI(fullMovie);

            }
        });


    }
}
