/*
 * MIT License
 *
 * Copyright (c) 2016. Dmytro Karataiev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.adkdevelopment.movieslist.ui.adapters;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.adkdevelopment.movieslist.data.remote.Movie;
import com.adkdevelopment.movieslist.databinding.ItemMovieBinding;
import com.adkdevelopment.movieslist.ui.interfaces.ItemClickListener;
import com.adkdevelopment.movieslist.ui.interfaces.ItemTouchHelperAdapter;
import com.adkdevelopment.movieslist.ui.viewholders.MovieViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for the ListFragment with Movies.
 * Created by karataev on 9/15/16.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder>
        implements ItemTouchHelperAdapter {

    private List<Movie> mMovies;
    private ItemClickListener<Movie, View> mListener;

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemMovieBinding binding = ItemMovieBinding.inflate(inflater, parent, false);
        return new MovieViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.getBinding().setMovie(mMovies.get(position));
        holder.getBinding().setClick(mListener);
    }

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String v) {
        // TODO: 9/15/16 add progress bar && error image
        Picasso.with(imageView.getContext()).load(Movie.PATH + v).into(imageView);
    }

    @Override
    public int getItemCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    public void setTasks(List<Movie> movies,
                         ItemClickListener<Movie, View> listener) {
        mMovies = movies;
        notifyDataSetChanged();
        mListener = listener;
    }

    @Override
    public boolean onItemDismiss(int position) {
        notifyItemRemoved(position);
        mMovies.remove(position);
        return true;
    }
}
