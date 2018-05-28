package com.example.vvost.moviesapppart1.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vvost.moviesapppart1.Models.Trailer;
import com.example.vvost.moviesapppart1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvost on 3/18/2018.
 */

public class TrailersActivityAdapter extends RecyclerView.Adapter<TrailersActivityAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<Trailer> trailerList;

    public TrailersActivityAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        trailerList = new ArrayList<>();
    }

    public List<Trailer> getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.trailer_element, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.trailer.setText(position + " Trailer");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="
                        + trailerList.get(position).getKey())));
            }
        });

    }


    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView trailer;

        MyViewHolder(View itemView) {
            super(itemView);
            trailer = itemView.findViewById(R.id.trailer);


        }
    }
}
