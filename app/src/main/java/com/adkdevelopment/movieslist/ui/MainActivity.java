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

package com.adkdevelopment.movieslist.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Pair;
import android.view.View;

import com.adkdevelopment.movieslist.R;
import com.adkdevelopment.movieslist.data.remote.Movie;
import com.adkdevelopment.movieslist.databinding.ActivityMainBinding;
import com.adkdevelopment.movieslist.ui.base.BaseActivity;
import com.adkdevelopment.movieslist.ui.contracts.MainContract;
import com.adkdevelopment.movieslist.ui.interfaces.FragmentListener;

/**
 * Main Activity of the app.
 */
public class MainActivity extends BaseActivity
        implements MainContract.View, FragmentListener {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initActionBar();
    }

    /**
     * Performs all preparation procedures to initialize Toolbar and ActionBar
     */
    private void initActionBar() {
        // Initialize a custom Toolbar
        setSupportActionBar(mBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();

        // Add back button to the actionbar
        if (actionBar != null) {
            actionBar.setLogo(R.drawable.logo_title);
        }
    }

    @Override
    public void onFragmentInteraction(Movie movie, View view) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Movie.MOVIE_EXTRA, movie);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Pair poster = Pair.create(view.findViewById(R.id.movieitem_image),
                    view.findViewById(R.id.movieitem_image).getTransitionName());
            Pair title = Pair.create(view.findViewById(R.id.movieitem_title),
                    view.findViewById(R.id.movieitem_title).getTransitionName());
            Pair overview = Pair.create(view.findViewById(R.id.movieitem_overview),
                    view.findViewById(R.id.movieitem_overview).getTransitionName());
            Pair rating = Pair.create(view.findViewById(R.id.movieitem_rating),
                    view.findViewById(R.id.movieitem_rating).getTransitionName());
            Pair card = Pair.create(view.findViewById(R.id.movieitem_card),
                    view.findViewById(R.id.movieitem_card).getTransitionName());
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this,
                    poster, title, overview, rating, card).toBundle();

            startActivity(intent, bundle);
        } else {
            startActivity(intent);
        }
    }
}
