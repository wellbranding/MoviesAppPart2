package com.example.vvost.moviesapppart1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.vvost.moviesapppart1.Data.MovieContractDB;
import com.example.vvost.moviesapppart1.Models.FullMovie;
import com.example.vvost.moviesapppart1.UIViews.Movie_Fragment;
import com.example.vvost.moviesapppart1.UIViews.Review_Fragment;
import com.example.vvost.moviesapppart1.UIViews.Trailers_Fragment;

import java.util.Objects;

public class MovieActivity extends AppCompatActivity {

    // Resources
    Resources res;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    RecyclerView.LayoutManager layoutManager;
    MovieActivityViewModel viewModel;
    FullMovie mfullMovie;
    Boolean added = false;
    FloatingActionButton favourite;
    Integer id = 0;


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        res = getResources();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        favourite = findViewById(R.id.favourite);
        favourite.setVisibility(View.INVISIBLE);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        assert data != null;
        id = data.getInt("id");
        FindIfFavourite();
        viewModel = ViewModelProviders.of(this).
                get(MovieActivityViewModel.class);
        viewModel.setId(id);
        viewModel.getMovie().observe(this, new Observer<FullMovie>() {
            @Override
            public void onChanged(@Nullable FullMovie fullMovie) {
                mfullMovie = fullMovie;
                assert fullMovie != null;
                favourite.setVisibility(View.VISIBLE);
                Log.d("kokia", String.valueOf(fullMovie.getOverview()));

            }
        });

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!added) {
                    ContentValues dbmovievalues = new ContentValues();
                    added = true;
                    favourite.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    favourite.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    favourite.setRippleColor(getResources().getColor(R.color.colorAccent));
                    dbmovievalues.put(MovieContractDB.SingleMovieEntry.COLUMN_MOVIE_ID, id);
                    dbmovievalues.put(MovieContractDB.SingleMovieEntry.MOVIE_TITLE, mfullMovie.getTitle());
                    dbmovievalues.put(MovieContractDB.SingleMovieEntry.COLUMN_POSTER_PATH, mfullMovie.getPoster_path());
                    Uri uri = getContentResolver().insert(MovieContractDB.SingleMovieEntry.CONTENT_URI, dbmovievalues);
                    if (uri != null) {
                        Toast.makeText(MovieActivity.this, "Movie successfully added to favourites", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    added = false;
                    favourite.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
                    favourite.setRippleColor(getResources().getColor(R.color.colorPrimaryDark));
                    favourite.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    Uri uri = MovieContractDB.SingleMovieEntry.CONTENT_URI;
                    Uri appendeduri = uri.buildUpon().appendPath(Integer.toString(id)).build();
                    Log.d("svarbus", Integer.toString(id));
                    int result = getContentResolver().delete(appendeduri,
                            null, null);
                    if (result > 0) {
                        Toast.makeText(MovieActivity.this, "Movie successfully removed from favourites", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        String[] tabTitles = new String[]{
                res.getString(R.string.tab1_title),
                res.getString(R.string.tab2_title),
                res.getString(R.string.tab3_title)};
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), tabTitles);
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }

    public void FindIfFavourite() {
        Uri queryuri = Uri.parse(MovieContractDB.SingleMovieEntry.CONTENT_URI + "/" + Integer.toString(id));
        Cursor cursor = getContentResolver().query(queryuri, null, null, null, null,
                null);
        if (cursor.moveToNext()) {
            added = true;
            favourite.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            favourite.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        } else {
            added = false;
            favourite.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            favourite.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private String[] tabTitles;
        private Bundle queryParam;

        SectionsPagerAdapter(FragmentManager fm, String[] tabTitles) {
            super(fm);
            this.tabTitles = tabTitles;

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Movie_Fragment();
                case 1:
                    return new Review_Fragment();
                case 2:
                    return new Trailers_Fragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }


    }
}
