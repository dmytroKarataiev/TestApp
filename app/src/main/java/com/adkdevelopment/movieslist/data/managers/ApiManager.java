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

package com.adkdevelopment.movieslist.data.managers;

import com.adkdevelopment.movieslist.data.services.MovieService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Manager to perform all REST-related work.
 * Created by karataev on 9/15/16.
 */
public class ApiManager {

    private Retrofit MOVIE_ADAPTER;
    private MovieService MOVIE_SERVICE;

    public MovieService getMoviesService() {
        return MOVIE_SERVICE;
    }

    public void init() {
        initRetrofit();
        initService();
    }

    /**
     * Initialises Retrofit with a BASE_URL, XML converted and Rx adapter.
     */
    private void initRetrofit() {

        final String BASE_URL = "http://api.themoviedb.org/3/";

        MOVIE_ADAPTER = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private void initService() {
        MOVIE_SERVICE = MOVIE_ADAPTER.create(MovieService.class);
    }

    public void clear() {
        MOVIE_ADAPTER = null;
        MOVIE_SERVICE = null;
    }

}
