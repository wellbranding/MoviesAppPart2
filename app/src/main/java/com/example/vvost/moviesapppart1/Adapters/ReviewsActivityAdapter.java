package com.example.vvost.moviesapppart1.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vvost.moviesapppart1.Models.Review;
import com.example.vvost.moviesapppart1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvost on 3/18/2018.
 */

public class ReviewsActivityAdapter extends RecyclerView.Adapter<ReviewsActivityAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<Review> reviewslist;

    public ReviewsActivityAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        reviewslist = new ArrayList<>();
    }

    public List<Review> getReviewslist() {
        return reviewslist;
    }

    public void setReviewslist(List<Review> reviewslist) {
        this.reviewslist = reviewslist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.review_element, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.content.setText(reviewslist.get(position).getContent());
        holder.author.setText(reviewslist.get(position).getAuthor());
    }


    @Override
    public int getItemCount() {
        return reviewslist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView author;
        TextView content;

        MyViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            content = itemView.findViewById(R.id.content);


        }
    }
}
