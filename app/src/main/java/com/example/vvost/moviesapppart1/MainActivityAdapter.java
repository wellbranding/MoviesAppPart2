package com.example.vvost.moviesapppart1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vvost.moviesapppart1.Models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvost on 3/18/2018.
 */

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<Movie> movieList;
    private Cursor mCursor;

    public List<Movie> getMovieList() {
        return movieList;
    }

    void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    MainActivityAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        movieList = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_element, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Picasso.with(context.getApplicationContext())
                .load(context.getString(R.string.image_load_picasso_size) + movieList.get(position).getPoster_path())
                .into(holder.imageView);

    }


    @Override
    public int getItemCount() {

        if (movieList == null)
            return 0;
        return movieList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, MovieActivity.class);
                    intent.putExtra("id", movieList.get(getAdapterPosition()).getId());
                    context.startActivity(intent);

                }
            });


        }
    }
}
