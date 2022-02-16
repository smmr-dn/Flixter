package com.example.flixter.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixter.DetailActivity;
import com.example.flixter.MainActivity;
import com.example.flixter.R;
import com.example.flixter.models.Movies;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        ImageView ivIcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            container = itemView.findViewById(R.id.container);

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
            int radius = 50;
            int margin = 20;
            //Else, go with default
            Glide.with(context).load(imageURL).centerCrop().transform(new RoundedCornersTransformation(radius, margin)).into(ivPoster);

            //1. Register click listener on the overlay icon
            ivIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //2. Navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movies));
                    Pair<View, String> p1 = Pair.create((View)ivPoster, "transition");
                    Pair<View, String> p2 = Pair.create((View)tvTitle, "transitionTitle");
                    ActivityOptionsCompat options;
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, p1, p2);
                    context.startActivity(i, options.toBundle());
                }
            });
        }
    }
}
