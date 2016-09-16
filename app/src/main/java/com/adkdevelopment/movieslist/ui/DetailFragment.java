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

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.adkdevelopment.movieslist.R;
import com.adkdevelopment.movieslist.data.remote.Movie;
import com.adkdevelopment.movieslist.databinding.FragmentDetailBinding;
import com.adkdevelopment.movieslist.ui.base.BaseFragment;
import com.adkdevelopment.movieslist.ui.contracts.DetailContract;
import com.adkdevelopment.movieslist.ui.presenters.DetailPresenter;
import com.squareup.picasso.Picasso;

/**
 * Fragment with details about a movie.
 */
public class DetailFragment extends BaseFragment
        implements DetailContract.View {

    private static final String TAG = DetailFragment.class.getSimpleName();

    private DetailPresenter mPresenter;
    private FragmentDetailBinding mBinding;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);

        View view = mBinding.getRoot();

        mPresenter = new DetailPresenter();
        mPresenter.attachView(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.loadData(getActivity().getIntent());
    }

    @Override
    public void showData(Movie movie) {
        mBinding.setMovie(movie);
    }

    @Override
    public void showError() {
        Log.d(TAG, "Error");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String v) {
        // TODO: 9/15/16 add progress bar && error image
        String path = Movie.PATH;
        if (imageView.getId() == R.id.backdrop) {
            path += Movie.HIGH_DIM;
        } else {
            path += Movie.LOW_DIM;
        }
        Picasso.with(imageView.getContext()).load(path + v).into(imageView);

    }

}
