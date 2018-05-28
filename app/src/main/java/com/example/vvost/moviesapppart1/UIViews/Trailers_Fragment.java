package com.example.vvost.moviesapppart1.UIViews;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vvost.moviesapppart1.Adapters.TrailersActivityAdapter;
import com.example.vvost.moviesapppart1.Models.Trailer;
import com.example.vvost.moviesapppart1.MovieActivityViewModel;
import com.example.vvost.moviesapppart1.R;

import java.util.List;

/**
 * Created by vvost on 3/22/2018.
 */

public class Trailers_Fragment extends Fragment {
    private static final String TAG = "ahahjjhaha";
    TextView title;
    ImageView imageView;
    TextView summary;
    TextView ReleaseDate;
    ProgressBar loader;
    TextView VoteAvg;
    RecyclerView recyclerView;
    TrailersActivityAdapter trailersadapter;

    MovieActivityViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).
                get(MovieActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.trailers_fragment, container, false);
        rootView.setTag(TAG);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        Initialize(rootView);
        return rootView;
    }

    private void UpdateUI(List<Trailer> trailers) {
        trailersadapter.setTrailerList(trailers);
        trailersadapter.notifyDataSetChanged();


    }

    @SuppressLint("SetTextI18n")
    private void Initialize(View view) {

        trailersadapter = new TrailersActivityAdapter(getActivity().getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(trailersadapter);

        viewModel.getTrailers().observe(this, trailers -> {
            assert trailers != null;
            UpdateUI(trailers);

        });


    }
}
