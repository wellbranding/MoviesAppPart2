package com.example.vvost.moviesapppart1.UIViews;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vvost.moviesapppart1.Adapters.ReviewsActivityAdapter;
import com.example.vvost.moviesapppart1.Models.Review;
import com.example.vvost.moviesapppart1.MovieActivityViewModel;
import com.example.vvost.moviesapppart1.R;

import java.util.List;

/**
 * Created by vvost on 3/22/2018.
 */

public class Review_Fragment extends Fragment {
    private static final String TAG = "ahahjjhaha";
    TextView title;
    ImageView imageView;
    TextView summary;
    TextView ReleaseDate;
    ProgressBar loader;
    TextView VoteAvg;
    RecyclerView recyclerView;
    ReviewsActivityAdapter reviewsActivityAdapter;

    MovieActivityViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).
                get(MovieActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reviews_fragment, container, false);
        rootView.setTag(TAG);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        Initialize(rootView);
        return rootView;
    }

    private void UpdateUI(List<Review> reviews) {
        reviewsActivityAdapter.setReviewslist(reviews);
        reviewsActivityAdapter.notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    private void Initialize(View view) {
        reviewsActivityAdapter = new ReviewsActivityAdapter(getActivity().getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(reviewsActivityAdapter);

        viewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                assert reviews != null;
                //   Log.d("kokia", String.valueOf(reviews.getOverview()));
                UpdateUI(reviews);

            }
        });


    }
}
