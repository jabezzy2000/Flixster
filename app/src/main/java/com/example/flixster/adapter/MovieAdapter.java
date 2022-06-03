package com.example.flixster.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }
    //inflates a layout from xml file and returns the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder((movieView));
    }
    //Involves populating data into the item through the holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get the movie passed in position
        //bind the movie into the viewholder
        Movie movie = movies.get(position);
        holder.bind(movie);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView tvTitle;
    TextView tvOverview;
    ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);

        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            //if phone is in landscape, imageUrl = backdrop image
            if (context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
                //incorporating glide into if else statements to have different placeholders depending on orientation
                imageUrl = movie.getBackdropPath();
                Glide.with(context).load(imageUrl).placeholder(R.drawable.flicks_backdrop_placeholder).into(ivPoster);

            }
            else {
                imageUrl = movie.getPosterPath();
                int radius = 30;
                int margin = 10;
                Glide.with(context).load(imageUrl).placeholder(R.drawable.flicks_movie_placeholder).into(ivPoster);
            }
            //using glide, placeholder is loaded while imageUrl loads
            //Glide.with(context).load(imageUrl).placeholder(R.drawable.flicks_backdrop_placeholder).into(ivPoster);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                Movie movie = movies.get(position);
                Intent intent = new Intent (context, MovieDetailsActivity.class);
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                context.startActivity(intent);
            }

        }
    }
}
