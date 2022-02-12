package com.example.flixter.adapters;

import java.util.List;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.R;
import com.example.flixter.models.Movies;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movies> movies;

    public MovieAdapter(Context context, List<Movies> movies) {
        this.context = context;
        this.movies = movies;
    }

    //Usually involves inflating a layout of XMl and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        //Get the movie at the passed in position
        Movies movie = movies.get(position);
        //Bind the movie data in the VH
        holder.bind(movie);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        public void bind(Movies movies) {
            tvTitle.setText(movies.getTitle());
            tvOverview.setText(movies.getOverview());
            String imageURL;

            //If phone is in landscape then imageURL = back drop image
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageURL = movies.getBackdropPath();
            }
            else{
                imageURL = movies.getPosterPath();
            }
            //Else, go with default
            Glide.with(context).load(imageURL).into(ivPoster);
        }
    }
}
