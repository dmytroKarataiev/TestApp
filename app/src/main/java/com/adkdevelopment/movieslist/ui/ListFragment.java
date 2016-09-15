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

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adkdevelopment.movieslist.R;
import com.adkdevelopment.movieslist.data.remote.Movie;
import com.adkdevelopment.movieslist.databinding.FragmentMainBinding;
import com.adkdevelopment.movieslist.ui.adapters.MovieAdapter;
import com.adkdevelopment.movieslist.ui.base.BaseFragment;
import com.adkdevelopment.movieslist.ui.behavior.TouchHelperCallback;
import com.adkdevelopment.movieslist.ui.contracts.ListContract;
import com.adkdevelopment.movieslist.ui.interfaces.ItemClickListener;
import com.adkdevelopment.movieslist.ui.presenters.ListPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListFragment extends BaseFragment 
        implements ListContract.View, ItemClickListener<Movie, View> {

    public static final String TAG = ListFragment.class.getSimpleName();

    private ListPresenter mPresenter;
    private MovieAdapter mAdapter;

    // TODO: 9/15/16 add fragment listener

    // Due to using Presenter - we have to save position of a RecyclerView manually
    // that's a hacky way, in the production we should retain whole presenter
    private static final String POSITION = "pos";
    private static final String MOVIES = "movies";
    private int mPosition = 0;
    private List<Movie> mMovies;

    // view via databinding
    private RecyclerView mRecyclerView;

    // swipe to delete
    private ItemTouchHelper mItemTouchHelper;

    public ListFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListFragment.
     */
    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentMainBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);

        View view = binding.getRoot();

        mRecyclerView = binding.recyclerview;

        mAdapter = new MovieAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // add swipe to delete
        ItemTouchHelper.Callback callback = new TouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        mPresenter = new ListPresenter();
        mPresenter.attachView(this);

        // prevent re-downloading of movies on each rotation
        if (savedInstanceState == null) {
            mPresenter.fetchData();
        } else {
            mMovies = savedInstanceState.getParcelableArrayList(MOVIES);
            if (mMovies != null) {
                showData(mMovies);
            } else {
                mPresenter.fetchData();
            }
        }

        return view;
    }

    @Override
    public void showData(List<Movie> movieList) {
        mMovies = movieList;
        mAdapter.setTasks(movieList, this);
        mRecyclerView.scrollToPosition(mPosition);
    }

    @Override
    public void showEmpty() {
        mAdapter.setTasks(null, null);
    }

    @Override
    public void showError() {
        Log.e(TAG, "showError: ");
    }

    @Override
    public void showProgress(boolean isInProgress) {
        // TODO: 9/15/16 add progress 
    }

    @Override
    public void onItemClicked(Movie item, View view) {
        // TODO: 9/15/16 add another interface
        Log.d(TAG, "onItemClicked: " + item.getTitle());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
        // TODO: 9/15/16 add interface for activity
        // mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                .findFirstVisibleItemPosition();
        outState.putInt(POSITION, mPosition);
        outState.putParcelableArrayList(MOVIES, (ArrayList<Movie>) mMovies);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mPosition = savedInstanceState.getInt(POSITION);
        }
    }

}
